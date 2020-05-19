
# docker快速启动

## 常用命令
```shell
docker-compose up -d                   # 后台启动，如果容器不存在根据镜像自动创建
docker-compose down -v                 # 停止容器并删除容器
docker-compose start                   # 启动容器，容器不存在就无法启动，不会自动创建镜像
docker-compose stop                    # 停止容器
docker build -t waynechu/xxx:v1.0.0 .  # 创建镜像
docker push waynechu/xxx:v1.0.0        # 推送远程仓库
```

## 启动/停止容器
```
docker-compose start/stop
docker-compose -f docker-compose-elk.yml start/stop
docker-compose -f docker-compose-apollo.yml start/stop
docker-compose -f docker-compose-eureka.yml start/stop
docker-compose -f docker-compose-service.yml start/stop
docker-compose -f docker-compose-dashboard.yml start/stop
```

## 初始化容器
### 1. 基础依赖(必须)

进入docker目录, 执行`docker-compose up -d` 或单个启动`docker-compose up 服务名 -d`, 服务名如下:  

|  服务           |  服务名          |  端口     |  帐号/密码         |  地址                         |
|----------------|------------------|-----------|------------------|-------------------------------|
|  数据库(主)     |   mysql-master   |  3306     |  waynechu/123456  |                               |
|  数据库(从)     |   mysql-slave1   |  3307     |  waynechu/123456  |                               |
|  数据库(从)     |   mysql-slave2   |  3308     |  waynechu/123456  |                               |
|  KV缓存         |   redis         |  6379     |  123456           |                               |
|  消息中间件     |   rabbitmq       |  5672     |  waynechu/123456  |  http://localhost:15672/      |                 |

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
    change master to master_host='172.20.0.2',master_user='replication',master_password='EiO8Rx4W.T3',master_log_file='mysql-bin.000003',master_log_pos=445,master_port=3306;
    start slave;
    show slave status; -- Slave_IO_Running 和 Slave_SQL_Running 为 YES 代表配置成功
    ```

### 2. elk(可选)

`docker-compose -f docker-compose-elk.yml up -d`

|  服务           |  服务名          |  端口     |  帐号/密码         |  地址                         |
|---------------- |-----------------|-----------|------------------|-------------------------------|
|  搜索引擎        |   elasticsearch |  9200     |                  |                               |
|  日志分析工具    |   kibana        |  5601     |                   |  http://localhost:5601/       |
|  日志收集工具    |   logstash      |  7002     |                   |                               |

### 3. apollo(可选)

`docker-compose -f docker-compose-apollo.yml up -d`

|  服务           |  服务名          |  端口     |  帐号/密码         |  地址                         |
|---------------- |-----------------|-----------|------------------|-------------------------------|
|  配置中心        |   apollo        |  9070     |  apollo/admin    |  http://localhost:8070/       |

### 4. eureka注册中心

`docker-compose -f docker-compose-eureka.yml up -d`

|  服务           |  服务名          |  端口     |  帐号/密码         |  地址                         |
|---------------- |-----------------|-----------|------------------|-------------------------------|
|  注册中心(peer1) |   eureka-1      |  9001     |                  |  http://localhost:9001/       |
|  注册中心(peer1) |   eureka-2      |  9002     |                  |  http://localhost:9002/       |

### 5. dashboard(包括网关、监控等)

`docker-compose -f docker-compose-dashboard.yml up -d`

|  服务           |  服务名          |  端口     |  帐号/密码         |  地址                                        |
|---------------- |-----------------|-----------|------------------|----------------------------------------------|
|  网关           |   inner-gateway |  9011     |                  |  http://localhost:9011/swagger-ui.html        |
|  监控中心        |   boot-admin    |  9020     |                  |  http://localhost:9020/                      |

### 6. skywalking

`docker-compose -f docker-compose-elk.yml -f docker-compose-skywalking.yml up -d`

|  服务           |  服务名          |  端口     |  帐号/密码         |  地址                                        |
|---------------- |-----------------|-----------|------------------|----------------------------------------------|
|  skywalking     |   skywalking-ui |  9011     |                  |  http://localhost:9011/swagger-ui.html        |

### 7. 普通服务

`docker-compose -f docker-compose-service.yml up -d`

|  服务            |  服务名                  |  端口     |  帐号/密码         |  地址                                    |
|------------------|-------------------------|-----------|------------------|------------------------------------------|
|  订单服务(peer1)  |  service-order-peer1    |  10010    |                  |  http://localhost:10010/swagger-ui.html  |
|  订单服务(peer2)  |  service-order-peer2    |  10011    |                  |  http://localhost:10011/swagger-ui.html  |
|  订单服务(peer1)  |  service-product-peer1  |  10020    |                  |  http://localhost:10020/swagger-ui.html  |
|  订单服务(peer2)  |  service-product-peer2  |  10021    |                  |  http://localhost:10021/swagger-ui.html  |
|  公共服务(peer1)  |  service-utility-peer1  |  10030    |                  |  http://localhost:10030/swagger-ui.html  |
|  公共服务(peer2)  |  service-utility-peer2  |  10031    |                  |  http://localhost:10031/swagger-ui.html  |