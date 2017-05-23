package dds.ospl

import dds.qos.{SubscriberQos, DataReaderQos}
import dds.sub.{Subscriber, DataReader}
import dds.ospl.Runtime._
import dds.{BaseTopic, ContentFilteredTopic, Topic}

class SubscriberImpl(dp: DomainParticipantImpl,
                     qos: SubscriberQos) extends Subscriber(dp, qos) {

  private val ddsSub =
    dp.create_subscriber(subscriberQos2DDSQos(dp, qos), null, DDS.STATUS_MASK_ANY_V1_2.value)

  def ddsPeer : DDS.Subscriber = ddsSub



  def createDataReader[T](t: BaseTopic[T],
                          qos: DataReaderQos)
                         (implicit m: Manifest[T]) : DataReader[T]
  = new DataReaderImpl[T](this, t, qos)

  def createDataReader[T](t: BaseTopic[T])
                         (implicit m: Manifest[T]) : DataReader[T]
  = new DataReaderImpl[T](this, t, DataReaderQos())


}