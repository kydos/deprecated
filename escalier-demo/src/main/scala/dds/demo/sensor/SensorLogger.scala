package dds.demo.sensor

import dds._
import dds.sub._
import dds.event._
import org.opensplice.demo.TempSensor
import dds.qos._

object TempSensor2String {
  def apply(t: TempSensor): String = "(id = "+ t.id +" temp = "+ t.temp +" humidity = "+ t.hum +")"
}

object SensorLogger {
  def main(args: Array[String]) {
    if (args.length < 2) {
      println("USAGE:\n\tSensorLogger <window-size> <filter-expression>")
      sys.exit()
    }

    val topic = Topic[TempSensor]("TTempSensor")
    val ftopic = ContentFilteredTopic[TempSensor]("CFTempSensor",topic, args(1))

    val rqos = DataReaderQos() <= History.KeepLast(args(0).toInt)
    val reader = DataReader[TempSensor](ftopic, rqos)

    reader.reactions += {
      case e: DataAvailable[_] => {
        println("+--------------------------------------------------------")
        println("Read Sensors:")
        reader.history foreach (t => {
          println("| " + TempSensor2String(t))
        })
      }
    }
  }
}