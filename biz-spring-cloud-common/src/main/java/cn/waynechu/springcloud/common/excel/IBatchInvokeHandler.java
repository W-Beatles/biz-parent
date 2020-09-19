package cn.waynechu.springcloud.common.excel;

import java.util.List;

/**
 * 批量解析回调处理器
 *
 * @author zhuwei
 * @since 2020/1/6 17:41
 */
public interface IBatchInvokeHandler<T> {

    /**
     * 保存解析的excel数据
     *
     * @param data 解析出来的model
     */
    void batchInvoke(List<T> data);
}
