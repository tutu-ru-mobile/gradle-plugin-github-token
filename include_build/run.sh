#!/usr/bin/env bash
./gradlew clean && \
  ./gradlew client:myBuildProduction && \
  du -sh client/build/distributions/* && \
  ./gradlew local-server:run
