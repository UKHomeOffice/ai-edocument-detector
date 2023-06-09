#!/bin/bash

docker run -it --rm -v $PWD:/tmp/app -v /home/phill/tmp/ai-edocument-detection/demo-records/:/tmp/images -w /tmp -u 1000:1000 tensorflow-edocument java -jar /tmp/app/target/scala-3.2.2/scala-client.jar
