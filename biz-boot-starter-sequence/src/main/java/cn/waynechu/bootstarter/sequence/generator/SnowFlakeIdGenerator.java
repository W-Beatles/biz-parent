package cn.waynechu.bootstarter.sequence.generator;

import cn.waynechu.bootstarter.sequence.connector.BaseGeneratorConnector;
import cn.waynechu.bootstarter.sequence.register.WorkerRegister;
import cn.waynechu.bootstarter.sequence.stratagy.SnowFlake;

/**
 * @author zhuwei
 * @since 2020/6/11 16:35
 */
public class SnowFlakeIdGenerator extends BaseGeneratorConnector implements IdGenerator {

    private SnowFlake snowFlake;

    private WorkerRegister workerRegister;

    public SnowFlakeIdGenerator(WorkerRegister workerRegister) {
        this.workerRegister = workerRegister;
    }

    public void init() {
        connect();
    }

    @Override
    public long nextId(String appName, String tbName) {
        return snowFlake.nextId();
    }

    @Override
    public long[] nextIds(String appName, String tbName, int size) {
        return new long[0];
    }

    @Override
    public String nextStringId(String appName, String tbName) {
        return null;
    }

    @Override
    public String[] nextStringIds(String appName, String tbName, int size) {
        return new String[0];
    }

    @Override
    public void connect() {
        if (!isConnecting()) {
            connecting = true;
        }
    }

}
