package cn.waynechu.bootstarter.sequence.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;

/**
 * @author zhuwei
 * @since 2020/6/11 21:15
 */
@Slf4j
public class RegExceptionHandler {

    /**
     * 处理异常.
     *
     * <p>处理掉中断和连接失效异常</p>
     *
     * @param cause 待处理异常.
     */
    public static void handleException(final Exception cause) {
        if (null == cause) {
            return;
        }
        if (isIgnoredException(cause) || (cause.getCause() != null && isIgnoredException(cause.getCause()))) {
            log.debug("Sequence: ignored exception for: {}", cause.getMessage());
        } else if (cause instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        } else {
            throw new SequenceException(cause.getMessage());
        }
    }

    private static boolean isIgnoredException(final Throwable cause) {
        return cause instanceof KeeperException.ConnectionLossException
                || cause instanceof KeeperException.NoNodeException
                || cause instanceof KeeperException.NodeExistsException;
    }
}
