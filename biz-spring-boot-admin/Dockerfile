FROM waynechu/java-skywalking
LABEL maintainer="waynechu@waynechu.cn"
LABEL version="1.0.2019070901-RELEASE"
LABEL description="biz-spring-boot-admin"
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} /app.jar
ENV JAVA_OPTS="-server -Xms256m -Xmx256m -Xmn100m -Xss256k"
ENTRYPOINT java ${JAVA_OPTS} -jar /app.jar
EXPOSE 8080