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

package org.omg.dds.core.status;

import org.omg.dds.core.InstanceHandle;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.sub.DataReader;
import org.omg.dds.topic.Topic;


/**
 * The {@link DataWriter} has found a {@link DataReader} that matches the
 * {@link Topic} and has compatible QoS, or has ceased to be matched with a
 * DataReader that was previously considered to be matched.
 *
 * @see PublicationMatchedEvent
 * @see SubscriptionMatchedStatus
 */
public abstract class PublicationMatchedStatus extends Status
{
    // -----------------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------------

    private static final long serialVersionUID = 275828934444687975L;



    // -----------------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------------

    /**
     * Total cumulative count the concerned {@link DataWriter} discovered a
     * "match" with a {@link DataReader}. That is, it found a DataReader for
     * the same {@link Topic} with a requested QoS that is compatible with
     * that offered by the DataWriter.
     */
    public abstract int getTotalCount();

    /**
     * The change in totalCcount since the last time the listener was called
     * or the status was read.
     */
    public abstract int getTotalCountChange();

    /**
     * The number of {@link DataReader}s currently matched to the concerned
     * {@link DataWriter}.
     */
    public abstract int getCurrentCount();

    /**
     * The change in currentCount since the last time the listener was called
     * or the status was read.
     */
    public abstract int getCurrentCountChange();

    /**
     * Handle to the last {@link DataReader} that matched the
     * {@link DataWriter}, causing the status to change.
     */
    public abstract InstanceHandle getLastSubscriptionHandle();
}
