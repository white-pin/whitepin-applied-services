#!/usr/bin/env bash

SCRIPT_PATH=$(cd "$(dirname $0)" && pwd)
PROJECT=${1}
DEFAULT_PROJECT="airbnb"

if [[ -z ${PROJECT} ]]; then
    PROJECT=${DEFAULT_PROJECT}
fi

case "${PROJECT}" in
    airbnb )
    ;;
    * )
    echo "Available projects is [airbnb]"
    exit 1
    ;;
esac

chmod +x ${SCRIPT_PATH}/../mvnw

if [[ -z ${PROJECT} ]]; then
    cd ${SCRIPT_PATH}/../ && ${SCRIPT_PATH}/../mvnw --projects ${PROJECT} -Dmaven.test.skip=true clean package
elif [[ "${PROJECT}" != "server" && "${PROJECT}" != "frontend" ]]; then
    cd ${SCRIPT_PATH}/../ && ${SCRIPT_PATH}/../mvnw -Dmaven.test.skip=true clean package
fi


