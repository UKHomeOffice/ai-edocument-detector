
import uk.gov.homeoffice.edocument._

@main def main() :Unit = {
  val result :Float = ClassifyScala.classifyImage(
    sys.env("MODEL_PATH"),
    sys.env("TEST_SET_ROOT") + "/evwzxu7dhs.jpg"
  )
  println(s"Edocument result (known bad record): $result")

  val result2 :Float = ClassifyScala.classifyImage(
    sys.env("MODEL_PATH"),
    sys.env("TEST_SET_ROOT") + "/evw25ajxxa.jpg"
  )
  println(s"Edocument result (known edoc): $result2")
}

