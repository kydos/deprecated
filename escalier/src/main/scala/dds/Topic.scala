package dds

import dds.qos.TopicQos

object Topic {

  var defaultDomainParticipant: DomainParticipant = null

  def apply[T](dp: DomainParticipant, name: String, qos: TopicQos)(implicit m: Manifest[T]) = {
		dp.createTopic[T](name, qos)
	}

	def apply[T](dp: DomainParticipant, name: String)(implicit m: Manifest[T]) = {
		val qos: TopicQos = TopicQos()
		dp.createTopic[T](name, qos)
	}

  def apply[T](name: String, qos: TopicQos)(implicit m: Manifest[T]) = {
    if (defaultDomainParticipant == null)
      defaultDomainParticipant = DomainParticipant(0)
      defaultDomainParticipant.createTopic[T](name, qos)
    }

  def apply[T](name: String)(implicit m: Manifest[T]) = {
    if (defaultDomainParticipant == null)
      defaultDomainParticipant = DomainParticipant(0)
		val qos: TopicQos = TopicQos()
		defaultDomainParticipant.createTopic[T](name, qos)
	}
}
abstract class Topic[T](dp: DomainParticipant,
                        name: String,
                        val qos: TopicQos)(implicit m: Manifest[T])
  extends BaseTopic[T](dp, name)(m) {

  override type Peer = DDS.Topic

}