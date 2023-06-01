#!/bin/bash

docker run -it --rm -v $AI_EDOCUMENT_DETECTOR_ROOT:/tmp/app -v $TRAINING_SET_DEMO_RECORDS:/tmp/images -w /tmp -u 1000:1000 ai-edocument-detector python /tmp/app/classify.py
