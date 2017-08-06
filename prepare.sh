#!/bin/sh

echo "rootProject.name='keuson'\ninclude 'keuson-java', 'keuson-js'" > settings.gradle
touch build.gradle
java/gradlew wrapper
