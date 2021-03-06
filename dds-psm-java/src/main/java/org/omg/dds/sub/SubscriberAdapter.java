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

import org.omg.dds.core.status.DataAvailableEvent;
import org.omg.dds.core.status.DataOnReadersEvent;
import org.omg.dds.core.status.LivelinessChangedEvent;
import org.omg.dds.core.status.RequestedDeadlineMissedEvent;
import org.omg.dds.core.status.RequestedIncompatibleQosEvent;
import org.omg.dds.core.status.SampleLostEvent;
import org.omg.dds.core.status.SampleRejectedEvent;
import org.omg.dds.core.status.SubscriptionMatchedEvent;


public class SubscriberAdapter implements SubscriberListener
{
    public void onDataAvailable(DataAvailableEvent<?> status)
    {
        // empty
    }

    public void onLivelinessChanged(LivelinessChangedEvent<?> status)
    {
        // empty
    }

    public void onRequestedDeadlineMissed(
            RequestedDeadlineMissedEvent<?> status)
    {
        // empty
    }

    public void onRequestedIncompatibleQos(
            RequestedIncompatibleQosEvent<?> status)
    {
        // empty
    }

    public void onSampleLost(SampleLostEvent<?> status)
    {
        // empty
    }

    public void onSampleRejected(SampleRejectedEvent<?> status)
    {
        // empty
    }

    public void onSubscriptionMatched(SubscriptionMatchedEvent<?> status)
    {
        // empty
    }

    public void onDataOnReaders(DataOnReadersEvent status)
    {
        // empty
    }
}
