package dds.demo.ishapes

import scala.swing._
import scala.swing.event._
import BorderPanel.Position._
import Swing._
import dds.qos._

object WriterQosDialog extends Dialog {
	
	title = "Writer QoS"

	var qos: DataWriterQos = DataWriterQos()
		
	private def updateQos = {
		// set qos according to values set in Dialog
		println("update QoS")
	  
		qos ++= ( 
				// Add Reliability
				{reliability.selected.get match {
					case `besteffort` => Reliability.BestEffort
					case `reliable`   => Reliability.Reliable
				}},
				// Add Ownership
				{ownership.selected.get match {
					case `shared`      => Ownership.Shared
					case `exclusive`   => Ownership.Exclusive(strength.getValue().asInstanceOf[Int])
				}},
				// Add Durability
				{durability.selection.item match {
				  case `Volatile`   => Durability.Volatile
				  case `Transient`  => Durability.Transient
				  case `Persistent` => Durability.Persistent
				}},
				// Add TransportPriority
				TransportPriority(priority.getValue().asInstanceOf[Int])
		)
				
		println("Updated QoS: " + qos)
	}
	
	private def initDialog = {
		// set Dialog's buttons + spinners values according to qos
		reliability.select(besteffort)
		ownership.select(shared)
		strength.setValue(50)
		durability.selection.item = Volatile
		priority.setValue(50)
	}
	
	override def open = {
		initDialog
		super.open
	}
	
	private val reliability = new ButtonGroup
	private val reliable = new RadioButton("Reliable")
	private val besteffort = new RadioButton("Best Effort")
	private val reliabilityButtons = List(reliable, besteffort)
	reliability.buttons ++= reliabilityButtons
	
	private val ownership = new ButtonGroup
	private val shared = new RadioButton("Shared")
	private val exclusive = new RadioButton("Exclusive")
	private val strength = new javax.swing.JSpinner { setPreferredSize((60,20)) }
	private val ownershipButtons = List(shared, exclusive)
	ownership.buttons ++= ownershipButtons
	
	case class DurabilityKind(name: String)
	object Volatile   extends DurabilityKind("Volatile")
	object Transient  extends DurabilityKind("Transient")
	object Persistent extends DurabilityKind("Persistent")
	private val durability = new ComboBox(List(Volatile, Transient, Persistent)) { 
		renderer = ListView.Renderer(_.name)
		preferredSize = (100,20)
	}
	
	private val priority = new javax.swing.JSpinner { setPreferredSize((60,20)) }

	private val ok = new Button("OK")
	private val cancel = new Button("Cancel")
	
	contents = new BoxPanel(Orientation.Vertical) {
		border = Swing.EmptyBorder(10, 10, 10, 10)

		contents += new BoxPanel(Orientation.Vertical) {
			border = CompoundBorder(TitledBorder(EtchedBorder, "Reliability QoS"), EmptyBorder(5,5,5,10))
			
			contents += new BoxPanel(Orientation.Horizontal) {
				contents ++= reliabilityButtons
			}
		}

		contents += new BoxPanel(Orientation.Vertical) {
			border = CompoundBorder(TitledBorder(EtchedBorder, "Ownership QoS"), EmptyBorder(5,5,5,10))
			
			contents += new BoxPanel(Orientation.Horizontal) {
				contents ++= ownershipButtons
			}
			contents += new GridBagPanel {
				val c = new Constraints
				c.fill = GridBagPanel.Fill.Horizontal
				c.insets = new Insets(0, 5, 0, 5)
				c.anchor = GridBagPanel.Anchor.LineEnd
				
				c.grid = (0, 0)
				layout(new Label("Strength:")) = c
				c.grid = (1, 0)
				layout(Component.wrap(strength)) = c
			}
		}

		contents += new BoxPanel(Orientation.Vertical) {
			border = CompoundBorder(TitledBorder(EtchedBorder, "Durability QoS"), EmptyBorder(5,5,5,10))
			
			contents += new GridBagPanel {
				val c = new Constraints
				c.fill = GridBagPanel.Fill.Horizontal
				c.insets = new Insets(0, 5, 0, 5)
				c.anchor = GridBagPanel.Anchor.LineEnd
				
				c.grid = (0, 0)
				layout(new Label("Durability:")) = c
				c.grid = (1, 0)
				layout(durability) = c
			}
		}

		contents += new BoxPanel(Orientation.Vertical) {
			border = CompoundBorder(TitledBorder(EtchedBorder, "Transport Priority QoS"), EmptyBorder(5,5,5,10))
			
			contents += new GridBagPanel {
				val c = new Constraints
				c.fill = GridBagPanel.Fill.Horizontal
				c.insets = new Insets(0, 5, 0, 5)
				c.anchor = GridBagPanel.Anchor.LineEnd
				
				c.grid = (0, 0)
				layout(new Label("Priority:")) = c
				c.grid = (1, 0)
				layout(Component.wrap(priority)) = c
			}
		}

		contents += new BoxPanel(Orientation.Horizontal) {
			contents += ok
			contents += cancel
		}
	}
	
	listenTo(ok, cancel)
	reactions += {
		case ButtonClicked(`ok`) =>
			println("OK "+durability.selection.item.name)
			updateQos
			close
		case ButtonClicked(`cancel`) =>
			println("Cancel "+durability.selection.item.name)
			close
	}

}