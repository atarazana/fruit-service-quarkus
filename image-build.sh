#!/bin/sh

. ./image-env.sh

docker build -f ./oci/Dockerfile.native-full -t $IMAGE_NAME:$IMAGE_VERSION .