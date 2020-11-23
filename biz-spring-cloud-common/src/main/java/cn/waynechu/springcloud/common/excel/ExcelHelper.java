package cn.waynechu.springcloud.common.excel;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import cn.waynechu.facade.common.page.BizPageInfo;
import cn.waynechu.facade.common.request.BizPageRequest;
import cn.waynechu.facade.common.response.BizResponse;
import cn.waynechu.springcloud.common.enums.ExportStatusEnum;
import cn.waynechu.springcloud.common.excel.convert.LocalDateConvert;
import cn.waynechu.springcloud.common.excel.convert.LocalDateTimeConvert;
import cn.waynechu.springcloud.common.excel.convert.LocalTimeConvert;
import cn.waynechu.springcloud.common.model.ExportResultResponse;
import cn.waynechu.springcloud.common.util.JsonBinder;
import cn.waynechu.springcloud.common.util.PageLoopHelper;
import cn.waynechu.springcloud.common.util.StringUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * excel解析、导出工具类
 *
 * @author zhuwei
 * @since 2020-03-22 17:57
 */
@Slf4j
public class ExcelHelper {

    private Executor executor;

    private StringRedisTemplate redisTemplate;

    private RestTemplate restTemplate;

    public ExcelHelper(Executor executor, StringRedisTemplate redisTemplate, RestTemplate restTemplate) {
        this.executor = executor;
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
    }

    /**
     * 解析excel，推荐数据行数小于3000行时使用
     *
     * @param file  MultipartFile
     * @param clazz 解析的数据模型
     * @return 数据列表
     */
    public static <T> List<T> parse(MultipartFile file, Class<T> clazz) {
        try {
            return EasyExcel.read(file.getInputStream()).head(clazz).sheet(0).doReadSync();
        } catch (IOException e) {
            throw new BizException(BizErrorCodeEnum.OPERATION_FAILED, "解析excel失败");
        }
    }

    /**
     * 导出excel
     *
     * @param list     数据列表
     * @param clazz    导出模型类
     * @param filename 导出文件名
     * @param response httpServletResponse
     * @deprecated 推荐使用 {@code exportForSid} 异步导出方法
     */
    @Deprecated
    public static <T> void export(List<T> list, Class<T> clazz, String filename, HttpServletResponse response) {
        try {
            filename = URLEncoder.encode(filename, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // do nothing here.
        }

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.addHeader("Content-Disposition", "attachment; filename=" + filename + EXPORT_UPLOAD_EXTENSIONS);
            EasyExcel.write(outputStream, clazz).sheet(filename).doWrite(list);
        } catch (IOException e) {
            throw new BizException(BizErrorCodeEnum.OPERATION_FAILED, filename + "导出失败");
        }
    }

    /**
     * 导出并获取excel的sid
     *
     * <pre>
     *     使用 list数据 及 自定义model 导出
     *     适用于数据量比较小的导出，推荐1W行下使用
     *     注意，如果需要查询的数据量较大，会增大获取sid接口耗时
     * </pre>
     *
     * @param fileName 文件名
     * @param data     要导出的数据
     * @param <T>      excel对象类
     * @param clazz    excel对象类型
     * @return sid 导出唯一标识
     */
    public <T> String exportForSid(final String fileName, List<T> data, Class<T> clazz) {
        return exportForSid(fileName, sid -> generateExcelFile(sid, fileName, data, clazz, null));
    }

    /**
     * 导出并获取excel的sid
     *
     * <pre>
     *     使用 list数据 及 动态标题头 导出
     *     适用于数据量比较小的导出，推荐1W行下使用
     *     注意，如果需要查询的数据量较大，会增大获取sid接口耗时
     * </pre>
     *
     * @param fileName 文件名
     * @param data     要导出的数据
     * @param <T>      excel对象类
     * @param heads    标题头。如果标题头无需合并，则list中只用添加一个名称即可
     * @return sid 导出唯一标识
     */
    public <T> String exportForSid(final String fileName, List<T> data, List<List<String>> heads) {
        return exportForSid(fileName, sid -> generateExcelFile(sid, fileName, data, null, heads));
    }

    /**
     * 导出并获取excel的sid
     *
     * <pre>
     *      根据 查询列表数据方法 及 自定义model 导出
     *      因为传入查询方法后内部会异步去查询，故该方式不会产生获取sid超时的问题
     *      该方式可复用列表查询的方法，推荐老项目改造时使用该方式
     *      但要注意如果查询列表方法导出的list数据量很大，仍然会占用较多内存
     * </pre>
     *
     * @param fileName 文件名。无需拼接.xlsx后缀
     * @param clazz    excel对象类型
     * @param supplier 查询数据列表的方法
     * @param <T>      excel对象类
     * @return sid 导出唯一标识
     */
    public <T> String exportForSid(final String fileName, Supplier<List<T>> supplier, Class<T> clazz) {
        return exportForSid(fileName, sid -> generateExcelFile(sid, fileName, supplier.get(), clazz, null));
    }

    /**
     * 导出并获取excel的sid
     *
     * <pre>
     *      根据 查询列表数据方法 及 动态标题头 导出
     *      因为传入查询方法后内部会异步去查询，故该方式不会产生获取sid超时的问题
     *      该方式可复用列表查询的方法，推荐老项目改造时使用该方式
     *      但要注意如果查询列表方法导出的list数据量很大，仍然会占用较多内存
     * </pre>
     *
     * @param fileName 文件名。无需拼接.xlsx后缀
     * @param supplier 查询数据列表的方法
     * @param <T>      excel对象类
     * @param heads    标题头。如果标题头无需合并，则list中只用添加一个名称即可
     * @return sid 导出唯一标识
     */
    public <T> String exportForSid(final String fileName, Supplier<List<T>> supplier, List<List<String>> heads) {
        return exportForSid(fileName, sid -> generateExcelFile(sid, fileName, supplier.get(), null, heads));
    }

    /**
     * 导出并获取excel的sid
     *
     * <pre>
     *     根据 分页查询的方法 及 自定义model 导出
     *     注意该分页查询的方法入参必须继承 {@code BizPageRequest}，且返回值类型为 {@code BizPageInfo}
     *     该方式可复用列表查询的方法，推荐新项目接入时采用该方式
     * </pre>
     *
     * @param fileName 文件名。无需拼接.xlsx后缀
     * @param request  查询参数
     * @param supplier 分页查询方法
     * @param clazz    excel对象类型
     * @param <T>      excel对象类
     * @return sid 导出唯一标识
     */
    public <T> String exportForSid(final String fileName, BizPageRequest request, Supplier<BizPageInfo<T>> supplier, Class<T> clazz) {
        return exportForSid(fileName, sid -> generateExcelFile(sid, fileName, request, supplier, clazz, null));
    }

    /**
     * 导出并获取excel的sid
     *
     * <pre>
     *     根据 分页查询的方法 及 动态标题头 导出excel
     *     注意该分页查询的方法入参必须继承 {@code BizPageRequest}，且返回值类型为 {@code BizPageInfo}
     *     该方式可复用列表查询的方法，推荐新项目接入时采用该方式
     * </pre>
     *
     * @param fileName 文件名。无需拼接.xlsx后缀
     * @param request  查询参数
     * @param supplier 分页查询方法
     * @param <T>      excel对象类
     * @param heads    标题头。如果标题头无需合并，则list中只用添加一个名称即可
     * @return sid 导出唯一标识
     */
    public <T> String exportForSid(final String fileName, BizPageRequest request, Supplier<BizPageInfo<T>> supplier, List<List<String>> heads) {
        return exportForSid(fileName, sid -> generateExcelFile(sid, fileName, request, supplier, null, heads));
    }

    /**
     * 导出并获取excel的sid
     *
     * @param fileName             文件名
     * @param generateFileFunction 生成excel文件的方法
     * @return sid 导出唯一标识
     */
    private <T> String exportForSid(final String fileName, Function<String, File> generateFileFunction) {
        final String sid = UUID.randomUUID().toString();
        this.syncExportResult(sid, fileName, ExportStatusEnum.GENERATED, null);

        executor.execute(() -> {
            File tempFile = null;
            ExportStatusEnum exportStatus = ExportStatusEnum.FAIL;
            String url = null;
            try {
                // 生成excel
                tempFile = generateFileFunction.apply(sid);
                // 上传excel
                url = this.uploadExcelFile(tempFile);
                if (StringUtil.isNotEmpty(url)) {
                    exportStatus = ExportStatusEnum.SUCCESS;
                }
            } catch (Exception e) {
                log.error("导出失败", e);
            } finally {
                // 同步导出状态
                this.syncExportResult(sid, fileName, exportStatus, url);
                // 删除临时excel文件
                if (tempFile != null && tempFile.exists()) {
                    // noinspection ResultOfMethodCallIgnored
                    tempFile.delete();
                }
            }
        });
        return sid;
    }

    /**
     * 生成excel文件
     *
     * @param sid       导出唯一标识
     * @param sheetName 导出sheet名称
     * @param data      导出的数据
     * @param clazz     excel对象类型
     * @param <T>       excel对象类
     * @param heads     标题头
     * @return excel文件
     */
    private <T> File generateExcelFile(String sid, String sheetName, List<T> data, Class<T> clazz, List<List<String>> heads) {
        File tempFile;
        try {
            tempFile = File.createTempFile(sid, EXPORT_UPLOAD_EXTENSIONS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sheetName = this.encodeFileName(sheetName);
        if (clazz != null) {
            EasyExcel.write(tempFile, clazz).sheet(sheetName).doWrite(data);
        } else {
            EasyExcel.write(tempFile).head(heads).sheet(sheetName).doWrite(data);
        }
        return tempFile;
    }

    /**
     * 生成excel文件
     *
     * @param sid       导出唯一标识
     * @param sheetName 导出sheet名称
     * @param clazz     excel对象模型
     * @param request   查询参数
     * @param supplier  分页查询方法
     * @return excel文件
     */
    private <T> File generateExcelFile(String sid, String sheetName, BizPageRequest request
            , Supplier<BizPageInfo<T>> supplier, Class<T> clazz, List<List<String>> heads) {
        File tempFile;
        try {
            tempFile = File.createTempFile(sid, EXPORT_UPLOAD_EXTENSIONS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ExcelWriter excelWriter;
        if (clazz != null) {
            // 使用table方式写入，设置sheet不需要头
            excelWriter = EasyExcel.write(tempFile, clazz).needHead(false).build();
        } else {
            excelWriter = EasyExcel.write(tempFile).head(heads).build();
        }

        sheetName = this.encodeFileName(sheetName);
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName)
                // 添加 java8 时间类库支持
                .registerConverter(new LocalTimeConvert())
                .registerConverter(new LocalDateConvert())
                .registerConverter(new LocalDateTimeConvert())
                .build();

        WriteTable writeTable;
        BizPageInfo<T> bizPageInfo;
        int pageIndex = 1;
        // 默认从第一页开始查
        request.setPageNum(1);
        // 限制单次查询条数
        request.setPageSize(QUERY_LIMIT);

        do {
            // 第一次写入会创建头，后面直接写入数据
            writeTable = EasyExcel.writerTable(pageIndex).needHead(pageIndex == 1).build();

            // 循环分页查询
            request.setPageNum(pageIndex);
            bizPageInfo = supplier.get();
            excelWriter.write(bizPageInfo.getList(), writeSheet, writeTable);
            pageIndex++;
        } while (bizPageInfo.getHasNextPage() && pageIndex < PageLoopHelper.PAGE_LOOP_LIMIT);

        excelWriter.finish();
        return tempFile;
    }

    /**
     * 文件名转义
     *
     * <pre>
     *     防止excel的sheet名出现不支持的字符抛出异常
     *     如 "订单明细 1899-12-31 23:54:17"，其中冒号就是sheet名无法支持字符
     * </pre>
     *
     * @param fileName 文件名
     * @return 转义后的文件名
     */
    private String encodeFileName(String fileName) {
        String encodeFileName = null;
        try {
            encodeFileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // do nothing here.
        }
        return encodeFileName;
    }

    /**
     * 上传excel文件
     *
     * @param file 文件
     */
    private String uploadExcelFile(File file) {
        String url = null;

        FileSystemResource resource = new FileSystemResource(file);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("file", resource);

        // 上传excel文件
        ResponseEntity<BizResponse<String>> responseEntity = restTemplate.exchange(FILE_UPLOAD_URL, HttpMethod.POST
                , new HttpEntity<>(params), new ParameterizedTypeReference<BizResponse<String>>() {
                });
        if (HttpStatus.OK.equals(responseEntity.getStatusCode()) && responseEntity.getBody() != null) {
            url = responseEntity.getBody().getData();
        }
        return url;
    }

    /**
     * 同步导出状态
     *
     * @param sid        导出唯一标识
     * @param fileName   导出文件名
     * @param statusEnum 导出状态
     * @param url        导出的excel文件地址
     */
    private void syncExportResult(String sid, String fileName, ExportStatusEnum statusEnum, String url) {
        ExportResultResponse exportResultResponse = new ExportResultResponse();
        exportResultResponse.setStatus(statusEnum.getCode());
        exportResultResponse.setFileName(fileName + EXPORT_UPLOAD_EXTENSIONS);
        exportResultResponse.setUrl(url);
        exportResultResponse.setSid(sid);

        String key = EXPORT_CACHE_KEY + sid;
        String value = JsonBinder.toJson(exportResultResponse);
        redisTemplate.opsForValue().set(key, value, EXPORT_CACHE_TIME_OUT, EXPORT_CACHE_TIME_OUT_UNIT);
    }

    /**
     * 导出限制单次查询条数
     */
    public static final int QUERY_LIMIT = 1000;

    /**
     * 缓存KEY前缀
     */
    public static final String EXPORT_CACHE_KEY = "utility-excel-export:";

    /**
     * 缓存过期时间. 默认30分钟
     */
    public static final Long EXPORT_CACHE_TIME_OUT = 30L;

    /**
     * 缓存过期时间单位
     */
    public static final TimeUnit EXPORT_CACHE_TIME_OUT_UNIT = TimeUnit.MINUTES;

    /**
     * 导出文件上传文件后缀名
     */
    public static final String EXPORT_UPLOAD_EXTENSIONS = ".xlsx";

    /**
     * 文件上传地址
     */
    public static final String FILE_UPLOAD_URL = "http://service-utility/files/upload";
}
