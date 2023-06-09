
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

saved_model_path = '/tmp/saved-model'
model = tf.keras.models.load_model(saved_model_path)

model.summary()

class_names = ['edocuments', 'not-edocuments']

i = 0

def classify(filepath):
    global i
    skip = False
    try:
        img = tf.keras.utils.load_img(
            filepath, target_size=(img_height, img_width)
        )
    except:
        return ('err', 'err')

    img_array = tf.keras.utils.img_to_array(img)
    img_array = tf.expand_dims(img_array, 0) # Create a batch

    predictions = model.predict(img_array)
    print("%s, %s, %s, %s" % (
        i,
        filepath,
        str(predictions[0][0]),
        str(predictions[0][1])
    ))
    i = i + 1
    return (predictions[0][0], predictions[0][1])

allResults = []

os.chdir("/tmp/images/")
for filename in os.listdir():
    (edocumentconfidence, notedocumentconfidence) = classify("/tmp/images/" + filename)
    if (edocumentconfidence == 'err'):
        continue
    te = TestEntry(filename, True, edocumentconfidence, notedocumentconfidence)
    allResults.append(te)

print("analysis")
for r in allResults:
    print(r.filename + "," + str(r.isEDocument) + "," + str(r.edocumentconfidence) + "," + str(r.notedocumentconfidence))


print("finished")
