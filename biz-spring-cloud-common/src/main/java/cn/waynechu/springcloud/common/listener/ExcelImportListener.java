package cn.waynechu.springcloud.common.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;

import java.util.ArrayList;
import java.util.List;

/**
 * excel解析监听器
 * <p>
 * 每解析一行会回调invoke()方法。
 * 整个excel解析结束会执行doAfterAllAnalysed()方法
 *
 * @author zhuwei
 * @date 2019/7/29 21:00
 */
public class ExcelImportListener<T extends BaseRowModel> extends AnalysisEventListener<T> {

    /**
     * 用于暂时存储导入数据
     */
    private List<T> datas = new ArrayList<>();

    @Override
    public void invoke(T object, AnalysisContext context) {
        datas.add(object);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // do nothing here.
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
