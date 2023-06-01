#!/bin/bash

docker run -it --rm -v $AI_EDOCUMENT_DETECTOR_ROOT:/tmp/app -v $TRAINING_SET2_ROOT:/tmp/images -w /tmp -u 1000:1000 ai-edocument-detector python /tmp/app/analyse-set.py
