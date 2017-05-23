package dds

import dds.qos._
import dds.pub._
import dds._
import ospl.OsplEntityFactory

object EntityFactory {

	
	val escalierFactory: EntityFactory = createFactory
	
	private def createFactory() : EntityFactory = {
    new OsplEntityFactory
	}
}

abstract class EntityFactory {
	def createTopic[T](name:String, qos: TopicQos, pub: Publisher)(implicit m: Manifest[T]) : Topic[T]
	
	def createDomainParticipant(domain: Int): DomainParticipant
	
}