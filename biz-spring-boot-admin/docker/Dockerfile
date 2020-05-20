FROM waynechu/java-skywalking:latest
LABEL maintainer="waynechu@waynechu.cn"
LABEL version="1.0.2019070901-RELEASE"
LABEL description="docker build -t waynechu/biz-spring-boot-admin ."
VOLUME /tmp
ADD biz-spring-boot-admin-1.0.2019070901-RELEASE.jar /app.jar
ENV JAVA_OPTS="\
-server -Xms256m -Xmx256m -Xmn100m -Xss256k"
ENTRYPOINT java ${JAVA_OPTS} -jar /app.jar
EXPOSE 8080