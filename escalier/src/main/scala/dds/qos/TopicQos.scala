package dds.qos

object TopicQos {

  def apply(policies: TopicPolicy*): TopicQos = {
      var ddl = Deadline.DefaultValue
      var dso = DestinationOrder.DefaultValue
      var dur = Durability.DefaultValue
      var dus = DurabilityService.DefaultValue
      var his = History.DefaultValue
      var lat = LatencyBudget.DefaultValue
      var lif = Lifespan.DefaultValue
      var liv = Liveliness.DefaultValue
      var own = Ownership.DefaultValue
      var rel = Reliability.DefaultValue
      var res = ResourceLimits.DefaultValue
      var tod = TopicData.DefaultValue
      var trp = TransportPriority.DefaultValue   
	  policies foreach (_ match {
        case p: Deadline          => ddl = p
		case p: DestinationOrder  => dso = p
		case p: Durability        => dur = p
		case p: DurabilityService => dus = p
		case p: History           => his = p
		case p: LatencyBudget     => lat = p
		case p: Lifespan          => lif = p
		case p: Liveliness        => liv = p
		case p: Ownership         => own = p
		case p: Reliability       => rel = p
		case p: ResourceLimits    => res = p
		case p: TopicData	      => tod = p
		case p: TransportPriority => trp = p
      })
      new TopicQos(ddl, dso, dur, dus, his, lat, lif, liv, own, rel, res, tod, trp)
  }
}

class TopicQos(val deadline: Deadline,
			   val destinationOrder: DestinationOrder,
			   val durability: Durability,
			   val durabilityService: DurabilityService,
               val history: History,
               val latencyBudget: LatencyBudget,
               val lifespan: Lifespan,
               val liveliness: Liveliness,
               val ownership: Ownership,
               val reliability: Reliability,
               val resourceLimits: ResourceLimits,
               val topicData: TopicData,
               val transportPriority: TransportPriority) 
{
    val all: Seq[TopicPolicy] = 
        Seq(deadline, 
            destinationOrder, 
            durability, 
            durabilityService, 
            history, 
            latencyBudget, 
            lifespan, 
            liveliness,
            ownership,
            reliability,
            resourceLimits,
            topicData,
            transportPriority)

    def <= (p: TopicPolicy) = TopicQos(all :+ p: _*)
  
    def + (p: TopicPolicy) = TopicQos(all :+ p: _*)
  
    def ++ (p: TopicPolicy*) = TopicQos(all ++ p :_*)

    override def toString() = "TopicQos{ " + durability + " , " + history + 
                              " , " + ownership + " , " + reliability + " }"
}