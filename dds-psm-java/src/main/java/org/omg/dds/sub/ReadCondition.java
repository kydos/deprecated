/* Copyright 2010, Object Management Group, Inc.
 * Copyright 2010, PrismTech, Inc.
 * Copyright 2010, Real-Time Innovations, Inc.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.omg.dds.sub;

import java.io.Closeable;
import java.util.Set;

import org.omg.dds.core.Condition;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.WaitSet;


/**
 * ReadCondition objects are conditions specifically dedicated to read
 * operations and attached to one {@link DataReader}.
 * 
 * ReadCondition objects allow an application to specify the data samples it
 * is interested in by specifying the desired sample states, view states,
 * and instance states. (See {@link Subscriber.DataState}.)
 * This allows the middleware to enable the condition only when suitable
 * information is available. They are to be used in conjunction with a
 * {@link WaitSet} as normal conditions. More than one ReadCondition may be
 * attached to the same DataReader.
 * 
 * Similar to the {@link StatusCondition}, a ReadCondition also has a
 * triggerValue that determines whether the attached {@link WaitSet} is
 * BLOCKED or UNBLOCKED. However, unlike the StatusCondition, the
 * triggerValue of the ReadCondition is tied to the presence of at least a
 * sample managed by the Service with {@link SampleState}, {@link ViewState},
 * and {@link InstanceState} matching those of the ReadCondition.
 * 
 * The fact that the triggerValue of a ReadCondition is dependent on the
 * presence of samples on the associated DataReader implies that a single
 * {@link DataReader#take()} operation can potentially change the
 * triggerValue of several ReadCondition or {@link QueryCondition}
 * conditions. For example, if all samples are taken, any ReadCondition and
 * QueryCondition conditions associated with the DataReader that had their
 * triggerValue == true before will see the triggerValue change to false.
 * Note that this does not guarantee that WaitSet objects that were
 * separately attached to those conditions will not be woken up. Once we have
 * triggerValue == true on a condition it may wake up the attached WaitSet,
 * the condition transitioning to triggerValue == false does not necessarily
 * 'unwakeup' the WaitSet as 'unwakening' may not be possible in general. The
 * consequence is that an application blocked on a WaitSet may return from
 * the wait with a list of conditions. some of which are no longer "active."
 * This is unavoidable if multiple threads are concurrently waiting on
 * separate WaitSet objects and taking data associated with the same
 * DataReader entity.
 * 
 * To elaborate further, consider the following example: A ReadCondition that
 * has a sample state collection of {NOT_READ} will have triggerValue of true
 * whenever a new sample arrives and will transition to false as soon as all
 * the newly-arrived samples are either read (so their status changes to
 * {@link SampleState#READ}) or taken (so they are no longer managed by the
 * Service). However if the same ReadCondition had a sample_statesample state
 * collection of {READ, NOT_READ}, then the triggerValue would only become
 * false once all the newly-arrived samples are taken (it is not sufficient
 * to read them as that would only change the SampleState to READ, which
 * overlaps the collection on the ReadCondition.
 * 
 * @param <TYPE>    The concrete type of the data that can be read using the
 *                  the {@link DataReader} that created this ReadCondition.
 */
public interface ReadCondition<TYPE> extends Closeable, Condition
{
    /**
     * This operation returns the set of sample states that are taken into
     * account to determine the triggerValue of the ReadCondition. These are
     * the sample states specified when the ReadCondition was created.
     * 
     * @return  an unmodifiable set.
     */
    public Set<SampleState> getSampleStates();

    /**
     * This operation returns the set of view states that are taken into
     * account to determine the triggerValue of the ReadCondition. These are
     * the view states specified when the ReadCondition was created.
     * 
     * @return  an unmodifiable set.
     */
    public Set<ViewState> getViewStates();

    /**
     * This operation returns the set of instance states that are taken into
     * account to determine the triggerValue of the ReadCondition. These are
     * the instance states specified when the ReadCondition was created.
     * 
     * @return  an unmodifiable set.
     */
    public Set<InstanceState> getInstanceStates();

    /**
     * @return  the DataReader associated with the ReadCondition. Note that
     *          there is exactly one DataReader associated with each
     *          ReadCondition.
     */
    public DataReader<TYPE> getParent();

    /**
     * Reclaim any resources associated with this condition.
     */
    public void close();
}
