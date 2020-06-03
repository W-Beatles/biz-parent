FROM waynechu/java-skywalking
LABEL maintainer="waynechu@waynechu.cn"
LABEL version="1.0.2020022601-SNAPSHOT"
LABEL description="biz-spring-cloud-oauth-server"
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} /app.jar
ENV JAVA_OPTS="-server -Xms256m -Xmx256m -Xmn100m -Xss256k"
ENTRYPOINT java ${JAVA_OPTS} -jar /app.jar
EXPOSE 8080