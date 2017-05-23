package dds.sub

import dds.qos.{SubscriberQos, DataReaderQos}
import dds._

object Subscriber {

  	def apply(dp: DomainParticipant, qos: SubscriberQos) = {
		val sub = dp.createSubscriber(qos)
		require(sub != null)
		sub
	}

	def apply(dp: DomainParticipant) = {
		val sub = dp.createSubscriber(SubscriberQos())
		require(sub != null)
		sub
	}
}
abstract class Subscriber(val dp: DomainParticipant, val qos: SubscriberQos) extends Entity {

  type Peer = DDS.Subscriber



	def createDataReader[T](t: BaseTopic[T], qos: DataReaderQos)
	(implicit m: Manifest[T]) : DataReader[T]

  def createDataReader[T](t: BaseTopic[T])
	(implicit m: Manifest[T]) : DataReader[T]
}

