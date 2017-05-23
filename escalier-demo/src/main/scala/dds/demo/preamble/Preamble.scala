package dds.demo.preamble

/**
 * This is the Preamble object for all the demos.
 */

import org.opensplice.demo.ShapeType
object Preamble {
  implicit def shapeType2String(s: ShapeType) = "("+ s.color +", ("+ s.x + ", "+ s.y +"), "+ s.shapesize +")"
}