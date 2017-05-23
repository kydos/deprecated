package dds.demo.shapes

import dds.Topic
import dds.pub.DataWriter
import org.opensplice.demo.ShapeType


object ShapesWriter {
  def main(args: Array[String]) {
    val circle = Topic[ShapeType]("Circle")
    val writer = DataWriter[ShapeType](circle)

    val s = new ShapeType("RED", 100, 100, 100)
    while (true)
        writer ! s
  }

}