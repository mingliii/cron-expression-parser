#!/bin/sh

#cd ..
#./mvnw compile exec:java -Dexec.mainClass="com.interview.Main" -Dexec.args="'$*'"

java -jar cron-expression-parser.jar "$*"