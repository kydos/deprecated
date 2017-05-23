package dds.qos

import dds.time.Duration

/**
 * Base class for all QoS Policies.
 *  
 * @author Angelo Corsaro <mailto:angelo.corsaro@gmail.com>
 *
 */
abstract class Policy {
	def name: String
	def id: Int
}

/**
 * Trait for a Policy which apply to Participant.
 */
trait ParticipantPolicy extends Policy
/**
 * Trait for a Policy which apply to Topic.
 */
trait TopicPolicy extends Policy
/**
 * Trait for a Policy which apply to Publisher.
 */
trait PublisherPolicy extends Policy
/**
 * Trait for a Policy which apply to Subscriber.
 */
trait SubscriberPolicy extends Policy
/**
 * Trait for a Policy which apply to DataWriter.
 */
trait DataWriterPolicy extends Policy
/**
 * Trait for a Policy which apply to DataReader.
 */
trait DataReaderPolicy extends Policy


/**
 * The <code>Reliability</code> QoS Policy controls whether data will be 
 * distributed reliably among the publisher and matched subscribers or 
 * in a best effor manner.
 *   
 * @author Angelo Corsaro  <mailto:angelo.corsaro@gmail.com>
 *
 */
abstract class Reliability() extends Policy 
	with TopicPolicy with DataReaderPolicy with DataWriterPolicy 
{
	def name = "Reliability"
	def id = 11
}


object Reliability {
  case object Reliable extends Reliability {
    override def toString = name + "[reliable]"
  }

  case object BestEffort extends Reliability {
    override def toString = name + "[best-effort]"
  }
  
  val DefaultValue: Reliability = BestEffort
}
/**
 * The <code>History</code> QoS Policy allows to control the number
 * of samples per instance that the infrastructure will store in the
 * local cache (either on the reader or writer side). 
 * 
 * @author Angelo Corsaro  <mailto:angelo.corsaro@gmail.com>
 */
abstract class History() extends Policy 
	with TopicPolicy with DataReaderPolicy with DataWriterPolicy
{
	def name = "History"
	def id   = 13
}
object History {
  case class KeepLast(depth: Int) extends History {
    require(depth > 0)
    override def toString = name + "[" + depth + "]"
  }

  case object KeepAll extends History {
    override def toString = name
  }
  
  val DefaultValue: History = KeepLast(1)
}

/**
 * The <code>Owenership</code> QoS Policy controls whether 
 * more than one writer can concurrently change the same instance or not.
 * 
 * @author Angelo Corsaro  <mailto:angelo.corsaro@gmail.com>
 *
 */
abstract class Ownership() extends Policy
	with TopicPolicy with DataReaderPolicy with DataWriterPolicy
{
	def name = "Ownership"
	def id = 6
	
}

object Ownership {
  case object Shared extends Ownership {
    override def toString = name + "[shared]"
  }

  case class Exclusive(strength: Int = 0) extends Ownership {
    require (strength > 0)
    override def toString = name + "[exclusive]"
  }
  
  val DefaultValue: Ownership = Shared
}

/**
 * The <code>Durability</code> QoS Policy controls the degrees of time
 * decoupling between the producer and consumers of data.
 * 
 * @author Angelo Corsaro  <mailto:angelo.corsaro@gmail.com>
 *
 */
abstract class Durability() extends Policy
	with TopicPolicy with DataReaderPolicy with DataWriterPolicy
{
	def name = "Durability"
	def id = 22
}
object Durability {

  case object Volatile extends Durability {
    override def toString = name + "[Volatile]"
  }

  case object TransientLocal extends Durability {
    override def toString = name + "[TransientLocal]"
  }

  case object Transient extends Durability {
    override def toString = name + "[Transient]"
  }

  case object Persistent extends Durability {
    override def toString = name + "[Persistent]"
  }

  val DefaultValue: Durability = Volatile
}

case class WriterDataLifecycle(val autodisposeUnregisteredInstances: Boolean,
                               val autopurgeSuspendedSamplesDelay: Duration,
                               val autounregisterInstanceDelay: Duration) 
  extends Policy 
  with DataWriterPolicy  
{
  def name = "WriterDataLifecycle"
  def id = 16
  override def toString = name
}

object WriterDataLifecycle {
  val DefaultValue: WriterDataLifecycle = WriterDataLifecycle(true, Duration.infinite, Duration.infinite)
}

case class TopicData(val value: Array[Byte]) extends Policy
	with TopicPolicy
{
	def name = "TopicData"
	def id = 18
	override def toString = name
}
object TopicData {
	val DefaultValue: TopicData = TopicData(new Array[Byte](0))
}

case class DurabilityService(val history: History,
                             val cleanupDelay: Duration,
                             val maxSamples: Int,
                             val maxInstances: Int,
                             val maxSamplesPerInstance: Int)
	extends Policy
	with TopicPolicy with DataWriterPolicy
{
	def name = "DurabilityService"
	def id = 22
	override def toString = name
}
object DurabilityService {
    val DefaultValue: DurabilityService = DurabilityService(History.KeepLast(1), Duration.zero, -1, -1, -1)
}

case class Deadline(val period: Duration) extends Policy
	with TopicPolicy with DataReaderPolicy with DataWriterPolicy
{
	def name = "Deadline"
	def id = 4
	override def toString = name
}
object Deadline {
    val DefaultValue: Deadline = Deadline(Duration.infinite)
}

case class LatencyBudget(val duration: Duration) extends Policy
	with TopicPolicy with DataReaderPolicy with DataWriterPolicy
{
	def name = "LatencyBudget"
	def id = 5
	override def toString = name
}
object LatencyBudget {
    val DefaultValue: LatencyBudget = LatencyBudget(Duration.zero)
}

abstract class Liveliness(val leaseDuration: Duration) extends Policy
	with TopicPolicy with DataReaderPolicy with DataWriterPolicy
{
	def name = "Liveliness"
	def id = 8
	override def toString = name
}
object Liveliness {
  case class Automatic(lease: Duration) extends Liveliness(lease) {
    override def toString = name + "[Automatic]"
  }
  case class ManualByParticipant(lease: Duration) extends Liveliness(lease) {
    override def toString = name + "[ManualByParticipant]"
  }
  case class ManualByTopic(lease: Duration) extends Liveliness(lease) {
    override def toString = name + "[ManualByTopic]"
  }
  
  val DefaultValue: Liveliness = Automatic(Duration.infinite)
}

abstract class DestinationOrder() extends Policy
	with TopicPolicy with DataReaderPolicy with DataWriterPolicy
{
	def name = "DestinationOrder"
	def id = 12
}
object DestinationOrder {
  case object SourceTimeStamp extends DestinationOrder {
    override def toString = name + "[Source]"
  }
  case object ReceptionTimeStamp extends DestinationOrder {
    override def toString = name + "[Reception]"
  }
  
  val DefaultValue: DestinationOrder = ReceptionTimeStamp
}

case class ResourceLimits(val maxSamples: Int,
    					  val maxInstances: Int,
    					  val maxSamplesPerInstance: Int) 
    extends Policy
	with TopicPolicy with DataReaderPolicy with DataWriterPolicy
{
	def name = "ResourceLimits"
	def id = 14
	override def toString = name
}
object ResourceLimits {
    val Unlimited = ResourceLimits(-1, -1, -1)
    val DefaultValue = Unlimited
}

case class TransportPriority(value: Int) extends Policy
	with TopicPolicy with DataWriterPolicy
{
	require(value >= 0)
	def name = "TransportPriority"
	def id = 20
	override def toString = name
}
object TransportPriority {
    val DefaultValue = TransportPriority(0)
}

case class Lifespan(val duration: Duration) extends Policy
	with TopicPolicy with DataWriterPolicy
{
	def name = "LifeSpan"
	def id = 21
	override def toString = name
}
object Lifespan {
    val DefaultValue = Lifespan(Duration.infinite)
}

case class Partition(val partitions: List[String]) extends Policy 
	with PublisherPolicy with SubscriberPolicy
{
  val id = 10
  val name = "Partition"

  override def toString = name + partitions.toString
}
object Partition {
  def apply(p: String*) = new Partition(p toList)
  
  val DefaultValue = Partition()
}
