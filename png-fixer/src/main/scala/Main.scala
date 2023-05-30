import java.nio.file.{Files, Paths}
import scala.collection.JavaConverters._
import java.io._

val pngSignature = "PNG".getBytes.toList

@main def helloWorld() =

  println("started")

  val files = Files.list(Paths.get("/home/phill/tmp/ai-edocument-detection/production-training-set/edocuments")).iterator().asScala.toList.filter { file =>
    println(s"file: $file")
    val stream = new BufferedInputStream(FileInputStream(file.toString))
    val extractedByteBuffer = new Array[Byte](3)

    stream.mark(0)
    stream.skip(1)
    stream.read(extractedByteBuffer)
    stream.reset

    (extractedByteBuffer.toList == pngSignature)
  }.map { file =>
    val noExt = file.getFileName().toString.split("\\.")(0)
    println(s"mv ${file.getFileName} ${noExt}.png")
    println(s"mogrify -format jpg ${noExt}.png")
    println(s"rm ${noExt}.png")
  }

  println("finished")
