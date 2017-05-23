package dds.demo.ishapes

import scala.swing.Panel
import scala.swing.Swing._
import scala.collection.mutable.Buffer

object ShapesPanel extends Panel {
	
	val width  = IShapeConstants.SHAPE_PANEL_WIDTH 
	val height = IShapeConstants.SHAPE_PANEL_HEIGHT 
	
	var shapesList: Buffer[ShapeComponent] = Buffer.empty
	
	def +=(shape: ShapeComponent) = {
		shapesList += shape
	}
	
	def +=(shapes: List[ShapeComponent]) = {
		shapesList ++= shapes
	}
	
	def clear = {
		shapesList = Buffer.empty
	}
	
	background = java.awt.Color.white
	preferredSize = (width, height)
	border = BeveledBorder(Lowered)
	
	override def paint(g: java.awt.Graphics2D) = {
		super.paintComponent(g)
		shapesList.foreach(_.paint(g))
	}
}
