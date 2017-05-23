package dds.event

case class OfferedDeadlineMissed[T](override  val writer: dds.pub.DataWriter[T],
                                    val status: DDS.OfferedDeadlineMissedStatus)
  extends DataWriterEvent[T](writer)
