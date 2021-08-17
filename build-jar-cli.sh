#!/bin/bash

function copy_and_start() {
  cp include_build/jar-cli/build/libs/jar-cli-1.0.0-all.jar create-token.jar
  du -sh create-token.jar
  java -jar create-token.jar
}

export BUILD_JAR_CLI=true

include_build/gradlew -p include_build jar-cli:installDist #(multi jars)
include_build/gradlew -p include_build jar-cli:shadowJar \
  && copy_and_start

