package uk.gov.homeoffice.edocument;

import java.util.HashMap;
import java.util.Map;

import org.tensorflow.*;
import org.tensorflow.ndarray.buffer.FloatDataBuffer;
import org.tensorflow.op.Ops;
import org.tensorflow.op.core.Constant;
import org.tensorflow.op.core.ExpandDims;
import org.tensorflow.op.dtypes.Cast;
import org.tensorflow.op.image.DecodeJpeg;
import org.tensorflow.op.image.ResizeBilinear;
import org.tensorflow.op.io.ReadFile;
import org.tensorflow.types.*;

public class Classify {

  public static float classifyImage(String modelPath, String testImagePath) {

      Graph g = new Graph();
      Session s = new Session(g);
      Ops tf = Ops.create(g);

      final int H = 180;
      final int W = 180;

      SavedModelBundle model = SavedModelBundle.load(modelPath, "serve");

      Constant<TString> fileName = tf.constant(testImagePath);
      ReadFile readFile = tf.io.readFile(fileName);
      DecodeJpeg.Options options = DecodeJpeg.channels(3L);
      DecodeJpeg decodeImage = tf.image.decodeJpeg(readFile.contents(), options);
      Cast<TFloat32> casted = tf.dtypes.cast(decodeImage.image(), TFloat32.class);
      ExpandDims expanded = tf.expandDims(casted.asOutput(), tf.constant(0));
      ResizeBilinear rsz = tf.image.resizeBilinear(expanded.output(), tf.constant(new int[] { H,  W}));
      Tensor inputTensor = s.runner().fetch(rsz.resizedImages()).run().get(0);

      System.out.println("inputTensor:" + inputTensor.shape() + " rank " + inputTensor.rank());

      Map<String, Tensor> feedDict = new HashMap<>();
      feedDict.put("rescaling_1_input", inputTensor);

      model.signatures().forEach((a) -> System.out.println("a: " + a));
      Result result = model.function("serving_default").call(feedDict);
      System.out.println("Got to the end without an exception");

      for (int i = 0; i < result.size(); i++) {
          System.out.println("result get number: " + result.get(i).dataType().getNumber());
          FloatDataBuffer fdb = result.get(i).asRawTensor().data().asFloats();
          System.out.println("float buffer size: " + fdb.size());
          System.out.println("edocument confidence: " + fdb.getFloat(0));
          System.out.println("not edocument confidence: " + fdb.getFloat(1));
          return fdb.getFloat(0); // TODO.
      }
      return 0f;
  }
}
