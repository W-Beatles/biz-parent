## 修改swagger文档聚合名称
```
eureka.instance.metadata-map.swagger-name=默认使用applicationName,可以自定义文档聚合名称
```

###  配置版本路由权重
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
      # 配置版本路由权重
      routes:
        - id: users_service_v1
          uri: http://localhost:8081/api/v1
          predicates:
            - Path=/api/v1
            - Weight=service1, 5
        - id: users_service_v2
            uri: http://localhost:8081/api/v2
            predicates:
              - Path=/api/v1
              - Weight=service1, 5
```