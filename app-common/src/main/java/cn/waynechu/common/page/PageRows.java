package cn.waynechu.common.page;

import cn.waynechu.common.util.JsonBinder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuwei
 * @date 2018/11/7 11:43
 */
public class PageRows<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Pagination page;
    private List<T> rows = new ArrayList<>();

    public PageRows() {
    }

    public Pagination getPage() {
        return page;
    }

    public void setPage(Pagination page) {
        this.page = page;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return JsonBinder.buildAlwaysBinder().toPrettyJson(this);
    }
}

