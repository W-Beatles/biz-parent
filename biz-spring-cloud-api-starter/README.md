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


### 扩展一：异步导出组件

#### 设计原则
- 尽量降低excel导出时的内存消耗尽量
- 后端导出统一化、前端导出组件化。实现导出功能统一化
- 封装公共导出方法，管理导出的整个生命周期，简化开发流程。详见`ExcelHelper.java`
- 使用自定义线程池异步处理，避免导出功能占用大量的服务器资源，导致其它接口无法正常响应
- 引入文件系统。避免因导出文件过大，导出逻辑耗时过长带来的网关超时等的问题

#### 设计思路
1. 公共服务提供统一的excel导出接口，前端通过统一的接口来导出。统一导出接口会根据导出的url参数转发请求到各业务方的接口实现上，并带上导出参数  
    参考`biz-spring-cloud-api-service-utility`公共基础服务的`/excels/export/sid`接口的实现
2. 公共组件包中提供通用异步导出方法。该方法封装分页查库 -> 分页数据写入excel -> excel文件上传 -> 同步导出状态等逻辑  
    参考`biz-spring-cloud-api-starter`公共组件包的`ExcelHelper.java`的实现，提供多种导出方法工业务方使用
3. 支持导出模型不同类型字段支持，包括Java8时间类库以及常用的类型转换支持  
    因为是基于`EasyExcel`实现，所以支持`EasyExcel`中所有的功能特性
4. 公共服务提供统一的获取导出excel文件下载地址的方法，支持异步轮询下载导出文件
    参考`biz-spring-cloud-api-service-utility`公共基础服务的`/excels/export/status`接口的实现
    
#### 使用方式
1. 添加pom依赖
    ```xml
    <dependency>
        <groupId>cn.waynechu</groupId>
        <artifactId>biz-spring-cloud-api-starter</artifactId>
    </dependency>
    ```
2. 在本地项目中添加导出接口，返回Sid(导出唯一表示)
    ```
        @ApiOperation("导出项目原型列表")
        @PostMapping("/export")
        public BizResponse<String> export(@RequestBody SearchArchetypeRequest request) {
            String sid = archetypeService.export(request);
            return BizResponse.success(sid);
        }
    ```
3. 定义导出的Excel模型
    ```
    @Data
    @ApiModel
    public class SearchArchetypeResponse {
    
        @ExcelProperty("原型id")
        @ApiModelProperty("原型id")
        private Long id;
    
        @ExcelIgnore
        @ApiModelProperty("项目类型: 0Service 1SDK")
        private Integer appType;
    
        @ExcelIgnore
        @ApiModelProperty("状态: 0生成中 1成功 2失败")
        private Integer statusCode;
    
        @ExcelProperty(value = "上传git", converter = BooleanConvert.class)
        @ApiModelProperty("上传git: 0否 1是")
        private Boolean gitUploadType;
    
        @ExcelProperty("创建人")
        @ApiModelProperty("创建人")
        private String createdUser;
    
        @ExcelProperty("创建时间")
        @ApiModelProperty("创建时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
        private LocalDateTime createdTime;
        ...
    }
    ```

   **相关注解说明：**

    | 注解                                                  |   说明                                            | 
    |-------------------------------------------------------|--------------------------------------------------|
    |  @ExcelProperty                                       |   自定义导出的标题头                               |
    |  @ExcelIgnore(可选)                                    |   导出时忽略该字段                                |
    |  @ColumnWidth(可选)                                    |   自定义导出的列宽度。加在类上全局生效，否则单列生效  |
    |  @HeadRowHeight(可选)                                  |   自定义标题列的高度。只能加在类上                  |
    |  @ContentRowHeight(可选)                               |   自定义内容列的高度。只能加在类上                  |
    |  @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")(可选)    |   自定义时间字段格式                               |
    |  @NumberFormat("#.##%")(可选)                          |   自定义数值类型格式。如加上百分号等                |
   > 注：如果想要自定义类型转化器，可参考 `cn.waynechu.springcloud.common.excel.convert` 包下的转化器实现。

4. 定义分页查询的方法
    ```
    @Service
    public class ArchetypeServiceImpl implements ArchetypeService {
    
        @Autowired
        private ExcelHelper excelHelper;

        @Override
        public BizPageInfo<SearchArchetypeResponse> search(SearchArchetypeRequest request) {
            ····
        }
    }
    ```
   
5. 使用导出工具类导出
    ```
    @Service
    public class ArchetypeServiceImpl implements ArchetypeService {
    
        @Autowired
        private ExcelHelper excelHelper;

        @Override
        public BizPageInfo<SearchArchetypeResponse> search(SearchArchetypeRequest request) {
            ····
        }
   
        @Override
        public String export(SearchArchetypeRequest request) {
            String fileName = "原型列表 " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return excelHelper.exportForSid(fileName, SearchArchetypeResponse.class, request, () -> search(request));
        }
    }
    ```
   > 注：导出工具还提供 exportForSid(final String fileName, Class<T> clazz, List<T> data) 导出方式，该方法只需要传入要导出的list即可。当导出数据量比较小的时候使用该方式比较灵活

6. 前后端联调
    1. 前端请求通用导出地址获取sid。接口地址为: `POST /service-utility/excels/export/sid`  
    2. 通用导出根据地址转发请求到业务放项目的导出接口上。导出地址格式为项目名 + 接口路径  
    3. 前端拿到sid之后调用通用服务接口获取导出结果。 接口地址为: `POST /service-utility/excels/export/status` 

    **导出状态status说明:**
    
    | 状态Code | 状态       |   说明                                     | 
    |----------|-----------|--------------------------------------------|
    |   -1     | 导出失败   | 导出失败，具体失败原因可通过requestId查询      |
    |    0     | 生成中     | 该状态下，前端轮询调用获取导出结果直到状态发生变更。轮询间隔建议从1秒开始指数型递增。如: 1s, 2s, 4s, 8s, 16s, 32s |
    |    1     | 生成成功   | 成功状态下，取返回的url即为导出的excel文件地址 |
