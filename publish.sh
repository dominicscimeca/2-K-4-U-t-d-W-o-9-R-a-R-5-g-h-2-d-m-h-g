#!/usr/bin/env bash

APP=disneydogimagesapi
VERSION=1.0.1
ACCOUNT_ID=$(aws sts get-caller-identity | jq '.Account' -r)

echo "## Creating jar and Running Tests"
scripts/build-and-run-tests.sh

echo "## Create ECR Repository"
scripts/create-repository.sh ${APP}

echo "## Push to Repository"
scripts/push.sh ${APP} ${VERSION} ${ACCOUNT_ID}

echo "## Deploy to AWS"
scripts/deploy.sh ${APP} ${VERSION} ${ACCOUNT_ID}
