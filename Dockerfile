FROM tensorflow/tensorflow

RUN apt-get update
RUN apt-get -y upgrade
RUN apt-get -y install python3-dev libpython3-dev vim openjdk-17-jdk
RUN python3 -m pip install --upgrade pip
RUN pip install matplotlib tensorflowjs
RUN pip install jax==0.4.10
