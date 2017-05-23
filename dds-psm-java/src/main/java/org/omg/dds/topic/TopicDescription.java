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

package org.omg.dds.topic;

import java.io.Closeable;

import org.omg.dds.core.DDSObject;
import org.omg.dds.core.Entity;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.sub.DataReader;
import org.omg.dds.type.TypeSupport;


/**
 * This interface is the base for {@link Topic}, {@link ContentFilteredTopic},
 * and {@link MultiTopic}.
 * 
 * TopicDescription represents the fact that both publications and
 * subscriptions are tied to a single data type. Its attribute typeName
 * defines a unique resulting type for the publication or the subscription
 * and therefore creates an implicit association with a {@link TypeSupport}.
 * TopicDescription has also a name that allows it to be retrieved locally.
 *
 * @param <TYPE>    The concrete type of the data that will be published and/
 *                  or subscribed by the readers and writers that use this
 *                  topic description.
 */
public interface TopicDescription<TYPE> extends Closeable, DDSObject
{
    /**
     * @return  the {@link TypeSupport} used to create this TopicDescription.
     */
    public TypeSupport<TYPE> getTypeSupport();

    /**
     * Cast this topic description to the given type, or throw an exception if
     * the cast fails.
     * 
     * @param <OTHER>   The type of the data exchanged on this topic,
     *                  according to the caller.
     * @return          this topic description
     * @throws          ClassCastException if the cast fails
     */
    public <OTHER> TopicDescription<OTHER> cast();

    /**
     * @return  the type name used to create the TopicDescription.
     */
    public String getTypeName();

    /**
     * @return  the name used to create the TopicDescription.
     */
    public String getName();

    /**
     * @return  the {@link DomainParticipant} to which the TopicDescription
     *          belongs.
     */
    public DomainParticipant getParent();

    /**
     * Dispose the resources held by this object.
     * 
     * A TopicDescription cannot be closed if it is in use by any
     * {@link DataWriter}s or {@link DataReader}s. With respect to
     * {@link Topic}s specifically: a Topic cannot be closed if it has any
     * remaining {@link ContentFilteredTopic}s or {@link MultiTopic}s related
     * to it.
     * 
     * @see     Entity#close()
     */
    public void close();
}
