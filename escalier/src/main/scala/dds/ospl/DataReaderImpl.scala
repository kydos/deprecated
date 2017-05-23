package dds.ospl

import dds.qos.DataReaderQos
import dds.ospl.Runtime._
import dds.BaseTopic
import java.lang.RuntimeException
import org.opensplice.dds.dcps.FooDataReaderImpl
import dds.sub.{SampleSelector, DataReader, Samples}
import DDS.SampleInfo

class DataReaderImpl[T](p: SubscriberImpl,
                        t: BaseTopic[T],
                        qos: DataReaderQos)
                       (implicit m: Manifest[T]) extends DataReader[T](p, t, qos) {


  private val noData = new Samples(new Array[T](0), new Array[SampleInfo](0))

  private var copyCache: Long = getCopyCache()

  val dataSeqClass = Class.forName(m.erasure.getName + "SeqHolder")
  val valueField = dataSeqClass.getField("value")

  val ddsPeer: DDS.DataReader = {
    topic.ddsPeer match {
      case td: DDS.TopicDescription => {
        p.ddsPeer.create_datareader(td, dataReaderQos2DDSQos(qos, sub), new EventListener[T](this), DDS.STATUS_MASK_ANY_V1_2.value)
      }
      case _ => throw new RuntimeException("Invalid TopicType")

    }

  }

  def getCopyCache(): Long = {
    val tsClass = topic.typeSupport.getClass
    val method = tsClass.getDeclaredMethod("get_copyCache")
    val res = method.invoke(t.typeSupport)
    res match {
      case l: java.lang.Long => l.longValue
      case _ => throw new RuntimeException
    }
  }

  def read[SampleSeqHolder](data: SampleSeqHolder) {
    import org.opensplice.dds.dcps.FooDataReaderImpl
    val infoSeq = new DDS.SampleInfoSeqHolder
    FooDataReaderImpl.read(ddsPeer, copyCache, data, infoSeq, DDS.LENGTH_UNLIMITED.value,
      DDS.NOT_READ_SAMPLE_STATE.value, DDS.ANY_VIEW_STATE.value, DDS.ALIVE_INSTANCE_STATE.value)
  }

  def read(n: Int, s: SampleSelector = SampleSelector.NewData): Samples[T] = {
    import dds.sub.SampleSelector._
    s match {
      case NewData =>
        readi(DDS.NOT_READ_SAMPLE_STATE.value,
          DDS.ANY_VIEW_STATE.value,
          DDS.ALIVE_INSTANCE_STATE.value, n)
      case AllData =>
        readi(DDS.ANY_SAMPLE_STATE.value,
          DDS.ANY_VIEW_STATE.value,
          DDS.ALIVE_INSTANCE_STATE.value, n)
      case AllSamples =>
        readi(DDS.ANY_SAMPLE_STATE.value,
          DDS.ANY_VIEW_STATE.value,
          DDS.ANY_INSTANCE_STATE.value, n)
    }
  }
  def read(s: SampleSelector) = this.read(DDS.LENGTH_UNLIMITED.value, s)


  def take(n: Int, s: SampleSelector = SampleSelector.NewData) = {
    import dds.sub.SampleSelector._
    s match {
      case NewData =>
        takei(DDS.NOT_READ_SAMPLE_STATE.value,
          DDS.ANY_VIEW_STATE.value,
          DDS.ALIVE_INSTANCE_STATE.value, n)
      case AllData =>
        takei(DDS.ANY_SAMPLE_STATE.value,
          DDS.ANY_VIEW_STATE.value,
          DDS.ALIVE_INSTANCE_STATE.value, n)
      case AllSamples =>
        takei(DDS.ANY_SAMPLE_STATE.value,
          DDS.ANY_VIEW_STATE.value,
          DDS.ANY_INSTANCE_STATE.value, n)
    }
  }

  def take(s: SampleSelector) = take(DDS.LENGTH_UNLIMITED.value, s)


  private def readi(sampleState: Int,
                    viewState: Int,
                    instanceState: Int,
                    n: Int): Samples[T] = {
    import org.opensplice.dds.dcps.FooDataReaderImpl
    val data = dataSeqClass.newInstance
    val infoSeq = new DDS.SampleInfoSeqHolder

    FooDataReaderImpl.read(ddsPeer, copyCache, data, infoSeq, n,
      sampleState, viewState, instanceState)

    val value = (valueField.get(data))
    val va = value.asInstanceOf[Array[T]]
    new Samples[T](va, infoSeq.value)
  }



  private def takei(sampleState: Int,
                    viewState: Int,
                    instanceState: Int,
                    n: Int = DDS.LENGTH_UNLIMITED.value) = {
    import org.opensplice.dds.dcps.FooDataReaderImpl
    val data = dataSeqClass.newInstance
    val infoSeq = new DDS.SampleInfoSeqHolder
    FooDataReaderImpl.take(ddsPeer, copyCache, data, infoSeq, n,
      sampleState, viewState, instanceState)

    val value = valueField.get(data)
    val va = value.asInstanceOf[Array[T]]
    new Samples[T](va, infoSeq.value)
  }

  def lookupInstance(instance: T): Option[Long] = {
    val handle = FooDataReaderImpl.lookupInstance(ddsPeer,copyCache, instance)
    if (handle != DDS.HANDLE_NIL.value) Some(handle) else None
  }

  def read(handle: Long) = {
    readi(DDS.NOT_READ_SAMPLE_STATE.value, handle)
  }

  def read(instance: T) = {
    val handle = FooDataReaderImpl.lookupInstance(ddsPeer,copyCache, instance)
    if (handle != DDS.HANDLE_NIL.value)
      readi(DDS.NOT_READ_SAMPLE_STATE.value, handle)
    else noData
  }

  def history(handle: Long) = {
    readi(DDS.ANY_SAMPLE_STATE.value, handle)
  }

  def history(instance: T) = {
    val handle = FooDataReaderImpl.lookupInstance(ddsPeer,copyCache, instance)
    if (handle != DDS.HANDLE_NIL.value)
      readi(DDS.ANY_SAMPLE_STATE.value, handle)
    else noData
  }

  private def readi(sampleState: Int, handle: Long) = {
    import org.opensplice.dds.dcps.FooDataReaderImpl
    val data = dataSeqClass.newInstance
    val infoSeq = new DDS.SampleInfoSeqHolder
    FooDataReaderImpl.readInstance(ddsPeer, copyCache,
      data, infoSeq, DDS.LENGTH_UNLIMITED.value,
      handle, sampleState, DDS.ANY_VIEW_STATE.value,
      DDS.ALIVE_INSTANCE_STATE.value)

    val value = valueField.get(data)
    val va = value.asInstanceOf[Array[T]]
    new Samples(va, infoSeq.value)
  }

  // -- Listener Implementation
  private class EventListener[T: Manifest](var reader: DataReader[T]) extends DDS.DataReaderListener {
    import dds.event._
    def on_requested_deadline_missed(r: DDS.DataReader,
                                     status: DDS.RequestedDeadlineMissedStatus) {
      reader.reactions(RequestedDeadlineMissed[T](reader, status))
    }

    def on_requested_incompatible_qos(r: DDS.DataReader,
                                      status: DDS.RequestedIncompatibleQosStatus) {
      reader.reactions(RequestedIncompatibleQos[T](reader, status))
    }

    def on_sample_rejected(r: DDS.DataReader, status: DDS.SampleRejectedStatus) {
      reader.reactions(SampleRejected[T](reader, status))
    }

    def on_liveliness_changed(r: DDS.DataReader, status: DDS.LivelinessChangedStatus) {
      reader.reactions(LivelinessChanged[T](reader, status))
    }

    def on_data_available(r: DDS.DataReader) {
      reader.reactions(DataAvailable[T](reader)(manifest[T]))
    }

    def on_subscription_matched(r: DDS.DataReader,
                                status: DDS.SubscriptionMatchedStatus) {
      reader.reactions(SubscriptionMatched[T](reader, status))
    }

    def on_sample_lost(r: DDS.DataReader,
                       status: DDS.SampleLostStatus) {
      reader.reactions(SampleLost[T](reader, status))
    }

  }

}