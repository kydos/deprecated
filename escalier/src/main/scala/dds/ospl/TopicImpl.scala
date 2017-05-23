package dds.ospl
import dds.qos.TopicQos
import dds.{Topic, DomainParticipant}

import dds.ospl.Runtime._

class TopicImpl[T](dp: DomainParticipantImpl,
                   name: String,
                   qos: TopicQos)
		(implicit m: Manifest[T]) extends Topic[T](dp, name, qos) {

	val paramType = m.erasure
	val typeSupport: DDS.TypeSupport= registerTypeSupport()

	private val ddsTopic = createDDSTopic[T](dp)
	

	val ddsPeer: DDS.Topic = ddsTopic
	
	private def registerTypeSupport(): DDS.TypeSupport = {
		val t = m.erasure
		val tsclazz = Class.forName(t.getName + "TypeSupport")
		val tsupport = tsclazz.newInstance
		tsupport match {
			case ts: DDS.TypeSupport =>  { 
				ts.register_type(dp.ddsPeer, t.getName())
				ts
			}
			case _ => throw new RuntimeException()
		}
	
	}
	private def createDDSTopic[T](dp: DomainParticipantImpl)(implicit m: Manifest[T]) : DDS.Topic = {
		val topic = dp.create_topic(name, paramType.getName, topicQos2DDSQos(dp, qos), null, DDS.STATUS_MASK_ANY_V1_2.value)
		require(topic != null)
		topic
	}
	
}