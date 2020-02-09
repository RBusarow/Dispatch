#!/bin/bash
./gradlew clean build dokka
./gradlew uploadArchives --no-daemon --no-parallel
