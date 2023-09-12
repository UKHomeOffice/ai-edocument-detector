#!/bin/bash

docker run -it --rm --gpus all --ipc=host --ulimit memlock=-1 --ulimit stack=67108864 -v "$AI_EDOCUMENT_DETECTOR_ROOT/scala-client:/tmp/app" -v "$TEST_SET_ROOT:/tmp/images" -v "$MODEL_PATH:/tmp/saved-model" -e MODEL_PATH -e TEST_SET_ROOT -w /tmp -u 1000:1000 ai-edocument-detector java -jar /tmp/app/target/scala-3.2.2/scala-client.jar
