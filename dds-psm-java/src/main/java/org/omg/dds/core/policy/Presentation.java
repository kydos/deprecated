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

import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.Publisher;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.Subscriber;


/**
 * Specifies how the samples representing changes to data instances are
 * presented to the subscribing application. This policy affects the
 * application's ability to specify and receive coherent changes and to see
 * the relative order of changes. The accessScope determines the largest
 * scope spanning the entities for which the order and coherency of changes
 * can be preserved. The two booleans control whether coherent access and
 * ordered access are supported within the scope accessScope.
 * 
 * <b>Concerns:</b> {@link Publisher}, {@link Subscriber}
 * 
 * <b>RxO:</b> Yes
 * 
 * <b>Changeable:</b> No
 * 
 * This QoS policy controls the extent to which changes to data instances can
 * be made dependent on each other and also the kind of dependencies that can
 * be propagated and maintained by the Service.
 * 
 * The setting of coherentAccess controls whether the Service will
 * preserve the groupings of changes made by the publishing application
 * by means of the operations {@link Publisher#beginCoherentChanges()} and
 * {@link Publisher#endCoherentChanges()}.
 * 
 * The setting of orderedAccess controls whether the Service will preserve
 * the order of changes.
 * 
 * The granularity is controlled by the setting of the accessScope.
 * 
 * Note that this QoS policy controls the scope at which related changes are
 * made available to the subscriber. This means the subscriber <em>can</em>
 * access the changes in a coherent manner and in the proper order; however,
 * it does not necessarily imply that the Subscriber <em>will</em> indeed
 * access the changes in the correct order. For that to occur, the
 * application at the subscriber end must use the proper logic in reading the
 * DataReader objects.
 * 
 * The value offered is considered compatible with the value requested if and
 * only if the following conditions are met:
 * 
 * <ol>
 *      <li>The inequality "offered access_scope >= requested access_scope"
 *          evaluates to true. For the purposes of this inequality, the
 *          values of {@link Presentation.AccessScopeKind} are
 *          considered ordered such that INSTANCE < TOPIC < GROUP.</li>
 *      <li>Requested coherentAccess is false, or else both offered and
 *          requested coherentAccess are true.</li>
 *      <li>Requested orderedAccess is false, or else both offered and
 *          requested orderedAccess are true.</li>
 * </ol>
 */
public interface Presentation
extends QosPolicy.ForPublisher,
        QosPolicy.ForSubscriber,
        RequestedOffered<Presentation>
{
    // -----------------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------------

    /**
     * @return the accessScope
     */
    public AccessScopeKind getAccessScope();

    /**
     * If coherentAccess is set, then the accessScope controls the maximum
     * extent of coherent changes. The behavior is as follows:
     * 
     * <ul>
     *      <li>If accessAcope is set to
     *          {@link Presentation.AccessScopeKind#INSTANCE}, the
     *          use of {@link Publisher#beginCoherentChanges()} and
     *          {@link Publisher#endCoherentChanges()} has no effect on how
     *          the subscriber can access the data because with the scope
     *          limited to each instance, changes to separate instances are
     *          considered independent and thus cannot be grouped by a
     *          coherent change.
     *      <li>If accessScope is set to
     *          {@link Presentation.AccessScopeKind#TOPIC}, then
     *          coherent changes (indicated by their enclosure within calls to
     *          {@link Publisher#beginCoherentChanges()} and
     *          {@link Publisher#endCoherentChanges()}) will be made available
     *          as such to each remote {@link DataReader} independently. That
     *          is, changes made to instances within each individual
     *          {@link DataWriter} will be available as coherent with respect
     *          to other changes to instances in that same DataWriter, but
     *          will not be grouped with changes made to instances belonging
     *          to a different DataWriter.
     *      <li>If accessScope is set to
     *          {@link Presentation.AccessScopeKind#GROUP}, then
     *          coherent changes made to instances through a DataWriter
     *          attached to a common {@link Publisher} are made available as
     *          a unit to remote subscribers.</li>
     * </ul>
     * 
     * @see #getAccessScope()
     */
    public boolean isCoherentAccess();

    /**
     * If orderedAccess is set, then the accessScope controls the maximum
     * extent for which order will be preserved by the Service.
     * 
     * <ul>
     *  <li>If accessScope is set to
     *      {@link Presentation.AccessScopeKind#INSTANCE} (the
     *      lowest level), then changes to each instance are considered
     *      unordered relative to changes to any other instance. That means
     *      that changes (creations, deletions, modifications) made to two
     *      instances are not necessarily seen in the order they occur. This
     *      is the case even if it is the same application thread making the
     *      changes using the same {@link DataWriter}.</li>
     *  <li>If accessScope is set to
     *      {@link Presentation.AccessScopeKind#TOPIC}, changes
     *      (creations, deletions, modifications) made by a single
     *      {@link DataWriter} are made available to subscribers in the same
     *      order they occur. Changes made to instances through different
     *      DataWriter entities are not necessarily seen in the order they
     *      occur. This is the case, even if the changes are made by a single
     *      application thread using DataWriter objects attached to the same
     *      {@link Publisher}.</li>
     *  <li>Finally, if accessScope is set to
     *      {@link Presentation.AccessScopeKind#GROUP}, changes made
     *      to instances via DataWriter entities attached to the same
     *      Publisher object are made available to subscribers on the same
     *      order they occur.</li>
     * </ul>
     * 
     * @see #getAccessScope()
     */
    public boolean isOrderedAccess();


    // --- Modification: -----------------------------------------------------

    /**
     * Copy this policy and override the value of the property.
     * 
     * @return  a new policy
     */
    public Presentation withAccessScope(AccessScopeKind accessScope);

    /**
     * Copy this policy and override the value of the property.
     * 
     * @return  a new policy
     */
    public Presentation withCoherentAccess(boolean coherentAccess);

    /**
     * Copy this policy and override the value of the property.
     * 
     * @return  a new policy
     */
    public Presentation withOrderedAccess(boolean orderedAccess);

    public Presentation withInstance();
    
    public Presentation withTopic();
    
    public Presentation withGroup();

    // -----------------------------------------------------------------------
    // Types
    // -----------------------------------------------------------------------

    public enum AccessScopeKind {
        /**
         * Scope spans only a single instance. Indicates that changes to one
         * instance need not be coherent nor ordered with respect to changes
         * to any other instance. In other words, order and coherent changes
         * apply to each instance separately. This is the default accessScope.
         */
        INSTANCE,

        /**
         * Scope spans to all instances within the same {@link DataWriter}
         * (or {@link DataReader}), but not across instances in different
         * DataWriter (or DataReader).
         */
        TOPIC,

        /**
         * [optional] Scope spans to all instances belonging to
         * {@link DataWriter} (or {@link DataReader}) entities within the
         * same {@link Publisher} (or {@link Subscriber}).
         */
        GROUP
    }

}
