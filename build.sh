#!/usr/bin/env bash
# exit whenever encounter errors
set -e

./script/build-script/build-before.sh

#gradle clean build dockerImageBuild -x test -Pprofile=dev

./gradlew clean build  -x test -Pprofile=dev

./script/build-script/build-after.sh
