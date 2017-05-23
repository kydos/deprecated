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

import org.omg.dds.core.Duration;
import org.omg.dds.core.Time;

/**
 * Specifies the configuration of the durability service. That is, the service
 * that implements the {@link Durability.Kind} of
 * {@link Durability.Kind#TRANSIENT} and {@link Durability.Kind#PERSISTENT}.
 * 
 * <b>Concerns:</b> {@link org.omg.dds.topic.Topic}, {@link org.omg.dds.pub.DataWriter}
 * 
 * <b>RxO:</b> No
 * 
 * <b>Changeable:</b> No
 * 
 * This policy is used to configure the {@link History} and the
 * {@link ResourceLimits} used by the fictitious {@link org.omg.dds.sub.DataReader} and
 * {@link org.omg.dds.pub.DataWriter} used by the "persistence service." The "persistence
 * service" is the one responsible for implementing
 * {@link Durability.Kind#TRANSIENT} and {@link Durability.Kind#PERSISTENT}.
 * 
 * @see Durability
 */
public class DurabilityService implements QosPolicy {

	private static final long serialVersionUID = -2000671481342023181L;
	
	public static final int ID = 22;
    private static final String NAME = "DurabilityService";

    final private History history;
    final private Duration cleanupDelay;
    final private int maxSamples;
    final private int maxInstances;
    final private int maxSamplesPerInstance;


    /** TODO create some more constructors with defaults. */
    public DurabilityService(History history, Duration cleanupDelay,
            int maxSamples, int maxInstances, int maxSamplesPerInstance) {
        this.history = history;
        this.cleanupDelay = cleanupDelay;
        this.maxSamples = maxSamples;
        this.maxInstances = maxInstances;
        this.maxSamplesPerInstance = maxSamplesPerInstance;
    }

    public Duration getServiceCleanupDelay() {
        return this.cleanupDelay;
    }

    /**
     * @return the history
     */
    public History getHistory() {
        return this.history;
    }

    /**
     * @return the maxSamples
     */
    public int getMaxSamples() {
        return this.maxSamples;
    }

    /**
     * @return the maxInstances
     */
    public int getMaxInstances() {
        return this.maxInstances;
    }

    /**
     * @return the maxSamplesPerInstance
     */
    public int getMaxSamplesPerInstance() {
        return this.maxSamplesPerInstance;
    }

    public int getPolicyId() {
        return ID;
    }

    public String getPolicyName() {
        return NAME;
    }

}
