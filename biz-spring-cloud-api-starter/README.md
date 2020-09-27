# biz-spring-cloud-api-starter

### 项目介绍
提供微服务项目基础功能封装，如日志切面、统一异常处理、分布式锁、Swagger文档配置、跨域配置、线程池配置...等。

### 功能清单
1. ~~DataModifiedInterceptor SQL拦截器~~ (已移除)

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
    
2. ControllerExceptionHandler 统一异常处理切面 (默认开启)

3. ControllerLogAspect Controller 日志切面 (默认开启)

4. MethodLogAspect 方法调用情况切面 (默认开启)

5. DistributedLockAspect 分布式锁实现，用于资源访问控制 (默认关闭)  
    参考配置:
    ```
    ### distributed-lock
    biz.api.starter.distributed-lock.enable=true
    biz.api.starter.distributed-lock.prefix=work.waynechu.cn
    ```

6. CorsAutoConfiguration 跨域配置 (默认关闭)  
    参考配置:
    ```
    ### cors
    biz.api.starter.cors.enable=true
    biz.api.starter.cors.allowedOrigins=api.waynchu.cn,*.waynechu.cn
    ```

7. SwaggerAutoConfiguration 接口Swagger API文档 (默认关闭)  
    参考配置:
    ```
    ### swagger
    biz.api.starter.swagger.enable=true
    biz.api.starter.swagger.api-title=文档标题
    biz.api.starter.swagger.api-description=文档描述
    biz.api.starter.swagger.api-version=1.0.0
    biz.api.starter.swagger.scan-package=cn.waynechu
    biz.api.starter.swagger.contact-name=联系人名称
    biz.api.starter.swagger.contact-url=联系人主页
    biz.api.starter.swagger.contact-email=联系人邮箱
    ```

8. FeignHeaderInterceptor拦截器  
    该拦截器用于微服务间使用Feign互相调用传递header请求头信息

9. RestTemplateTraceInterceptor拦截器  
   该拦截器用于微服务间使用RestTemplate互相调用传递header请求头信息
   
10. RedisUtil Redis缓存工具类  
    注入`RedisUtil`即可使用

11. PageLoopHelper 基于BizPageInfo的分页查询工具  
    注入`PageLoopHelper`即可使用

12. ExcelHelper excel解析、异步导出工具
    支持大批量、大数据量场景下的excel导出。简化导出代码，优化导出流程

13. BizThreadPoolExecutor自定义线程池
    - 解决异步线程`MDC`信息、`ThreadLocal`信息无法传递的问题
    - 解决异步线程`requestAttributes`信息无法传递的问题
    - 提供 `Executor`、`ExecutorService` 实例供开发使用
    - 兼容Spring框架提供的`applicationTaskExecutor`、`taskExecutor`实例，使用`@Async`注解的异步线程也能打印日志到ELK中
