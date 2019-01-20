#!/usr/bin/env bash

ACCOUNT_ID=$(aws sts get-caller-identity | jq '.Account' -r)

echo "## Creating jar and Running Tests"
scripts/build-and-run-tests.sh

echo "## Create ECR Repository"
scripts/create-repository.sh disneydogimagesapi

echo "## Push to Repository"
scripts/push.sh disneydogimagesapi 1.0 ${ACCOUNT_ID}

echo "## Deploy to AWS"
scripts/deploy.sh disneydogimagesapi 1.0 ${ACCOUNT_ID}
