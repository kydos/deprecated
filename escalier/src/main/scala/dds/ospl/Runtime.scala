package dds.ospl

import dds._
import dds.qos._
import dds.time._
import DDS.ReliabilityQosPolicyKind.{RELIABLE_RELIABILITY_QOS, BEST_EFFORT_RELIABILITY_QOS}
import DDS.DurabilityQosPolicyKind.{
    VOLATILE_DURABILITY_QOS,
    TRANSIENT_LOCAL_DURABILITY_QOS,
    TRANSIENT_DURABILITY_QOS,
    PERSISTENT_DURABILITY_QOS}
import DDS.HistoryQosPolicyKind.{KEEP_ALL_HISTORY_QOS, KEEP_LAST_HISTORY_QOS}
import DDS.{PublisherQosHolder, DomainParticipant, DataReaderQosHolder, DataReaderQos}

object Runtime {

    def copyDuration(d: Duration, ddsD: DDS.Duration_t) = {
        ddsD.sec = (d.sec toInt)
        ddsD.nanosec = (d.nsec toInt)
    }
    
    def copy(r: Reliability, ddsR: DDS.ReliabilityQosPolicy) = {
        r match {
            case Reliability.BestEffort =>
                ddsR.kind = DDS.ReliabilityQosPolicyKind.BEST_EFFORT_RELIABILITY_QOS
            case Reliability.Reliable =>
                ddsR.kind = DDS.ReliabilityQosPolicyKind.RELIABLE_RELIABILITY_QOS
        }
    }
    
    def copy(h: History, ddsH: DDS.HistoryQosPolicy) = {
        h match {
            case History.KeepAll =>
                ddsH.kind = DDS.HistoryQosPolicyKind.KEEP_ALL_HISTORY_QOS
            case kl: History.KeepLast =>
                ddsH.kind = DDS.HistoryQosPolicyKind.KEEP_LAST_HISTORY_QOS
                ddsH.depth = kl.depth
        }
    }
    
    def copy(o: Ownership, ddsO: DDS.OwnershipQosPolicy) = {
        o match {
            case Ownership.Shared =>
                ddsO.kind = DDS.OwnershipQosPolicyKind.SHARED_OWNERSHIP_QOS
            case e: Ownership.Exclusive =>
                ddsO.kind = DDS.OwnershipQosPolicyKind.EXCLUSIVE_OWNERSHIP_QOS
        }
    }
    
    def copy(o: Ownership, ddsO: DDS.OwnershipQosPolicy, ddsOS: DDS.OwnershipStrengthQosPolicy) = {
        o match {
            case Ownership.Shared =>
                ddsO.kind = DDS.OwnershipQosPolicyKind.SHARED_OWNERSHIP_QOS
            case e: Ownership.Exclusive =>
                ddsO.kind = DDS.OwnershipQosPolicyKind.EXCLUSIVE_OWNERSHIP_QOS
                ddsOS.value = e.strength
        }
    }
    
    def copy(d: Durability, ddsD: DDS.DurabilityQosPolicy) = {
        d match {
	        case Durability.Volatile =>
	        	ddsD.kind = DDS.DurabilityQosPolicyKind.VOLATILE_DURABILITY_QOS
	        case Durability.TransientLocal =>
	        	ddsD.kind = DDS.DurabilityQosPolicyKind.TRANSIENT_LOCAL_DURABILITY_QOS
	        case Durability.Transient =>
	        	ddsD.kind = DDS.DurabilityQosPolicyKind.TRANSIENT_DURABILITY_QOS
	        case Durability.Persistent =>
	        	ddsD.kind = DDS.DurabilityQosPolicyKind.PERSISTENT_DURABILITY_QOS
        }
    }
    
    def copy(t: TopicData, ddsT: DDS.TopicDataQosPolicy) = {
        ddsT.value = t.value
    }
    
    def copy(d: DurabilityService, ddsD: DDS.DurabilityServiceQosPolicy) = {
    	copyDuration(d.cleanupDelay, ddsD.service_cleanup_delay)
        d.history match {
            case History.KeepAll =>
                ddsD.history_kind = DDS.HistoryQosPolicyKind.KEEP_ALL_HISTORY_QOS
            case kl: History.KeepLast =>
                ddsD.history_kind = DDS.HistoryQosPolicyKind.KEEP_LAST_HISTORY_QOS
                ddsD.history_depth = kl.depth
        }
    	ddsD.max_samples = d.maxSamples
    	ddsD.max_instances = d.maxInstances
    	ddsD.max_samples_per_instance = d.maxSamplesPerInstance
    }
    
    def copy(d: Deadline, ddsD: DDS.DeadlineQosPolicy) = {
        copyDuration(d.period, ddsD.period)
    }
    
    def copy(l: LatencyBudget, ddsL: DDS.LatencyBudgetQosPolicy) = {
        copyDuration(l.duration, ddsL.duration)
    }
    
    def copy(l: Liveliness, ddsL: DDS.LivelinessQosPolicy) = {
    	copyDuration(l.leaseDuration, ddsL.lease_duration)
        l match {
            case Liveliness.Automatic(_) =>
                ddsL.kind = DDS.LivelinessQosPolicyKind.AUTOMATIC_LIVELINESS_QOS
            case Liveliness.ManualByParticipant(_) =>
                ddsL.kind = DDS.LivelinessQosPolicyKind.MANUAL_BY_PARTICIPANT_LIVELINESS_QOS
            case Liveliness.ManualByTopic(_) =>
                ddsL.kind = DDS.LivelinessQosPolicyKind.MANUAL_BY_TOPIC_LIVELINESS_QOS
        }
    }
    
    def copy(d: DestinationOrder, ddsD: DDS.DestinationOrderQosPolicy) = {
        d match {
            case DestinationOrder.SourceTimeStamp =>
                ddsD.kind = DDS.DestinationOrderQosPolicyKind.BY_SOURCE_TIMESTAMP_DESTINATIONORDER_QOS
            case DestinationOrder.ReceptionTimeStamp =>
                ddsD.kind = DDS.DestinationOrderQosPolicyKind.BY_RECEPTION_TIMESTAMP_DESTINATIONORDER_QOS
        }
    }
    
    def copy(r: ResourceLimits, ddsR: DDS.ResourceLimitsQosPolicy) = {
        ddsR.max_samples = r.maxSamples
        ddsR.max_instances = r.maxInstances
        ddsR.max_samples_per_instance = r.maxSamplesPerInstance
    }
    
    def copy(t: TransportPriority, ddsT: DDS.TransportPriorityQosPolicy) = {
        ddsT.value = t.value
    }
    
    def copy(l: Lifespan, ddsL: DDS.LifespanQosPolicy) = {
    	copyDuration(l.duration, ddsL.duration)
    }
    
    def copy(p: Partition, ddsP: DDS.PartitionQosPolicy) = {
        ddsP.name = (p.partitions toArray)
    }

    def copy(wdl: WriterDataLifecycle, ddsWdl: DDS.WriterDataLifecycleQosPolicy) = {
        ddsWdl.autodispose_unregistered_instances = wdl.autodisposeUnregisteredInstances
        copyDuration(wdl.autopurgeSuspendedSamplesDelay, ddsWdl.autopurge_suspended_samples_delay)
        copyDuration(wdl.autounregisterInstanceDelay, ddsWdl.autounregister_instance_delay)
    }
    
    def topicQos2DDSQos(dp: dds.DomainParticipant, qos: dds.qos.TopicQos) : DDS.TopicQos = {

        val qosH = new DDS.TopicQosHolder
        dp.ddsPeer.get_default_topic_qos(qosH)
        val ddsQos = qosH.value

        copy(qos.deadline, ddsQos.deadline)
        copy(qos.destinationOrder, ddsQos.destination_order)
        copy(qos.durability, ddsQos.durability)
        copy(qos.durabilityService, ddsQos.durability_service)
        copy(qos.history, ddsQos.history)
        copy(qos.latencyBudget, ddsQos.latency_budget)
        copy(qos.lifespan, ddsQos.lifespan)
        copy(qos.liveliness, ddsQos.liveliness)
        copy(qos.ownership, ddsQos.ownership)
        copy(qos.reliability, ddsQos.reliability)
        copy(qos.resourceLimits, ddsQos.resource_limits)
        copy(qos.topicData, ddsQos.topic_data)
        copy(qos.transportPriority, ddsQos.transport_priority)

        ddsQos
    }

    def publisherQos2DDSQos(dp: dds.DomainParticipant, qos: dds.qos.PublisherQos) : DDS.PublisherQos = {
        val qosH = new DDS.PublisherQosHolder
        dp.ddsPeer.get_default_publisher_qos(qosH)
        val ddsQos = qosH.value

        copy(qos.partition, ddsQos.partition)
        ddsQos
    }

    def subscriberQos2DDSQos(dp: dds.DomainParticipant, qos: dds.qos.SubscriberQos) : DDS.SubscriberQos = {
        val qosH = new DDS.SubscriberQosHolder
        dp.ddsPeer.get_default_subscriber_qos(qosH)
        val ddsQos = qosH.value

        copy(qos.partition, ddsQos.partition)
        ddsQos
    }


    def dataReaderQos2DDSQos(qos: dds.qos.DataReaderQos,
            sub: dds.sub.Subscriber) : DDS.DataReaderQos = {
        val qosH = new DDS.DataReaderQosHolder
        sub.ddsPeer.get_default_datareader_qos(qosH)
        val ddsQos = qosH.value

        copy(qos.deadline, ddsQos.deadline)
        copy(qos.destinationOrder, ddsQos.destination_order)
        copy(qos.durability, ddsQos.durability)
        copy(qos.history, ddsQos.history)
        copy(qos.latencyBudget, ddsQos.latency_budget)
        copy(qos.liveliness, ddsQos.liveliness)
        copy(qos.ownership, ddsQos.ownership)
        copy(qos.reliability, ddsQos.reliability)
        copy(qos.resourceLimits, ddsQos.resource_limits)

        ddsQos
    }

    def dataWriterQos2DDSQos(qos: dds.qos.DataWriterQos,
            pub: dds.pub.Publisher) : DDS.DataWriterQos = {
        val qosH = new DDS.DataWriterQosHolder
        pub.ddsPeer.get_default_datawriter_qos(qosH)
        val ddsQos = qosH.value

        copy(qos.deadline, ddsQos.deadline)
        copy(qos.destinationOrder, ddsQos.destination_order)
        copy(qos.durability, ddsQos.durability)
        // copy(qos.durabilityService, ddsQos.durability_service) !! NOT SUPPORTED BY OSPL
        copy(qos.history, ddsQos.history)
        copy(qos.latencyBudget, ddsQos.latency_budget)
        copy(qos.lifespan, ddsQos.lifespan)
        copy(qos.liveliness, ddsQos.liveliness)
        copy(qos.ownership, ddsQos.ownership, ddsQos.ownership_strength)
        copy(qos.reliability, ddsQos.reliability)
        copy(qos.resourceLimits, ddsQos.resource_limits)
        copy(qos.transportPriority, ddsQos.transport_priority)
        copy(qos.writerDataLifecycle, ddsQos.writer_data_lifecycle)

        ddsQos
    }

    implicit def topic2DDSTopic(t: TopicImpl[_]) : DDS.Topic = {
        t.ddsPeer
    }

    implicit def DP2DDSDP(dp: DomainParticipantImpl) : DDS.DomainParticipant = {
        dp.ddsPeer
    }
}