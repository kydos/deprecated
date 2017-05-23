package dds.ospl

import dds.pub.DataWriter
import dds.qos.DataWriterQos
import dds.ospl.Runtime._

class DataWriterImpl[T](p: PublisherImpl,
                        t: TopicImpl[T],
                        qos: DataWriterQos)
                       (implicit m: Manifest[T]) extends DataWriter[T](p, t, qos) {

	private val copyCache: Long = getCopyCache
	val ddsPeer =
		p.ddsPeer.create_datawriter(t.ddsPeer, dataWriterQos2DDSQos(qos, pub), new EventListener[T](this), DDS.STATUS_MASK_ANY_V1_2.value)
    
	def getCopyCache: Long = {
		val tsClass = t.typeSupport.getClass 
		val method = tsClass.getDeclaredMethod("get_copyCache")
		val res = method.invoke(t.typeSupport)
		res match {
			case l: java.lang.Long => l.longValue
			case _ => throw new RuntimeException
		}
	}

	def write(sample: T) = {
		org.opensplice.dds.dcps.FooDataWriterImpl.write(ddsPeer, copyCache, sample, DDS.HANDLE_NIL.value)
	}

  // Listener Implementation
  private class EventListener[T](val writer: dds.pub.DataWriter[T]) extends DDS.DataWriterListener {
    import dds.event._

    def on_offered_deadline_missed(w: DDS.DataWriter, status: DDS.OfferedDeadlineMissedStatus) {
      writer.reactions(OfferedDeadlineMissed(writer, status))
    }

    def on_publication_matched(w: DDS.DataWriter, status: DDS.PublicationMatchedStatus) {
      writer.reactions(PublicationMatched(writer, status))
    }

    def on_liveliness_lost(w: DDS.DataWriter, status: DDS.LivelinessLostStatus) {
      writer.reactions(LivelinessLost(writer, status))
    }

    def on_offered_incompatible_qos(w: DDS.DataWriter, status: DDS.OfferedIncompatibleQosStatus) {
      writer.reactions(OfferedIncompatibleQoS(writer, status))
    }
  }
	
}