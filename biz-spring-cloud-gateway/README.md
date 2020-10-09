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
            - TimeBetween=上午9:00,下午6:00  # 基于时间的谓词规则
          filters:
            - StripPrefix=1  # 从二级url路径开始转发
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.burstCapacity: 1000  # 令牌桶容量(最大并发量)
                redis-rate-limiter.replenishRate: 100  # 流速(平均并发量)
                key-resolver: "#{@remoteAddrKeyResolver}" # SPEL表达式获取对应的bean
```