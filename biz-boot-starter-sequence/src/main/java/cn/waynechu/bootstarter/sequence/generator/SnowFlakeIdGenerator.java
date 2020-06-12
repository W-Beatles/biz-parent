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
        if (!connecting) {
            connect();
        }
    }

    @Override
    public void connect() {
        if (!connecting) {
            long workerId = this.register.register();
            if (workerId >= 0) {
                snowFlake = new SnowFlake(workerId);
                connecting = true;
            } else {
                throw new SequenceException("failed to get worker id");
            }
        }
    }

    @Override
    public void disconnect() {
        register.logout();
    }

    @Override
    public void close() throws IOException {
        if (connecting) {
            disconnect();
        }
    }

    @Override
    public long nextId() {
        if (connecting) {
            return snowFlake.nextId();
        }
        throw new IllegalStateException("worker isn't connecting, registry center may shutdown");
    }

    @Override
    public long[] nextIds(int size) {
        if (connecting) {
            return snowFlake.nextIds(size);
        }
        throw new IllegalStateException("worker isn't connecting, registry center may shutdown");
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

}
