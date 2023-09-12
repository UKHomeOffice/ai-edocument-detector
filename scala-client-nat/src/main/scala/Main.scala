
import uk.gov.homeoffice.edocument._

@main def main() :Unit = {
  val result = ClassifyScala.classifyImage(
    sys.env("MODEL_PATH"),
    sys.env("TEST_SET_ROOT") + "/evwzxu7dhs.jpg"
  )
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

  val result2 = ClassifyScala.classifyImage(
    sys.env("MODEL_PATH"),
    sys.env("TEST_SET_ROOT") + "/evw25ajxxa.jpg"
  )
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

