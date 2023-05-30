#!/bin/bash

# First command, build the docker image.

# docker build -t tensorflow-edocument .

# Second command, train the model
docker run -it --rm -v $PWD:/tmp/app -v /home/phill/tmp/ai-edocument-detection/demo-records/:/tmp/images -w /tmp -u 1000:1000 tensorflow-edocument python /tmp/app/classify.py
