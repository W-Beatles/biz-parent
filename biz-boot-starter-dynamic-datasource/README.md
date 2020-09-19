# biz-boot-starter-dynamic-datasource

### 项目介绍
1. 基于Spring提供的 `LazyConnectionDataSourceProxy` 实现多数据源路由选择
2. 基于MyBatis拦截器实现主从数据源的动态切换
3. 支持类、方法级别添加注解 `SwitchDataSource` 来手动切换目标数据源
4. 可实现 `DynamicDataSourceStrategy` 接口并自定义动态数据源选择策略。默认提供轮询、随机两种
5. 集成Druid数据源，支持原生SQL监控、防火墙监控、慢查询监控、Url监控、Spring监控等
6. 兼容 `mybatis-plus3` 持久层框架，简化CRUD开发
7. 默认添加 `mybatis-typehandlers-jsr310` 时间类库依赖，支持数据库时间类型到Java8 `LocalData`、`LocalDataTime`时间类型的映射

### 使用方法
1. 添加pom依赖
    ```
    <dependency>
        <groupId>cn.waynechu</groupId>
        <artifactId>biz-boot-starter-dynamic-datasource</artifactId>
    </dependency>
    ```
2. 启动类添加注解配置 `mapper` java文件扫描路径
   ```
   @MapperScan("com.xxx.yyy.dal.mapper")
   ```
2. 添加数据源配置
    参数配置参考下一节，更多配置可查阅Druid官方文档。除多数据源配置不一致外，Druid其他特性配置都能够很好地支持。

### 参考配置    
```
## mybatis-plus
mybatis-plus.mapper-locations=classpath:sqlmap/**/*Mapper.xml
mybatis-plus.configuration.cache-enabled=false
mybatis-plus.configuration.map-underscore-to-camel-case=true
mybatis-plus.configuration.use-generated-keys=true
mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl

## dynamic datasource
### 打印动态数据源执行情况
spring.datasource.dynamic.logger-enable=true
### 设置动态数据源选择策略。不配置默认使用轮询策略
spring.datasource.dynamic.strategy=cn.waynechu.bootstarter.dynamicdatasource.strategy.RoundRobinDynamicDataSourceStrategy
### 设置Druid密码加密公钥
spring.datasource.dynamic.druid.public-key=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIxH6Gne6flG+enZOeGsbg4hoiGFi1ORqvsi8EzlObP3Gz/NVQpJACcBidowsWolaYKyfv8jHUClNja3GCE2x+kCAwEAAQ==
### 开启stat后台监控页面
spring.datasource.dynamic.druid.stat-view-servlet.enabled=true
spring.datasource.dynamic.druid.stat-view-servlet.login-username=admin
spring.datasource.dynamic.druid.stat-view-servlet.login-password=123456
### 打开SQL监控、防火墙监控
spring.datasource.dynamic.druid.filters=stat,wall
### 限制单次查询数量
spring.datasource.dynamic.druid.wall.select-limit=100000
### 多数据源SQL合并，开启慢查询日志记录
spring.datasource.dynamic.druid.connection-properties.druid.stat.mergeSql=true
spring.datasource.dynamic.druid.connection-properties.druid.stat.slowSqlMillis=1000
spring.datasource.dynamic.druid.connection-properties.druid.stat.logSlowSql=true
### 打开Web应用监控
spring.datasource.dynamic.druid.web-stat-filter.enabled=true
### 设置Spring监控AOP包路径
spring.datasource.dynamic.druid.aop-patterns=cn.waynechu.*.api.controller.*,cn.waynechu.*.domain.service.*
### 多数据源配置
#### order database 订单库
spring.datasource.dynamic.datasource.order-master.username=root
spring.datasource.dynamic.datasource.order-master.password=LP1lXJ+2jrs+QhjLUJJRv3iALW9dgsoHAWyzVihmGW5Oooiw0Gyhi4nzeRW/JWrTxwUSgxnkt5pcbtppXjtbqA==
spring.datasource.dynamic.datasource.order-master.url=jdbc:mysql://localhost:3306/order?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.dynamic.datasource.order-slave1.username=root
spring.datasource.dynamic.datasource.order-slave1.password=LP1lXJ+2jrs+QhjLUJJRv3iALW9dgsoHAWyzVihmGW5Oooiw0Gyhi4nzeRW/JWrTxwUSgxnkt5pcbtppXjtbqA==
spring.datasource.dynamic.datasource.order-slave1.url=jdbc:mysql://localhost:3307/order?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.dynamic.datasource.order-slave2.username=root
spring.datasource.dynamic.datasource.order-slave2.password=LP1lXJ+2jrs+QhjLUJJRv3iALW9dgsoHAWyzVihmGW5Oooiw0Gyhi4nzeRW/JWrTxwUSgxnkt5pcbtppXjtbqA==
spring.datasource.dynamic.datasource.order-slave2.url=jdbc:mysql://localhost:3308/order?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
#### product database 产品库
spring.datasource.dynamic.datasource.product-master.username=root
spring.datasource.dynamic.datasource.product-master.password=LP1lXJ+2jrs+QhjLUJJRv3iALW9dgsoHAWyzVihmGW5Oooiw0Gyhi4nzeRW/JWrTxwUSgxnkt5pcbtppXjtbqA==
spring.datasource.dynamic.datasource.product-master.url=jdbc:mysql://localhost:3306/product?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.dynamic.datasource.product-slave1.username=root
spring.datasource.dynamic.datasource.product-slave1.password=LP1lXJ+2jrs+QhjLUJJRv3iALW9dgsoHAWyzVihmGW5Oooiw0Gyhi4nzeRW/JWrTxwUSgxnkt5pcbtppXjtbqA==
spring.datasource.dynamic.datasource.product-slave1.url=jdbc:mysql://localhost:3307/product?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.dynamic.datasource.product-slave2.username=root
spring.datasource.dynamic.datasource.product-slave2.password=LP1lXJ+2jrs+QhjLUJJRv3iALW9dgsoHAWyzVihmGW5Oooiw0Gyhi4nzeRW/JWrTxwUSgxnkt5pcbtppXjtbqA==
spring.datasource.dynamic.datasource.product-slave2.url=jdbc:mysql://localhost:3308/product?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
```

### 健康检查
默认开启健康检查，如需关闭请添加如下配置
```
## health endpoint
management.health.dynamic-datasource.enabled=false
```

### 约定与说明
比如：
```
spring.datasource.dynamic.datasource.order-master.balalala...
spring.datasource.dynamic.datasource.order-slave1.balalala...
spring.datasource.dynamic.datasource.order-slave2.balalala...
spring.datasource.dynamic.datasource.order-slave3.balalala...
```

1. 其中 **order-master**、**order-slave1**、**order-slave2** 代表数据源的名称，动态数据源会将 **-** 之前字符相同的数据源划分到同一组数据源中
2. 减号 **-** 后面的部分为数据源名称，你可以起任何有意义的名称。要想配置某个组内的某个数据源为主数据源，只需要数据源名称中包含 **master** 字符标记即可
3. 如果不使用 **master** 标记主数据源，会使用添加到该组的第一个数据源作为主数据源。有时候这将会因为从数据源的只读设置带来读写失败异常的情况发生，所以最好给主数据源名称加个 **master** 标记符哦！

### 当前版本局限
1. 不支持跨库事务/分布式事务

    比如以下事务方法中同时操作两个数据源的情况：
    
    ```
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean transitionTest() {
        ...
        // 先操作产品库 (192.168.2.100:3306)
        productRepository.create(product);
        
        // 再操作订单库 (192.168.1.100:3306)
        orderRepository.create(order);
        ...
    }
    ```
    
    当方法执行到创建订单时，因为此时操作的还是产品库，会抛出异常。在事务控制下，无法实现数据源的切换。

2. 限制了 **XxxMapper.java** 文件放置的位置。当前查询要使用的数据库是通过MyBatis拦截器拿到的。  
   取值为 **MappedStatement** 对象的 **resource** 字符，即当前查询的 **XxxMapper.java** 存放路径。
     
     关键代码如下：
     ```
     MappedStatement ms = (MappedStatement) args[0];
     String resource = ms.getResource(); // resource = "com/waynechu/dynamicdatasource/dal/mapper/order/OrderMapper.java (best guess)";
     String[] splitResource = resource.replaceAll("\\\\", "/").split("/");
     String groupName = splitResource[splitResource.length - 2]; // groupName = "order"
     ```
    这样拦截器就可以获取到当前要操作的数据库属于 **order** 数据源组。
    
    所以，**关键！关键！关键！**
    把属于不同数据库的 **XxxMapper.java** 文件放在 **对应数据库组名** 的文件夹下
    
    比如：
    - cn.waynechu.demo.dal
       - mapper
          - order
             - OrderMapper.java
          - product
             - ProductMapper.java