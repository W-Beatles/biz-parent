package cn.waynechu.springcloud.common.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * excel解析监听器
 * <p>
 * 每解析一行会回调invoke()方法。
 * 整个excel解析结束会执行doAfterAllAnalysed()方法
 *
 * @author zhuwei
 * @since 2019/7/29 21:00
 */
public class BatchImportListener<T> extends AnalysisEventListener<T> {

    /**
     * 默认每隔3000条调用一次batchInvokeHandler的回调方法, 然后清理list, 防止几万条数据在内存中, 容易OOM
     */
    private int batchAnalysisLimit = 3000;

    /**
     * 用于暂存解析的数据
     */
    private List<T> data = new ArrayList<>();

    /**
     * 回调处理器
     */
    private IBatchInvokeHandler<T> batchInvokeHandler;

    public BatchImportListener() {
    }

    public BatchImportListener(IBatchInvokeHandler<T> batchInvokeHandler) {
        this.batchInvokeHandler = batchInvokeHandler;
    }

    public BatchImportListener(int batchAnalysisLimit, IBatchInvokeHandler<T> batchInvokeHandler) {
        this.batchAnalysisLimit = batchAnalysisLimit;
        this.batchInvokeHandler = batchInvokeHandler;
    }

    @Override
    public void invoke(T object, AnalysisContext context) {
        data.add(object);

        // 达到解析数量上限, 需要调用一次回调方法并清理list
        if (batchInvokeHandler != null && data.size() >= batchAnalysisLimit) {
            batchInvoke();
            // 回调完成并清理 list
            data.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要回调，确保最后遗留的数据也能解析到
        if (batchInvokeHandler != null) {
            batchInvoke();
        }
    }

    public void batchInvoke() {
        batchInvokeHandler.batchInvoke(data);
    }

    public List<T> getData() {
        return data;
    }
}
