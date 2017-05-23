package dds.demo.ishapes

import java.awt.Graphics2D

trait CircleTag extends ShapeComponent {

	val whiteTagColor: Boolean
	
	override def paint(g: Graphics2D) {
		super.paint(g)

		if (whiteTagColor)
			g.setColor(java.awt.Color.white)
		else
			g.setColor(java.awt.Color.black)
		
		val size = data.shapesize / 3
		g.fillOval(shapeCenter._1 - size/2 , shapeCenter._2 - size/2, size, size)		
	}
	
}