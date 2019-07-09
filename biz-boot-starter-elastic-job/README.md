# app-boot-starter-elastic-job

### 项目介绍

支持Elastic-Job-Lite开箱即用，简化开发

### 使用方法

1. 添加pom依赖

    ```
    <dependency>
        <groupId>cn.waynechu</groupId>
        <artifactId>app-boot-starter-elastic-job</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
    ```

2. 添加配置
    
    ```
    ## elastic-job
    #elastic.job.enable=true
    #elastic.job.log=false
    #elastic.job.server-lists=localhost:2181
    #elastic.job.namespace=${spring.application.name}/elastic-job
    ```

3. 创建任务

   示例代码：
   
   ```java
    @Slf4j
    @Component
    public class MyElasticJob implements SimpleJob {
    
        @Override
        public void execute(ShardingContext shardingContext) {
            int shardingTotalCount = shardingContext.getShardingTotalCount();
            int shardingItem = shardingContext.getShardingItem();
            String shardingParameter = shardingContext.getShardingParameter();
            String jobName = shardingContext.getJobName();
            String jobParameter = shardingContext.getJobParameter();
            switch (shardingItem) {
                case 0:
                    log.info("分片1：任务总片数: {}, 当前分片项: {}，当前参数: {}, 当前任务名称: {}.当前任务参数: {}",
                            shardingTotalCount, shardingItem, shardingParameter, jobName, jobParameter);
                    break;
                case 1:
                    log.info("分片2：任务总片数: {}, 当前分片项: {}，当前参数: {}, 当前任务名称: {}.当前任务参数: {}",
                            shardingTotalCount, shardingItem, shardingParameter, jobName, jobParameter);
                    break;
                default:
                    break;
            }
        }
    }
    ```
    
 4. 配置定时任务
 
    ```java
    @Configuration
    public class ElasticJobConfig {
    
        @Autowired
        private MyElasticJob myElasticJob;
    
        @Bean(initMethod = "init")
        public SpringJobScheduler initOrderCancelJob(ZookeeperRegistryCenter zookeeperRegistryCenter) {
            String cron = "*/5 * * * * ?";
            return ElasticJobBuilder.getInstance(zookeeperRegistryCenter)
                    .setJobName("testJobName")
                    .setDescription("testDescription")
                    .setJobParameter("testParameter")
                    .setElasticJob(myElasticJob)
                    .setShardingItemParameters("0=A,1=B") // 设置分片参数
                    .setShardingTotalCount(2) // 设置分片总数
                    .setCronExpression(cron).build();
        }
    }
    ```