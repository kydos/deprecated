package org.omg.dds.core.policy;

import org.omg.dds.core.Duration;
import org.omg.dds.core.ServiceEnvironment;

// AC: Policies are Algebraic Data Types. The changes applied to this class
//     are to reflect this fact.
public abstract class PolicyFactory implements org.omg.dds.core.DDSObject {

	public static PolicyFactory getPolicyFactory(ServiceEnvironment env)
	{
		return env.getSPI().getPolicyFactory();
	}
	
    /**
     * @return the durability
     */
    public abstract Durability Durability();

    /**
     * @return the deadline
     */
    public abstract Deadline Deadline(Duration d);

    /**
     * @return the latencyBudget
     */
    public abstract LatencyBudget LatencyBudget(Duration d);

    /**
     * @return the liveliness
     */
    public abstract Liveliness Liveliness();

    /**
     * @return the destinationOrder
     */
    public abstract DestinationOrder DestinationOrder();

    /**
     * @return the history
     */
    public abstract History History();

    /**
     * @return the resourceLimits
     */
    public abstract ResourceLimits ResourceLimits();

    /**
     * @return the userData
     */
    public abstract UserData UserData(byte data[]);

    /**
     * @return the ownership
     */
    public abstract Ownership Ownership();

    /**
     * @return the timeBasedFilter
     */
    public abstract TimeBasedFilter TimeBasedFilter(Duration d);

    /**
     * @return the readerDataLifecycle
     */
    public abstract ReaderDataLifecycle ReaderDataLifecycle();

    /**
     * @return the Representation
     */
    public abstract DataRepresentation Representation();

    /**
     * @return the typeConsistency
     */
    public abstract TypeConsistencyEnforcement TypeConsistency();

    /**
     * @return the durabilityService
     */
    public abstract DurabilityService DurabilityService();

    /**
     * @return the reliability
     */
    public abstract Reliability Reliability();

    /**
     * @return the transportPriority
     */
    public abstract TransportPriority TransportPriority(int priority);

    /**
     * @return the lifespan
     */
    public abstract Lifespan Lifespan(Duration d);

    /**
     * @return the ownershipStrength
     */
    public abstract OwnershipStrength OwnershipStrength(int strength);

    /**
     * @return the writerDataLifecycle
     */
    public abstract WriterDataLifecycle WriterDataLifecycle();

    /**
     * @return the partition
     */
    public abstract Partition Partition(String p);

    public abstract Partition Partition(Iterable<String> p);

    /**
     * @return the groupData
     */
    public abstract GroupData getGroupData(byte data[]);

    /**
     * @return the entityFactory
     */
    public abstract EntityFactory getEntityFactory();
}

