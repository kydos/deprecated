package dds.demo.twitter

import java.util.Scanner

import dds._
import dds.pub._

import dds.demo.twitter.gen.Twit

object TwitsWriter {

	def main(args: Array[String]) {

		if (args.length < 2) {
			println("TwitsWriter <uid> <count>")
      sys.exit(1)
		}

    val console = new Scanner(System.in)


		// Create a Tweet Topic in the default domain with default QoS
		val twits: Topic[Twit] = Topic[Twit]("Twits")

		// Create a DataWriter for writing Tweet
		val writer = DataWriter[Twit](twits)

		val tweet = new Twit
		tweet.name = args(0)
		val count = args(1).toInt

		for (i <- 1 to count) {
      print(tweet.name + ":> ")
			tweet.msg = console.nextLine()

			writer ! tweet
		}
	}

}