
import matplotlib.pyplot as plt
import numpy as np
import PIL
import tensorflow as tf

from tensorflow import keras
from tensorflow.keras import layers
from tensorflow.keras.models import Sequential

import pathlib

batch_size = 32
img_height = 180
img_width = 180

print("started %s" % tf.__version__)

data_dir = pathlib.Path("/tmp/images")

saved_model_path = '/tmp/model-path'
model = tf.keras.models.load_model(saved_model_path)

model.summary()

class_names = ['edocuments', 'not-edocuments']

def classify(filepath):
    img = tf.keras.utils.load_img(
        filepath, target_size=(img_height, img_width)
    )

    img_array = tf.keras.utils.img_to_array(img)
    img_array = tf.expand_dims(img_array, 0) # Create a batch

    predictions = model.predict(img_array)
    score = tf.nn.softmax(predictions[0])

    print(
        "image {} most likely belongs to {} with a {:.2f} percent confidence."
        .format(filepath, class_names[np.argmax(score)], 100 * np.max(score))
    )

    print("raw index 0: " + str(predictions[0]));
    print("raw index 00: " + str(predictions[0][0]));
    print("raw index 01: " + str(predictions[0][1]));

## good demo record
goodRecord = "/tmp/images/good.jpg"
classify(goodRecord)

badRecord = "/tmp/images/bad.jpg"
classify(badRecord)

print("finished")
