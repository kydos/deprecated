package dds.demo.shapes

import dds._
import dds.sub._
import dds.pub._
import dds.event._
import qos.{History, DataReaderQos}
import org.opensplice.demo.ShapeType

object AverageShape {
  implicit def shape2String(s: ShapeType)
  = "("+ s.color +", "+ s.x +", "+ s.y +", "+ s.shapesize+")"

  def main(args: Array[String]) {
    if (args.length < 3) {
      println("\n\tUsage: ShapeReader <in-topic> <average-topic> <color>")
      sys.exit()
    }


    val dp = DomainParticipant(0)
    val sub = Subscriber(dp)
    val pub = Publisher(dp)

    val inTopic = Topic[ShapeType](dp, args(0))
    val outTopic = Topic[ShapeType](dp, args(1))

    val qos = DataReaderQos() <= History.KeepLast(1)
    val reader = DataReader[ShapeType](sub, inTopic, qos)

    val writer = DataWriter[ShapeType](pub, outTopic)

    val sumShapes = (a: ShapeType, b: ShapeType) => {
      a.x += b.x
      a.y += b.y
      a.shapesize += b.shapesize
      a
    }

    reader.reactions += {
      case e: DataAvailable[_] => {
        val data = reader history
        val len = data.length

        val s = new ShapeType
        s.color = args(2)

        val fs = (s /: data)(sumShapes)
        fs.x = fs.x / len
        fs.y = fs.y / len
        fs.shapesize = fs.shapesize / len
        writer ! fs
      }

    }
    Thread.currentThread.join
  }
}