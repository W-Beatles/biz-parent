FROM waynechu/java-skywalking

ARG VERSION
ARG DESCRIPTION
ARG JAR_FILE
ENV JAVA_OPTS="-server -Xms256m -Xmx256m -Xmn100m -Xss256k"
VOLUME /tmp

LABEL maintainer="waynechu@waynechu.cn"
LABEL version=${VERSION}
LABEL description=${DESCRIPTION}

ADD ${JAR_FILE} /app.jar
ENTRYPOINT java ${JAVA_OPTS} -jar /app.jar
EXPOSE 8080