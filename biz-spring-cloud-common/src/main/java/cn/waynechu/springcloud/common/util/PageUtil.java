package cn.waynechu.springcloud.common.util;

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
        int pageCount = 0;
        int m = totalCount % pageSize;
        if (m > 0) {
            pageCount = totalCount / pageSize + 1;
        } else {
            pageCount = totalCount / pageSize;
        }

        if (m == 0) {
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

    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        List<Integer> page = PageUtil.getPage(integers, 1, 5);
        System.out.println(page);
    }
}
