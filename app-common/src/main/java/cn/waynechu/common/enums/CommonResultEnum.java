package cn.waynechu.common.enums;

/**
 * @author zhuwei
 * @date 2018/11/6 18:49
 */
public enum CommonResultEnum implements IBaseEnum {
    /**
     * 响应枚举
     */
    SUCCESS(10000, "success", "操作成功"),
    SYSTEM_ERROR(10002, "system_error", "系统异常"),

    MISSING_REQUEST_PARAMETER(10010, "missing_request_parameter", "缺少请求参数"),
    ILLEGAL_ARGUMENT(10011, "illegal_argument", "参数错误");

    private int code;
    private String name;
    private String desc;

    CommonResultEnum(int code, String name, String desc) {
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
    public IBaseEnum getByCode(int code) {
        for (CommonResultEnum baseResultEnum : values()) {
            if (baseResultEnum.getCode() == code) {
                return baseResultEnum;
            }
        }
        return null;
    }

    @Override
    public IBaseEnum getByName(String name) {
        for (CommonResultEnum baseResultEnum : values()) {
            if (baseResultEnum.getName().equals(name)) {
                return baseResultEnum;
            }
        }
        return null;
    }
}
