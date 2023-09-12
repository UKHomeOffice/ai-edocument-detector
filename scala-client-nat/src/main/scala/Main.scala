
import uk.gov.homeoffice.edocument._
import java.nio._
import java.nio.file._
import java.nio.charset._
import scala.collection.JavaConverters._
import scala.util.Try
import java.io.FileWriter

@main def all() :Unit =

  // Status log allows us to resume from failures on very long testing runs (was useful when CPU bound)
  val trackingFile = "/tmp/status.log"
  val imagesAlreadyProcessed = Try(Files.readAllLines(Paths.get(trackingFile)).asScala.toList).toEither match {
    case Right(lines) => lines.map(_.split(',').headOption.getOrElse("")).toList
    case Left(e) =>
      println(s"unable to read file: $e. Starting from beginning")
      Nil
  }
  val fw = new FileWriter(trackingFile, true)

  val classifier = new ClassifyScala(sys.env("MODEL_PATH"))

  Files.list(Paths.get(sys.env("TEST_SET_ROOT"))).iterator.asScala.toList
    .sorted
    .filter { !Files.isDirectory(_) }
    .filter { n => List(".jpg", ".jpeg", ".png").exists(d => n.toString.endsWith(d)) }
    .filter { n => !imagesAlreadyProcessed.contains(n.toString) }
    .zipWithIndex
    .foreach { (file, idx) =>
      val results :List[Classification] = classifier.classifyImage(file.toString)
      val areEDocConf = results.collect { case StandardClassification("are", true, areEdocConfidence) => areEdocConfidence }.headOption.getOrElse("")
      val outputLine = results.headOption match {
        // columns are: filename,are-edoc confidence, nationality, isEDoc, isGenuine
        case Some(NonGenuineDocument(confidence)) => s"${file},,,,$confidence,non-genuine"
        case Some(StandardClassification(nationality, true, confidence)) => s"${file},$areEDocConf,$nationality,true,$confidence,"
        case Some(StandardClassification(nationality, false, confidence)) => s"${file},$areEDocConf,$nationality,false,$confidence,"
        case Some(ClassificationFailure) => s"${file},,,false,,classification-failure"
        case None => s"${file},,,false,,no-results"
      }
      println(s"$idx: $outputLine")
      fw.write(outputLine + '\n')
    }

  fw.close()

@main def quickSample() :Unit = {
  val classifier = new ClassifyScala(sys.env("MODEL_PATH"))
  val result = classifier.classifyImage(sys.env("TEST_SET_ROOT") + "/evwzxu7dhs.jpg")
  println(s"Edocument result (known acceptable saudi doc):")
  result.foreach { r => println(s"   $r") }

  result.headOption match {
    case Some(NonGenuineDocument(confidence)) =>
      println(s"Classification: Reject this non-genuine document. confidence score: $confidence")
    case Some(StandardClassification(nationality, true, confidence)) =>
      println(s"Classification: EDocument! Request new image! confidence score: $confidence (nationality: $nationality)")
    case Some(StandardClassification(nationality, false, confidence)) =>
      println(s"Classification: Acceptable $nationality passport. confidence score: $confidence")
    case Some(ClassificationFailure) =>
      println("No results. Bad or incompatible image")
    case None =>
      println("No results. Bad or incompatible image")
  }

  val result2 = classifier.classifyImage(sys.env("TEST_SET_ROOT") + "/evw25ajxxa.jpg")
  println(s"Edocument result (known are edoc)")
  result2.foreach { r => println(s"   $r") }

  result2.headOption match {
    case Some(NonGenuineDocument(confidence)) =>
      println(s"Classification: Reject this non-genuine document. confidence score: $confidence")
    case Some(StandardClassification(nationality, true, confidence)) =>
      println(s"Classification: EDocument! Request new image! confidence score: $confidence (nationality: $nationality)")
    case Some(StandardClassification(nationality, false, confidence)) =>
      println(s"Classification: Acceptable $nationality passport. confidence score: $confidence")
    case Some(ClassificationFailure) =>
      println("No results. Bad or incompatible image")
    case None =>
      println("No results. Bad or incompatible image")
  }
}

