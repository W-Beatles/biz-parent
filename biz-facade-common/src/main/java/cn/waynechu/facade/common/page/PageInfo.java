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
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "分页返回对象")
public class PageInfo<T> extends PageSerializable<T> {
    private static final long serialVersionUID = -5598171228549570150L;

    @ApiModelProperty("当前页数")
    private int pageNum;

    @ApiModelProperty("分页大小")
    private int pageSize;

    @ApiModelProperty("总页数")
    private int pages;

    @ApiModelProperty("是否为最后一页")
    private Boolean isLastPage = false;

    public PageInfo(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    /**
     * 包装Page对象
     *
     * @param list page对象
     */
    public PageInfo(List<T> list) {
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
        isLastPage = pageNum == pages || pages == 0;
    }

    public static <T> PageInfo<T> of(List<T> list) {
        return new PageInfo<>(list);
    }

    /**
     * 替换pageInfo的list数据
     *
     * @param replaceList 替换的数据
     * @param <E>         替换之后的类型
     * @return 替换后的pageInfo
     */
    @SuppressWarnings("unchecked")
    public <E> PageInfo<E> replace(List<E> replaceList) {
        PageInfo replacePageInfo = this;
        replacePageInfo.setList(replaceList);
        return replacePageInfo;
    }

    /**
     * 返回空分页信息
     *
     * @return 空分页信息
     */
    public PageInfo<T> emptyPage() {
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
    public PageInfo<T> emptyPage(int pageNum, int pageSize) {
        this.setPageNum(pageNum);
        this.setPageSize(pageSize);
        this.setList(Collections.emptyList());
        return this;
    }
}
