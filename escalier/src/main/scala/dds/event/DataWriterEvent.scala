package dds.event

import dds.pub.DataWriter

abstract class DataWriterEvent[T](val writer: DataWriter[T]) extends Event