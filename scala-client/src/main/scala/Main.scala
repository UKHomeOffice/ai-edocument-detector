
import uk.gov.homeoffice.edocument._

@main def main() :Unit = {
  val score :Float = Classify.classifyImage(
    "/tmp/saved-model",
    "/tmp/images/bad.jpg"
  )
  println(s"Edocument confidence: $score")
}

