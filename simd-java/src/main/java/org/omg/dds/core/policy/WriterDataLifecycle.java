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
 * Specifies the behavior of the {@link org.omg.dds.pub.DataWriter} with regards to the life
 * cycle of the data instances it manages.
 * 
 * <b>Concerns:</b> {@link org.omg.dds.pub.DataWriter}
 * 
 * <b>RxO:</b> N/A
 * 
 * <b>Changeable:</b> Yes
 * 
 * This policy controls the behavior of the DataWriter with regards to the
 * lifecycle of the data instances it manages, that is, the data instances that
 * have been either explicitly registered with the DataWriter using the
 * {@link org.omg.dds.pub.DataWriter#registerInstance(Object)} operations or implicitly by
 * directly writing the data (see {@link org.omg.dds.pub.DataWriter#write(Object)}).
 * 
 * The autodisposeUnregisteredInstances flag controls the behavior when the
 * DataWriter unregisters an instance by means of the
 * {@link org.omg.dds.pub.DataWriter#unregisterInstance(org.omg.dds.core.InstanceHandle)}
 * operations:
 * 
 * <ul>
 * <li>The setting "autodisposeUnregisteredInstances = true' causes the
 * DataWriter to dispose the instance each time it is unregistered. The behavior
 * is identical to explicitly calling one of the
 * {@link org.omg.dds.pub.DataWriter#dispose(org.omg.dds.core.InstanceHandle)} operations on the
 * instance prior to calling the unregister operation.</li>
 * 
 * <li>The setting 'autodisposeUnregisteredInstances = false' will not cause
 * this automatic disposition upon unregistering. The application can still call
 * one of the dispose operations prior to unregistering the instance and
 * accomplish the same effect. Refer to Section 7.1.3.23.3 of the DDS
 * specification, "Semantic difference between unregister_instance and dispose",
 * for a description of the consequences of disposing and unregistering
 * instances.</li>
 * </ul>
 * 
 * Note that the deletion of a DataWriter automatically unregisters all data
 * instances it manages (see {@link org.omg.dds.pub.DataWriter#close()}). Therefore the setting
 * of the autodisposeUnregisteredInstances flag will determine whether instances
 * are ultimately disposed when the DataWriter is deleted either directly by
 * means of the {@link org.omg.dds.pub.DataWriter#close()} operation or indirectly as a
 * consequence of calling {@link org.omg.dds.pub.Publisher#closeContainedEntities()} or
 * {@link org.omg.dds.domain.DomainParticipant#closeContainedEntities()}.
 */
public class WriterDataLifecycle implements QosPolicy {


	private static final long serialVersionUID = 7452968616794253527L;
	
	public static final int ID = 17;
    private static final String NAME = "WriterDataLifecycle";
    private static WriterDataLifecycle AUT_DISPOSE_UNREGISTERED_INSTANCES = new WriterDataLifecycle(
            true);
    private static WriterDataLifecycle NOT_AUT_DISPOSE_UNREGISTERED_INSTANCES = new WriterDataLifecycle(
            false);

    //TODO: Add durations for controlling disposal
    private final boolean autDisposeUnregisteredInstances;

    /**
     * @return the autDisposeUnregisteredInstances
     */
    public boolean isAutDisposeUnregisteredInstances() {
        return autDisposeUnregisteredInstances;
    }

    private WriterDataLifecycle(boolean autDisposeUnregisteredInstances) {
        this.autDisposeUnregisteredInstances = autDisposeUnregisteredInstances;
    }

    public static WriterDataLifecycle AutDisposeUnregisterdInstances() {
        return AUT_DISPOSE_UNREGISTERED_INSTANCES;
    }

    public static WriterDataLifecycle NotAutDisposeUnregisterdInstance() {
        return NOT_AUT_DISPOSE_UNREGISTERED_INSTANCES;
    }

    public int getPolicyId() {
        return ID;
    }

    public String getPolicyName() {
        return NAME;
    }

}
