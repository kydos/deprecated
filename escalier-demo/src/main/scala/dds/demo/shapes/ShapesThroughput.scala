package dds.demo.shapes


import com.espertech.esper.client._
import dds.Topic
import org.opensplice.demo.ShapeType
import dds.sub.DataReader
import dds.event.{Event, DataAvailable}

object ShapesThroughput {
  def main(args: Array[String]) {

    val circle= Topic[ShapeType]("Circle")
    val creader = DataReader[ShapeType](circle)

    val square= Topic[ShapeType]("Square")
    val sreader = DataReader[ShapeType](square)

    val triangle= Topic[ShapeType]("Triangle")
    val treader = DataReader[ShapeType](triangle)

    val expression = "insert into ShapesxSec select color, count(*) as cnt from ShapeType.win:time_batch(1 second) group by color"

    // -- Esper Configuration
    val config = new Configuration
    val ddsConf = new ConfigurationEventTypeLegacy
    ddsConf.setAccessorStyle(ConfigurationEventTypeLegacy.AccessorStyle.PUBLIC)

    config.addEventType("ShapeType", classOf[org.opensplice.demo.ShapeType].getName, ddsConf)
    val cep: EPServiceProvider = EPServiceProviderManager.getDefaultProvider(config)



    // -- Statement Registration
    val statement = cep.getEPAdministrator.createEPL(expression)

    val listener = new UpdateListener {
      def update(ne: Array[EventBean], oe: Array[EventBean]) {
        println("+-----------------------------------------")
        ne foreach(e => {
          println("| color = "+e.get("color") +" frame/sec = "+ e.get("cnt"))

        })
      }
    }
    statement.addListener(listener)

    val runtime = cep.getEPRuntime

    val reaction: PartialFunction[swing.event.Event, Unit] = {
      case e: DataAvailable[_] => (e[ShapeType].reader read) foreach(runtime sendEvent _)
    }

    creader.reactions += reaction
    sreader.reactions += reaction
    treader.reactions += reaction
  }

}