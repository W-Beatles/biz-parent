# biz-spring-cloud-api-starter

### 项目介绍

1. MDCFilter过滤器 (默认关闭)

    该过滤器用于添加请求信息到MDC上下文中
    
    参考配置:
    ```
    ### mdc-filter
    biz.api.starter.mdc-filter.enable=true
   ```
   
   添加的MDC信息有:
   ```
    ---------- MDC信息 ----------
    traceNo                  - 请求跟踪号。可添加到请求头或请求参数上，用于自定义追踪标记
    requestId                - 请求唯一标识。格式为UUID(32个字符)
    apiVersion               - 请求的API版本号。该值为 spring.application.name 的赋值
    channel                  - 调用方标识
    deviceId                 - 设备id
    traceAppNames            - appName调用链路追踪记录，以逗号分割
    traceHostNames           - hostName调用链路追踪记录，以`,`分割
    traceHostAddresses       - hostAddress调用链路追踪记录，以`,`分割
   ```
   
2. FeignHeaderInterceptor拦截器。 (默认关闭)

    该拦截器用于微服务间使用feign互相调用传递header请求头信息
    
    参考配置:
    ```
    ### feign-header-interceptor
    biz.api.starter.feign-trace-interceptor.enable=true
    biz.api.starter.feign-trace-interceptor.feign-trace-headers=requestId,traceNo,traceAppNames,traceHostNames,traceHostAddresses
    ```
    
3. ~~DataModifiedInterceptor SQL拦截器~~ (已移除)

    > **last commit (a765265a)**: feat(biz-boot-starter-common): 添加RegexUtils工具类 Z-Beatles 2019-03-18 21:43
    
    ~~该拦截器基于MyBatis拦截器实现，会拦截执行的SQL并对`insert`和`update`操作自动添加上操作人和操作时间，这样就无须在业务代码
    中手动为`createUser/updateUser`、`createTime/updateTime`设值~~

    ~~参考配置:~~
    ```
     ### data-modified-interceptor
     biz.boot.starter.data-modified-interceptor.enable=true
     biz.boot.starter.data-modified-interceptor.created-user-attr-name=createdUser
     biz.boot.starter.data-modified-interceptor.created-time-attr-name=createdTime
     biz.boot.starter.data-modified-interceptor.updated-user-attr-name=updatedUser
     biz.boot.starter.data-modified-interceptor.updated-time-attr-name=updatedTime
    ```
    
4. ControllerExceptionHandler 统一异常处理切面 (默认开启)

5. ControllerLogAspect Controller 日志切面 (默认开启)

6. MethodLogAspect 方法调用情况切面 (默认开启)

7. DistributedLockAspect 分布式锁实现，用于方法访问控制 (默认关闭)

    参考配置:
    ```
    ### distributed-lock
    biz.api.starter.distributed-lock.enable=true
    biz.api.starter.distributed-lock.prefix=work.waynechu.cn
    ```

8. RedisCache Redis缓存工具类 (默认关闭)

9. CorsAutoConfiguration 跨域配置 (默认关闭)

    参考配置:
    ```
    ### cors
    biz.api.starter.cors.enable=true
    biz.api.starter.cors.allowedOrigins=api.waynchu.cn,*.waynechu.cn
    ```

10. SwaggerAutoConfiguration 开启Swagger API文档 (默认关闭)

    参考配置:
    ```
    ### swagger
    biz.api.starter.swagger.enable=true
    biz.api.starter.swagger.api-title=文档标题
    biz.api.starter.swagger.api-description=文档描述
    biz.api.starter.swagger.api-version=1.0.0
    biz.api.starter.swagger.scan-package=com.waynechu
    biz.api.starter.swagger.contact-name=联系人名称
    biz.api.starter.swagger.contact-url=联系人主页
    biz.api.starter.swagger.contact-email=联系人邮箱
    ```
    