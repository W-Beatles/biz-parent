#!/bin/sh

#------------------------------
#author: waynechu1996@gmail.com
#Created 2019-05-11 10:35
#------------------------------

# Setting environment variables
DATETIME=`date +%Y-%m-%d_%H:%M`

# =============== Please do not modify the following content =============== #
# go to app-parent directory
cd ..

# package project
echo "==== starting to build project ===="
mvn clean package -DskipTests
echo "==== building project finished ===="

echo "==== 1.starting app-spring-cloud-eureka-peer1(9001), app-spring-cloud-eureka-peer2(9002) ===="
nohup java -jar \
        -Denv=PRO -Dapollo.meta=http://apollo.waynechu.cn:8080 \
        -Dserver.port=9001 -Dspring.profiles.active=prd \
        -Deureka.client.service-url.defaultZone=http://admin:$1Eq93asro2V6R@dev.waynechu.cn:9002/eureka/ \
        -server -Xms256m -Xmx256m -Xmn100m -Xss256k \
        -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/oom-error-${DATETIME}.hprof \
        ./app-spring-cloud-eureka/target/app-spring-cloud-eureka-0.0.1-SNAPSHOT.jar &
nohup java -jar \
        -Denv=PRO -Dapollo.meta=http://apollo.waynechu.cn:8080 \
        -Dserver.port=9002 -Dspring.profiles.active=prd \
        -Deureka.client.service-url.defaultZone=http://admin:$1Eq93asro2V6R@dev.waynechu.cn:9001/eureka/ \
        -server -Xms256m -Xmx256m -Xmn100m -Xss256k \
        -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/oom-error-${DATETIME}.hprof \
        ./app-spring-cloud-eureka/target/app-spring-cloud-eureka-0.0.1-SNAPSHOT.jar &
echo "     [\033[32mvisit: http://dev.waynechu.cn:9001/\033[0m]"
echo "     [\033[32mvisit: http://dev.waynechu.cn:9002/\033[0m]"