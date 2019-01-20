#!/usr/bin/env bash

REPOSITORY_NAME=$1

aws ecr describe-repositories --repository-names ${REPOSITORY_NAME} > /dev/null 2>&1
repositoryExists=$?

if [ "$repositoryExists" -eq 255 ]; then

    echo "Creating ECR Repository for ${REPOSITORY_NAME}"
    aws ecr create-repository --repository-name ${REPOSITORY_NAME}

fi