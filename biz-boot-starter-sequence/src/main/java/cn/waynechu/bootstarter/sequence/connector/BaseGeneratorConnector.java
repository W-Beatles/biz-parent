package cn.waynechu.bootstarter.sequence.connector;

import cn.waynechu.bootstarter.sequence.exception.SequenceException;

/**
 * @author zhuwei
 * @since 2020/6/11 19:42
 */
public abstract class BaseGeneratorConnector implements GeneratorConnector {

    protected volatile boolean connecting = false;

    @Override
    public void connect() {
        throw new SequenceException("Unsupported connect operation.");
    }

    @Override
    public void disconnect() {
        throw new SequenceException("Unsupported disconnect operation.");
    }

    @Override
    public boolean isConnecting() {
        return connecting;
    }
}
