package cn.waynechu.bootstarter.sequence.generator;

import cn.waynechu.bootstarter.sequence.register.WorkerRegister;
import cn.waynechu.bootstarter.sequence.stratagy.SnowFlake;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhuwei
 * @since 2020/6/11 16:35
 */
public class SnowFlakeIdGenerator implements IdGenerator {

    protected volatile boolean working;

    static final String FIXED_STRING_FORMAT = "%019d";

    private WorkerRegister register;

    private Map<String, SnowFlake> flakeHolder = new ConcurrentHashMap<>();

    public SnowFlakeIdGenerator(WorkerRegister register) {
        this.register = register;
        register.init();
        this.working = true;
    }

    @Override
    public long nextId(String bizTag) {
        if (working) {
            SnowFlake snowFlake = this.getSnowFlake(bizTag);
            return snowFlake.nextId();
        }
        throw new IllegalStateException("Generator isn't working, registry center may shutdown");
    }

    @Override
    public long[] nextIds(String bizTag, int size) {
        if (working) {
            SnowFlake snowFlake = this.getSnowFlake(bizTag);
            return snowFlake.nextIds(size);
        }
        throw new IllegalStateException("Generator isn't working, registry center may shutdown");
    }

    @Override
    public String nextStringId(String bizTag) {
        return String.valueOf(nextId(bizTag));
    }

    @Override
    public String[] nextStringIds(String bizTag, int size) {
        String[] ids = new String[size];
        for (int i = 0; i < size; i++) {
            ids[i] = nextStringId(bizTag);
        }
        return ids;
    }

    @Override
    public String nextFixedStringId(String bizTag) {
        return String.format(FIXED_STRING_FORMAT, nextId(bizTag));
    }

    public void close() {
        working = false;
        flakeHolder.forEach((k, v) -> register.logout(k, v.getWorkerId()));
        register.close();
    }

    private SnowFlake getSnowFlake(String bizTag) {
        SnowFlake snowFlake = flakeHolder.get(bizTag);
        if (snowFlake == null) {
            synchronized (this) {
                snowFlake = flakeHolder.get(bizTag);
                if (snowFlake == null) {
                    int workId = register.register(bizTag);
                    snowFlake = new SnowFlake(workId);
                    flakeHolder.put(bizTag, snowFlake);
                }
            }
        }
        return snowFlake;
    }
}
