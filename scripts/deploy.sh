#!/usr/bin/env bash

appName=$1
version=$2
accountID=$3

port=8080

imageTag=${accountID}.dkr.ecr.us-east-1.amazonaws.com/${appName}:${version}
SCRIPT_DIR=$(dirname $0)

aws cloudformation describe-stacks --stack-name ${appName} > /dev/null 2>&1
stackExists=$?

if [ "$stackExists" -eq 0 ]; then
    echo "stack exists"
    upsertStack=update-stack
    waitCommand=stack-update-complete
else
    echo "stack doesn't exists"
    upsertStack=create-stack
    waitCommand=stack-create-complete
fi

aws cloudformation ${upsertStack} \
    --stack-name ${appName} \
    --template-body file://${SCRIPT_DIR}/deploy.yml \
    --parameters  \
        ParameterKey=ImageUrl,ParameterValue=${imageTag} \
        ParameterKey=ServiceName,ParameterValue=${appName} \
        ParameterKey=ContainerPort,ParameterValue=${port} \
        ParameterKey=VPCStackName,ParameterValue=vpc \
        ParameterKey=UsersStackName,ParameterValue=users \
        ParameterKey=Path,ParameterValue=* \
        ParameterKey=HealthCheckPath,ParameterValue=/actuator/health \
        ParameterKey=Priority,ParameterValue=1 \
        ParameterKey=DesiredCount,ParameterValue=1

aws cloudformation wait ${waitCommand} --stack-name ${appName}