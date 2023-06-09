
import uk.gov.homeoffice.edocument._

@main def main() :Unit = {
  val score :Float = Classify.classifyImage(
    "/home/phill/tmp/ai-edocument-detection/ai-edocument-detection/model-trainer-py/saved-model",
    "/home/phill/tmp/ai-edocument-detection/demo-records/bad.jpg"
  )
  println(s"Edocument confidence: $score")
}

