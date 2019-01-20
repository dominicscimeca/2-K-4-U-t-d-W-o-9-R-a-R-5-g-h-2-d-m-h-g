#!/usr/bin/env bash

APP_ROOT=$(dirname $0)/../

#Go to app root
cd ${APP_ROOT}

#Package app
mvn package