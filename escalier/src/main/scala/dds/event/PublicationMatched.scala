package dds.event

case class PublicationMatched[T](override val writer: dds.pub.DataWriter[T],
                                 val status: DDS.PublicationMatchedStatus)
  extends DataWriterEvent[T](writer)