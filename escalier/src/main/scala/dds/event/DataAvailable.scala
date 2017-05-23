package dds.event

import dds.sub.DataReader

case class DataAvailable[T: Manifest](override val reader: DataReader[T])  extends DataReaderEvent[T](reader) {
  def apply[T]: DataAvailable[T]  = {
    this.asInstanceOf[DataAvailable[T]]
  }
}