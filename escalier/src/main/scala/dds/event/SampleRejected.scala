package dds.event

import dds.sub.DataReader


case class SampleRejected[T](override val reader: DataReader[T],
                             val status: DDS.SampleRejectedStatus) extends DataReaderEvent[T](reader)