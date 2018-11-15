package cn.waynechu.common.enums;

/**
 * @author zhuwei
 * @date 2018/11/6 18:49
 */
public enum ResultEnum implements BaseEnum {
    /**
     * 响应枚举
     */
    SUCCESS(10000, "success", "操作成功"),
    SYSTEM_ERROR(10002, "system_error", "系统异常"),

    ILLEGAL_ARGUMENT(10010, "illegal_argument", "参数错误"),
    MISSING_REQUEST_PARAMETER(10011, "missing_request_parameter", "缺少请求参数");

    private int code;
    private String name;
    private String desc;

    ResultEnum(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public BaseEnum codeOf(int code) {
        for (ResultEnum resultEnum : values()) {
            if (resultEnum.getCode() == code) {
                return resultEnum;
            }
        }
        return null;
    }

    @Override
    public BaseEnum nameOf(String name) {
        for (ResultEnum resultEnum : values()) {
            if (resultEnum.getName().equals(name)) {
                return resultEnum;
            }
        }
        return null;
    }
}
