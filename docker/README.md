
# docker快速启动

## 常用命令
```shell
docker-compose up -d                   # 初始化容器，如果容器不存在根据镜像自动创建
docker-compose down -v                 # 停止容器并删除容器
docker-compose start                   # 启动容器
docker-compose stop                    # 停止容器
```

## 快速启动

进入docker目录, 执行`docker-compose up -d`，或者`docker-compose up 服务名1 服务名2 -d`启动指定服务，服务名如下: 
> 注: 服务前加*代表必须依赖、

```
// 启动级别1
docker-compose up -d apollo elasticsearch 
// 启动级别2
docker-compose up -d eureka-1 eureka-2 skywalking-ui
// 启动剩余服务
docker-compose up -d
```

|  服务            |   服务名                 |  端口     |  帐号/密码         |  地址                                     |
|------------------|-------------------------|-----------|-------------------|------------------------------------------|
|  *数据库(主)      |   mysql-master          |  3306     |  waynechu/123456  |                                          |
|  *数据库(从)      |   mysql-slave1          |  3307     |  waynechu/123456  |                                          |
|  *数据库(从)      |   mysql-slave2          |  3308     |  waynechu/123456  |                                          |
|  *KV缓存          |   redis                |  6379      |  123456          |                                          |
|  *消息中间件       |   rabbitmq             |  5672     |  waynechu/123456  |  http://localhost:15672                  |
|  搜索引擎         |  elasticsearch          |  9200     |                   |                                          |
|  日志分析工具      |  kibana                |  5601     |                   |  http://localhost:5601                   |
|  日志收集工具      |  logstash              |  7002     |                   |                                          |
|  *配置中心        |  apollo                 |  9070     |  apollo/admin     |  http://localhost:8070                  |
|  *配置中心        |  apollo-mysql           |  3316     |                   |                                         |
|  *注册中心(peer1) |  eureka-1               |  9001     |                   |  http://localhost:9001                  |
|  *注册中心(peer1) |  eureka-2               |  9002     |                   |  http://localhost:9002                  |
|  网关             |  inner-gateway          |  9011     |                   |  http://localhost:9011/swagger-ui.html  |
|  监控中心         |  boot-admin             |  9020     |                   |  http://localhost:9020                  |
|  skywalking      |  skywalking-oap         |  12800    |                   |                                         |
|  skywalking      |  skywalking-ui          |  8090     |                   |  http://localhost:8090                   |
|  订单服务(peer1)  |  service-order-peer1    |  10010    |                   |  http://localhost:10010/swagger-ui.html  |
|  订单服务(peer2)  |  service-order-peer2    |  10011    |                   |  http://localhost:10011/swagger-ui.html  |
|  订单服务(peer1)  |  service-product-peer1  |  10020    |                   |  http://localhost:10020/swagger-ui.html  |
|  订单服务(peer2)  |  service-product-peer2  |  10021    |                   |  http://localhost:10021/swagger-ui.html  |
|  公共服务(peer1)  |  service-utility-peer1  |  10030    |                   |  http://localhost:10030/swagger-ui.html  |
|  公共服务(peer2)  |  service-utility-peer2  |  10031    |                   |  http://localhost:10031/swagger-ui.html  |

**扩展:** 配置MySQL主从链路

首次启动主从集群后，需要手动建立主从同步链路

1. 登录主库并配置链路用户权限
    ```
    CREATE USER 'replication'@'172.20.0.%' IDENTIFIED WITH mysql_native_password BY 'EiO8Rx4W.T3';
    GRANT REPLICATION SLAVE ON *.* to 'replication'@'172.20.0.%';
    flush privileges;
    show master status; --记下File、Position信息，配置从库时需要用到
    ```

2. 登录从库并启动复制链路(从库都需要执行)
    ```
    change master to master_host='172.20.0.201',master_user='replication',master_password='EiO8Rx4W.T3',master_log_file='mysql-bin.000003',master_log_pos=445,master_port=3306;
    start slave;
    show slave status; -- Slave_IO_Running 和 Slave_SQL_Running 为 YES 代表配置成功
    ```