package dds.sub

import dds.qos.{DataReaderQos, SubscriberQos, Partition}
import dds.{BaseTopic, ContentFilteredTopic, Entity, Topic}
import swing.Reactions


object DataReader {

  var defaultSubscriber: Subscriber = null

  def apply[T](sub: Subscriber,
               t: BaseTopic[T],
               qos: DataReaderQos) (implicit m: Manifest[T])
  = sub.createDataReader(t, qos)

  def apply[T](sub: Subscriber,
               t: BaseTopic[T]) (implicit m: Manifest[T])
  = sub.createDataReader(t, DataReaderQos())

  def apply[T](t: BaseTopic[T],
               qos: DataReaderQos) (implicit m: Manifest[T]) = {
    if (defaultSubscriber == null)
      defaultSubscriber = Subscriber(t.dp)
    defaultSubscriber.createDataReader(t, qos)
  }

  def apply[T](t: BaseTopic[T]) (implicit m: Manifest[T]) = {
    if (defaultSubscriber == null)
      defaultSubscriber = Subscriber(t.dp)
    defaultSubscriber.createDataReader(t, DataReaderQos())
  }

  def apply[T](t: BaseTopic[T],
               qos: DataReaderQos,
               subQos: SubscriberQos) (implicit m: Manifest[T]) = {
    def sub = Subscriber(t.dp, subQos)
    sub.createDataReader(t, qos)
  }

  def apply[T](t: BaseTopic[T],
               qos: DataReaderQos,
               partitions: String*) (implicit m: Manifest[T]) = {
    def sub = Subscriber(t.dp, SubscriberQos(partitions toList))
    sub.createDataReader(t, qos)
  }

}

abstract class DataReader[T](val sub: Subscriber,
                             val topic: BaseTopic[T],
                             val qos: DataReaderQos)
                            (implicit m: Manifest[T]) extends Entity {


  type Peer = DDS.DataReader

  val reactions = new Reactions.Impl

  def read(s: SampleSelector): Samples[T]

  def read: Samples[T] =  this.read(SampleSelector.NewData)

  def read(n: Int, s: SampleSelector = SampleSelector.NewData): Samples[T]
  def read(instance: T): Samples[T]
  def read(handle: Long): Samples[T]

  def read[SampleSeqHolder](data: SampleSeqHolder)

  def take(s: SampleSelector): Samples[T]
  // def take(handle: Long): Array[T]
  def take: Samples[T] = this.take(SampleSelector.NewData)
  def take(n: Int, s: SampleSelector = SampleSelector.NewData): Samples[T]

  def history(): Samples[T] = {
    this.read(SampleSelector.AllData)
  }
  def history(instance: T): Samples[T]

  def history(handle: Long): Samples[T]

  def lookupInstance(instance: T): Option[Long]
}
