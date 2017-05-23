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
 * Specifies how the samples representing changes to data instances are
 * presented to the subscribing application. This policy affects the
 * application's ability to specify and receive coherent changes and to see the
 * relative order of changes. The accessScope determines the largest scope
 * spanning the entities for which the order and coherency of changes can be
 * preserved. The two booleans control whether coherent access and ordered
 * access are supported within the scope accessScope.
 * 
 * <b>Concerns:</b> {@link org.omg.dds.pub.Publisher}, {@link org.omg.dds.sub.Subscriber}
 * 
 * <b>RxO:</b> Yes
 * 
 * <b>Changeable:</b> No
 * 
 * This QoS policy controls the extent to which changes to data instances can be
 * made dependent on each other and also the kind of dependencies that can be
 * propagated and maintained by the Service.
 * 
 * The setting of coherentAccess controls whether the Service will preserve the
 * groupings of changes made by the publishing application by means of the
 * operations {@link org.omg.dds.pub.Publisher#beginCoherentChanges()} and
 * {@link org.omg.dds.pub.Publisher#endCoherentChanges()}.
 * 
 * The setting of orderedAccess controls whether the Service will preserve the
 * order of changes.
 * 
 * The granularity is controlled by the setting of the accessScope.
 * 
 * Note that this QoS policy controls the scope at which related changes are
 * made available to the subscriber. This means the subscriber <em>can</em>
 * access the changes in a coherent manner and in the proper order; however, it
 * does not necessarily imply that the Subscriber <em>will</em> indeed access
 * the changes in the correct order. For that to occur, the application at the
 * subscriber end must use the proper logic in reading the DataReader objects.
 * 
 * The value offered is considered compatible with the value requested if and
 * only if the following conditions are met:
 * 
 * <ol>
 * <li>The inequality "offered access_scope >= requested access_scope" evaluates
 * to true. For the purposes of this inequality, the values of
 * {@link Presentation.AccessScopeKind} are considered ordered such that
 * INSTANCE < TOPIC < GROUP.</li>
 * <li>Requested coherentAccess is false, or else both offered and requested
 * coherentAccess are true.</li>
 * <li>Requested orderedAccess is false, or else both offered and requested
 * orderedAccess are true.</li>
 * </ol>
 */
public class Presentation implements QosPolicy {

	private static final long serialVersionUID = 5431219018741643415L;

	// -- Constant Members
    public static final int ID = 16;
    private final static String NAME = "Presentation";

    final private AccessScopeKind accessScope;
    final private boolean orderedAccess;
    final private boolean coherentAccess;

    // -----------------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------------

    /**
     * @return the accessScope
     */
    public AccessScopeKind getAccessScope() {
        return accessScope;
    }

    /**
     * If coherentAccess is set, then the accessScope controls the maximum
     * extent of coherent changes. The behavior is as follows:
     * 
     * <ul>
     * <li>If accessAcope is set to
     * {@link Presentation.AccessScopeKind#INSTANCE}, the use of
     * {@link org.omg.dds.pub.Publisher#beginCoherentChanges()} and
     * {@link org.omg.dds.pub.Publisher#endCoherentChanges()} has no effect on how the
     * subscriber can access the data because with the scope limited to each
     * instance, changes to separate instances are considered independent and
     * thus cannot be grouped by a coherent change.
     * <li>If accessScope is set to {@link Presentation.AccessScopeKind#TOPIC},
     * then coherent changes (indicated by their enclosure within calls to
     * {@link org.omg.dds.pub.Publisher#beginCoherentChanges()} and
     * {@link org.omg.dds.pub.Publisher#endCoherentChanges()}) will be made available as such to
     * each remote {@link org.omg.dds.sub.DataReader} independently. That is, changes made to
     * instances within each individual {@link org.omg.dds.pub.DataWriter} will be available as
     * coherent with respect to other changes to instances in that same
     * DataWriter, but will not be grouped with changes made to instances
     * belonging to a different DataWriter.
     * <li>If accessScope is set to {@link Presentation.AccessScopeKind#GROUP},
     * then coherent changes made to instances through a DataWriter attached to
     * a common {@link org.omg.dds.pub.Publisher} are made available as a unit to remote
     * subscribers.</li>
     * </ul>
     * 
     * @see #getAccessScope()
     */
    public boolean isCoherentAccess() {
        return coherentAccess;
    }

    /**
     * If orderedAccess is set, then the accessScope controls the maximum extent
     * for which order will be preserved by the Service.
     * 
     * <ul>
     * <li>If accessScope is set to
     * {@link Presentation.AccessScopeKind#INSTANCE} (the lowest level), then
     * changes to each instance are considered unordered relative to changes to
     * any other instance. That means that changes (creations, deletions,
     * modifications) made to two instances are not necessarily seen in the
     * order they occur. This is the case even if it is the same application
     * thread making the changes using the same {@link org.omg.dds.pub.DataWriter}.</li>
     * <li>If accessScope is set to {@link Presentation.AccessScopeKind#TOPIC},
     * changes (creations, deletions, modifications) made by a single
     * {@link org.omg.dds.pub.DataWriter} are made available to subscribers in the same order
     * they occur. Changes made to instances through different DataWriter
     * entities are not necessarily seen in the order they occur. This is the
     * case, even if the changes are made by a single application thread using
     * DataWriter objects attached to the same {@link org.omg.dds.pub.Publisher}.</li>
     * <li>Finally, if accessScope is set to
     * {@link Presentation.AccessScopeKind#GROUP}, changes made to instances via
     * DataWriter entities attached to the same Publisher object are made
     * available to subscribers on the same order they occur.</li>
     * </ul>
     * 
     * @see #getAccessScope()
     */
    public boolean isOrderedAccess() {
        return orderedAccess;
    }

    // -----------------------------------------------------------------------
    // Types
    // -----------------------------------------------------------------------

    public enum AccessScopeKind {
        /**
         * Scope spans only a single instance. Indicates that changes to one
         * instance need not be coherent nor ordered with respect to changes to
         * any other instance. In other words, order and coherent changes apply
         * to each instance separately. This is the default accessScope.
         */
        INSTANCE,

        /**
         * Scope spans to all instances within the same {@link org.omg.dds.pub.DataWriter} (or
         * {@link org.omg.dds.sub.DataReader}), but not across instances in different DataWriter
         * (or DataReader).
         */
        TOPIC,

        /**
         * [optional] Scope spans to all instances belonging to
         * {@link org.omg.dds.pub.DataWriter} (or {@link org.omg.dds.sub.DataReader}) entities within the same
         * {@link org.omg.dds.pub.Publisher} (or {@link org.omg.dds.sub.Subscriber}).
         */
        GROUP
    }

    public Presentation(AccessScopeKind accessScope, boolean orderedAccess,
            boolean coherentAccess) {
        this.accessScope = accessScope;
        this.orderedAccess = orderedAccess;
        this.coherentAccess = coherentAccess;
    }

    public int getPolicyId() {
        return ID;
    }

    public String getPolicyName() {
        return NAME;
    }

}
