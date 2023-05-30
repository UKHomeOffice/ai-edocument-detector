
async function classify(image) {

  const labels = [ 'edocument', 'not-edocument' ];
  const model = await tf.loadLayersModel('./saved-model-tfjs/model.json');
  const imageX = tf.browser.fromPixels(document.getElementById('myCanvas'));
  const resizedImage = imageX.resizeBilinear([180,180]);
  const expanded = tf.expandDims(resizedImage, 0);
  const prediction = model.predict(expanded).dataSync();
  const results = [];

  edocumentConfidence = prediction[0];
  nonEDocumentConfidence = prediction[1];

  //for (let i = 0; i < prediction.length; i++) {
  //  results.push({ score: prediction[i], label: labels[i] });
  //  alert("label: " + labels[i] + ": prediction=" + prediction[i]);
  //}

  //document.getElementById('micro-out-div').innerText = prediction;
  if (edocumentConfidence > nonEDocumentConfidence && edocumentConfidence > 1.0) {
    document.getElementById('micro-out-div').innerText = "This is an edocument! Confidence: " + edocumentConfidence;
    document.getElementById('micro-out-div').className = "edocument";
  } else {
    document.getElementById('micro-out-div').innerText = "This image is acceptable ( edocument=" + edocumentConfidence + ", non-edocument: " + nonEDocumentConfidence + ")";
    document.getElementById('micro-out-div').className = "notedocument";
  }
}

//run();
