
import uk.gov.homeoffice.edocument._
import java.nio.file._
import java.nio.charset._
import scala.collection.JavaConverters._
import java.io._

@main def main() :Unit =

  val path = "/tmp/images"
  val model = "/tmp/saved-model"
  val outputFile = "/tmp/output.log"

  println("started")
  val files = Files.list(Paths.get(path)).toList.asScala.map(_.toString).sorted

  val output = Files.readString(Paths.get(outputFile), StandardCharsets.UTF_8)
  val continueFrom = output.count(_ == '\n')

  println("total files: " + files.length + ", continueFrom: " + continueFrom)
  val remainingFiles = files.drop(continueFrom.toInt)

  remainingFiles.zipWithIndex.foreach { (filename, idx) =>
    val fw = new FileWriter(outputFile, true)
    val x = ClassifyScala.classifyImage(model, filename.toString)
    fw.write(s"$idx,$filename,$x\n")
    fw.close()
  }
  println("finished")

