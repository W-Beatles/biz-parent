package cn.waynechu.facade.common.page;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageSerializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

/**
 * @author zhuwei
 * @date 2018/12/28 19:07
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "分页返回对象")
public class BizPageInfo<T> extends PageSerializable<T> {
    private static final long serialVersionUID = -5598171228549570150L;

    @ApiModelProperty("当前页数")
    private int pageNum;

    @ApiModelProperty("分页大小")
    private int pageSize;

    @ApiModelProperty("总页数")
    private int pages;

    @ApiModelProperty("是否有下一页")
    private Boolean hasNextPage = false;

    public BizPageInfo(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    /**
     * 包装Page对象
     *
     * @param list page对象
     */
    public BizPageInfo(List<T> list) {
        super(list);
        if (list instanceof Page) {
            Page page = (Page) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.pages = page.getPages();
        } else {
            this.pageNum = 1;
            this.pageSize = list.size();
            this.pages = this.pageSize > 0 ? 1 : 0;
        }
        hasNextPage = pageNum < pages;
    }

    @Override
    public void setTotal(long total) {
        this.total = total;
        if (pageSize != 0) {
            this.pages = Math.toIntExact(total / pageSize + 1);
        }
        hasNextPage = pageNum >= pages;
    }

    public static <T> BizPageInfo<T> of(List<T> list) {
        return new BizPageInfo<>(list);
    }

    /**
     * 替换pageInfo的list数据
     *
     * @param replaceList 替换的数据
     * @param <E>         替换之后的类型
     * @return 替换后的pageInfo
     */
    @SuppressWarnings("unchecked")
    public <E> BizPageInfo<E> replace(List<E> replaceList) {
        BizPageInfo replaceBizPageInfo = this;
        replaceBizPageInfo.setList(replaceList);
        return replaceBizPageInfo;
    }

    /**
     * 返回空分页信息
     *
     * @return 空分页信息
     */
    public BizPageInfo<T> emptyPage() {
        this.setList(Collections.emptyList());
        return this;
    }

    /**
     * 返回空分页信息
     *
     * @param pageNum  当前页
     * @param pageSize 页大小
     * @return 空分页信息
     */
    public BizPageInfo<T> emptyPage(int pageNum, int pageSize) {
        this.setPageNum(pageNum);
        this.setPageSize(pageSize);
        this.setList(Collections.emptyList());
        return this;
    }
}
