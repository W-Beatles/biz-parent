# app-boot-starter-elk-sentry

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
        <artifactId>app-boot-starter-elk-sentry</artifactId>
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
    
3. 添加Logback配置 `logback-spring.xml`

    ```
    ...
    <springProperty scope="context" name="sentryEnable" source="sentry.enable" defaultValue="false"/>

    <springProperty scope="context" name="elkEnable" source="elk.enable" defaultValue="false"/>
    <springProperty scope="context" name="elkHost" source="elk.host" defaultValue="localhost"/>
    <springProperty scope="context" name="elkPort" source="elk.port" defaultValue="5672"/>
    <springProperty scope="context" name="elkUsername" source="elk.username" defaultValue="guest"/>
    <springProperty scope="context" name="elkPassword" source="elk.password" defaultValue="guest"/>
    <springProperty scope="context" name="elkVirtualHost" source="elk.virtual-host" defaultValue="/"/>
    <springProperty scope="context" name="elkExchange" source="elk.exchange" defaultValue="topic.loggingExchange"/>
    <springProperty scope="context" name="elkRoutingKey" source="elk.routing-key" defaultValue="logback.#"/>
    <springProperty scope="context" name="elkApplicationId" source="elk.application-id" defaultValue="anonymous"/>
    <springProperty scope="context" name="elkConnectionName" source="elk.connection-name" defaultValue="UNDEFINED" />

    <if condition='property("sentryEnable").contains("true")'>
        <then>
            <appender name="Sentry" class="io.sentry.logback.SentryAppender">
                <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
                    <level>WARN</level>
                </filter>
            </appender>
        </then>
    </if>
    
    <if condition='property("elkEnable").contains("true")'>
        <then>
            <appender name="ELK" class="org.springframework.amqp.rabbit.logback.AmqpAppender">
                <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
                    <level>INFO</level>
                </filter>
                <host>${elkHost}</host>
                <port>${elkPort}</port>
                <username>${elkUsername}</username>
                <password>${elkPassword}</password>
                <applicationId>${applicationId}</applicationId>
                <virtualHost>${elkVirtualHost}</virtualHost>
                <exchangeName>${elkExchange}</exchangeName>
                <exchangeType>topic</exchangeType>
                <declareExchange>true</declareExchange>
                <routingKeyPattern>${elkRoutingKey}</routingKeyPattern>
                <autoDelete>false</autoDelete>
                <generateId>true</generateId>
                <durable>true</durable>
                <deliveryMode>PERSISTENT</deliveryMode>
                <charset>UTF-8</charset>
                <contentType>text/json</contentType>
                <connectionName>${elkConnectionName}</connectionName>
                <layout class="cn.waynechu.bootstarter.elk.layout.RabbitmqLayout"/>
            </appender>
        </then>
    </if>
    ...
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