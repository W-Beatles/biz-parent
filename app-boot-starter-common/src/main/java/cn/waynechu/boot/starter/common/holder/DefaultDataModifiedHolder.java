package cn.waynechu.boot.starter.common.holder;

/**
 * @author zhuwei
 * @date 2019/1/8 10:58
 */
@Deprecated
public class DefaultDataModifiedHolder implements IDataModifiedHolder {

    @Override
    public String getModifierName() {
        return "admin";
    }
}
