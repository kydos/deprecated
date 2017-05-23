package dds.demo.ishapes

import org.opensplice.demo.ShapeType
import dds.pub.DataWriter

trait PublishedShape extends ShapeComponent {
	
	val datawriter: DataWriter[ShapeType]
	
	def publish() {
		datawriter ! data
	}

}