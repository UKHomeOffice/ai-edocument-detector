
import matplotlib.pyplot as plt
import numpy as np
import PIL
import tensorflow as tf

from tensorflow import keras
from tensorflow.keras import layers
from tensorflow.keras.models import Sequential

import pathlib

print("started %s" % tf.__version__)

data_dir = pathlib.Path("/tmp/images")
image_count = len(list(data_dir.glob('*/*.jpg')))
print("images detected: %s" % image_count)

batch_size = 128
img_height = 180
img_width = 180

train_ds = tf.keras.utils.image_dataset_from_directory(
  data_dir,
  validation_split=0.2,
  subset="training",
  seed=123,
  image_size=(img_height, img_width),
  interpolation='bilinear',
  batch_size=batch_size
)

val_ds = tf.keras.utils.image_dataset_from_directory(
  data_dir,
  validation_split=0.2,
  subset="validation",
  seed=123,
  image_size=(img_height, img_width),
  interpolation='bilinear',
  batch_size=batch_size
)

class_names = train_ds.class_names
print("class names: ", class_names)
num_classes = len(class_names)

model = Sequential([
  layers.Rescaling(1./255, input_shape=(img_height, img_width, 3)),
  layers.Conv2D(16, 3, padding='same', activation='relu'),
  layers.MaxPooling2D(),
  layers.Conv2D(32, 3, padding='same', activation='relu'),
  layers.MaxPooling2D(),
  layers.Conv2D(64, 3, padding='same', activation='relu'),
  layers.MaxPooling2D(),
  layers.Conv2D(128, 3, padding='same', activation='relu'),
  layers.MaxPooling2D(),
  layers.Conv2D(256, 3, padding='same', activation='relu'),
  layers.MaxPooling2D(),
  layers.Flatten(),
  layers.Dense(256, activation='relu'),
  layers.Dense(num_classes)
])

model.compile(
  optimizer='adam',
  loss=tf.keras.losses.SparseCategoricalCrossentropy(from_logits=True),
  metrics=['accuracy']
)

model.summary()

epochs=26

history = model.fit(
  train_ds,
  validation_data=val_ds,
  epochs=epochs
)

epochs = range(1, len(history.history["loss"]) + 1)
loss = history.history["loss"]
val_loss = history.history["val_loss"]
plt.figure()
plt.plot(epochs, loss, "bo", label = "Training loss")
plt.plot(epochs, val_loss, "b", label = "Validation loss")
plt.title("epoch vs loss curve for ARE edocument detection")
plt.legend()
plt.savefig("/results/epoch-loss-curve.png")

# Now we have done the training export the model
# to a file. This will be copied our client apps.

saved_model_path = '/tmp/model-path/model.keras'
model.save(saved_model_path)

# exporting Javascript models is now disabled.
#import tensorflowjs as tfjs
#saved_model_js_path = '/tmp/model-path-tfjs'
#tfjs.converters.save_keras_model(model, saved_model_js_path)

print("finished")
