
import matplotlib.pyplot as plt
import numpy as np
import PIL
import tensorflow as tf

from tensorflow import keras
from tensorflow.keras import layers
from tensorflow.keras.models import Sequential

from tensorflow.python.ops.numpy_ops import np_config
np_config.enable_numpy_behavior()

import pathlib
import os

class TestEntry:

    def __init__(self, filename, isEDocument, edocumentconfidence, notedocumentconfidence):
        self.filename = filename
        self.isEDocument = isEDocument
        self.edocumentconfidence = edocumentconfidence
        self.notedocumentconfidence = notedocumentconfidence

batch_size = 32
img_height = 180
img_width = 180

print("started %s" % tf.__version__)

data_dir = pathlib.Path("/tmp/images")

saved_model_path = '/tmp/app/saved-model'
model = tf.keras.models.load_model(saved_model_path)

model.summary()

class_names = ['edocuments', 'not-edocuments']

def classify(filepath):
    print("classifying: " + filepath)
    img = tf.keras.utils.load_img(
        filepath, target_size=(img_height, img_width)
    )

    img_array = tf.keras.utils.img_to_array(img)
    img_array = tf.expand_dims(img_array, 0) # Create a batch

    predictions = model.predict(img_array)
    print("predictions dim: " + str(predictions.ndim))
    print("predictions size: " + str(predictions.size))
    print("predictions shape: " + str(predictions.shape))
    print("predictions: " + str(predictions[0][0]) + " and " + str(predictions[0][1]))
    return (predictions[0][0], predictions[0][1])

allResults = []

os.chdir("/tmp/images/edocuments/")
for filename in os.listdir():
    (edocumentconfidence, notedocumentconfidence) = classify("/tmp/images/edocuments/" + filename)
    te = TestEntry(filename, True, edocumentconfidence, notedocumentconfidence)
    allResults.append(te)

os.chdir("/tmp/images/notedocuments/")
for filename in os.listdir():
    (edocumentconfidence, notedocumentconfidence) = classify("/tmp/images/notedocuments/" + filename)
    te = TestEntry(filename, False, edocumentconfidence, notedocumentconfidence)
    allResults.append(te)

print("analysis")
for r in allResults:
    print(r.filename + "," + str(r.isEDocument) + "," + str(r.edocumentconfidence) + "," + str(r.notedocumentconfidence))


print("finished")
