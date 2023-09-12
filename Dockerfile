FROM nvcr.io/nvidia/tensorflow:23.07-tf2-py3

RUN python3 -m pip install --upgrade pip
RUN pip install matplotlib pyqt5
RUN apt-get update
RUN apt-get install -y openjdk-17-jdk
