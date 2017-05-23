package dds.demo.ishapes

import org.opensplice.demo.ShapeType
import scala.swing.Component
import java.awt.Graphics2D

abstract class ShapeComponent(val data: ShapeType) extends Component {

	// implicit conversion from ShapeType to java.awt.Shape
	// inherited class have to override it to describe Shape
    implicit def ShapeTypeToAwtShape(data: ShapeType): java.awt.Shape
	
	override def paint(g: Graphics2D) {
    	// get java.awt.Color corresponding to color name
		val c = Class.forName("java.awt.Color").getDeclaredField(data.color).get(null)
		// draw and fill Shape (implicit conversion from ShapeType do java.awt.Shape)
		g.setColor(c.asInstanceOf[java.awt.Color])
		g.fill(data)
		g.setColor(java.awt.Color.white)
		g.draw(data)
	}
    
    override def toString(): String = 
    	data.color + " size:" + data.shapesize + " position:(" + data.x+","+data.y+")"
    	
    // by default shape center is center of the wrapping square
    def shapeCenter = (data.x + data.shapesize/2, data.y + data.shapesize/2)
}
// define accepted ShapeTypes
case class ShapeKind(name: String)

// Circle
class Circle(data: ShapeType) extends ShapeComponent(data) {
	override implicit def ShapeTypeToAwtShape(data: ShapeType): java.awt.Shape = {
		return new java.awt.geom.Ellipse2D.Float(data.x, data.y, data.shapesize, data.shapesize)
	}
	
	override def toString(): String =
		"Circle "+super.toString
}
object circleKind extends ShapeKind("circle")


// Square
class Square(data: ShapeType) extends ShapeComponent(data) {
	override implicit def ShapeTypeToAwtShape(data: ShapeType): java.awt.Shape = {
		return new java.awt.geom.Rectangle2D.Float(data.x, data.y, data.shapesize, data.shapesize)
	}
	
	override def toString(): String =
		"Square "+super.toString
}
object squareKind extends ShapeKind("square")

// Triangle
class Triangle(data: ShapeType) extends ShapeComponent(data) {
	override implicit def ShapeTypeToAwtShape(data: ShapeType): java.awt.Shape = {
		return new java.awt.Polygon( 
				Array(data.x, data.x+data.shapesize/2, data.x+data.shapesize),
				Array(data.y+data.shapesize, data.y, data.y+data.shapesize),
				3)
	}

	override def toString(): String =
		"Triangle "+super.toString
		
	// center of triangle is not center of wrapping square
	override def shapeCenter = (data.x + data.shapesize/2, data.y + 2*(data.shapesize/3))
}
object triangleKind extends ShapeKind("triangle")
