#!/usr/bin/env bash

cd source/backend

./gradlew clean build

cp ./build/libs/*.jar ../../backend-built/