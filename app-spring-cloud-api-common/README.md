# app-spring-cloud-api-common

### 项目介绍

1. MDCFilter过滤器 (默认关闭)

    该过滤器会调用 MDC.put("reqKey", reqKeyValue) 添加reqKey到MDC映射调试上下文中，这样便于在log日志中追踪请求调用信息   
    其中reqKey的格式为: traceNo-prefix-shortJavaUUID-localHostName
    - traceNo: 请求追踪号。可添加至请求头或请求参数中来追踪请求链路，其中key为traceNo
    - prefix: 项目唯一标识。默认取值为`${spring.application.name}`
    - shortJavaUUID: 请求的唯一标识。长度6个字符
    - localHostName: 服务器HostName
    
    参考配置:
    ```
    ### mdc-filter
    app.api.common.mdc-filter.enable=true
    app.api.common.mdc-filter.prefix=${spring.application.name}
    ```
    
2. ~~DataModifiedInterceptor SQL拦截器~~ (已移除)

    > **last commit (a765265a)**: feat(app-boot-starter-common): 添加RegexUtils工具类 Z-Beatles 2019-03-18 21:43
    
    ~~该拦截器基于MyBatis拦截器实现，会拦截执行的SQL并对`insert`和`update`操作自动添加上操作人和操作时间，这样就无须在业务代码
    中手动为`createUser/updateUser`、`createTime/updateTime`设值~~

    ~~参考配置:~~
    ```
     ### data-modified-interceptor
     app.boot.starter.data-modified-interceptor.enable=true
     app.boot.starter.data-modified-interceptor.created-user-attr-name=createdUser
     app.boot.starter.data-modified-interceptor.created-time-attr-name=createdTime
     app.boot.starter.data-modified-interceptor.updated-user-attr-name=updatedUser
     app.boot.starter.data-modified-interceptor.updated-time-attr-name=updatedTime
    ```
    
3. ControllerExceptionHandler 统一异常处理切面 (默认开启)

4. ControllerLogAspect Controller 日志切面 (默认开启)

5. MethodLogAspect 方法调用情况切面 (默认开启)

6. RedisCache Redis缓存工具类 (默认关闭)

7. CorsAutoConfiguration 跨域配置 (默认关闭)

    参考配置:
    ```
    ### cors
    app.api.common.cors.enable=true
    app.api.common.cors.allowedOrigins=api.waynchu.cn,*.waynechu.cn
    ```
    
8. SpringContextHolder Spring上下文工具类 (默认注入)

    使用方式:   
    使用 `@Autowire` 注解注入 `SpringContextHolder` 即可

9. SwaggerAutoConfiguration 开启Swagger API文档 (默认关闭)

    参考配置:
    ```
    ### swagger
    app.api.common.swagger.enable=true
    app.api.common.swagger.api-title=文档标题
    app.api.common.swagger.api-description=文档描述
    app.api.common.swagger.api-version=1.0.0
    app.api.common.swagger.scan-package=com.waynechu
    app.api.common.swagger.contact-name=联系人名称
    app.api.common.swagger.contact-url=联系人名称
    app.api.common.swagger.contact-email=联系人邮箱
    ```
    