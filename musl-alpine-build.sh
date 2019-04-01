#!/usr/bin/env bash
./gradlew clean build -x test && docker build -t alpine-sb2-logback-musl -f Dockerfile.alpine .