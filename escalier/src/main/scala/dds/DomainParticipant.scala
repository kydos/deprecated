package dds

import dds.qos.{TopicQos, PublisherQos, SubscriberQos}
import dds.pub.Publisher
import dds.sub.Subscriber

object DomainParticipant {
	import dds.EntityFactory._
	
	def apply(domain: Int) = escalierFactory createDomainParticipant(domain)

}

abstract class DomainParticipant(val domain: Int) extends Entity {

  type Peer = DDS.DomainParticipant

	def ignoreEntity(name: String) : Unit
	
	def createTopic[T](name: String, qos: TopicQos)(implicit m: Manifest[T]) : Topic[T]

  def createContentFilteredTopic[T](name: String,
                                    topic: Topic[T],
                                    query: Query) (implicit m: Manifest[T]): ContentFilteredTopic[T]
	
	// def createPublisher() : Publisher
  // def createSubscriber() : Subscriber
  def createPublisher(qos: PublisherQos) : Publisher
  def createSubscriber(qos: SubscriberQos) : Subscriber

  /*
	def createPublisher(partition: String) : Publisher
	
	def createPublisher(name: String, qos: PublisherQos) : Publisher
	
	def createSubscriber(name: String, partition: Array[String]) : Subscriber
	
	def createSubscriber(name: String, partition: String) : Subscriber
	
	def createSubscriber(name: String, qos: SubscriberQos) : Subscriber

	  */
}