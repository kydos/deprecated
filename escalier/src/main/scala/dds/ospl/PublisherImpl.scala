package dds.ospl

import dds.pub.{Publisher, DataWriter}
import dds.ospl.Runtime._
import dds.{ContentFilteredTopic, Topic}
import dds.qos.{DataReaderQos, PublisherQos, DataWriterQos}
import dds.sub.DataReader

class PublisherImpl(dp: DomainParticipantImpl,
                    qos: PublisherQos) extends Publisher(dp, qos) {

	private val ddsPub = 
		dp.create_publisher(publisherQos2DDSQos(dp, qos), null, DDS.STATUS_MASK_ANY_V1_2.value)

	def ddsPeer : DDS.Publisher = ddsPub
	
	def createDataWriter[T](t: Topic[T],
                          qos: DataWriterQos) (implicit m: Manifest[T]) : DataWriter[T] = {
		t match {
			case ti: TopicImpl[_] => new DataWriterImpl[T](this, ti, qos)
			case _ => throw new RuntimeException("Invalid Topic type")
		}
		
	}

  def createDataReader[T](cft: ContentFilteredTopic[T], qos: DataReaderQos)
	(implicit m: Manifest[T]) : DataReader[T] = null
}