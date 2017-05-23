package dds.qos

object SubscriberQos {
	def apply() = new SubscriberQos(Partition(""))
	def apply(p: String) = new SubscriberQos(Partition(p))
  def apply(p: List[String]) = new SubscriberQos(Partition(p))
}
class SubscriberQos(val partition: Partition) {
  def <= (p: Partition) = new SubscriberQos(p)
  def + (p: Partition) = new SubscriberQos(p)
}