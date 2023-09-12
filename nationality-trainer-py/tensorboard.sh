#!/bin/bash

docker run -it --rm -v "$AI_EDOCUMENT_DETECTOR_ROOT/nationality-trainer-py:/tmp/app" \
  --gpus all --ipc=host --ulimit memlock=-1 --ulimit stack=67108864 \
  -v "$TRAINING_SET_ROOT:/tmp/images" -v "$MODEL_PATH:/tmp/model-path" \
  -w /tmp -u 1000:1000 \
  -v "/tmp:/results" \
  -p "0.0.0.0:6006:6006" \
  ai-edocument-detector tensorboard --logdir /results
