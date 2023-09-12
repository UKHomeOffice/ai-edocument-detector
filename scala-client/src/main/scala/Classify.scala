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

object ClassifyScala:

  def classifyImage(modelPath :String, testImagePath :String) :Float =

    val graph = new Graph()
    val session = new Session(graph)
    val tf = Ops.create(graph)

    val IMAGE_HEIGHT = 180
    val IMAGE_WIDTH = 180

    val model = SavedModelBundle.load(modelPath, "serve")

    val filename :Constant[TString] = tf.constant(testImagePath)
    val readFile = tf.io.readFile(filename)

    val jpegDecodeOptions :DecodeJpeg.Options = DecodeJpeg.channels(3)
    val decodeImage = tf.image.decodeJpeg(readFile.contents, jpegDecodeOptions)
    val cast = tf.dtypes.cast(decodeImage.image, classOf[TFloat32])
    val expand = tf.expandDims(cast.asOutput, tf.constant(0))
    val resizeBilinear = tf.image.resizeBilinear(expand.output, tf.constant(List(IMAGE_HEIGHT, IMAGE_WIDTH).toArray))
    val inputTensor = session.runner().fetch(resizeBilinear.resizedImages).run.get(0)

    println(s"input tensor: $inputTensor")

    val feedDict = new java.util.HashMap[String, Tensor](
      ScalaHashMap("rescaling_1_input" -> inputTensor).asJava
    )

    val predictions :Result = model.function("serving_default").call(feedDict)

    println(predictions)
    val fdb = predictions.get(0).asRawTensor.data.asFloats
    fdb.getFloat(0)
