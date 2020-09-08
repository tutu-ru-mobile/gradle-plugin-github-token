#!/usr/bin/env bash
./gradlew clean && \
  ./gradlew client:webBuildProduction && \
  du -sh client/build/distributions/* && \
  ./gradlew local-server:run
