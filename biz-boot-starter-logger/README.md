# biz-boot-starter-logger

### 项目介绍

该模块用于日志上传到elk中(支持RabbitMQ或Kafka两种上传方式)，并且还能将异常信息上传到sentry中实现错误报警。

1. 该模块会将logback日志通过Appender发送到指定RabbitMQ消息队列/Kafka消息队列中，然后通过配置logstash的input为
RabbitMQ、output为elasticsearch即可将日志收集到ES中并在Kibana中展示。  
2. 该模块会将error级别日志通过SentryAppender发送到指定的Sentry DSN地址，便于错误日志汇总、Bug排查定位，
还能及时收到应用的错误报警。
3. 该模块还添加调用链路信息到请求头和MDC上下文中，实现微服务直接调用的全链路追踪。
4. elk中记录的信息除当前服务的基础信息之外，还包含微服务调用链路信息。
    ```
     ---------- 基础信息 ----------
     parentProjectVersion - 父项目版本号。该值为 `biz.logger.version.parent-project` 的赋值，方便定位基础服务框架的一些问题
     appId                - 项目唯一标识。该值为 `spring.application.name` 的赋值
     appName              - 项目名称。该值为 `spring.application.name` 的赋值
     hostName             - 服务器名
     hostAddress          - 服务器IP
     logger               - logger名称
     threadName           - 线程名称
     level                - 日志级别
     time                 - 日志时间。格式为 yyyy-MM-dd HH:mm:ss.SSS
     message              - 日志内容
    ```
5. 定制化banner样式。
6. 添加默认的 `logback-spring.xml` 配置，项目代码无需再添加日志配置文件。

### 过滤器 & 拦截器

1. MDCFilter过滤器

    该过滤器用于添加请求信息到 MDC上下文中 及 在请求头中传递调用链路信息。

    添加的MDC上下文和请求头中的信息有:
    ```
    ---------- MDC ----------
    api-version                - 请求的API版本号
    channel                    - 渠道，调用方标识
    device-id                  - 设备id
    ---------- MDC & 请求头 ----------
    request-id                 - 请求跟踪号，全链路唯一标识。格式为UUID(32个字符)，来自header或者由该过滤器初始化
    sc-client-ip               - 请求来源客户端ip
    origin-url                 - 请求来源地址
    trace-app-ids              - appId调用链路追踪记录。来自header并由该过滤器追加，以`,`分割
    trace-app-names            - appName调用链路追踪记录。来自header并由该过滤器追加，以`,`分割
    trace-host-names           - hostName调用链路追踪记录。来自header并由该过滤器追加，以`,`分割
    trace-host-addresses       - hostAddress调用链路追踪记录。来自header并由该过滤器追加，以`,`分割
   ```


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
   
    ## elk-rabbit (两种方式二选一即可)
    elk.rabbitmq.enable=true
    elk.rabbitmq.host=mq.waynechu.cn
    elk.rabbitmq.port=5672
    elk.rabbitmq.username=waynechu
    elk.rabbitmq.password=123456
    elk.rabbitmq.application-id=${spring.application.name}
    elk.rabbitmq.virtual-host=/logback
    elk.rabbitmq.exchange=topic.loggingExchange
    elk.rabbitmq.routing-key=logback.#
    elk.rabbitmq.connection-name=biz|${spring.application.name}
   
    ## elk-rabbit (两种方式二选一即可)
    elk.kafka.enable=true
    elk.kafka.host=kafka.waynechu.cn
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
        spring.rabbitmq.password=123456
        spring.rabbitmq.virtual-host=/dev
        spring.rabbitmq.publisher-confirms=true
        ```

3. (可选)如需要自定义日志相关配置，可新建`src/main/resources/logback-custom-spring.xml`来增加相关配置
    ```
    <?xml version="1.0" encoding="UTF-8"?>
    <included>
        自定义配置
    </included>
    ```

### 附: Docker快速搭建ELK环境

这里我们固定了elk的ip地址，防止重启ip地址发生变动

```
// 创建网络
docker network create --subnet=172.18.0.0/16 elk
// 启动ES
docker run -d --name es --net elk --ip 172.18.0.2 -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.5.1
// 启动Kibana
docker run -d --name kibana -e ELASTICSEARCH_URL=http://172.18.0.2:9200 --net elk --ip 172.18.0.3 -p 5601:5601 kibana:7.5.1
注: 若无法连接到es，需修改/usr/share/kibana/config的 elasticsearch.hosts: [ "http://172.18.0.2:9200" ]
// 启动Rabbitmq
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 --net elk --ip 172.18.0.4 -e RABBITMQ_DEFAULT_USER=waynechu -e RABBITMQ_DEFAULT_PASS=123456 -e RABBITMQ_DEFAULT_VHOST=/logback rabbitmq:3-management
// 启动Logstash
(Linux) docker run -d --name logstash -p 7002:7002 --net elk --ip 172.18.0.5 -it -v /root/tools/logstash/config/logstash.conf:/usr/share/logstash/pipeline/logstash.conf logstash:7.5.1
(Windows) docker run -d --name logstash -p 7002:7002 --net elk --ip 172.18.0.5 -it -v /E/work/Tools/logstash/pipeline/logstash.conf:/usr/share/logstash/pipeline/logstash.conf logstash:7.5.1
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