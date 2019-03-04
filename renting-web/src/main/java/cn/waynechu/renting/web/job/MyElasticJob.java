//package cn.waynechu.renting.web.job;
//
//import com.dangdang.ddframe.job.api.ShardingContext;
//import com.dangdang.ddframe.job.api.simple.SimpleJob;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
///**
// * @author zhuwei
// * @date 2019/2/25 17:50
// */
//@Slf4j
//@Component
//public class MyElasticJob implements SimpleJob {
//
//    @Override
//    public void execute(ShardingContext shardingContext) {
//        int shardingTotalCount = shardingContext.getShardingTotalCount();
//        int shardingItem = shardingContext.getShardingItem();
//        String shardingParameter = shardingContext.getShardingParameter();
//        String jobName = shardingContext.getJobName();
//        String jobParameter = shardingContext.getJobParameter();
//        switch (shardingItem) {
//            case 0:
//                log.info("任务总片数: {}, 当前分片项: {}，当前参数: {}, 当前任务名称: {}.当前任务参数: {}",
//                        shardingTotalCount, shardingItem, shardingParameter, jobName, jobParameter);
//                break;
//            case 1:
//                log.info("任务总片数: {}, 当前分片项: {}，当前参数: {}, 当前任务名称: {}.当前任务参数: {}",
//                        shardingTotalCount, shardingItem, shardingParameter, jobName, jobParameter);
//                break;
//            default:
//                break;
//        }
//    }
//}
