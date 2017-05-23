package dds.demo.dfs

import java.io.{File,  FileInputStream}
import dds._
import dds.sub._
import event.DataAvailable
import org.opensplice.demo.{DDSFile, OctetSequence_tHolder}
import qos._

object Reader {
  def main(args: Array[String]) {
    val fileTopic = Topic[DDSFile]("DDSFileTopic")
    val drQos = DataReaderQos() <= History.KeepAll
    
    val fileReader = DataReader[DDSFile](fileTopic)

    fileReader.reactions += {
      case DataAvailable(dr) => {
        println("----------------------------------------")
        (fileReader history) foreach (f => println("File: "+ f.name + "\nSize:"+ f.data.length +"\n"))
      }
    }
  }

}