package dds.qos

object PublisherQos {
	def apply() = new PublisherQos(Partition(""))
  def apply(p: String) = new PublisherQos(Partition(p))
  def apply(p: List[String]) = new PublisherQos(Partition(p))
}
class PublisherQos(val partition: Partition) {
  def <= (p: Partition) = new PublisherQos(p)
  
  def + (p: Partition) = new PublisherQos(p)
}
