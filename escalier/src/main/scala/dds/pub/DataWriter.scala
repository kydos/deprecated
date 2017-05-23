package dds.pub

import dds.{Entity, Topic}
import dds.qos.{DataWriterQos, PublisherQos, Partition}
import swing.Reactions


object DataWriter {
    var defaultPublisher: Publisher = null

      def apply[T](pub: Publisher,
                 t: Topic[T],
                 qos: DataWriterQos)
                (implicit m: Manifest[T]) = {
        pub.createDataWriter(t, qos)
    }

    def apply[T](pub: Publisher, t: Topic[T])
              (implicit m: Manifest[T]) = {
        pub.createDataWriter(t, DataWriterQos())
    }

      def apply[T](t: Topic[T],
                 qos: DataWriterQos)
                (implicit m: Manifest[T]) = {
        if (defaultPublisher == null)
            defaultPublisher = Publisher(t.dp)
        defaultPublisher.createDataWriter(t, qos)
    }

    def apply[T](t: Topic[T],
                 qos: DataWriterQos,
                 pubQos: PublisherQos)
                (implicit m: Manifest[T]) = {
        def pub = Publisher(t.dp, pubQos)
        pub.createDataWriter(t, qos)
    }

    def apply[T](t: Topic[T],
                 qos: DataWriterQos,
                 partitions: String*)
                (implicit m: Manifest[T]) = {
        def pub = Publisher(t.dp, PublisherQos(partitions toList))
        pub.createDataWriter(t, qos)
    }

    def apply[T](t: Topic[T])
              (implicit m: Manifest[T]) = {
        if (defaultPublisher == null)
            defaultPublisher = Publisher(t.dp)
        defaultPublisher.createDataWriter(t, DataWriterQos())
    }
}

abstract class DataWriter[T](val pub: Publisher,
                             val t: Topic[T],
                             val qos: DataWriterQos)
                            (implicit m: Manifest[T]) extends Entity {

    type Peer = DDS.DataWriter
    val reactions = new Reactions.Impl

    def write(sample: T): Unit
    
    def ! (sample: T): Unit = this write sample

    def << (sample: T): Unit = this ! sample
    
    def write(samples: List[T]) : Unit = samples foreach (this write _)

}