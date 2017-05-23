package dds.event

import dds.sub.DataReader

case class LivelinessChanged[T](override val reader: DataReader[T],
                                val status: DDS.LivelinessChangedStatus) extends DataReaderEvent[T](reader)