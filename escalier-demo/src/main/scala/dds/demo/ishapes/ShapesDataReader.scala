package dds.demo.ishapes

import org.opensplice.demo.ShapeType
import scala.actors.Actor
import dds.sub._

abstract class ShapesDataReader {

	val datareader: DataReader[ShapeType]
	
	implicit def ShapeTypeToShapeComponent(data: ShapeType): ShapeComponent
	
	def read: Array[ShapeComponent] = (datareader.history.data) map(s =>  ShapeTypeToShapeComponent(s))
}

class CircleDataReader extends ShapesDataReader {
	override lazy val datareader = ShapesDDSTopics.getDataReader(circleKind)
	
	override implicit def ShapeTypeToShapeComponent(data: ShapeType): ShapeComponent =
		new Circle(data) with CircleTag {
			override val whiteTagColor = false
		}
}

class SquareDataReader extends ShapesDataReader {
	override lazy val datareader = ShapesDDSTopics.getDataReader(squareKind)

	override implicit def ShapeTypeToShapeComponent(data: ShapeType): ShapeComponent =
		new Square(data) with CircleTag {
			override val whiteTagColor = false
		}
}

class TriangleDataReader extends ShapesDataReader {
	override lazy val datareader = ShapesDDSTopics.getDataReader(triangleKind)
	
	override implicit def ShapeTypeToShapeComponent(data: ShapeType): ShapeComponent =
		new Triangle(data) with CircleTag {
			override val whiteTagColor = false
		}
}
