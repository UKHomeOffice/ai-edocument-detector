
# AI E-document detection demonstration

The Home Office has numerous online services that request users upload images of their passports, such as Electronic Visa Waiver. For legal and policy reasons, we require a photo of the physical passport. Some countries, such as United Arab Emirates issue customers a "edocument" copy of their passport. However this is not a document we can accept at the current time.

This project aims to identify edocuments and photographs of edocuments uploaded to our systems so we can reduce delays and improve the customer experience. This is an AI solution using Tensorflow 2.

This git repo is experimental time boxed work to evaluate the accuracy that can be gained from using AI for this problem, but also whether AI tools can be encorporated into our technology stacks. This is why there are clients in Scala and Javascript, two main languages used at the HomeOffice.

Common Structure
=================

Most of the projects share a docker image with Tensorflow, Python and JVM dependencies included. You can build the image once. Our javascript app requires a separate image.

```
docker build -t ai-edocument-detector .

cd javascript-client
docker build -t ai-edocument-detector-js -f Dockerfile.build .
```

We keep production training images outside of the github repository for obvious reasons. Our scripts use environmental variables to allow easy configuration.

```
export AI_EDOCUMENT_DETECTOR_ROOT=$PWD                                  # where ever this repo is checked out to.
export MODEL_PATH=$(AI_EDOCUMENT_DETECTOR_ROOT)/saved-model             # where the model is written to and read from.
export MODEL_PATH_TSJS=$(AI_EDOCUMENT_DETECTOR_ROOT)/saved-model-tfjs   # where the javascript model is written to and read from.
export TRAINING_SET_ROOT=/production/images                             # somewhere outside the github repo where passport images are kept
```

# Projects

model-trainer-py
  * This is a python project that uses Tensorflow to build an AI model. It expects TRAINING_SET_ROOT to point to a directory with two subdirectories inside. An "edocuments" directory and a "not-edocuments" directory. Using these two directories the AI will train a model to recognise the images inside.
  * To start the training, cd into the directory and run `./train.sh`
  * When the program has finished it should have created a model at `$MODEL_PATH` and a Javascript encoded model at `$(MODEL_PATH_TSJS)`. These directories can be bundled with our clients.

python-client
  * This project takes an image, classifies an image to show our AI in action on data it hasnt seen before.
  * To see the client in action, cd into the directory and run `./classify.sh`.

javascript-client
  * Our customer apps are written in Javascript therefore demonstrating the technology in the language we use is important. This visual website demo is used to explain to the business what the user interface would look like when customers are uploading their images. This working MVP demo can also be hosted in our UAT environment where internal decision makers can explore and evaluate the AI themselves.
  * This app has it's own Dockerfile. Build instructions above.
  * Start the image with `./demo.sh` and visit https://localhost:1234 in your browser.

scala-client
  * As our internal backend components are written in Scala this client is important to us.
  * Start `./classify.sh`

threshold-analysis
  * A simple python script that allows us to collect the confidence scores from a large collection of images so we can explore what happens at different thresholds.
  * Run `./analyse-set.sh` when TRAINING_SET_ROOT points to a large directory of images.

