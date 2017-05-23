package dds.event

case class OfferedIncompatibleQoS[T](override val writer: dds.pub.DataWriter[T],
                                  val status: DDS.OfferedIncompatibleQosStatus)
  extends DataWriterEvent[T](writer)
