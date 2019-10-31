# biz-boot-starter-logger

### 项目介绍

该模块用于日志上传。支持RabbitMQ或Kafka日志上传

1. 该模块会将logback日志通过Appender发送到指定RabbitMQ消息队列/Kafka消息队列中，然后通过配置logstash的input为
RabbitMQ、output为elasticsearch即可将日志收集到ES中并在Kibana中展示。  
2. 该模块会将error级别日志通过SentryAppender发送到指定的Sentry DSN地址，便于错误日志汇总、Bug排查定位，
还能及时收到的应用的错误报告。
3. 打印的信息有
    ```
     ---------- 基础信息 ----------
     parentProjectVersion - 父项目版本号。该值为 biz.logger.version.parent-project 的赋值
     appId                - 项目唯一标识。该值为 java._appid_ 的赋值
     appName              - 项目名称。该值为 spring.application.name 的赋值
     hostName             - 服务器名
     hostAddress          - 服务器IP
     logger               - logger名称
     threadName           - 线程名称
     level                - 日志级别
     time                 - 日志时间。格式为 yyyy-MM-dd HH:mm:ss.SSS
     message              - 日志内容
    ```

### 过滤器 & 拦截器

1. MDCFilter过滤器

    该过滤器用于添加调用链路日志信息到MDC中。

    添加的MDC信息有:
    ```
    ---------- MDC信息 ----------
    traceNo                  - 请求跟踪号。可添加到请求头或请求参数上，用于自定义追踪标记
    requestId                - 请求唯一标识。格式为UUID(32个字符)，来自header或者由该过滤器初始化
    apiVersion               - 请求的API版本号
    channel                  - 调用方标识
    deviceId                 - 设备id
    traceAppIds              - appId调用链路追踪记录。来自header并由该过滤器追加，以`,`分割
    traceAppNames            - appName调用链路追踪记录。来自header并由该过滤器追加，以`,`分割
    traceHostNames           - hostName调用链路追踪记录。来自header并由该过滤器追加，以`,`分割
    traceHostAddresses       - hostAddress调用链路追踪记录。来自header并由该过滤器追加，以`,`分割
   ```
2. FeignHeaderInterceptor拦截器。

    该拦截器用于微服务间使用feign互相调用传递header请求头信息。
  
### 使用方式

1. 添加依赖

    ```
    <dependency>
        <groupId>cn.waynechu</groupId>
        <artifactId>biz-boot-starter-logger</artifactId>
    </dependency>
    ```
2. 添加配置

    ```
    ## sentry
    sentry.enable=true
    sentry.dsn=http://a1c395c85d244742ae2a50b90f1535b8@sentry.waynechu.cn:9000/2
    sentry.stacktrace-app-packages=
   
    ## elk-rabbit
    elk.rabbitmq.enable=true
    elk.rabbitmq.host=mq.waynechu.cn
    elk.rabbitmq.port=5672
    elk.rabbitmq.username=waynechu
    elk.rabbitmq.password=Swro39qE.mB5
    elk.rabbitmq.application-id=${spring.application.name}
    elk.rabbitmq.virtual-host=/logback
    elk.rabbitmq.exchange=topic.loggingExchange
    elk.rabbitmq.routing-key=logback.#
    elk.rabbitmq.connection-name=biz|${spring.application.name}
   
    ## elk-rabbit
    elk.kafka.enable=true
    elk.kafka.host=localhost
    elk.kafka.port=9092
    elk.kafka.topic=logback
    ```
   
    注意：
    - 如果抛出 `org.springframework.amqp.AmqpConnectException` Rabbit health check failed，这是因为`org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration`生效。
    需添加如下Rabbitmq配置：
        ```
        spring.rabbitmq.host=mq.waynechu.cn
        spring.rabbitmq.port=5672
        spring.rabbitmq.username=waynechu
        spring.rabbitmq.password=Swro39qE.mB5
        spring.rabbitmq.virtual-host=/dev
        spring.rabbitmq.publisher-confirms=true
        ```

3. (可选)如需要自定义日志相关配置，可新建`src/main/resources/logback-custom-spring.xml`来增加相关配置
    ```
    <?xml version="1.0" encoding="UTF-8"?>
    <included>
        相关配置内容
    </included>
    ```
### 附: Docker快速搭建ELK环境

```
// 创建网络
docker network create elk
// 启动ES
docker run -d --restart=always --name elasticsearch --net elk -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:6.6.1
// 启动Kibana
docker run -d --restart=always --name kibana -e ELASTICSEARCH_URL=http://xxx.xxx.xxx.xxx:9200 --net elk -p 5601:5601 kibana:6.6.1
// 启动Logstash
docker run -d --restart=always --name logstash -p 7002:7002 --net elk -v /root/tools/logstash/config/logstash.yml:/usr/share/logstash/config/logstash.yml logstash:6.6.1
```

### 附: logstash收集RabbitMQ消息到elasticsearch

示例pipeline配置 `logstash.conf`: 

```
input {
  rabbitmq {
    host => "xxx.xxx.xxx.xxx"
    port => 5672
    user => "waynechu"
    password =>"yourpassword"
    vhost => "/logback"
    exchange => "topic.loggingExchange"
    exchange_type => "topic"
    queue => "logback"
    key => "logback.#"
    codec => json
    durable => true
	_type => logback
  }
}

output {
  elasticsearch {
    hosts => ["xxx.xxx.xxx.xxx:9200"]
    index => "logstash-logback-%{+YYYY.MM.dd}"
  }

  #stdout{
  #  codec => rubydebug
  #}
}
```