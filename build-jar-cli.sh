#!/bin/bash

function copy_and_start() {
#  cp include_build/jar-cli/build/libs/jar-cli-1.0.0-all.jar create-token-cli.jar
  rm -rf create-token-cli || echo "skip step rm"
  cp -r include_build/jar-cli/build/install/* ./create-token-cli/
  rm -rf create-token-cli/lib/gradle-api-*
  rm -rf create-token-cli/lib/groovy*
  rm -rf create-token-cli/lib/kotlin-reflect-*
  rm -rf create-token-cli/lib/kotlin-stdlib-1.3.72.jar
  du -sh create-token-cli
  ./create-token-cli.sh
}

export BUILD_JAR_CLI=true

#include_build/gradlew -p include_build jar-cli:shadowJar
include_build/gradlew -p include_build jar-cli:installDist \
  && copy_and_start

