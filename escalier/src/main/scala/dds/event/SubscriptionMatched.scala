package dds.event

import dds.sub.DataReader
import DDS.SubscriptionMatchedStatus

case class SubscriptionMatched[T](override val reader: DataReader[T],
                                  val status: SubscriptionMatchedStatus) extends DataReaderEvent[T](reader)