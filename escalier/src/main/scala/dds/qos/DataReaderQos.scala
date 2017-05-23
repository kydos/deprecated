package dds.qos


object DataReaderQos {
  
  def apply(policies: DataReaderPolicy*): DataReaderQos = {
      var ddl = Deadline.DefaultValue
      var dso = DestinationOrder.DefaultValue
      var dur = Durability.DefaultValue
      var his = History.DefaultValue
      var lat = LatencyBudget.DefaultValue
      var liv = Liveliness.DefaultValue
      var own = Ownership.DefaultValue
      var rel = Reliability.DefaultValue
      var res = ResourceLimits.DefaultValue
	  policies foreach (_ match {
        case p: Deadline          => ddl = p
		case p: DestinationOrder  => dso = p
		case p: Durability        => dur = p
		case p: History           => his = p
		case p: LatencyBudget     => lat = p
		case p: Liveliness        => liv = p
		case p: Ownership         => own = p
		case p: Reliability       => rel = p
		case p: ResourceLimits    => res = p
      })
      new DataReaderQos(ddl, dso, dur, his, lat, liv, own, rel, res)
  }
}

class DataReaderQos(val deadline: Deadline,
					val destinationOrder: DestinationOrder,
					val durability: Durability,
                    val history: History,
                    val latencyBudget: LatencyBudget,
                    val liveliness: Liveliness,
                    val ownership: Ownership,
                    val reliability: Reliability,
                    val resourceLimits: ResourceLimits) 
{
    def all: Seq[DataReaderPolicy] = 
        Seq(deadline, 
            destinationOrder, 
            durability, 
            history, 
            latencyBudget, 
            liveliness,
            ownership,
            reliability,
            resourceLimits)

    def <= (p: DataReaderPolicy) = DataReaderQos(all :+ p: _*)
  
    def + (p: DataReaderPolicy) = DataReaderQos(all :+ p: _*)
  
    def ++ (p: DataReaderPolicy*) = DataReaderQos(all ++ p: _*)

    override def toString() = "DataReaderQos{ " + durability + " , " + history + 
                              " , " + ownership + " , " + reliability + " }"
}