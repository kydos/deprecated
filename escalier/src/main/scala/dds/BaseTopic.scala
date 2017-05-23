package dds

import DDS.TopicDescription

abstract  class BaseTopic[T](val dp: DomainParticipant,
                             val name: String)
                            (implicit val manifest: Manifest[T]) extends Entity {
  type Peer <: DDS.TopicDescription
  val typeSupport: DDS.TypeSupport
}