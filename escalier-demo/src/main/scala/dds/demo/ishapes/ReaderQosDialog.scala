package dds.demo.ishapes

import scala.swing._
import scala.swing.event._
import BorderPanel.Position._
import Swing._
import dds.qos._

object ReaderQosDialog extends Dialog {
	
	title = "Reader QoS"

	var qos: DataReaderQos = DataReaderQos()
		
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
				// Add History
				{history.selected.get match {
					case `keepAll`  => History.KeepAll
					case `keepLast` => History.KeepLast(depth.getValue().asInstanceOf[Int])
				}},
				// Add Durability
				{durability.selection.item match {
					case `Volatile`   => Durability.Volatile
					case `Transient`  => Durability.Transient
					case `Persistent` => Durability.Persistent
				}}
		)
	}
	
	private def initDialog = {
		// set Dialog's buttons + spinners values according to qos
		reliability.select(besteffort)
		ownership.select(shared)
		strength.setValue(50)
		history.select(keepLast)
		depth.setValue(1)
		durability.selection.item = Volatile
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
	private val strength = new javax.swing.JSpinner
	private val ownershipButtons = List(shared, exclusive)
	ownership.buttons ++= ownershipButtons
	
	private val history = new ButtonGroup
	private val keepAll = new RadioButton("Keep All")
	private val keepLast = new RadioButton("Keep Last")
	private val depth = new javax.swing.JSpinner
	private val historyButtons = List(keepAll, keepLast)
	history.buttons ++= historyButtons
	
	case class DurabilityKind(name: String)
	object Volatile   extends DurabilityKind("Volatile")
	object Transient  extends DurabilityKind("Transient")
	object Persistent extends DurabilityKind("Persistent")
	private val durability = new ComboBox(List(Volatile, Transient, Persistent)) { 
		renderer = ListView.Renderer(_.name) 
	}
	
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
			contents += new BoxPanel(Orientation.Horizontal) {
				contents += new Label("Strength:")
				contents += Component.wrap(strength)
			}
		}

		contents += new BoxPanel(Orientation.Vertical) {
			border = CompoundBorder(TitledBorder(EtchedBorder, "History QoS"), EmptyBorder(5,5,5,10))
			
			contents += new BoxPanel(Orientation.Horizontal) {
				contents ++= historyButtons
			}
			contents += new BoxPanel(Orientation.Horizontal) {
				contents += new Label("Depth:")
				contents += Component.wrap(depth)
			}
			contents += Component.wrap(depth)
		}

		contents += new BoxPanel(Orientation.Vertical) {
			border = CompoundBorder(TitledBorder(EtchedBorder, "Durability QoS"), EmptyBorder(5,5,5,10))
			
			contents += durability
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