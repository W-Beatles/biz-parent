# biz-spring-cloud-api-starter

### 项目介绍

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

7. SwaggerAutoConfiguration 开启Swagger API文档 (默认关闭)

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
   
8. RedisUtil Redis缓存工具类
    注入`RedisUtil`即可使用

9. PageLoopUtil 基于BizPageInfo的分页查询工具
    注入`PageLoopUtil`即可使用

10. ExcelUtil excel解析、异步下载工具

    设计原则： 
    - 解耦数据导出服务与业务站点
    - 后端统一接口/前端组件化
    - 提供数据获取接口，便于业务方对接
    - 提供简单的导出数据模型定义方法
    - 封装公共组件方法，管理整个生命周期，简化接入方开发
    - 异步数据处理，引入文件系统，避免因网络限制带来的问题