
# docker快速启动

`docker-compose up -d`

|  服务           |  服务名          |  端口     |  帐号/密码         |  地址                         |
|----------------|------------------|-----------|------------------|-------------------------------|
|  数据库         |   MySQL         |  3306     |  waynechu/123456  |                               |
|  KV缓存         |   Redis         |  6379     |  123456           |                               |
|  消息中间件      |   RabbitMQ      |  5672     |  waynechu/123456  |  http://localhost:15672/      |                 |
|  搜索引擎        |   ElasticSearch |  9200     |                   |                               |
|  日志分析工具    |   Kibana        |  5601     |                   |  http://localhost:5601/       |
|  日志收集工具    |   logstash      |  7002     |                   |                               |