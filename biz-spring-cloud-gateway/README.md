# biz-spring-cloud-gateway

### 项目介绍
微服务网关

### 功能点
- 负载均衡
- 服务聚合
- 动态路由
- 权限控制
- 版本控制(灰度)
- 黑白名单
- 熔断限流
- 链路追踪

### 网关鉴权
![gateway-authentication.png](../docs/gateway/gateway-authentication.png "网关鉴权")

### 相关配置说明
自定义swagger文档聚合名称，配置如下:
```
eureka.instance.metadata-map.swagger-name=${spring.applicationName}
```

### 示例: 配置权重路由规则
```
spring:
  application:
    name: biz-spring-cloud-gateway
  cloud:
    gateway:
      # 开启基于服务发现的路由规则
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      # 配置权重路由规则
      routes:
        - id: service-utility_v1
          uri: http://localhost:10030/service-utility/test/v1
          predicates:
            - Path=/service-utility/test
            - Weight=service1, 1
        - id: service-utility_v2
          uri: http://localhost:10030/service-utility/test/v2
          predicates:
            - Path=/service-utility/test
            - Weight=service2, 2
```

### 示例: 配置限流路由规则
```
spring:
  application:
    name: biz-spring-cloud-gateway
  cloud:
    gateway:
      # 开启基于服务发现的路由规则
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      # 配置限流路由规则
      routes:
        - id: service-utility-route
          uri: lb://service-utility
          order: -1  # 配置优先级高于默认0
          predicates:
            - Path=/service-utility/**
            - TimeBetween=09:00,18:00  # 基于时间的谓词规则
          filters:
            - StripPrefix=1  # 从二级url路径开始转发
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.burstCapacity: 1000  # 令牌桶容量(最大并发量)
                redis-rate-limiter.replenishRate: 100  # 流速(平均并发量)
                key-resolver: "#{@remoteAddrKeyResolver}" # SPEL表达式获取对应的bean
```

### 扩展：路由谓词工厂配置
|  谓词工厂     |   备注                 | 示例                        |
|--------------|------------------------|----------------------------|
|After	       |  此谓词匹配当前日期时间之后发生的请求 | After=2020-10-01T09:00:00.000+08:00[Asia/Shanghai] |
|Before	       |  此谓词匹配在当前日期时间之前发生的请求 | Before=2020-10-01T18:00:00.000+08:00[Asia/Shanghai] |
|Between       |  此谓词匹配datetime1之后和datetime2之前发生的请求。 datetime2参数必须在datetime1之后 |
|Cookie	       |  Cookie Route Predicate Factory有两个参数，cookie名称和正则表达式。此谓词匹配具有给定名称且值与正则表达式匹配的cookie |
|Header	       |  Header Route Predicate Factory有两个参数，标题名称和正则表达式。与具有给定名称且值与正则表达式匹配的标头匹配 |
|Host	       |  Host Route Predicate Factory采用一个参数：主机名模式。该模式是一种Ant样式模式“.”作为分隔符。此谓词匹配与模式匹配的Host标头 |
|Method	       |  Method Route Predicate Factory采用一个参数：要匹配的HTTP方法 |
|Path	       |  匹配请求的path |
|Query	       |  Query Route Predicate Factory有两个参数：一个必需的参数和一个可选的正则表达式 |
|RemoteAddr	   |  RemoteAddr Route Predicate Factory采用CIDR符号（IPv4或IPv6）字符串的列表（最小值为1），例如， 192.168.0.1/16（其中192.168.0.1是IP地址，16是子网掩码） |
	
