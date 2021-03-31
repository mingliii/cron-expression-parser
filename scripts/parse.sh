#!/bin/sh

# ./parse.sh "*/45 0 1,2,15 * 1-5 /usr/bin/find"
#cd ..
#./mvnw compile exec:java -Dexec.mainClass="com.interview.CronExpression" -Dexec.args="'$*'"

java -jar deliveroo-cron.jar "$*"