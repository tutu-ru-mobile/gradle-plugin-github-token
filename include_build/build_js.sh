#!/usr/bin/env bash
./gradlew client:webBuildProduction

cp -r client/build/distributions/* docs
du -sh docs/*
