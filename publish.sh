#!/usr/bin/env bash

echo "#####   Welcome   #####"
echo "#####   This Script will build a jar, run tests, push to ecr, and deploy to fargate  #####"
echo ""

APP=disneydogimagesapi
echo "### App Repo Name: ${APP}"

ACCOUNT_ID=$(aws sts get-caller-identity | jq '.Account' -r)
echo "### Account Id: ${ACCOUNT_ID}"

LATEST_VERSION=$(aws ecr --repository-name=${APP}  list-images | jq -r ".imageIds[-1].imageTag")
echo "### Latest Version: ${LATEST_VERSION}"

LATEST_VERSION_PATCH=$(echo ${LATEST_VERSION} | cut -d"." -f3-)
NEW_VERSION_PATH=$(echo $((LATEST_VERSION_PATCH+1)))
VERSION=1.0.${NEW_VERSION_PATH}
echo "### New Version: ${VERSION}"


#echo "## Creating jar and Running Tests"
#scripts/build-and-run-tests.sh
#
#echo "## Create ECR Repository"
#scripts/create-repository.sh ${APP}
#
#echo "## Push to Repository"
#scripts/push.sh ${APP} ${VERSION} ${ACCOUNT_ID}

echo "## Deploy to AWS"
scripts/deploy.sh ${APP} ${LATEST_VERSION} ${ACCOUNT_ID}
