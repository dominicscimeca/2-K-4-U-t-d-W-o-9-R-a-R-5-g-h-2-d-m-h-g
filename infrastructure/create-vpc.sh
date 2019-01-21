#!/usr/bin/env bash

stackName=vpc
aws cloudformation describe-stacks --stack-name ${stackName} > /dev/null 2>&1
stackExists=$?

if [ "$stackExists" -eq 0 ]; then
    echo "stack exists"
    upsertStack=update-stack
else
    echo "stack doesn't exists"
    upsertStack=create-stack
fi

aws cloudformation ${upsertStack} \
    --stack-name ${stackName} \
    --template-body file://public-private-vpc.yml
