package dds.perf

import dds.Topic
import dds.pub._
import dds.sub._
import dds.qos._
import org.opensplice.perf.SeqPayload
import dds.event.DataAvailable
import swing.event.Event

object LatencyPub {

  def main(args: Array[String]) {
    if (args.length < 2) {
      println("USAGE:\n\tLatencyPub <size> <samples>")
    }
    val wtopic = Topic[SeqPayload]("WSeqPayload")
    val dwqos = DataWriterQos() <= Reliability.Reliable <= Durability.Transient
    val writer = DataWriter[SeqPayload](wtopic, dwqos)

    val rtopic = Topic[SeqPayload]("RSeqPayload")
    val drqos = DataReaderQos() <= Reliability.Reliable <= Durability.Transient
    val reader = DataReader[SeqPayload](rtopic, drqos)

    val length = args(0).toInt
    val samples = args(1).toInt

    val data = new SeqPayload(new Array[Byte](length))

    var count = 0
    val timestamp = new Array[Long](samples)
    class StartEvent extends Event
    val startEvent = new StartEvent

    reader.reactions += {
      case e: DataAvailable[_] => {
        timestamp(count) = System.nanoTime - timestamp(count)
        count += 1

        if (count < samples) {
          timestamp(count) = System.nanoTime
          writer ! data
        }
        else {
          timestamp.foreach(t => println(t/2000))
          sys.exit(0)
        }

      }
      case e: StartEvent => {
        timestamp(count) = System.nanoTime()
        writer ! data
      }
    }

    reader.reactions(startEvent)
  }

}