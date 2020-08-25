FROM waynechu/java-skywalking-maven
LABEL maintainer="waynechu@waynechu.cn"
LABEL version="0.1.0-SNAPSHOT"
LABEL description="This is a SpringCloud application."
ENV TZ='Asia/Shanghai'
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} /app.jar
ENV JAVA_OPTS="-server -Xms256m -Xmx256m -Xmn100m -Xss256k"
ENTRYPOINT java ${JAVA_OPTS} -jar /app.jar
EXPOSE 8080