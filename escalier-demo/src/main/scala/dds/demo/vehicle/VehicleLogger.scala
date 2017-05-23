package dds.demo.vehicle

import dds.Topic
import dds.sub.DataReader
import dds.event.DataAvailable
import org.opensplice.demo.VehicleStatus
import dds.qos.{History, DataReaderQos}

object VehicleLogger {
  def main(args: Array[String]) {
    implicit def vehicleStatus2String(vs: VehicleStatus): String = "["+ vs.vid +", ("+ vs.x +", "+ vs.y +")]"
    if (args.length < 1) {
      println("USAGE:\n\tVehicleLogger <history-depth>")
      sys.exit(1)
    }
    val vehicleStatusTopic = Topic[VehicleStatus]("TVehicleStatus")
    val qos = DataReaderQos() <= History.KeepLast(args(0).toInt)
    val vsDR= DataReader[VehicleStatus](vehicleStatusTopic, qos)

    println("+----------------------------------------------------+")
    println("+----------------------------------------------------+")
    vsDR.reactions += {
      case e: DataAvailable[_] => {
        (vsDR history) foreach (vs => println(vehicleStatus2String(vs)))
        println("+----------------------------------------------------+")
      }
    }

  }
}