package dds.demo.vehicle


import dds.Topic
import dds.sub.DataReader
import dds.event.DataAvailable
import dds.qos.{History, DataReaderQos}
import dds.pub.DataWriter
import dds.demo.geometry.{Region2D, Vector2D}
import org.opensplice.demo._

object VehicleDrawer {

  val colors =
    Array(
      new ColorRGB(0x33, 0x66, 0x99),
      new ColorRGB(0xcc, 0x33, 0x33),
      new ColorRGB(0x99, 0xcc, 0x66),
      new ColorRGB(0xff, 0x99, 0x33),
      new ColorRGB(0xff, 0xff, 0x66),
      new ColorRGB(0xcc, 0x99, 0xcc),
      new ColorRGB(0x99, 0xcc, 0xff),
      new ColorRGB(0x99, 0xcc, 0x66),
      new ColorRGB(0x99, 0x99, 0x99),
    new ColorRGB(0x66, 0x66, 0x66))

  implicit def vehicleStatus2String(vs: VehicleStatus): String = "["+ vs.vid +", ("+ vs.x +", "+ vs.y +")]"

  def main(args: Array[String]) {

    val vehicleStatusTopic = Topic[VehicleStatus]("TVehicleStatus")
    val vsDR= DataReader[VehicleStatus](vehicleStatusTopic)

    val vehicleRegionTopic = Topic[VehicleRegion]("TVehicleRegion")
    val vrDR = DataReader[VehicleRegion](vehicleRegionTopic)

    val shapeFill = Topic[Shape2D]("RectFillShape")
    val shapeContour = Topic[Shape2D]("RectShape")
    val shapeDW = DataWriter[Shape2D](shapeFill)
    val contourDW = DataWriter[Shape2D](shapeContour)


    // val color = new ColorRGB(0, 0, 255)
    val wheelColor = new ColorRGB(33, 33, 33)

    val drawVehicle = (vs: VehicleStatus) => {
      val ww = (vs.width/5).toInt
      val wh = (vs.height/5).toInt
      val id = 0;
      val xmid = vs.x + (vs.width/2).toInt
      val wxmid = (ww/2).toInt
      val wymid = (wh/2).toInt
      val ymid = vs.y + (vs.height/2).toInt

      val mainBody =
        new Shape2D(
          vs.vid, id,
          vs.width - 2*ww, vs.height - 2*wh,
          new Coord2D(vs.x + ww, vs.y + wh),
          colors(vs.vid % colors.length))

      val northWheel =
        new Shape2D(
          vs.vid, id + 1,
          ww, wh,
          new Coord2D(xmid - wxmid, vs.y),
          wheelColor)

      val southWheel =
        new Shape2D(
          vs.vid, id + 2,
          ww, wh,
          new Coord2D(xmid - wxmid, vs.y + vs.height - wh),
          wheelColor)

      val eastWheel =
        new Shape2D(
          vs.vid, id + 3,
          ww, wh,
          new Coord2D(vs.x + vs.width - ww, ymid - wymid),
          wheelColor)

      val westWheel =
        new Shape2D(
          vs.vid, id + 4,
          ww, wh,
          new Coord2D(vs.x, ymid - wymid),
          wheelColor)


      shapeDW ! mainBody
      shapeDW ! northWheel
      shapeDW ! southWheel
      shapeDW ! eastWheel
      shapeDW ! westWheel

    }

    val drawRegion = (vr: VehicleRegion) => {
      val boundingRect =
        new Shape2D(vr.vid, 15,
          vr.width, vr.height,
          new Coord2D(vr.x, vr.y),
          colors(vr.vid % colors.length));
      contourDW ! boundingRect
    }

    vsDR.reactions += {
      case e: DataAvailable[_] => {
        (vsDR read) foreach (vs => drawVehicle(vs))
      }
    }

    vrDR.reactions += {
      case e: DataAvailable[_] => {
        (vrDR read) foreach (vr => drawRegion(vr))
      }
    }
    Thread.currentThread().join()
  }
}
