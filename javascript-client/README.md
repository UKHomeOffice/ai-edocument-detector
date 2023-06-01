
# Javascript Client

This application runs in your browser and uses tensorflow-js. Upload an image and get a confidence score telling you if it's an edocument or not. Files are not saved.

```bash

docker run -v $AI_EDOCUMENT_DETECTOR_ROOT/javascript-client:/workspace" -it --net=host ai-edocument-detector-js /bin/bash

```

Once the container is running you can run the application with these commands:

```
yarn cache clean
yarn build
yarn run watch
```

Then visit localhost:1234 in your browser to see the interface.

I've manually copied the output from model-trainer-py/saved-model-tfjs to this directory. If you change or retain the model you will need to copy the files over. The work in this projectis not designed to be ready to go into production.
