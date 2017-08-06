#!/bin/sh

echo "rootProject.name='keuson'\ninclude 'java', 'js'" > settings.gradle
touch build.gradle
java/gradlew wrapper
