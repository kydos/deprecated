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

package org.omg.dds.core;


/**
 * A GuardCondition object is a specific Condition whose triggerValue is
 * completely under the control of the application. When it is first created,
 * the triggerValue is set to false.
 * 
 * The purpose of the GuardCondition is to provide the means for the
 * application to manually wake up a {@link WaitSet}. This is accomplished by
 * attaching the GuardCondition to the WaitSet and then setting the
 * triggerValue by means of the {@link #setTriggerValue(boolean)} operation.
 */
public abstract class GuardCondition implements Condition
{
    // -----------------------------------------------------------------------
    // Factory Methods
    // -----------------------------------------------------------------------

    /**
     * @param env       Identifies the Service instance to which the new
     *                  object will belong.
     */
    public static GuardCondition newGuardCondition(ServiceEnvironment env)
    {
        return env.getSPI().newGuardCondition();
    }



    // -----------------------------------------------------------------------
    // Instance Methods
    // -----------------------------------------------------------------------

    /**
     * This operation sets the triggerValue of the GuardCondition.
     * 
     * {@link WaitSet} objects' behavior depends on the changes of the
     * triggerValue of their attached conditions. Therefore, any WaitSet
     * to which the GuardCondition is attached is potentially affected by
     * this operation.
     * 
     * @see     Condition#getTriggerValue()
     */
    public abstract void setTriggerValue(boolean value);
}
