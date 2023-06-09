#!/bin/bash

docker run -it --rm -v "$AI_EDOCUMENT_DETECTOR_ROOT/threshold-analysis:/tmp/app" -v "$TRAINING_SET_ROOT:/tmp/images" -v "$MODEL_PATH:/tmp/saved-model" -w /tmp -u 1000:1000 ai-edocument-detector python /tmp/app/analyse-set.py
