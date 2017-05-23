package dds.demo.sensor

import dds.sub._
import dds.event._
import org.opensplice.demo.TempSensor
import dds._
import qos.{History, DataReaderQos}

trait Builder[Container[X], T] {
  def += (el: T): Unit
  def finalise(): Container[T]
}

trait Iterator[Container[X], T] {
  def filter(p: T => Boolean): Container[T]
  def remove(p: T=> Boolean): Container[T] = filter(x => !p(x))
}

object MovingAverageFilter {
   def main(args: Array[String]) {
    if (args.length < 1) {
      println("USAGE:\n\tMovingAverageFilter <window> <filter-expression>")
    }

    val topic = Topic[TempSensor]("TTempSensor")
    val ftopic = ContentFilteredTopic[TempSensor]("CFTempSensor",topic, args(1))

    val rqos = DataReaderQos() <= History.KeepLast(args(0).toInt)
    val reader = DataReader[TempSensor](ftopic, rqos)


    reader.reactions += {
      case e: DataAvailable[_] => {
        println("+--------------------------------------------------------")
        var average: Float = 0
        val window = e[TempSensor].reader.history
        window foreach (average += _.temp)
        average = average / window.length
        println("Moving Average: " + average)
      }
    }
  }
}