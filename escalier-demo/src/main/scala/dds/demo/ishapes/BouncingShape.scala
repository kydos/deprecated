package dds.demo.ishapes

import org.opensplice.demo.ShapeType
import scala.util.Random
import math._

trait BouncingShape extends ShapeComponent {
	
	val speed: Int
	val alpha: Double = Pi/6

	private var angle: Double = alpha
	
	private def xLimit = ShapesPanel.width-data.shapesize
	private def yLimit = ShapesPanel.height-data.shapesize

	private def flip = Random.nextBoolean
	
	
	def updatePosition(): Unit = {
		var x = data.x + speed*cos(angle)
		var y = data.y + speed*sin(angle)

		// test if x is out-of-bounds
		if (x <= 0) {
			angle = if (flip) -alpha else alpha 
			x = 0
		}
		else if (x >= xLimit) {
			angle = if (flip) Pi+alpha else Pi-alpha
			x = xLimit
		}
		
		// test if y is out-of-bounds
		if (y <= 0) {
			angle = if (flip) alpha else Pi-alpha 
			y = 0
		}
		else if (y >= yLimit) {
			angle = if (flip) Pi+alpha else -alpha
			y = yLimit
		}
		
		// update position
		data.x = round(x.floatValue)
		data.y = round(y.floatValue)
	}
	
	override def toString(): String =
		super.toString + " speed:" + speed
}

object BouncingShape {

	private def randX(size: Int): Int = 
		Random.nextInt(ShapesPanel.width - size)
	private def randY(size: Int): Int =
		Random.nextInt(ShapesPanel.height - size)
	
	// initialize a ShapeType at random position (within drawingBow limits)
	def shapeWithRandomPosition(size: Int, color: String): ShapeType = {
		var data = new ShapeType
		data.shapesize = size
		data.x = randX(data.shapesize)
		data.y = randY(data.shapesize)
		data.color = color
		println("created Shape: "+data)
		return data
	}
	
}