#!/bin/bash

docker run -it --rm -v "$AI_EDOCUMENT_DETECTOR_ROOT/model-trainer-py:/tmp/app" \
  --gpus all --ipc=host --ulimit memlock=-1 --ulimit stack=67108864 \
  -v "$TRAINING_SET_ROOT:/tmp/images" -v "$MODEL_PATH:/tmp/model-path" \
  -v "$MODEL_PATH_TFJS:/tmp/model-path-tfjs" -w /tmp -u 1000:1000 \
  -v "/tmp:/results" \
  ai-edocument-detector python /tmp/app/train.py
