FROM java:alpine
LABEL maintainer="waynechu@waynechu.cn"
LABEL version="1.0.0"
LABEL description="Base skywalking image for SpringCloud application."
ENV TZ='Asia/Shanghai'
RUN apk update \
    && apk add tzdata \
    && cp /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone
ADD /skywalking-agent/ /opt/skywalking-agent/