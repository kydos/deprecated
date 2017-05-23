package dds.event

import dds.sub.DataReader

case class RequestedIncompatibleQos[T](override val reader: DataReader[T],
                              val status: DDS.RequestedIncompatibleQosStatus) extends DataReaderEvent[T](reader) {
  override def toString =
    "RequestedIncompatibleQos"
}
