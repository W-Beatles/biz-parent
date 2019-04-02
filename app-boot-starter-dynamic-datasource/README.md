# app-boot-starter-dynamic-datasource

### 项目介绍

1. 基于Spring提供的 AbstractDataSource 实现多数据源路由选择
2. 基于MyBatis拦截器实现主从数据源的动态切换
3. 集成Druid数据源监控多数据源，支持原生SQL监控、防火墙监控、慢查询日志、Url监控、Spring监控等
4. 可实现 **DynamicDataSourceStrategy** 接口并自定义动态数据源选择策略。默认提供轮询、随机两种

### 使用方法

1. 添加pom依赖

    ```
    <dependency>
        <groupId>cn.waynechu</groupId>
        <artifactId>app-boot-starter-dynamic-datasource</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
    ```

2. 添加配置

    **推荐：开发环境开启log日志，可查看详细数据源切换的日志**
    
    ```
    ## logging
    logging.level.com.tuhu.dynamic.datasource=DEBUG
    ```

下面是参考配置，更多配置请参考Druid官方文档。除多数据源配置不一致外，Druid其他特性配置都能够很好地支持。

### 参考配置    

```
## mybatis
mybatis.mapper-locations=classpath:sqlmap/**/*Mapper.xml
mybatis.configuration.cache-enabled=false
mybatis.configuration.map-underscore-to-camel-case=true

## dynamic datasource
### 设置动态数据源选择策略。不配置默认使用轮询策略
spring.datasource.dynamic.strategy=com.tuhu.dynamic.datasource.strategy.RoundRobinDynamicDataSourceStrategy
### 设置Druid密码加密公钥
spring.datasource.dynamic.druid.public-key=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIxH6Gne6flG+enZOeGsbg4hoiGFi1ORqvsi8EzlObP3Gz/NVQpJACcBidowsWolaYKyfv8jHUClNja3GCE2x+kCAwEAAQ==
### 开启stat后台监控页面
spring.datasource.dynamic.druid.stat-view-servlet.enabled=true
spring.datasource.dynamic.druid.stat-view-servlet.login-username=admin
spring.datasource.dynamic.druid.stat-view-servlet.login-password=123456
### 打开SQL监控、防火墙监控
spring.datasource.dynamic.druid.filters=stat,wall
### 多数据源SQL合并，开启慢查询日志记录
spring.datasource.dynamic.druid.connection-properties.druid.stat.mergeSql=true
spring.datasource.dynamic.druid.connection-properties.druid.stat.slowSqlMillis=1000
spring.datasource.dynamic.druid.connection-properties.druid.stat.logSlowSql=true
### 打开Web应用监控
spring.datasource.dynamic.druid.web-stat-filter.enabled=true
### 设置Spring监控AOP包路径
spring.datasource.dynamic.druid.aop-patterns=com.tuhu.demo.web.controller.*,com.tuhu.demo.core.service.*
### 设置多数据源
#### order database 订单库配置(一主两从)
spring.datasource.dynamic.datasource.order-master.username=root
spring.datasource.dynamic.datasource.order-master.password=LP1lXJ+2jrs+QhjLUJJRv3iALW9dgsoHAWyzVihmGW5Oooiw0Gyhi4nzeRW/JWrTxwUSgxnkt5pcbtppXjtbqA==
spring.datasource.dynamic.datasource.order-master.url=jdbc:mysql://192.168.1.100:3306/order?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
spring.datasource.dynamic.datasource.order-slave1.username=root
spring.datasource.dynamic.datasource.order-slave1.password=LP1lXJ+2jrs+QhjLUJJRv3iALW9dgsoHAWyzVihmGW5Oooiw0Gyhi4nzeRW/JWrTxwUSgxnkt5pcbtppXjtbqA==
spring.datasource.dynamic.datasource.order-slave1.url=jdbc:mysql://192.168.1.101:3306/order?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
spring.datasource.dynamic.datasource.order-slave2.username=root
spring.datasource.dynamic.datasource.order-slave2.password=LP1lXJ+2jrs+QhjLUJJRv3iALW9dgsoHAWyzVihmGW5Oooiw0Gyhi4nzeRW/JWrTxwUSgxnkt5pcbtppXjtbqA==
spring.datasource.dynamic.datasource.order-slave2.url=jdbc:mysql://192.168.1.102:3306/order?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
#### product database 产品库配置(一主两从)
spring.datasource.dynamic.datasource.product-master.username=root
spring.datasource.dynamic.datasource.product-master.password=LP1lXJ+2jrs+QhjLUJJRv3iALW9dgsoHAWyzVihmGW5Oooiw0Gyhi4nzeRW/JWrTxwUSgxnkt5pcbtppXjtbqA==
spring.datasource.dynamic.datasource.product-master.url=jdbc:mysql://192.168.2.100:3306:3306/product?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
spring.datasource.dynamic.datasource.product-slave1.username=root
spring.datasource.dynamic.datasource.product-slave1.password=LP1lXJ+2jrs+QhjLUJJRv3iALW9dgsoHAWyzVihmGW5Oooiw0Gyhi4nzeRW/JWrTxwUSgxnkt5pcbtppXjtbqA==
spring.datasource.dynamic.datasource.product-slave1.url=jdbc:mysql://192.168.2.101:3306:3307/product?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
spring.datasource.dynamic.datasource.product-slave2.username=root
spring.datasource.dynamic.datasource.product-slave2.password=LP1lXJ+2jrs+QhjLUJJRv3iALW9dgsoHAWyzVihmGW5Oooiw0Gyhi4nzeRW/JWrTxwUSgxnkt5pcbtppXjtbqA==
spring.datasource.dynamic.datasource.product-slave2.url=jdbc:mysql://192.168.2.102:3306:3308/product?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
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

3. 如果不使用 **master** 标记主数据源，会使用添加到该组的第一个数据源作为主数据源。有时候这将会因为从数据源的只读设置带来读写失败异常的情况发生。所以最好给主数据源名称加个 **master** 字符标记哦！

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

2. 限制了 **mapper(xml)** 文件放置的位置。目前切换不同数据库的逻辑是使用MyBatis拦截器拿到
 **MappedStatement** 对象的 **resource** 字符串。其中 **resource** 代表了当前查询的mapper方法对应的xml的存放路径。
     
     关键代码如下：
     ```
     MappedStatement ms = (MappedStatement) args[0];
     String resource = ms.getResource(); // resource = "file [C:\Users\zhuwei\workspace\tuhu-parent\order-integration-dal\target\classes\sqlmap\gungnir\OrderMapper.xml]";
     String[] splitResource = resource.replaceAll("\\\\", "/").split("/");
     String groupName = splitResource[splitResource.length - 2]; // groupName = "gungnir"
     ```
    这样拦截器就可以获取到当前要操作的数据库属于 **gungnir** 数据源组。
    
    所以，**关键！关键！关键！**
    把属于不同数据库的mapper.xml文件放在 **对应数据库组名** 的文件夹下
    
    比如：
    - resource
       - sqlmap
          - gungnir
             - temp.xml
             - temp1.xml
          - tuhu_order
             - test.xml
             - test1.xml