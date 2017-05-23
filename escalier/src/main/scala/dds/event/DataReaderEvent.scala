package dds.event

import dds.sub.DataReader

abstract class DataReaderEvent[T](val reader: DataReader[T]) extends Event