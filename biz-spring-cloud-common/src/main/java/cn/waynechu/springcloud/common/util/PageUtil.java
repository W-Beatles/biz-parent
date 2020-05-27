package cn.waynechu.springcloud.common.util;

import cn.waynechu.facade.common.page.BizPageInfo;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author zhuwei
 * @date 2020/1/14 10:00
 */
public class PageUtil {

    /**
     * 物理分页
     *
     * @param list     待分页数据
     * @param pageNum  分页大小
     * @param pageSize 页数
     * @param <T>      待分页数据类型
     * @return 指定页数的数据
     */
    public static <T> List<T> getPage(List<T> list, int pageNum, int pageSize) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        int totalCount = list.size();
        int pageCount;
        int remainder = totalCount % pageSize;
        if (remainder > 0) {
            pageCount = totalCount / pageSize + 1;
        } else {
            pageCount = totalCount / pageSize;
        }

        if (remainder == 0) {
            return list.subList((pageNum - 1) * pageSize, pageNum * pageSize);
        } else {
            if (pageNum == pageCount) {
                return list.subList((pageNum - 1) * pageSize, totalCount);
            } else if (pageNum > 0 && pageNum < pageCount) {
                return list.subList((pageNum - 1) * pageSize, pageNum * pageSize);
            } else {
                return Collections.emptyList();
            }
        }
    }

    /**
     * 物理分页
     *
     * @param list     待分页数据
     * @param pageNum  分页大小
     * @param pageSize 页数
     * @param <T>      待分页数据类型
     * @return 分页后的bizPageInfo
     */
    public static <T> BizPageInfo<T> getPageInfo(List<T> list, int pageNum, int pageSize) {
        BizPageInfo<T> returnValue = new BizPageInfo<>(pageNum, pageSize);

        if (CollectionUtils.isEmpty(list)) {
            return returnValue;
        }

        int totalCount = list.size();
        returnValue.setTotal(totalCount);

        int pageCount;
        int remainder = totalCount % pageSize;
        if (remainder > 0) {
            pageCount = totalCount / pageSize + 1;
        } else {
            pageCount = totalCount / pageSize;
        }
        returnValue.setPages(pageCount);

        List<T> currPage;
        if (remainder == 0) {
            currPage = list.subList((pageNum - 1) * pageSize, pageNum * pageSize);
        } else {
            if (pageNum == pageCount) {
                currPage = list.subList((pageNum - 1) * pageSize, totalCount);
            } else if (pageNum > 0 && pageNum < pageCount) {
                currPage = list.subList((pageNum - 1) * pageSize, pageNum * pageSize);
            } else {
                currPage = Collections.emptyList();
            }
        }
        returnValue.setList(currPage);
        return returnValue;
    }

    /**
     * 根据页数和页大小获取起始行数
     *
     * @param pageNum  页数(1 ~ N+)
     * @param pageSize 页大小
     * @return 起始行数(0 ~ N +)
     */
    public Integer toFrom(Integer pageNum, Integer pageSize) {
        if (pageNum < 1) {
            throw new IllegalArgumentException("页数不能小于1");
        }
        return (pageNum - 1) * pageSize;
    }

    /**
     * 计算总页数
     *
     * @param total    总大小
     * @param pageSize 页大小
     * @return 总页数
     */
    public Integer getPage(Integer total, Integer pageSize) {
        int pages;
        long remainder = total % pageSize;
        if (remainder > 0) {
            pages = Math.toIntExact(total / pageSize + 1);
        } else {
            pages = Math.toIntExact(total / pageSize);
        }
        return pages;
    }

    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        List<Integer> page = PageUtil.getPage(integers, 1, 5);
        System.out.println(page);

        BizPageInfo<Integer> pageInfo = PageUtil.getPageInfo(integers, 1, 2);
        System.out.println(pageInfo);
    }

}
