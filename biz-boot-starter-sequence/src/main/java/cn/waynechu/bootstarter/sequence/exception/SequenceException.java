package cn.waynechu.bootstarter.sequence.exception;

/**
 * @author zhuwei
 * @since 2020/6/11 16:15
 */
public class SequenceException extends RuntimeException {

    private final int code;

    private final String message;

    public SequenceException(String message) {
        this(SequenceErrorCode.SEQUENCE_DEFAULT_ERROR_CODE, message);
    }

    public SequenceException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public SequenceException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
