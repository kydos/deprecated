package dds.demo.shapes

import dds._
import dds.sub._
import dds.pub._
import dds.event._
import qos.DataReaderQos._
import qos.History
import qos.{Liveliness, History, DataReaderQos}
import org.opensplice.demo.ShapeType

object ShapesFilter {
  implicit def shape2String(s: ShapeType)
  = "("+ s.color +", "+ s.x +", "+ s.y +", "+ s.shapesize+")"

  def main(args: Array[String]) {
    if (args.length < 3) {
      println("\n\tUsage: ShapeReader <in-topic> <filter-expression> <out-topic>")
      sys.exit()
    }


    val dp = DomainParticipant(0)

    val sub = Subscriber(dp)
    val pub = Publisher(dp)

    val inTopic = Topic[ShapeType](dp, args(0))
    val filter = ContentFilteredTopic[ShapeType]("FilteredCircle", inTopic, args(1))

    val outTopic = Topic[ShapeType](dp, args(2))


    val qos = DataReaderQos() <= History.KeepLast(1)
    val reader = DataReader[ShapeType](sub, filter, qos)
    val writer = DataWriter[ShapeType](pub, outTopic)


    reader.reactions += {
      case e: DataAvailable[_] =>  (reader read) foreach(writer write _)
    }
    Thread.currentThread.join
  }
}