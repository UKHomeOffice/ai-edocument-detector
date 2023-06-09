#!/bin/bash

docker run -v "$AI_EDOCUMENT_DETECTOR_ROOT/javascript-client/:/workspace" -v "$MODEL_PATH_TFJS:/workspace/dist/saved-model-tfjs" -w "/workspace" -it --net=host ai-edocument-detector-js bash

# if you modify the app you may need to clear the cache and copy the model to the build dir.
#docker run -v "$AI_EDOCUMENT_DETECTOR_ROOT/javascript-client/:/workspace" -v "$MODEL_PATH_TFJS:/workspace/saved-model-tfjs" -w "/workspace" -it --net=host ai-edocument-detector-js yarn cache clean
