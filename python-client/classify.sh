#!/bin/bash

docker run -it --rm -v "$AI_EDOCUMENT_DETECTOR_ROOT/python-client:/tmp/app" -v "$TRAINING_SET_ROOT:/tmp/images" -v "$MODEL_PATH:/tmp/model-path" -w /tmp -u 1000:1000 ai-edocument-detector python /tmp/app/classify.py
