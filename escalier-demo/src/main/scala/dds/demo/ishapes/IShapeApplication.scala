package dds.demo.ishapes

import scala.swing._
import scala.util._
import scala.swing.Swing._
import scala.swing.event._
import java.awt.{BorderLayout, GridBagConstraints}
import javax.swing.{JPanel, JButton}
import IShapeConstants._


object IShapeConstants {
	val MINIMUM_SHAPE_SIZE = 10
	val MAXIMUM_SHAPE_SIZE = 100
	val DEFAULT_SHAPE_SIZE = 50
	val MINIMUM_SHAPE_SPEED = 1
	val MAXIMUM_SHAPE_SPEED = 10
	val DEFAULT_SHAPE_SPEED = 3
	val REFRESH_TIMEOUT = 40
	val SHAPE_PANEL_WIDTH  = 500
	val SHAPE_PANEL_HEIGHT = 360
}




object IShapeApplication extends SimpleSwingApplication {
		
	// create a ShapesDataReader according to ShapeKind and ShapeType
	def createShapesDataReader(shape: ShapeKind) = shape match {
		case `circleKind` => new CircleDataReader
		case `squareKind` => new SquareDataReader
		case `triangleKind` => new TriangleDataReader
	}

	// create a BouncingShape according to ShapeKind and ShapeType
	def createBouncingShapeComponent(kind: ShapeKind, size: Int, color: String, shapeSpeed: Int) = {
		val data = BouncingShape.shapeWithRandomPosition(size, color)
		kind match {
			case `circleKind` =>
			 	new Circle(data) with CircleTag with BouncingShape with PublishedShape {
			 		override val whiteTagColor = true
			 		override val speed = shapeSpeed
			 		override val datawriter = ShapesDDSTopics.getDataWriter(kind)
			 		override def updatePosition() = {
			 			super.updatePosition()
			 			publish()
			 		}
			 	}
			case `squareKind` => 
				new Square(data) with CircleTag with BouncingShape with PublishedShape {
			 		override val whiteTagColor = true
					override val speed = shapeSpeed
			 		override val datawriter = ShapesDDSTopics.getDataWriter(kind)
			 		override def updatePosition() = {
			 			super.updatePosition()
			 			publish()
			 		}
				}
			case `triangleKind` => 
				new Triangle(data) with CircleTag with BouncingShape with PublishedShape {
			 		override val whiteTagColor = true
					override val speed = shapeSpeed
			 		override val datawriter = ShapesDDSTopics.getDataWriter(kind)
			 		override def updatePosition() = {
			 			super.updatePosition()
			 			publish()
			 		}
				}
		}
	}

	// define a ComboBox for shape selection
	def shapeBox = new ComboBox(List(circleKind, squareKind, triangleKind)) {
		renderer = ListView.Renderer(_.name)
	}

	// define a ComboBox for color selection	
	def colorBox = new ComboBox(List("blue", "red", "green", "orange", "yellow", "magenta", "cyan"))
	
	val pubShapeBox = shapeBox
	val pubColorBox = colorBox
	val pubQosButton = new Button("QoS")
	object pubSizeSlider extends Slider {
		min = MINIMUM_SHAPE_SIZE
		max = MAXIMUM_SHAPE_SIZE
		value = DEFAULT_SHAPE_SIZE
		paintTicks = true
		minorTickSpacing = 10
		preferredSize = (100, 30)
	}
	object pubSpeedSlider extends Slider {
		min = MINIMUM_SHAPE_SPEED
		max = MAXIMUM_SHAPE_SPEED
		value = DEFAULT_SHAPE_SPEED
		paintTicks = true
		minorTickSpacing = 1
		snapToTicks = true
		preferredSize = (100, 30)
	}
	val pubButton   = new Button("publish")
	
	
	val subShapeBox = shapeBox
	val subQosButton = new Button("QoS")
	val subFilterButton = new Button("Filter")
	val subButton   = new Button("subscribe")
	
	
	def top = new MainFrame {
		title = "DDS IShape on Domain " + sys.props.getOrElse("dds.domainId", "0") +
		        " and Partition: " + sys.props.getOrElse("dds.partition", "default partition")
		resizable = false
		contents = new BoxPanel(Orientation.Horizontal) {
			border = Swing.EmptyBorder(10, 10, 10, 10)
			
			contents += new BoxPanel(Orientation.Vertical) {
				border = Swing.EmptyBorder(5, 5, 5, 5)
				
				contents += new GridBagPanel {
					border = CompoundBorder(TitledBorder(EtchedBorder, "Publisher"), EmptyBorder(5,5,5,10))

					val c = new Constraints
					c.fill = GridBagPanel.Fill.Horizontal
					c.insets = new Insets(5, 5, 5, 5)
					c.anchor = GridBagPanel.Anchor.LineEnd
					
					c.grid = (0, 0)
					layout(new Label("Shape:")) = c
					c.grid = (1, 0)
					layout(pubShapeBox) = c
					
					c.grid = (0, 1)
					layout(new Label("Color:")) = c
					c.grid = (1, 1)
					layout(pubColorBox) = c
					
					c.grid = (0, 2)
					layout(new Label("Size:")) = c
					c.grid = (1, 2)
					layout(pubSizeSlider) = c
					
					c.grid = (0, 3)
					layout(new Label("Speed:")) = c
					c.grid = (1, 3)
					layout(pubSpeedSlider) = c
					
					c.gridwidth = 2
					c.grid = (0, 4)
					layout(pubQosButton) = c
					
					c.grid = (0, 5)
					layout(pubButton) = c
				}
				
				contents += new Separator
				contents += new GridBagPanel {
					border = CompoundBorder(TitledBorder(EtchedBorder, "Subscriber"), EmptyBorder(5,5,5,10))

					val c = new Constraints
					c.fill = GridBagPanel.Fill.Horizontal
					c.insets = new Insets(5, 5, 5, 5)
					c.anchor = GridBagPanel.Anchor.LineEnd
					
					c.grid = (0, 0)
					layout(new Label("Shape:")) = c
					c.grid = (1, 0)
					layout(subShapeBox) = c
					
					c.gridwidth = 2
					c.grid = (0, 1)
					layout(subQosButton) = c
					
					c.grid = (0, 2)
					layout(subFilterButton) = c
					
					c.grid = (0, 3)
					layout(subButton) = c
				}
			}
			
			contents += new Separator
			contents += ShapesPanel
		}
	}

	
	listenTo(pubQosButton, subQosButton, subFilterButton, pubButton, subButton)
	reactions += {
		case ButtonClicked(`pubQosButton`) =>
			WriterQosDialog.open()

		case ButtonClicked(`subQosButton`) =>
		    ReaderQosDialog.open()

		case ButtonClicked(`subFilterButton`) =>
		    new Dialog() {
		        private val cancel = new Button("Cancel")
		        contents = new BoxPanel(Orientation.Vertical) {
		            contents += new Label("Sorry, not yet implemented !")
		            contents += cancel
		        }
		        listenTo(cancel)
		        reactions += { case ButtonClicked(`cancel`) => close}
		    } open

		case ButtonClicked(`pubButton`) =>
			val shape = createBouncingShapeComponent(pubShapeBox.selection.item,
												     pubSizeSlider.value,
												     pubColorBox.selection.item.toUpperCase,
													 pubSpeedSlider.value)
			ShapesManager += shape

		case ButtonClicked(`subButton`) =>
			val shapeDataReader = createShapesDataReader(subShapeBox.selection.item)
			ShapesManager += shapeDataReader
	}
	
	
	override def startup(args: Array[String]): Unit = {
		super.startup(args)
		if (args.length > 0)
			sys.props +=(("dds.domainId", args(0)))
		if (args.length > 1)
			sys.props +=(("dds.partition", args(1)))
		ShapesManager start()
		println("IShapeApplication starting on domain " + ShapesDDSTopics.domainId)
	}
	
}