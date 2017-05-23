package dds.pub

import dds.qos.{PublisherQos, DataWriterQos}
import dds.{DomainParticipant, Entity, Topic}

object Publisher {

  def apply(dp: DomainParticipant, qos: PublisherQos) = {
		dp.createPublisher(qos)
	}

	def apply(dp: DomainParticipant) = {
		dp.createPublisher(PublisherQos())
	}
}
abstract class Publisher(val dp: DomainParticipant,
                         val qos: PublisherQos) extends Entity {
	
  type Peer = DDS.Publisher

	def createDataWriter[T](t: Topic[T], qos: DataWriterQos) (implicit m: Manifest[T]) : DataWriter[T]
	
	
}