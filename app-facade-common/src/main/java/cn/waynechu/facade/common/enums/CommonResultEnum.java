package cn.waynechu.facade.common.enums;

/**
 * @author zhuwei
 * @date 2018/11/6 18:49
 */
public enum CommonResultEnum implements AbstractEnum {
    /**
     * 响应枚举
     */
    SUCCESS(10000, "SUCCESS"),
    SYSTEM_ERROR(10002, "系统异常"),

    MISSING_REQUEST_PARAM(10010, "缺少请求参数"),
    ARGUMENT_IS_INCORRECT(10011, "请求参数格式不正确"),
    ARGUMENT_NOT_VALID(10012, "请求参数校验不合法");

    private int code;
    private String desc;

    CommonResultEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public AbstractEnum getByCode(int code) {
        for (CommonResultEnum baseResultEnum : values()) {
            if (baseResultEnum.getCode() == code) {
                return baseResultEnum;
            }
        }
        return null;
    }
}
