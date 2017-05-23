package dds.perf


import dds.Topic
import dds.pub._
import dds.sub._
import dds.qos._



import org.opensplice.perf.SeqPayload



object PolllingLatencySub {
  def main(args: Array[String]) {
    val wtopic = Topic[SeqPayload]("RSeqPayload")
    val dwqos = DataWriterQos() <= Reliability.Reliable
    val writer = DataWriter[SeqPayload](wtopic, dwqos)

    val rtopic = Topic[SeqPayload]("WSeqPayload")
    val drqos = DataReaderQos() <= Reliability.Reliable
    val reader = DataReader[SeqPayload](rtopic, drqos)

    while (true) {
      var s: Samples[SeqPayload] = null
      do {
        s = reader.read
      }
      while(s.length == 0)
      writer ! s.data(0)
    }
  }

}