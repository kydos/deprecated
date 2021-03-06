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

import org.omg.dds.core.policy.*;
import org.omg.dds.core.qos.DataQos;
import org.omg.dds.runtime.DDSRuntime;
import org.omg.dds.runtime.DataWriterPolicyProvider;


public class DataWriterQos extends DataQos {

    private Lifespan            lifespan;
    private OwnershipStrength   ownershipStrength;
    private UserData            userData;
    private WriterDataLifecycle writerDataLifecycle;

    private void setDefaults() {
        DataWriterPolicyProvider provider = DDSRuntime.getInstance()
                        .getDataWriterPolicyProvider();

        this.writerDataLifecycle = provider.getWriterDataLifecycle();
        this.userData = provider.getUserData();
        this.ownershipStrength = provider.getOwnershipStrength();
        this.lifespan                       = provider.getLifespan();

    }

    public DataWriterQos(QosPolicy ... policies) {
        super(policies);
        this.setDefaults();
        for (QosPolicy p: policies) {
            switch (p.getPolicyId()) {
                case WriterDataLifecycle.ID:
                    this.writerDataLifecycle = (WriterDataLifecycle)p;
                    break;
                case UserData.ID:
                    this.userData = (UserData)p;
                    break;
                case OwnershipStrength.ID:
                    this.ownershipStrength = (OwnershipStrength)p;
                    break;
                case Lifespan.ID:
                    this.lifespan = (Lifespan)p;
                    break;
            }
        }
    }


    public DataWriterQos(Durability durability,
                         DurabilityService durabilityService,
                         Deadline deadline,
                         LatencyBudget latencyBudget,
                         Liveliness liveliness,
                         Reliability reliability,
                         DestinationOrder destinationOrder,
                         History history,
                         ResourceLimits resourceLimits,
                         TransportPriority transportPriority,
                         Lifespan lifespan,
                         Ownership ownership,
                         OwnershipStrength ownershipStrength,
                         DataRepresentation dataRepresentation,
                         TypeConsistencyEnforcement typeConsistencyEnforcement,
                         UserData userData,
                         WriterDataLifecycle writerDataLifecycle) {
        super(
                durability,
                durabilityService,
                deadline,
                latencyBudget,
                liveliness,
                reliability,
                destinationOrder,
                history,
                resourceLimits,
                transportPriority,
                ownership,
               // dataRepresentation,
                typeConsistencyEnforcement);
        this.ownershipStrength = ownershipStrength;
        this.userData = userData;
        this.writerDataLifecycle = writerDataLifecycle;
        this.lifespan = lifespan;
    }
    public DataWriterQos with(QosPolicy... policies) {
        Durability                  durability                  = this.durability;
        DurabilityService           durabilityService           = this.durabilityService;
        Deadline                    deadline                    = this.deadline;
        LatencyBudget               latencyBudget               = this.latencyBudget;
        Liveliness                  liveliness                  = this.liveliness;
        Reliability                 reliability                 = this.reliability;
        DestinationOrder            destinationOrder            = this.destinationOrder;
        History                     history                     = this.history;
        ResourceLimits              resourceLimits              = this.resourceLimits;
        TransportPriority           transportPriority           = this.transportPriority;
        Lifespan                    lifespan                    = this.lifespan;
        Ownership                   ownership                   = this.ownership;
        OwnershipStrength           ownershipStrength           = this.ownershipStrength;
        DataRepresentation          dataRepresentation          = this.dataRepresentation;
        TypeConsistencyEnforcement  typeConsistencyEnforcement  = this.typeConsistencyEnforcement;
        UserData                    userData                    = this.userData;
        WriterDataLifecycle         writerDataLifecycle         = this.writerDataLifecycle;

        for(QosPolicy p: policies) {
            switch (p.getPolicyId()) {
                case Durability.ID:
                    durability = (Durability)p;
                    break;
                case DurabilityService.ID:
                    durabilityService = (DurabilityService)p;
                    break;
                case Deadline.ID:
                    deadline = (Deadline)p;
                    break;
                case LatencyBudget.ID:
                    latencyBudget = (LatencyBudget)p;
                    break;
                case Liveliness.ID:
                    liveliness = (Liveliness)p;
                    break;
                case  Reliability.ID:
                    reliability = (Reliability)p;
                    break;
                case DestinationOrder.ID:
                    destinationOrder = (DestinationOrder)p;
                    break;
                case History.ID:
                    history = (History)p;
                    break;
                case ResourceLimits.ID:
                    resourceLimits = (ResourceLimits)p;
                    break;
                case TransportPriority.ID:
                    transportPriority = (TransportPriority)p;
                    break;
                case Lifespan.ID:
                    lifespan = (Lifespan)p;
                    break;
                case Ownership.ID:
                    ownership = (Ownership)p;
                    break;
                case OwnershipStrength.ID:
                    ownershipStrength = (OwnershipStrength)p;
                    break;
                case DataRepresentation.ID :
                    dataRepresentation = (DataRepresentation)p;
                    break;
                case TypeConsistencyEnforcement.ID:
                    typeConsistencyEnforcement = (TypeConsistencyEnforcement)p;
                    break;
                case UserData.ID:
                    userData = (UserData)p;
                    break;
                case WriterDataLifecycle.ID:
                    writerDataLifecycle = (WriterDataLifecycle)p;
                    break;

                default:
                    System.err.println("[TopicQoS.new]: Unknown QoS");
            }
        }
        return new DataWriterQos(
                durability,
                durabilityService,
                deadline,
                latencyBudget,
                liveliness,
                reliability,
                destinationOrder,
                history,
                resourceLimits,
                transportPriority,
                lifespan,
                ownership,
                ownershipStrength,
                dataRepresentation,
                typeConsistencyEnforcement,
                userData,
                writerDataLifecycle);
    }

    public OwnershipStrength getOwnershipStrength() {
        return this.ownershipStrength;
    }
    /**
     * @return the writerDataLifecycle
     */
    public WriterDataLifecycle getWriterDataLifecycle() {
        return this.writerDataLifecycle;
    }
    public UserData getUserData() {
        return this.userData;
    }

    /**
     * @return the lifespan
     */
    public Lifespan getLifespan() {
        return this.lifespan;
    }

}
