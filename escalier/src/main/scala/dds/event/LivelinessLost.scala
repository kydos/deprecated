package dds.event


case class LivelinessLost[T](override val writer: dds.pub.DataWriter[T],
                             val status: DDS.LivelinessLostStatus)
  extends DataWriterEvent[T](writer)