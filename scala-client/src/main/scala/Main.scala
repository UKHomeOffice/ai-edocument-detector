
import uk.gov.homeoffice.edocument._

@main def main() :Unit = {
  val result :ImageResult = ClassifyScala.classifyImage(
    "/tmp/saved-model",
    "/tmp/images/bad.jpg"
  )
  println(s"Edocument result: $result")
}

