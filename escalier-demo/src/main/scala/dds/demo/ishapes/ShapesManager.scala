package dds.demo.ishapes

import scala.actors.Actor
import dds.sub.SampleSelector

object ShapesManager extends Actor {
	var refreshRate = IShapeConstants.REFRESH_TIMEOUT
	
	var publishedShapes: List[BouncingShape] = List.empty
	
	var shapeReaders: List[ShapesDataReader] = List.empty
	
	def +=(shape: BouncingShape) = {
		publishedShapes = publishedShapes :+ shape
	}
	
	def +=(shapeDataReader: ShapesDataReader) = {
		shapeReaders = shapeReaders :+ shapeDataReader
	}
	
	
	def act() {
		loop {
			
			// update bouncing shapes
			publishedShapes foreach (_.updatePosition())
			
			// read shapes from DataReaders (loop on shapeReaders, loop on each element resulting of read)
			val readShapes = for {
				reader <- shapeReaders
				shape <- reader read
			} yield shape
			
			// clear ShapesPanel
			ShapesPanel.clear
			
			// add bouncing shapes to ShapesPanel
			ShapesPanel += publishedShapes
			
			// add read shapes to ShapesPanel
			ShapesPanel += readShapes
			
			// repaint ShapesPanel
			ShapesPanel repaint
			
			java.lang.Thread.sleep(refreshRate)
		}
	}

}