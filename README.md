# biz-parent

### 项目介绍

SpringCloud微服务开发脚手架

### 模块说明

> 注： 详细介绍请参考各模块 **README.md** 文档

1. biz-boot-starter-dynamic-datasource  
    动态数据源模块。支持读写分离、多数据源动态切换
2. biz-boot-starter-elastic-job  
    elastic-job starter模块
3. biz-boot-starter-logger  
    elk&sentry starter模块。用于日志上传ELK及Sentry
4. biz-facade-common  
    通用facade模块
5. biz-spring-boot-admin  
    SpringBoot Admin监控模块
6. biz-spring-cloud-api-dynamic-datasource-test  
    动态数据源测试模块
7. biz-spring-cloud-api-service-order  
     订单模块(测试)
8. biz-spring-cloud-api-service-user  
     用户模块(测试)
9. biz-spring-cloud-api-service-utility  
     公共服务。提供省市区查询、短链生成及解析服务
10. biz-spring-cloud-api-starter  
   spring-cloud starter模块。提供MDC过滤器、接口/方法切面、分布式锁等一系列开箱即用功能
11. biz-spring-cloud-archetype  
    用于一键生成spring-cloud项目基础开发骨架
12. biz-spring-cloud-common  
    spring-cloud项目通用工具类
13. ~~(已废弃)biz-spring-cloud-dashboard-hystrix~~  
    推荐使用biz-spring-cloud-dashboard-turbine来进行Hystrix断路器监控
14. biz-spring-cloud-dashboard-turbine  
     用于监控应用hystrix.stream断点，并进行Hystrix断路器信息聚合
15. biz-spring-cloud-eureka  
     Eureka注册中心
16. biz-spring-cloud-gateway  
     API网关。用做服务代理及API聚合，并提供所有服务的Swagger文档聚合
17. biz-spring-cloud-test  
     测试模块

### TODO LIST

1. ~~支持微服务调用全链路日志追踪，添加`requestId`(请求唯一标识，网关生成或来自外部请求头，格式为UUID)、`appId`(应用唯一标识)、`appName`(应用名称)、`traceAppNames`(appName调用链路记录，以`,`分割)、`traceHostNames`(hostName调用链路记录，以`,`分割)、`traceHostAddresses`(hostAddress调用链路记录，以`,`分割)~~
2. ~~日志模块支持kafka日志上传~~
3. 完善动态数据源模块  
   - ~~支持注解声明式指定数据源~~
   - 集成`seata`分布式事务解决方案
5. 添加文件上传模块 `biz-spring-cloud-file-upload`  
   - 支持私有、公有文件上传，接入`云OSS`及`七牛云服务`存储服务，私有文件采用`FastDFS`分布式文件系统存储 
6. 添加统一认证鉴权模块 `biz-spring-cloud-oauth`
7. 添加分布式任务调度系统模板(扩展xxl-job)
   - 支持任务前置依赖配置
   - 支持任务优先级配置
   - 支持任务手动执行指定服务器IP
   - 定制RPC通信协议，支持跨语言跨平台调度
   - 新加任务执行线程池，替换JobThread,减少线程创建销毁带来的开销 `@see https://github.com/XuMinCha/xxl-job`
   - 新增注解@MultiplexJobHandler和@JobMethod，支持方法级别任务，类似spring的@scheduled `@see https://github.com/XuMinCha/xxl-job`