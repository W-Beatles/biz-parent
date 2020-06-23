package cn.waynechu.facade.common.page;

import com.github.pagehelper.Page;
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
 * @date 2019/1/9 14:04
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "分页详情返回对象")
public class BizPageDetail<T> extends BizPageInfo<T> {
    private static final long serialVersionUID = 4173165387592228326L;

    @ApiModelProperty("当前页数")
    private int pageNum;
    @ApiModelProperty("分页大小")
    private int pageSize;

    @ApiModelProperty("当前页大小")
    private int size;
    @ApiModelProperty("当前页面第一个元素在数据库中的行号")
    private int startRow;
    @ApiModelProperty("当前页面最后一个元素在数据库中的行号")
    private int endRow;

    @ApiModelProperty("总页数")
    private int pages;
    @ApiModelProperty("上页号")
    private int prePage;
    @ApiModelProperty("下页号")
    private int nextPage;

    @ApiModelProperty("总数量")
    private long total;
    @ApiModelProperty("分页数据")
    private List<T> list;

    @ApiModelProperty("是否为第一页")
    private Boolean isFirstPage = false;
    @ApiModelProperty("是否为最后一页")
    private Boolean isLastPage = false;
    @ApiModelProperty("是否有前一页")
    private Boolean hasPreviousPage = false;
    @ApiModelProperty("是否有下一页")
    private Boolean hasNextPage = false;

    @ApiModelProperty("导航页码数")
    private int navigatePages;
    @ApiModelProperty("所有导航页号")
    private int[] navigatePageNums;
    @ApiModelProperty("导航条上的第一页")
    private int navigateFirstPage;
    @ApiModelProperty("导航条上的最后一页")
    private int navigateLastPage;

    public BizPageDetail(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    /**
     * 包装Page对象
     *
     * @param list page对象
     */
    public BizPageDetail(List<T> list) {
        this(list, 8);
    }

    /**
     * 包装Page对象
     *
     * @param list          page对象
     * @param navigatePages 导航页码数
     */
    public BizPageDetail(List<T> list, int navigatePages) {
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();

            this.size = page.size();
            // 由于结果是 > startRow的，所以实际的需要+1
            if (this.size == 0) {
                this.startRow = 0;
                this.endRow = 0;
            } else {
                this.startRow = page.getStartRow() + 1;
                // 计算实际的endRow（最后一页的时候特殊）
                this.endRow = this.startRow - 1 + this.size;
            }

            this.pages = page.getPages();
            this.total = ((Page) list).getTotal();
        } else {
            this.pageNum = 1;
            this.pageSize = list.size();

            this.size = list.size();
            this.startRow = 0;
            this.endRow = list.isEmpty() ? 0 : list.size() - 1;

            this.pages = this.pageSize > 0 ? 1 : 0;
            this.total = list.size();
        }

        this.navigatePages = navigatePages;
        // 计算导航页
        calcNavigatePageNums();
        // 计算前后页，第一页，最后一页
        calcPage();
        // 判断页面边界
        judgePageBoundary();
    }

    public static <T> BizPageDetail<T> of(List<T> list) {
        return new BizPageDetail<>(list);
    }

    public static <T> BizPageDetail<T> of(List<T> list, int navigatePages) {
        return new BizPageDetail<>(list, navigatePages);
    }

    /**
     * 计算导航页
     */
    private void calcNavigatePageNums() {
        // 当总页数小于或等于导航页码数时
        if (pages <= navigatePages) {
            navigatePageNums = new int[pages];
            for (int i = 0; i < pages; i++) {
                navigatePageNums[i] = i + 1;
            }
        } else { // 当总页数大于导航页码数时
            navigatePageNums = new int[navigatePages];
            int startNum = pageNum - navigatePages / 2;
            int endNum = pageNum + navigatePages / 2;

            if (startNum < 1) {
                startNum = 1;
                // 最前navigatePages页
                for (int i = 0; i < navigatePages; i++) {
                    navigatePageNums[i] = startNum++;
                }
            } else if (endNum > pages) {
                endNum = pages;
                // 最后navigatePages页
                for (int i = navigatePages - 1; i >= 0; i--) {
                    navigatePageNums[i] = endNum--;
                }
            } else {
                // 所有中间页
                for (int i = 0; i < navigatePages; i++) {
                    navigatePageNums[i] = startNum++;
                }
            }
        }
    }

    /**
     * 计算前后页，第一页，最后一页
     */
    private void calcPage() {
        if (navigatePageNums != null && navigatePageNums.length > 0) {
            navigateFirstPage = navigatePageNums[0];
            navigateLastPage = navigatePageNums[navigatePageNums.length - 1];
            if (pageNum > 1) {
                prePage = pageNum - 1;
            }
            if (pageNum < pages) {
                nextPage = pageNum + 1;
            }
        }
    }

    /**
     * 判定页面边界
     */
    private void judgePageBoundary() {
        isFirstPage = pageNum == 1;
        isLastPage = pageNum == pages || pages == 0;
        hasPreviousPage = pageNum > 1;
        hasNextPage = pageNum < pages;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> BizPageDetail<E> replace(List<E> replaceList) {
        BizPageDetail replace = this;
        replace.setList(replaceList);
        return replace;
    }

    @Override
    public BizPageDetail<T> emptyPage() {
        this.setList(Collections.emptyList());
        return this;
    }

    @Override
    public BizPageDetail<T> emptyPage(int pageNum, int pageSize) {
        this.setPageNum(pageNum);
        this.setPageSize(pageSize);
        this.setList(Collections.emptyList());
        return this;
    }
}

