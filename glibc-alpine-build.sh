#!/usr/bin/env bash
./gradlew clean build -x test && docker build -t alpine-sb2-logback-glibc -f Dockerfile.alpine.glibc .