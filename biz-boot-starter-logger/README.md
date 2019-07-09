# biz-boot-starter-logger

### 项目介绍

该模块用于上传日志到elasticsearch及sentry中。

1. 该模块会将logback日志通过AmqpAppender发送到指定RabbitMQ消息队列中，然后通过配置logstash的input为
RabbitMQ、output为elasticsearch来将日志收集到ES中并在Kibana中展示。
2. 该模块会将error级别日志通过SentryAppender发送到指定的Sentry DSN地址，便于错误日志汇总、Bug排查定位，
还能及时收到的应用的错误报告。

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
    ## elk
    elk.enable=true
    elk.host=elk.waynechu.cn
    elk.port=5672
    elk.username=waynechu
    elk.password=youpassword
    elk.application-id=${spring.application.name}
    elk.virtual-host=/dev
    elk.exchange=topic.loggingExchange
    elk.routing-key=logback.#
    elk.connection-name=app|${spring.application.name}
    ## sentry
    sentry.enable=true
    sentry.dsn=http://a1c395c85d2xxxxxxxx0f1535b8@sentry.waynechu.cn:9000/2
    sentry.stacktrace-app-packages=
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
docker run -d --restart=always --name logstash -p 7002:7002 --net elk -v /root/tools/logstash/config/logstash.yml:/usr/share/logstash/config/logstash.yml -v /root/tools/logstash/pipeline/:/usr/share/logstash/pipeline/ logstash:6.6.1
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
    vhost => "/dev"
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