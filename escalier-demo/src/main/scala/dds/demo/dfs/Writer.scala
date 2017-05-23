package dds.demo.dfs

import java.io.{File,  FileInputStream}
import dds._
import dds.pub._
import org.opensplice.demo.{DDSFile, OctetSequence_tHolder}

object Writer {
  def main(args: Array[String])  {

    // Note: This implementation is just an high level sketch.
    // The QoS management is missing w.r.t. Durability and Partitons.
    val fileTopic = Topic[DDSFile]("DDSFileTopic")
    val fileWriter = DataWriter[DDSFile](fileTopic)

    do {
      print("Enter the directory path: ")
      val dir = readLine()
      print("Enter the file name: ")
      val file = readLine()

      val f = new File(dir + File.separator + file)
      // Notice this is actually limiting the size of the
      // file being copied on a single shot to the size of
      // a signed Int. If bigger file need to be sent over,
      // anyway, splitting the file into different chunks
      // is anyway a better idea
      val fileSize: Int = (f.length()).toInt
      val istream = new FileInputStream(f)
      val data = new Array[Byte](fileSize)
      // Read the file all at once
      istream.read(data)
      fileWriter.write(new DDSFile(file, data))
      print("Type 1 to contine 0 to exit:")
    } while (readInt() != 0)

  }
}