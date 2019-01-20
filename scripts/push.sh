#!/bin/bash

APP_ROOT=$(dirname $0)/../

imageName=$1
version=$2
accountID=$3

imageTag=${accountID}.dkr.ecr.us-east-1.amazonaws.com/${imageName}:${version}

#Build Docker Image
docker build --no-cache -t ${imageTag} ${APP_ROOT}

#Login to Docker
dockerLogin=$(aws ecr get-login --no-include-email)

${dockerLogin}

#Push to ECR
docker push ${imageTag}