FROM openjdk:8
LABEL maintainer="waynechu@waynechu.cn"
LABEL version="1.0.0"
LABEL description="Base maven image for SpringCloud application."
ENV TZ='Asia/Shanghai'
ENV MAVEN_HOME=/opt/apache-maven-3.5.4/
ENV PATH=$MAVEN_HOME/bin:$PATH
ADD /apache-skywalking-apm-bin/agent/ /opt/skywalking-agent/
ADD /apache-maven-3.5.4/ /opt/apache-maven-3.5.4/