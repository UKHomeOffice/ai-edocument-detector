#!/bin/bash

docker run -it --rm -v "$AI_EDOCUMENT_DETECTOR_ROOT/threshold-analysis-scala:/tmp/app" -v "$TRAINING_SET_ROOT:/tmp/images" -v "$MODEL_PATH:/tmp/saved-model" -w /tmp -u 1000:1000 tensorflow-edocument java -jar /tmp/app/target/scala-3.2.2/scala-client.jar
