package dds.ospl

import dds.{Topic, DomainParticipant, ContentFilteredTopic, Query}
import dds.pub.Publisher
import dds.sub.Subscriber
import dds.qos.{TopicQos, PublisherQos, SubscriberQos}

class DomainParticipantImpl(domain: Int) extends DomainParticipant(domain) {

    val dpfClass = Class.forName("DDS.DomainParticipantFactory")
    var domainIsInt = true
    val createParticipantMethod: java.lang.reflect.Method = {
      try {
        dpfClass.getMethod("create_participant",
                           classOf[Int],
                           classOf[DDS.DomainParticipantQos],
                           classOf[DDS.DomainParticipantListener],
                           classOf[Int])
      } catch {
        case e: NoSuchMethodException =>
          try {
            domainIsInt = false
            dpfClass.getMethod("create_participant",
                               classOf[String],
                               classOf[DDS.DomainParticipantQos],
                               classOf[DDS.DomainParticipantListener],
                               classOf[Int])
          } catch {
            case e: Exception => e.printStackTrace ; null
          }
        case e: Exception => e.printStackTrace ; null
      }
    }

  val ddsPeer : DDS.DomainParticipant = createDdsDP
	
	private def createDdsDP() : DDS.DomainParticipant = {
		val dpf = DDS.DomainParticipantFactory.get_instance()
		val holder = new DDS.DomainParticipantQosHolder
		dpf.get_default_participant_qos(holder)
    if (domainIsInt) {
      println("Connecting on Domain '"+domain+"'")
      val result = createParticipantMethod.invoke(dpf, domain.asInstanceOf[java.lang.Integer], holder.value, null, DDS.STATUS_MASK_ANY_V1_2.value.asInstanceOf[java.lang.Integer]).asInstanceOf[DDS.DomainParticipant]
      if (result == null)
        throw new RuntimeException("OpenSpliceDDS unable to create DomainParticipant (check ospl-error.log)")
      result
    } else {
      println("WARNING: you're using OpenSpliceDDS v5.x. Domain is ignored and 'null' is used instead.")
      createParticipantMethod.invoke(dpf, null, holder.value, null, DDS.STATUS_MASK_ANY_V1_2.value.asInstanceOf[java.lang.Integer]).asInstanceOf[DDS.DomainParticipant]
    }

	}
	
	
	def ignoreEntity(name: String) : Unit = { }	
	
	def createTopic[T](name: String, qos: TopicQos)(implicit m: Manifest[T]) : Topic[T] = {
		new TopicImpl[T](this, name, qos)
	}
	
	
	def createPublisher(partition: Array[String]) : Publisher = null
	
	def createPublisher( partition: String) : Publisher = null
	
	def createPublisher(qos: PublisherQos) : Publisher = {
		new PublisherImpl(this, qos)
	}
	
	def createSubscriber(name: String, partition: Array[String]) : Subscriber = null
	
	def createSubscriber(name: String, partition: String) : Subscriber = null
	
	def createSubscriber(qos: SubscriberQos) : Subscriber = {
		new SubscriberImpl(this, qos)
	}

  def createContentFilteredTopic[T](name: String, topic: Topic[T], query: Query)
  (implicit m: Manifest[T]): ContentFilteredTopic[T]  = new ContentFilteredTopicImpl[T](name, topic, query)

}
	