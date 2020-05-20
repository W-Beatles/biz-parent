FROM waynechu/java-skywalking:latest
LABEL maintainer="waynechu@waynechu.cn"
LABEL version="0.0.1-SNAPSHOT"
LABEL description="docker build -t waynechu/service-product . && docker push waynechu/service-product"
VOLUME /tmp
ADD service-product.jar /app.jar
ENV JAVA_OPTS="\
-server -Xms256m -Xmx256m -Xmn100m -Xss256k"
ENTRYPOINT java ${JAVA_OPTS} -jar /app.jar
EXPOSE 8080