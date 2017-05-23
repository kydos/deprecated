package dds.event

import dds.sub.DataReader

case class SampleLost[T](override val reader: DataReader[T],
                         val status: DDS.SampleLostStatus) extends DataReaderEvent[T](reader) {

}