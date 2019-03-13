package cn.waynechu.boot.starter.common.holder;

/**
 * @author zhuwei
 * @date 2019/1/8 10:53
 */
@Deprecated
public interface IDataModifiedHolder {

    /**
     * 获取当前数据库操作人名称
     *
     * @return modifierName
     */
    String getModifierName();
}
