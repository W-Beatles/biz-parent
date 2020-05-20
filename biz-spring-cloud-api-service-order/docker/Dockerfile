FROM waynechu/java-skywalking:latest
LABEL maintainer="waynechu@waynechu.cn"
LABEL version="0.0.1-SNAPSHOT"
LABEL description="docker build -t waynechu/service-order . && docker push waynechu/service-order"
VOLUME /tmp
ADD service-order.jar /app.jar
ENV JAVA_OPTS="\
-server -Xms256m -Xmx256m -Xmn100m -Xss256k"
ENTRYPOINT java ${JAVA_OPTS} -jar /app.jar
EXPOSE 8080