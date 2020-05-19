FROM java:alpine
LABEL maintainer="waynechu@waynechu.cn"
LABEL version="0.0.1-SNAPSHOT"
VOLUME /tmp
ADD service-utility.jar /app.jar
ENV JAVA_OPTS="\
-server -Xms256m -Xmx256m -Xmn100m -Xss256k"
ENTRYPOINT java ${JAVA_OPTS} -jar /app.jar
EXPOSE 8080