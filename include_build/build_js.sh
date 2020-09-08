#!/usr/bin/env bash
./gradlew client:myBuildProduction

cp -r client/build/distributions/* docs
du -sh docs/*
