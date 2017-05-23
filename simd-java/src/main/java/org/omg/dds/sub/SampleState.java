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

import java.util.Set;


public enum SampleState {
    // -----------------------------------------------------------------------
    // States
    // -----------------------------------------------------------------------

    READ(0x0001 << 0),
    NOT_READ(0x0001 << 1),
    ANY(0x0011);



    // -----------------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------------

    public final int value;



    // -----------------------------------------------------------------------
    // Object Life Cycle
    // -----------------------------------------------------------------------

    /**
     * @param bootstrap Identifies the Service instance to which the
     *                  object will belong.
     */
    /*
    public static Set<SampleState> anySampleStateSet(Bootstrap bootstrap) {
        return bootstrap.getSPI().anySampleStateSet();
    }
      */

    // -----------------------------------------------------------------------

    private SampleState(int value) {
        this.value = value;
    }

}
