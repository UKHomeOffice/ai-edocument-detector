
# AI E-document detection demonstration

The Home Office has numerous online services that request users upload images of their passports, such as Electronic Visa Waiver. For legal and policy reasons, we require a photo of the physical passport. Some countries, such as United Arab Emirates issue customers a "edocument" copy of their passport. However this is not a document we can accept at the current time.

This project aims to identify edocument images uploaded to our system so we can reduce delays and improve the customer experience. Therefore we are evaluating the use of AI to automatically detect edocuments. We use Tensorflow 2 to build our models.

# Projects

model-trainer-py
  * This is a python project that uses Tensorflow to build an AI model. It is loaded with 200+ edocuments and 200 regular passport images (not provided). Once the model is built it is saved in a few different formats so we can use the model from other programming languages.

python-client
  * This project takes an image, classifies an image to show our AI in action on data it hasnt seen before.

javascript-client
  * Our customer apps are written in Javascript therefore demonstrating the technology in the language we use is important. This visual website demo is used to explain to the business what the user interface would look like. This working MVP demo can also be hosted in our UAT environment where caseworkers can explore and evaluate it's effectiveness.

threshold-analysis
  * A simple script that allows us to collect the confidence scores from a large collection of images so we can set a programmatic threshold we are happy with.

Common Structure
=================
We use two docker images which all our mini-projects share. You only have to build them once.

```
docker build -t ai-edocument-detector .

cd javascript-client
docker build -t ai-edocument-detector-js .
```

We keep production training images outside of the github repository for obvious reasons. We expect two directories. One with edocuments and one for non-edocuments. Our scripts assume the environmental variables that point to those paths are provided.

```
export AI_EDOCUMENT_DETECTOR_ROOT=$PWD       # where ever this repo is checked out to
export TRAINING_SET_ROOT=/tmp/edocuments     # test images in two folders, edocuments and not-edocuments.
export TRAINING_SET_DEMO_RECORDS=/tmp/demo-records  # expects a good.jpg and bad.jpg example files.
export TRAINING_SET2_ROOT=/tmp/notedocuments
export MODEL_PATH=/tmp/saved-model
export MODEL_PATH_JS=/tmp/saved-model-js

```

