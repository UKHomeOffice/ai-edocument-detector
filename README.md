
# AI E-document detection demonstration

The Home Office has numerous online services that request users upload images of their passports, such as Electronic Visa Waiver. For legal and policy reasons, we require a photo of the physical passport. Some countries, such as United Arab Emirates issue customers a "edocument" copy of their passport. However this is not a document we can accept.

This project aims to identify edocument images uploaded to our system so we can reduce delays and improve the customer experience, as well as reduce caseworking effort. It should also be mentioned that EVWs need to be purchased 48 hours before travelling and the edocument is issued before physical passports arrive, so as well as honest mistakes, some customers have made interesting edits to edocument images before they were uploaded.

Therefore we are evaluating the use of AI to automatically detect edocuments. We use Tensorflow 2 to build our models.

# Projects

model-trainer-py
  * This is a python project that uses Tensorflow to build an AI model. It is loaded with 200+ edocuments and 200 regular passport images (not provided). Once the model is built it is saved in a few different formats so we can use the model from other programming languages.

python-client
  * This project takes an image, classifies an image to show our AI in action on data it hasnt seen before.

javascript-client
  * Our customer apps are written in Javascript therefore demonstrating the technology in the language we use is important. This visual website demo is used to explain to the business what the user interface would look like. This working MVP demo can also be hosted in our UAT environment where caseworkers can explore and evaluate it's effectiveness.

png-fixer
  * A not-very-noteworthy project used to identify and fix some png files and prepare our test training set.
  * Use `sbt run` for this Scala project although it isn't very useful outside of training set.

Directory Structure
=================
Outside of the git repository we expect a production training set directory with two directories. `edocuments` and `notedocuments`. These directories should contain all the data we are training the system on.
