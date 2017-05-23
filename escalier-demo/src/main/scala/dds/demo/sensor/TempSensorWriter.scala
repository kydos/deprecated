package dds.demo.sensor

import dds._
import dds.pub._
import org.opensplice.demo.TempSensor

object TempSensorWriter {
  def main(args: Array[String]) {
    if (args.length < 1) {
      println("USAGE:\n\tTempSensor <id>")
      sys.exit()
    }
    val topic = Topic[TempSensor]("TTempSensor")
    val writer = DataWriter[TempSensor](topic)
    var t = new TempSensor(args(0).toInt, 0, 0)
    while (true) {
      t.temp = (math.random * 40).toFloat
      t.hum = (math.random * 100).toFloat
      writer ! t
      println (">> " + TempSensor2String(t))
      Thread.sleep(1000)
    }
  }
}