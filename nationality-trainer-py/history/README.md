
History of previous model implementations
===

train.1.py (8th Sept 2023)
  * best validation loss: 0.3062
  first implementation, based on a standard image classification problem
train.2.py (8th Sept 2023)
  * best validation loss: 0.3552 (accuracy: 0.9401)
  after noticing a lot of overfitting we add a dropout layer to help improve generalisation
  also remove 256 layer, choking the model so it is forced to generalise and cannot memorise so much
train.3.py (8th Sept 2023)
  * best validation loss: 0.2357 (accuracy: 95.71)
  reintroduce 256 layer
  shift dropout position so it is more prominent
train.3.py (8th Sept 2023)
  * best validation loss: 0.1545 (accuracy: 96.23)
  replace 256 layer with 2 128 layers. A deep network forces more generalisation
  happy to take these results to a wider regression testing script to check the quality
