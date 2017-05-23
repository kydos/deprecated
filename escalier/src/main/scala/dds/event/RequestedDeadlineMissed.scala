package dds.event

import dds.sub.DataReader


case class RequestedDeadlineMissed[T](override val reader: DataReader[T],
                             val status: DDS.RequestedDeadlineMissedStatus) extends DataReaderEvent[T](reader) {
  override def toString =
    "RequestedDeadlineMissed(count = "+ status.total_count +", countChange = "+status.total_count_change +")"
}
