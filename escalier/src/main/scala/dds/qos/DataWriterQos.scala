package dds.qos


object DataWriterQos {

  def apply(policies: DataWriterPolicy*): DataWriterQos = {
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
      var trp = TransportPriority.DefaultValue 
      var wdl = WriterDataLifecycle.DefaultValue
   policies foreach (_ match {
        case p: Deadline            => ddl = p
        case p: DestinationOrder    => dso = p
        case p: Durability          => dur = p
        case p: DurabilityService   => dus = p
        case p: History             => his = p
        case p: LatencyBudget       => lat = p
        case p: Lifespan            => lif = p
        case p: Liveliness          => liv = p
        case p: Ownership           => own = p
        case p: Reliability         => rel = p
        case p: ResourceLimits      => res = p
        case p: TransportPriority   => trp = p
        case p: WriterDataLifecycle => wdl = p
      })
      new DataWriterQos(ddl, dso, dur, dus, his, lat, lif, liv, own, rel, res, trp, wdl)
  }
}

class DataWriterQos(val deadline: Deadline,
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
                    val transportPriority: TransportPriority,
                    val writerDataLifecycle: WriterDataLifecycle ) 
{
    def all: Seq[DataWriterPolicy] = 
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
            transportPriority,
            writerDataLifecycle)
                
    def <= (p: DataWriterPolicy) = DataWriterQos(all :+ p: _*)
  
    def + (p: DataWriterPolicy) = DataWriterQos(all :+ p: _*)
  
    def ++ (p: DataWriterPolicy*) = DataWriterQos(all ++ p: _*)

    override def toString() = "DataWriterQos{ " + durability + " , " + history + 
                              " , " + ownership + " , " + reliability + " }"
}