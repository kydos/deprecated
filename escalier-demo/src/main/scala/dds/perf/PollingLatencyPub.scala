package dds.perf

import dds.Topic
import dds.pub._
import dds.sub._
import dds.qos._
import org.opensplice.perf.SeqPayload
import dds.event.DataAvailable
import swing.event.Event

object PollingLatencyPub {

  def main(args: Array[String]) {
    if (args.length < 2) {
      println("USAGE:\n\tLatencyPub <size> <samples>")
    }
    val wtopic = Topic[SeqPayload]("WSeqPayload")
    val dwqos = DataWriterQos() <= Reliability.Reliable
    val writer = DataWriter[SeqPayload](wtopic, dwqos)

    val rtopic = Topic[SeqPayload]("RSeqPayload")
    val drqos = DataReaderQos() <= Reliability.Reliable
    val reader = DataReader[SeqPayload](rtopic, drqos)

    val length = args(0).toInt
    val samples = args(1).toInt

    val data = new SeqPayload(new Array[Byte](length))

    var count = 0
    val timestamp = new Array[Long](samples)
    class StartEvent extends Event
    val startEvent = new StartEvent

    for (i <- 0 to (samples -1)) {
      timestamp(i) = System.nanoTime()
      writer ! data
      while ((reader.read).length == 0) { }
      timestamp(i) = System.nanoTime() - timestamp(i)
    }

    timestamp.foreach(t => println(t/2000))
  }

}