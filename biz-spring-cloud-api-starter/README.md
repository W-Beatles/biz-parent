# biz-spring-cloud-api-starter

### 项目介绍

1. MDCFilter过滤器 (默认关闭)

    该过滤器会调用 MDC.put("reqKey", reqKeyValue) 添加`reqKey`到MDC映射调试上下文中，这样便于在log日志中追踪请求调用信息   
    其中reqKey的格式为: traceNo-prefix-shortJavaUUID-localHostName
    - traceNo: 请求追踪号。可添加至请求头或请求参数中来追踪请求链路，其中key为traceNo
    - prefix: 项目唯一标识。默认取值为`${spring.application.name}`
    - shortJavaUUID: 请求的唯一标识。长度6个字符
    - localHostName: 服务器HostName
    
    参考配置:
    ```
    ### mdc-filter
    biz.api.starter.mdc-filter.enable=true
    biz.api.starter.mdc-filter.prefix=${spring.application.name}
    ```
    
2. ~~DataModifiedInterceptor SQL拦截器~~ (已移除)

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
    
3. ControllerExceptionHandler 统一异常处理切面 (默认开启)

4. ControllerLogAspect Controller 日志切面 (默认开启)

5. MethodLogAspect 方法调用情况切面 (默认开启)

6. DistributedLockAspect 分布式锁实现，用于方法访问控制 (默认关闭)

    参考配置:
    ```
    ### distributed-lock
    biz.api.starter.distributed-lock.enable=true
    biz.api.starter.distributed-lock.prefix=work.waynechu.cn
    ```

7. ~~RedisCache Redis缓存工具类 (默认关闭)~~(已移除)

8. CorsAutoConfiguration 跨域配置 (默认关闭)

    参考配置:
    ```
    ### cors
    biz.api.starter.cors.enable=true
    biz.api.starter.cors.allowedOrigins=api.waynchu.cn,*.waynechu.cn
    ```
    
9. SpringContextHolder Spring上下文工具类 (默认注入)

    使用方式:   
    使用 `@Autowire` 注解注入 `SpringContextHolder` 即可

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
    