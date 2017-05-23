package dds.sub

/*
sealed case class SampleState()
case class ReadSample() extends SampleState
case class NewSample() extends SampleState
  */
object SampleState extends Enumeration {
  type SampleState = Value
  val ReadSample, NewSample, Any = Value
}

object InstanceState extends Enumeration {
  type InstanceState = Value
  val AliveInstance, NoWriterInstance, DisposedInstance, Any = Value
}

object ViewState extends Enumeration {
  type ViewState = Value
  val New, Viewed, Any = Value
}

import SampleState._
import InstanceState._
import ViewState._

object SampleSelector {
  val NewData = new SampleSelector(SampleState.NewSample, InstanceState.AliveInstance, ViewState.Any)
  val AllData = new SampleSelector(SampleState.Any, InstanceState.AliveInstance, ViewState.Any)
  val AllSamples = new SampleSelector(SampleState.Any, InstanceState.Any, ViewState.Any)
}
class SampleSelector(sampleState: SampleState, instanceState: InstanceState, viewState: ViewState)
