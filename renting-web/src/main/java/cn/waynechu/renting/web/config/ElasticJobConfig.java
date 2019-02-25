package cn.waynechu.renting.web.config;

import cn.waynechu.boot.starter.elasticjob.ElasticJobBuilder;
import cn.waynechu.renting.web.job.MyElasticJob;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * @author zhuwei
 * @date 2019/2/25 18:42
 */
//@Configuration
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
                .setShardingItemParameters("0=A,1=B")
                .setShardingTotalCount(2)
                .setCronExpression(cron).build();
    }
}
