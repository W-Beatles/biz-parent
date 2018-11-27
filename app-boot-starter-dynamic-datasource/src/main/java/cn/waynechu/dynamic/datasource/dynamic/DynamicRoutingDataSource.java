package cn.waynechu.dynamic.datasource.dynamic;

import cn.waynechu.dynamic.datasource.properties.RoutingPatternEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhuwei
 * @date 2018/11/7 14:05
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    private static final Logger log = LoggerFactory.getLogger(DynamicRoutingDataSource.class);

    /**
     * 写数据源
     */
    private DataSource master;
    /**
     * 读数据源
     */
    private List<DataSource> slaves;
    /**
     * 读数据源个数
     */
    private int readDataSourceSize;
    /**
     * 读数据源路由方式：0轮询，1随机。默认0
     */
    private int routingPattern = RoutingPatternEnum.POLLING.getCode();
    /**
     * 并发计数
     **/
    private AtomicLong counter = new AtomicLong(0);
    /**
     * 计数最大值
     **/
    private static final Long MAX_POOL = Long.MAX_VALUE;
    /**
     * 线程锁
     */
    private final Lock lock = new ReentrantLock();

    @Override
    public void afterPropertiesSet() {
        if (master == null) {
            throw new IllegalArgumentException("Missing datasource, master datasource is required!");
        }

        setDefaultTargetDataSource(master);
        Map<Object, Object> targetDataSources = new HashMap<>(slaves.size() + 1);
        targetDataSources.put(DataSourceTypeHolder.DATASOURCE_TYPE_MASTER, master);

        if (slaves.isEmpty()) {
            readDataSourceSize = 0;
            log.warn("Slaves datasource is empty");
        } else {
            for (int i = 0; i < slaves.size(); i++) {
                targetDataSources.put(DataSourceTypeHolder.DATASOURCE_TYPE_SALVE + i, slaves.get(i));
            }
            readDataSourceSize = slaves.size();
        }

        // 设置数据源
        setTargetDataSources(targetDataSources);
        log.debug("[slave] datasource quantity: [{}]", slaves.size());
        super.afterPropertiesSet();
    }

    /**
     * 决定数据源Key
     *
     * @return 数据源Key
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String dynamicKey = DataSourceTypeHolder.getDataSourceType();
        if (DataSourceTypeHolder.getDataSourceType() == null) {
            log.debug("Set default datasource to [master]");
            return DataSourceTypeHolder.DATASOURCE_TYPE_MASTER;
        }
        if (DataSourceTypeHolder.DATASOURCE_TYPE_MASTER.equals(dynamicKey) || readDataSourceSize <= 0) {
            log.debug("Determine target dataSource [master]");
            return DataSourceTypeHolder.DATASOURCE_TYPE_MASTER;
        }
        int index;
        if (RoutingPatternEnum.POLLING.getCode() == routingPattern) {
            // 轮询方式
            long currValue = counter.getAndIncrement();
            if ((currValue + 1) >= MAX_POOL) {
                try {
                    lock.lock();
                    if ((currValue + 1) >= MAX_POOL) {
                        counter.set(0);
                    }
                } finally {
                    lock.unlock();
                }
            }
            index = (int) (currValue % readDataSourceSize);
        } else if (RoutingPatternEnum.RANDOM.getCode() == routingPattern) {
            // 随机方式
            index = ThreadLocalRandom.current().nextInt(0, readDataSourceSize);
        } else {
            return DataSourceTypeHolder.DATASOURCE_TYPE_MASTER;
        }

        if (log.isDebugEnabled()) {
            log.debug("Determine target dataSource [{}-{}], routing pattern [{}]", dynamicKey, index, RoutingPatternEnum.codeOf(routingPattern).getName());
        }
        return dynamicKey + index;
    }

    public void setMaster(DataSource master) {
        this.master = master;
    }

    public void setSlaves(List<DataSource> slaves) {
        this.slaves = slaves;
    }

    public void setRoutingPattern(int routingPattern) {
        RoutingPatternEnum routingPatternEnum = RoutingPatternEnum.codeOf(routingPattern);

        if (routingPatternEnum != null) {
            this.routingPattern = routingPattern;
            log.debug("Set routing pattern to [{}]", routingPatternEnum.getName());
        } else {
            log.debug("Unknown routing pattern, set to default [{}]", RoutingPatternEnum.POLLING.getName());
        }
    }
}
