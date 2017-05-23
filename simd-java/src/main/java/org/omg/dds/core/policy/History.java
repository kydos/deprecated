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

/**
 * Specifies the behavior of the Service in the case where the value of a sample
 * changes (one or more times) before it can be successfully communicated to one
 * or more existing subscribers. This QoS policy controls whether the Service
 * should deliver only the most recent value, attempt to deliver all
 * intermediate values, or do something in between. On the publishing side this
 * policy controls the samples that should be maintained by the DataWriter on
 * behalf of existing DataReader entities. The behavior with regards to a
 * DataReader entities discovered after a sample is written is controlled by the
 * {@link Durability}. On the subscribing side it controls the samples that
 * should be maintained until the application "takes" them from the Service via
 * {@link org.omg.dds.sub.DataReader#take()}.
 * 
 * <b>Concerns:</b> {@link org.omg.dds.topic.Topic}, {@link org.omg.dds.sub.DataReader}, {@link org.omg.dds.pub.DataWriter}
 * 
 * <b>RxO:</b> No
 * 
 * <b>Changeable:</b> No
 * 
 * <ol>
 * <li>This policy controls the behavior of the Service when the value of an
 * instance changes before it is finally communicated to some of its existing
 * DataReader entities.</li>
 * 
 * <li>If the kind is set to {@link Kind#KEEP_LAST}, then the Service will only
 * attempt to keep the latest values of the instance and discard the older ones.
 * In this case, the value of depth regulates the maximum number of values (up
 * to and including the most current one) the Service will maintain and deliver.
 * The default (and most common setting) for depth is one, indicating that only
 * the most recent value should be delivered.</li>
 * 
 * <li>If the kind is set to {@link Kind#KEEP_ALL}, then the Service will
 * attempt to maintain and deliver all the values of the instance to existing
 * subscribers. The resources that the Service can use to keep this history are
 * limited by the settings of the {@link ResourceLimits}. If the limit is
 * reached, then the behavior of the Service will depend on the
 * {@link Reliability}. If the reliability kind is
 * {@link Reliability.Kind#BEST_EFFORT}, then the old values will be discarded.
 * If reliability is {@link Reliability.Kind#RELIABLE}, then the Service will
 * block the DataWriter until it can deliver the necessary old values to all
 * subscribers.</li>
 * </ol>
 * 
 * The setting of HISTORY depth must be consistent with the RESOURCE_LIMITS
 * maxSamplesPerInstance. For these two QoS to be consistent, they must verify
 * that depth &lt;= maxSamplesPerInstance.
 * 
 * @see Reliability
 * @see ResourceLimits
 */
public class History implements QosPolicy {

	private static final long serialVersionUID = -5053820784464142641L;
	
	public static final int ID = 13;
    public static final String NAME = "History";
    public static final History KEEP_LAST = new History(Kind.KEEP_LAST, 1);
    public static final History KEEP_ALL = new History(Kind.KEEP_ALL);

    final private Kind kind;
    final private int depth;

    public enum Kind {
        /**
         * On the publishing side, the Service will only attempt to keep the
         * most recent "depth" samples ({@link History#getDepth()}) of each
         * instance of data (identified by its key) managed by the
         * {@link org.omg.dds.pub.DataWriter}. On the subscribing side, the DataReader will only
         * attempt to keep the most recent "depth" samples received for each
         * instance (identified by its key) until the application "takes" them
         * via {@link org.omg.dds.sub.DataReader#take()}. KEEP_LAST is the default kind. The
         * default value of depth is 1. If a value other than 1 is specified, it
         * must be consistent with the settings of the {@link ResourceLimits}.
         */
        KEEP_LAST,

        /**
         * On the publishing side, the Service will attempt to keep all samples
         * (representing each value written) of each instance of data
         * (identified by its key) managed by the {@link org.omg.dds.pub.DataWriter} until they
         * can be delivered to all subscribers. On the subscribing side, the
         * Service will attempt to keep all samples of each instance of data
         * (identified by its key) managed by the {@link org.omg.dds.sub.DataReader}. These
         * samples are kept until the application "takes" them from the Service
         * via {@link org.omg.dds.sub.DataReader#take()}. The setting of depth has no effect.
         * Its implied value is {@link ResourceLimits#LENGTH_UNLIMITED}.
         */
        KEEP_ALL
    }

    private History(Kind kind) {
        this.kind = kind;
        this.depth = 1;
    }

    private History(Kind kind, int depth) {
        this.kind = kind;
        this.depth = depth;
    }

    // -----------------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------------

    /**
     * @return the kind
     */
    public Kind getKind() {
        return this.kind;
    }

    /**
     * @return the depth
     */
    public int getDepth() {
        return this.depth;
    }

    public static History KeepLast() {
        return History.KEEP_LAST;
    }

    public static History KeepLast(int depth) {
        return new History(Kind.KEEP_LAST, depth);
    }

    public static History KeepAll() {
        return History.KEEP_ALL;
    }

    public int getPolicyId() {
        return ID;
    }

    public String getPolicyName() {
        return NAME;
    }

    // -----------------------------------------------------------------------
    // Types
    // -----------------------------------------------------------------------

}
