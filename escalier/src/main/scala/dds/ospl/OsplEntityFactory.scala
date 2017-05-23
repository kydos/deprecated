package dds.ospl
import dds._
import dds.pub.Publisher
import dds.qos.TopicQos

class OsplEntityFactory extends EntityFactory {
	def createTopic[T](name:String, qos: TopicQos, pub: Publisher)(implicit m: Manifest[T]) : Topic[T] = {
		null
	}

	def createDomainParticipant(domain: Int): DomainParticipant = {
		new DomainParticipantImpl(domain)

	}
}