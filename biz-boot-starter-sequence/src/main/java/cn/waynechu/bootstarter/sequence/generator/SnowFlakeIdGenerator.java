package cn.waynechu.bootstarter.sequence.generator;

import cn.waynechu.bootstarter.sequence.connector.BaseGeneratorConnector;
import cn.waynechu.bootstarter.sequence.exception.SequenceException;
import cn.waynechu.bootstarter.sequence.register.WorkerRegister;
import cn.waynechu.bootstarter.sequence.stratagy.SnowFlake;

import java.io.IOException;

/**
 * @author zhuwei
 * @since 2020/6/11 16:35
 */
public class SnowFlakeIdGenerator extends BaseGeneratorConnector implements IdGenerator {

    static final String FIXED_STRING_FORMAT = "%019d";

    private SnowFlake snowFlake;

    private WorkerRegister register;

    public SnowFlakeIdGenerator(WorkerRegister register) {
        this.register = register;
    }

    @Override
    public void init() {
        connect();
    }

    @Override
    public void connect() {
        if (!isConnected()) {
            long workerId = register.register();
            if (workerId >= 0) {
                snowFlake = new SnowFlake(workerId);
                connected = true;
            } else {
                throw new SequenceException("Failed to get worker id");
            }
        }
    }

    @Override
    public long nextId() {
        if (isConnected()) {
            return snowFlake.nextId();
        }
        throw new IllegalStateException("Worker isn't connected, registry center may shutdown");
    }

    @Override
    public long[] nextIds(int size) {
        if (isConnected()) {
            return snowFlake.nextIds(size);
        }
        throw new IllegalStateException("Worker isn't connected, registry center may shutdown");
    }

    @Override
    public String nextStringId() {
        return String.valueOf(nextId());
    }

    @Override
    public String[] nextStringIds(int size) {
        String[] ids = new String[size];
        for (int i = 0; i < size; i++) {
            ids[i] = nextStringId();
        }
        return ids;
    }

    @Override
    public String nextFixedStringId() {
        return String.format(FIXED_STRING_FORMAT, nextId());
    }

    @Override
    public void close() throws IOException {
        connected = false;
        register.logout();
    }
}
