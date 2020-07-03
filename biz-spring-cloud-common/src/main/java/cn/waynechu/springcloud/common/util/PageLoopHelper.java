package cn.waynechu.springcloud.common.util;

import cn.waynechu.facade.common.page.BizPageInfo;
import cn.waynechu.facade.common.request.BizPageRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 分页请求工具类
 *
 * @author zhuwei
 * @date 2020-03-04 21:25
 */
@Slf4j
public class PageLoopHelper {

    /**
     * 循环翻页的最大值。默认10000
     */
    public static final Integer PAGE_LOOP_LIMIT = 10000;

    private Executor executor;

    public PageLoopHelper(Executor executor) {
        this.executor = executor;
    }

    /**
     * 循环查询所有分页数据
     *
     * <pre>
     *   - 单线程for循环去翻页查询，返回的列表数据具有顺序性
     *   - 最大翻页数量为10000页
     * </pre>
     *
     * @param bizPageRequest 分页请求参数
     * @param supplier       分页查询的方法。该方法入参需继承 {@code BizPageRequest}，出参需是 {@code BizPageInfo} 类型
     * @param <R>            查询结果类型
     * @return 所有的分页数据
     */
    public static <R> List<R> listAllPage(BizPageRequest bizPageRequest, Supplier<BizPageInfo<R>> supplier) {
        List<R> returnValue = new ArrayList<>();
        BizPageInfo<R> bizPageInfo;

        int count = 1;
        try {
            do {
                bizPageInfo = supplier.get();
                if (bizPageInfo != null) {
                    returnValue.addAll(bizPageInfo.getList());
                }
                bizPageRequest.setPageNum(bizPageRequest.getPageNum() + 1);
                count++;
            } while (bizPageInfo != null
                    && bizPageInfo.getHasNextPage()
                    && count < PAGE_LOOP_LIMIT);
        } catch (Exception e) {
            log.error("分页查询失败 message: {}", e.getMessage());
        }
        return returnValue;
    }

    /**
     * 多线程并发查询所有分页数据
     *
     * <pre>
     *   - 多线程去翻页查询，返回的列表数据不保证顺序性
     *   - 最大翻页数量为10000页
     *   - 可设置同时请求的最大并发量，同一时间请求数量最多为 {@code parallelism} 个
     * </pre>
     *
     * @param bizPageRequest 分页请求参数
     * @param parallelism    并发查询的线程数
     * @param function       分页查询的方法。该方法入参需继承 {@code BizPageRequest}，出参需是 {@code BizPageInfo} 类型
     * @param <R>            查询结果类型
     * @return 所有的分页数据
     */
    public <R> List<R> listAllPage(BizPageRequest bizPageRequest, int parallelism, Function<BizPageRequest, BizPageInfo<R>> function) {
        if (parallelism <= 0) {
            parallelism = Runtime.getRuntime().availableProcessors();
        }

        List<R> returnValue = Collections.synchronizedList(new ArrayList<>());
        // 先翻第一页，获取总页数之后再并发去调用
        BizPageInfo<R> bizPageInfo = function.apply(bizPageRequest);
        if (bizPageInfo == null) {
            return returnValue;
        }

        returnValue.addAll(bizPageInfo.getList());
        int pages = bizPageInfo.getPages();
        if (pages <= 1) {
            return returnValue;
        }

        // 并发翻页
        Semaphore semaphore = new Semaphore(parallelism);
        for (int i = bizPageRequest.getPageNum() + 1; i <= pages && i < PAGE_LOOP_LIMIT; i++) {
            try {
                semaphore.acquire();
                // 处理线程不安全的共享变量
                BizPageRequest curRequest = bizPageRequest.getClass().newInstance();
                BeanUtil.copyProperties(bizPageRequest, curRequest);
                curRequest.setPageNum(i);
                executor.execute(() -> {
                    try {
                        BizPageInfo<R> currentBizPageInfo = function.apply(curRequest);
                        if (currentBizPageInfo != null) {
                            List<R> curPageList = currentBizPageInfo.getList();
                            if (CollectionUtil.isNotNullOrEmpty(curPageList)) {
                                returnValue.addAll(curPageList);
                            }
                        }
                    } finally {
                        semaphore.release();
                    }
                });
            } catch (Exception e) {
                log.error("分页查询失败 message: {}", e.getMessage());
            }
        }
        return returnValue;
    }
}
