package uk.gov.homeoffice.edocument

import java.util.HashMap
import java.util.Map

import org.tensorflow.*
import org.tensorflow.ndarray.buffer.FloatDataBuffer
import org.tensorflow.op.Ops
import org.tensorflow.op.core.Constant
import org.tensorflow.op.core.ExpandDims
import org.tensorflow.op.dtypes.Cast
import org.tensorflow.op.image.DecodeJpeg
import org.tensorflow.op.image.ResizeBilinear
import org.tensorflow.op.io.ReadFile
import org.tensorflow.types.*

import scala.jdk.CollectionConverters.*
import scala.collection.immutable.HashMap as ScalaHashMap

sealed trait Classification:
  val confidence :Float

case class StandardClassification(nationality :String, isEdocument :Boolean,val confidence :Float) extends Classification
case class NonGenuineDocument(val confidence :Float) extends Classification
case object ClassificationFailure extends Classification { val confidence = -100f }

object ClassifyScala:

  def classifyImage(modelPath :String, testImagePath :String) :List[Classification] =

    val graph = new Graph()
    val session = new Session(graph)
    val tf = Ops.create(graph)

    val IMAGE_HEIGHT = 220
    val IMAGE_WIDTH = 220

    val model = SavedModelBundle.load(modelPath, "serve")

    val filename :Constant[TString] = tf.constant(testImagePath)
    val readFile = tf.io.readFile(filename)

    val jpegDecodeOptions :DecodeJpeg.Options = DecodeJpeg.channels(3)
    val decodeImage = tf.image.decodeJpeg(readFile.contents, jpegDecodeOptions)
    val cast = tf.dtypes.cast(decodeImage.image, classOf[TFloat32])
    val expand = tf.expandDims(cast.asOutput, tf.constant(0))
    val resizeBilinear = tf.image.resizeBilinear(expand.output, tf.constant(List(IMAGE_HEIGHT, IMAGE_WIDTH).toArray))
    val inputTensor = session.runner().fetch(resizeBilinear.resizedImages).run.get(0)

    session.runner().fetch(resizeBilinear.resizedImages).run.keySet().asScala.foreach { v => println(s"GOT $v") }
    //graph.operations.asScala.foreach { operation => println("Operation Name: " + operation.name()) }
    println(s"input tensor: $inputTensor")

    val feedDict = new java.util.HashMap[String, Tensor](
      ScalaHashMap("rescaling_input" -> inputTensor).asJava
    )

    val predictions :Result = model.function("serving_default").call(feedDict)

    println(predictions)
    val fdb = predictions.get(0).asRawTensor.data.asFloats
    List(
      StandardClassification("are", false, fdb.getFloat(0)),
      StandardClassification("are", true, fdb.getFloat(1)),
      StandardClassification("bhr", false, fdb.getFloat(2)),
      StandardClassification("bhr", true, fdb.getFloat(3)),
      StandardClassification("kwt", false, fdb.getFloat(4)),
      StandardClassification("kwt", true, fdb.getFloat(5)),
      NonGenuineDocument(fdb.getFloat(6)),
      StandardClassification("omn", false, fdb.getFloat(7)),
      StandardClassification("qat", false, fdb.getFloat(8)),
      StandardClassification("sau", false, fdb.getFloat(9)),
      StandardClassification("sau", true, fdb.getFloat(10)),
    ).sortWith {
      case (a :Classification, b :Classification) => b.confidence <= a.confidence
    }
