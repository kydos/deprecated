package dds.ospl

import dds.{ContentFilteredTopic, Query,  Topic}


class ContentFilteredTopicImpl[T](name: String,
                                  topic : Topic[T],
                                  query: Query) (implicit m: Manifest[T])
  extends ContentFilteredTopic[T](name, topic, query) {

  val typeSupport: DDS.TypeSupport = topic.typeSupport
  val ddsPeer: DDS.ContentFilteredTopic = createContentFilteredTopic()

  private def createContentFilteredTopic() : DDS.ContentFilteredTopic = {
    val topicPeer: DDS.Topic = topic.ddsPeer
    val dp: DDS.DomainParticipant = topicPeer.get_participant
    dp.create_contentfilteredtopic(name, topicPeer,query.expression, query.params.toArray)
  }
}