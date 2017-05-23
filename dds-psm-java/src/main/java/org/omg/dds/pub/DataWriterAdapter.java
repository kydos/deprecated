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

package org.omg.dds.pub;

import org.omg.dds.core.status.LivelinessLostEvent;
import org.omg.dds.core.status.OfferedDeadlineMissedEvent;
import org.omg.dds.core.status.OfferedIncompatibleQosEvent;
import org.omg.dds.core.status.PublicationMatchedEvent;


public class DataWriterAdapter<TYPE> implements DataWriterListener<TYPE> {
    public void onLivelinessLost(LivelinessLostEvent<TYPE> status)
    {
        // empty
    }

    public void onOfferedDeadlineMissed(
            OfferedDeadlineMissedEvent<TYPE> status)
    {
        // empty
    }

    public void onOfferedIncompatibleQos(
            OfferedIncompatibleQosEvent<TYPE> status)
    {
        // empty
    }

    public void onPublicationMatched(PublicationMatchedEvent<TYPE> status)
    {
        // empty
    }
}
