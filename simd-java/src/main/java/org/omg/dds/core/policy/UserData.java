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

package org.omg.dds.core.policy;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * User data not known by the middleware, but distributed by means of built-in
 * topics. The default value is an empty (zero-sized) sequence.
 * 
 * <b>Concerns:</b> {@link org.omg.dds.domain.DomainParticipant}, {@link org.omg.dds.sub.DataReader},
 * {@link org.omg.dds.pub.DataWriter}
 * 
 * <b>RxO:</b> No
 * 
 * <b>Changeable:</b> Yes
 * 
 * The purpose of this QoS is to allow the application to attach additional
 * information to the created {@link org.omg.dds.core.Entity} objects such that when a remote
 * application discovers their existence it can access that information and use
 * it for its own purposes. One possible use of this QoS is to attach security
 * credentials or some other information that can be used by the remote
 * application to authenticate the source. In combination with operations such
 * as
 * {@link org.omg.dds.domain.DomainParticipant#ignoreParticipant(org.omg.dds.core.InstanceHandle)},
 * {@link org.omg.dds.domain.DomainParticipant#ignorePublication(org.omg.dds.core.InstanceHandle)},
 * {@link org.omg.dds.domain.DomainParticipant#ignoreSubscription(org.omg.dds.core.InstanceHandle)}
 * , and {@link org.omg.dds.domain.DomainParticipant#ignoreTopic(org.omg.dds.core.InstanceHandle)}
 * these QoS can assist an application to define and enforce its own security
 * policies. The use of this QoS is not limited to security, rather it offers a
 * simple, yet flexible extensibility mechanism.
 */
public class UserData implements QosPolicy {
	
    static final long serialVersionUID = 4791687692148780088L;
    
    public static final int ID = 1;
    private static final String NAME = "UserData";
    private static final UserData EMPTY_USER_DATA = new UserData();
    private final byte value[];

    public UserData() {
        this.value = new byte[0];
    }

    public UserData(byte value[]) {
        this.value = new byte[value.length];
        System.arraycopy(value, 0, this.value, 0, value.length);
    }

    /**
     * Copy the data into the given array, starting at the index at the given
     * offset.
     * 
     * @return The total number of bytes in the data, independent of the number
     *         of bytes copied. Callers can use this result to determine if the
     *         output array is long enough or, if it is long enough, what range
     *         within it contains valid data.
     */
    public byte[] getValue() {
        return this.value.clone();
    }

    public byte valueAt(int i) {
        return value[i];
    }



    /**
     * @return the length of the <code>value</code> property.
     */
    public int getLength() {
        return this.value.length;
    }

    public int getPolicyId() {
        return ID;
    }

    public String getPolicyName() {
        return NAME;
    }

    public UserData EmptyUserData() {
        return EMPTY_USER_DATA;
    }
}
