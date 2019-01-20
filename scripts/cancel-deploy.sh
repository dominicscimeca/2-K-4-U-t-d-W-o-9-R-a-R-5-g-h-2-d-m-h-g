#!/usr/bin/env bash

appName=$1

#Canceling deploy and not waiting until cancel is finished because doesn't seem directly possible
#TODO: Determine how to stop a new deploy when an old one is in the process of being cancelled
aws cloudformation cancel-update-stack --stack-name ${appName}