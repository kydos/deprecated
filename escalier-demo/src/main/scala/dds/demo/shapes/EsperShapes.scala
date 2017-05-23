package dds.demo.shapes

import com.espertech.esper.client._
import dds.Topic
import dds.sub.DataReader
import dds.pub.DataWriter
import dds.event.DataAvailable
import org.opensplice.demo.ShapeType

object ShapeFactory {
  def createShape(color: String, x: Int, y: Int, shapesize: Int) =
    new ShapeType(color, x, y,shapesize)
}

object EsperShapes {
  def main(args: Array[String]) {
    if (args.length < 2) {
      println("Usage:\n\tEsperShapes <inTopic> <outTopic> <epl-expression>")
      sys.exit()
    }
    val somma = 1.1 + 2.0




    val inTopic = Topic[ShapeType](args(0))
    val reader = DataReader[ShapeType](inTopic)

    val outTopic = Topic[ShapeType](args(1))
    val writer = DataWriter[ShapeType](outTopic)

    // -- Esper Configuration
    val config = new Configuration
    val ddsConf = new ConfigurationEventTypeLegacy
    ddsConf.setAccessorStyle(ConfigurationEventTypeLegacy.AccessorStyle.PUBLIC)

    config.addImport("dds.demo.shapes.*")
    config.addEventType("ShapeType", classOf[org.opensplice.demo.ShapeType].getName, ddsConf)
    val cep: EPServiceProvider = EPServiceProviderManager.getDefaultProvider(config)



    // -- Statement Registration
    val statement = cep.getEPAdministrator.createEPL(args(2))

    val listener = new UpdateListener {
      def update(ne: Array[EventBean], oe: Array[EventBean]) {
        if (ne.length > 0) {
          ne(0).get("NewShape") match {
            case s: ShapeType => {
              s.color = "GRAY"
              writer ! s
            }
            case _ => println(">> Mismatching Event Type")
          }
        }
      }
    }
    statement.addListener(listener)

    val runtime = cep.getEPRuntime

    reader.reactions += {
      case e: DataAvailable[_] => {
        (reader read) foreach(runtime sendEvent _)

      }
    }
  }

}