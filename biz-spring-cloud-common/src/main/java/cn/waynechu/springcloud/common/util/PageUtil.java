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
     * 物理分页工具类
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
     * 物理分页工具类
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

    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        List<Integer> page = PageUtil.getPage(integers, 1, 5);
        System.out.println(page);

        BizPageInfo<Integer> pageInfo = PageUtil.getPageInfo(integers, 1, 2);
        System.out.println(pageInfo);
    }
}
