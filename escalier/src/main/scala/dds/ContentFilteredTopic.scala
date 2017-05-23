package dds


object ContentFilteredTopic {

  def apply[T](name: String,
               topic: Topic[T],
               query: Query) (implicit m: Manifest[T]) =
    topic.dp.createContentFilteredTopic[T](name, topic, query)(m)

  def apply[T](name: String, topic: Topic[T],
               filter: String) (implicit m: Manifest[T]) =
    topic.dp.createContentFilteredTopic[T](name, topic, Query(filter, List()))(m)

}

abstract class ContentFilteredTopic[T](name: String,
                                       val topic: Topic[T],
                                       val query: Query) (implicit m: Manifest[T])
  extends BaseTopic[T](topic.dp, name)(m) {

  override type Peer = DDS.ContentFilteredTopic

}
