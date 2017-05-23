package dds.demo.vehicle

import dds.Topic
import dds.sub.DataReader
import dds.pub.DataWriter
import dds.event.DataAvailable
import org.opensplice.demo.{Coord2D, Line2D, VehicleStatus}

object CollisionDetector {

  def main(args: Array[String]) {

    if (args.length < 1) {
      println("USAGE:\n\tCollisionDetector <minDist>")
      sys.exit(1)
    }

    val minDist = args(0).toInt
    val vehicleStatusTopic = Topic[VehicleStatus]("TVehicleStatus")
    val vsDR= DataReader[VehicleStatus](vehicleStatusTopic)

    val line = Topic[Line2D] ("LineShape")
    val lineDW = DataWriter[Line2D](line)

    val distance = (a: VehicleStatus, b: VehicleStatus) => {
      math.sqrt(math.pow(a.x - b.x, 2) + math.pow(a.y - b.y, 2)).toInt
    }
    vsDR.reactions += {
      case e: DataAvailable[_] => {
        val data = vsDR history

        val collisionList =
          for (d <- data;
               minDistList <- data.filter ((vs: VehicleStatus) => {
                 val dist = distance (d, vs)
                 if ((d.vid != vs.vid) && dist < minDist) true else false
               } ) ) yield (d, minDistList)


        collisionList.foreach (tuple =>  {
          if (tuple._1.vid != tuple._2.vid ) {

            val l =
              new Line2D(
                tuple._1.vid,
                100+tuple._1.vid,
                new Coord2D(tuple._1.x + (tuple._1.width/2).toInt, tuple._1.y + (tuple._1.height/2).toInt),
                new Coord2D(tuple._2.x + (tuple._2.width/2).toInt, tuple._2.y + (tuple._2.height/2).toInt),
                VehicleDrawer.colors(VehicleDrawer.colors.length-1))


            lineDW ! l
          }
        })
      }
    }

  }
}