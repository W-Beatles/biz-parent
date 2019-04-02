# app-boot-starter-common

### 项目介绍

1. MDCFilter过滤器

    该过滤器会调用 `MDC.put("reqKey", reqKeyValue)` 添加`reqKey`到MDC映射调试上下文中，这样就可以在log日志中追踪请求调用信息。  
    其中`reqKey`的格式为: `traceNo-mdcPrefix-shortJavaUUID-localHostName`。
    - `traceNo`为请求的追踪号。可添加至请求头或请求参数中来追踪请求链路，其中key为`traceNo`
    - `mdcPrefix`为项目唯一标识
    - `shortJavaUUID`为请求的唯一标识
    - `localHostName`为服务器Host名称
    
    开启该过滤器的方式如下：
    ```
        common.mdc-filter.enable=true
        common.mdc-filter.prefix=${spring.application.name}
    ```
    
2. DataModifiedInterceptor SQL拦截器
