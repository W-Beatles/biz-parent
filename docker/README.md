
# docker快速启动

docker-compose常用命令:  
> 注: 以下命令都可添加 `-f` 参数来指定要使用的compose文件，默认为`docker-compose.yml`
```shell
docker-compose up -d        # 后台启动，如果容器不存在根据镜像自动创建
docker-compose down -v      # 停止容器并删除容器
docker-compose start        # 启动容器，容器不存在就无法启动，不会自动创建镜像
docker-compose stop         # 停止容器
```

## 基础依赖(必须)

进入docker目录, 执行`docker-compose up -d` 或单个启动`docker-compose up 服务名 -d`, 服务名如下:  

|  服务           |  服务名          |  端口     |  帐号/密码         |  地址                         |
|----------------|------------------|-----------|------------------|-------------------------------|
|  数据库(主)     |   mysql-master   |  3306     |  waynechu/123456  |                               |
|  数据库(从)     |   mysql-slave1   |  3307     |  waynechu/123456  |                               |
|  数据库(从)     |   mysql-slave2   |  3308     |  waynechu/123456  |                               |
|  KV缓存         |   redis         |  6379     |  123456           |                               |
|  消息中间件     |   rabbitmq       |  5672     |  waynechu/123456  |  http://localhost:15672/      |                 |

## elk(可选)

`docker-compose -f docker-compose.yml -f docker-compose-elk.yml up -d`

|  服务           |  服务名          |  端口     |  帐号/密码         |  地址                         |
|---------------- |-----------------|-----------|------------------|-------------------------------|
|  搜索引擎        |   elasticsearch |  9200     |                  |                               |
|  日志分析工具    |   kibana        |  5601     |                   |  http://localhost:5601/       |
|  日志收集工具    |   logstash      |  7002     |                   |                               |