package cn.waynechu.bootstarter.elasticjob;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhuwei
 * @date 2019/2/25 17:32
 */
@Data
@Accessors(chain = true)
public class ElasticJobBuilder {

    private ZookeeperRegistryCenter regCenter;

    private JobEventConfiguration jobEventConfiguration;

    private ElasticJob elasticJob;

    private String jobName;

    private String jobParameter;

    private String cronExpression;

    private int shardingTotalCount;

    private String shardingItemParameters;

    private String description;

    public static ElasticJobBuilder getInstance(ZookeeperRegistryCenter regCenter) {
        return new ElasticJobBuilder(regCenter, null);
    }

    private ElasticJobBuilder(ZookeeperRegistryCenter regCenter, JobEventConfiguration jobEventConfiguration) {
        this.regCenter = regCenter;
        this.jobEventConfiguration = jobEventConfiguration;
    }

    public SpringJobScheduler build() {
        if (regCenter == null) {
            regCenter = ElasticJobAutoConfiguration.getBean(ZookeeperRegistryCenter.class);
            assert regCenter != null;
        }

        JobCoreConfiguration jobCoreConfig = JobCoreConfiguration
                .newBuilder(
                        (StringUtils.isNotBlank(this.jobName)) ? this.jobName : elasticJob.getClass().getSimpleName(),
                        cronExpression, shardingTotalCount)
                .shardingItemParameters(this.shardingItemParameters)
                .description(this.description)
                .jobParameter(this.jobParameter)
                .build();
        JobTypeConfiguration jobTypeConfig = null;
        if (this.elasticJob instanceof SimpleJob) {
            jobTypeConfig = new SimpleJobConfiguration(jobCoreConfig, elasticJob.getClass().getCanonicalName());
        } else if (this.elasticJob instanceof DataflowJob) {
            jobTypeConfig = new DataflowJobConfiguration(jobCoreConfig, elasticJob.getClass().getCanonicalName(), true);
        }

        LiteJobConfiguration liteJobConfig = LiteJobConfiguration.newBuilder(jobTypeConfig).overwrite(true).build();
        if (jobEventConfiguration != null) {
            return new SpringJobScheduler(elasticJob, regCenter, liteJobConfig, jobEventConfiguration);
        } else {
            return new SpringJobScheduler(elasticJob, regCenter, liteJobConfig);
        }
    }
}
