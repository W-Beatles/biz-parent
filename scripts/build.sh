#!/bin/sh

#------------------------------
#author: waynechu1996@gmail.com
#Created 2019-05-11 10:35
#------------------------------

# Setting environment variables
DATETIME=`date +%Y-%m-%d_%H:%M`

# =============== Please do not modify the following content =============== #
# go to script directory
cd "${0%/*}"

cd ..

# package project
echo "==== starting to build project ===="

mvn clean package -DskipTests

echo "==== building project finished ===="

echo "==== starting to build portal ===="

nohup java -jar \
        -Dserver.port=9001 -Dspring.profiles.active=dev \
        -Deureka.client.service-url.defaultZone=http://admin:$123456@dev.waynechu.cn:9002/eureka/\
        -server -Xms256m -Xmx256m -Xmn100m -Xss256k \
        -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/oom-error-${DATETIME}.hprof \
        ./app-spring-cloud-eureka/target/app-spring-cloud-eureka-0.0.1-SNAPSHOT.jar &

nohup java -jar \
        -Dserver.port=9002 -Dspring.profiles.active=dev \
        -Deureka.client.service-url.defaultZone=http://admin:$123456@dev.waynechu.cn:9001/eureka/\
        -server -Xms256m -Xmx256m -Xmn100m -Xss256k \
        -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/oom-error-${DATETIME}.hprof \
        ./app-spring-cloud-eureka/target/app-spring-cloud-eureka-0.0.1-SNAPSHOT.jar &

nohup java -jar \
        -Dserver.port=8060 -Dspring.profiles.active=dev \
        -server -Xms256m -Xmx256m -Xmn100m -Xss256k \
        -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/oom-error-${DATETIME}.hprof \
        ./app-spring-cloud-eureka/target/app-spring-cloud-eureka-0.0.1-SNAPSHOT.jar &