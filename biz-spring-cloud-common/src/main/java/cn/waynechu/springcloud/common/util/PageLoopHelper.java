package cn.waynechu.springcloud.common.util;

import cn.waynechu.facade.common.page.BizPageInfo;
import cn.waynechu.facade.common.request.BizPageRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 分页请求工具类
 *
 * @author zhuwei
 * @since 2020-03-04 21:25
 */
@Slf4j
public class PageLoopHelper {

    /**
     * 循环翻页的最大值。默认10000
     */
    public static final Integer PAGE_LOOP_LIMIT = 10000;

    /**
     * 最大并发线程数
     */
    public static final Integer PAGE_LOOP_MAX_CONCURRENCY = 20;

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
    public <R> List<R> listAllPage(BizPageRequest bizPageRequest, Supplier<BizPageInfo<R>> supplier) {
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
     *   - 多线程去翻页查询，要注意返回的列表数据不保证顺序性
     *   - 可设置同时请求的最大并发量，同一时间请求数量最多为 {@code thread} 个
     *   - 限制：最大翻页数量为10000页，最大并发线程数20个
     * </pre>
     *
     * @param pageRequest 分页请求参数
     * @param thread      并发查询的线程数
     * @param function    分页查询的方法。该方法入参需继承 {@code BizPageRequest}，出参需是 {@code BizPageInfo} 类型
     * @param consumer    处理每一页返回的方法
     * @param <R>         查询结果类型
     */
    @SuppressWarnings("unchecked")
    public <T extends BizPageRequest, R> void listAllPage(T pageRequest, int thread, Function<T, BizPageInfo<R>> function,
                                                          Consumer<Collection<R>> consumer) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        if (thread <= 0 || thread >= PAGE_LOOP_MAX_CONCURRENCY) {
            thread = availableProcessors;
        }

        // 先翻第一页，获取总页数之后再并发去调用
        BizPageInfo<R> bizPageInfo = function.apply(pageRequest);
        if (bizPageInfo == null) {
            return;
        }

        consumer.accept(bizPageInfo.getList());
        int pages = bizPageInfo.getPages();
        if (pages <= 1) {
            return;
        }

        // 并发翻页
        Semaphore semaphore = new Semaphore(thread);
        CountDownLatch countDownLatch = new CountDownLatch(pages - 1);
        final String pageRequestStr = JsonBinder.toJson(pageRequest);
        try {
            for (int i = pageRequest.getPageNum() + 1; i <= pages && i < PAGE_LOOP_LIMIT; i++) {
                semaphore.acquire();
                T curRequest = (T) JsonBinder.fromJson(pageRequestStr, pageRequest.getClass());
                curRequest.setPageNum(i);

                executor.execute(() -> {
                    try {
                        BizPageInfo<R> currentBizPageInfo = function.apply(curRequest);
                        if (currentBizPageInfo != null) {
                            List<R> curPageList = currentBizPageInfo.getList();
                            if (CollectionUtil.isNotNullOrEmpty(curPageList)) {
                                consumer.accept(curPageList);
                            }
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    } finally {
                        semaphore.release();
                        countDownLatch.countDown();
                    }
                });
            }
            countDownLatch.await(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 多线程并发查询所有分页数据
     *
     * <pre>
     *   - 多线程去翻页查询，要注意返回的列表数据不保证顺序性
     *   - 可设置同时请求的最大并发量，同一时间请求数量最多为 {@code thread} 个
     *   - 限制：最大翻页数量为10000页，最大并发线程数20个
     * </pre>
     *
     * @param pageRequest 分页请求参数
     * @param thread      并发查询的线程数
     * @param function    分页查询的方法。该方法入参需继承 {@code BizPageRequest}，出参需是 {@code BizPageInfo} 类型
     * @param <R>         查询结果类型
     * @return 所有的分页数据
     */
    @SuppressWarnings("unchecked")
    public <T extends BizPageRequest, R> List<R> listAllPage(T pageRequest, int thread, Function<T, BizPageInfo<R>> function) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        if (thread <= 0 || thread >= PAGE_LOOP_MAX_CONCURRENCY) {
            thread = availableProcessors;
        }

        List<R> returnValue = Collections.synchronizedList(new ArrayList<>());
        // 先翻第一页，获取总页数之后再并发去调用
        BizPageInfo<R> bizPageInfo = function.apply(pageRequest);
        if (bizPageInfo == null) {
            return returnValue;
        }

        returnValue.addAll(bizPageInfo.getList());
        int pages = bizPageInfo.getPages();
        if (pages <= 1) {
            return returnValue;
        }

        // 并发翻页
        Semaphore semaphore = new Semaphore(thread);
        CountDownLatch countDownLatch = new CountDownLatch(pages - 1);
        final String pageRequestStr = JsonBinder.toJson(pageRequest);
        try {
            for (int i = pageRequest.getPageNum() + 1; i <= pages && i < PAGE_LOOP_LIMIT; i++) {
                semaphore.acquire();
                T curRequest = (T) JsonBinder.fromJson(pageRequestStr, pageRequest.getClass());
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
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    } finally {
                        semaphore.release();
                        countDownLatch.countDown();
                    }
                });
            }
            countDownLatch.await(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return returnValue;
    }
}
