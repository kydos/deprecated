package dds.demo.twitter

import dds.{DomainParticipant, Topic}
import dds.demo.twitter.gen.{Twit,TwitSeqHolder}
import dds.sub.Subscriber
import dds.sub.DataReader
import dds.qos.{History, DataReaderQos}
import dds.event.DataAvailable

object TwitsReader {
	
	def main(args : Array[String]) : Unit = {


		// Create a Tweet Topic in the default Domain with default Qos
		val t: Topic[Twit] = Topic[Twit]("Twits")

		// Create a DataReader for reading Tweets
    val qos = DataReaderQos() <= History.KeepLast(10)
		val reader = DataReader[Twit](t, qos)

    reader.reactions += {
      case d: DataAvailable[_] => {
        (reader history)foreach(t => println(t.name +" :> "+ t.msg))
      }
    }
		Thread.currentThread.join()
	}
}