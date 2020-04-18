package cn.waynechu.springcloud.common.excel;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import cn.waynechu.facade.common.page.BizPageInfo;
import cn.waynechu.facade.common.request.BizPageRequest;
import cn.waynechu.springcloud.common.enums.ExportStatusEnum;
import cn.waynechu.springcloud.common.model.ExportResultModel;
import cn.waynechu.springcloud.common.util.PageLoopUtil;
import cn.waynechu.springcloud.common.util.StringUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author zhuwei
 * @date 2020-03-22 17:57
 */
@Slf4j
public class ExcelUtil {

    private Executor executor;

    private RedisTemplate<Object, Object> redisTemplate;

    public ExcelUtil(Executor executor, RedisTemplate<Object, Object> redisTemplate) {
        this.executor = executor;
        this.redisTemplate = redisTemplate;
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
     */
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
     * @param fileName 文件名
     * @param clazz    excel对象类型
     * @param request  查询参数
     * @param supplier 分页查询方法
     * @return sid 导出唯一标识
     */
    public <T> String exportForSid(String fileName, Class<T> clazz, BizPageRequest request, Supplier<BizPageInfo<T>> supplier) {
        // 同步sid状态
        String sid = UUID.randomUUID().toString();
        this.syncResult(sid, ExportStatusEnum.GENERATED, null);

        executor.execute(() -> {
            // 设置导出文件名
            this.setFileName(sid, fileName);
            String sheetName = this.encodeFileName(fileName);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ExcelWriter excelWriter = EasyExcel.write(stream, clazz).build();
            // 使用table方式写入，设置sheet不需要头
            WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).needHead(Boolean.FALSE).build();

            WriteTable writeTable;
            BizPageInfo<T> bizPageInfo;
            int pageIndex = 1;
            // 默认从第一页开始查
            request.setPageNum(1);
            // 限制查询条数
            request.setPageSize(QUERY_LIMIT);

            do {
                // 第一次写入会创建头，后面直接写入数据
                writeTable = EasyExcel.writerTable(pageIndex).needHead(pageIndex == 1).build();

                // 分页查询
                request.setPageNum(pageIndex);
                bizPageInfo = supplier.get();
                excelWriter.write(bizPageInfo.getList(), writeSheet, writeTable);
                pageIndex++;
            } while (bizPageInfo.getHasNextPage() && pageIndex < PageLoopUtil.PAGE_LOOP_LIMIT);
            excelWriter.finish();

            // 上传excel
            this.processExcelStream(sid, stream);
        });
        return sid;
    }

    /**
     * 导出并获取excel的sid
     *
     * <pre>
     *     适用于数据量比较小的导出，推荐1W行下使用
     * </pre>
     *
     * @param fileName 文件名
     * @param clazz    excel对象类型
     * @param data     要导出的数据
     * @return sid 导出唯一标识
     */
    public <T> String exportForSid(String fileName, Class<T> clazz, List<T> data) {
        // 同步sid状态
        String sid = UUID.randomUUID().toString();
        this.syncResult(sid, ExportStatusEnum.GENERATED, null);

        executor.execute(() -> {
            // 设置导出文件名
            this.setFileName(sid, fileName);
            String sheetName = this.encodeFileName(fileName);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ExcelWriter excelWriter = EasyExcel.write(stream, clazz).build();
            WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
            excelWriter.write(data, writeSheet);
            excelWriter.finish();

            // 上传excel
            this.processExcelStream(sid, stream);
        });
        return sid;
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
     * 处理excel流
     *
     * @param sid    导出唯一id
     * @param stream excel流
     */
    private void processExcelStream(String sid, ByteArrayOutputStream stream) {
        ExportStatusEnum statusEnum = ExportStatusEnum.FAIL;
        String url = "";
        if (stream == null) {
            statusEnum = ExportStatusEnum.NULL;
        } else {
            try {
                url = this.processUpload(stream);
                if (StringUtil.isNotBlank(url)) {
                    statusEnum = ExportStatusEnum.SUCCESS;
                }
                stream.close();
            } catch (Exception ex) {
                log.info("处理excel异常", ex);
            }
        }
        this.syncResult(sid, statusEnum, url);
    }

    /**
     * 设置文件名称
     *
     * @param sid      导出唯一标识
     * @param fileName 文件名称
     */
    private void setFileName(String sid, String fileName) {
        redisTemplate.opsForValue().set(EXPORT_FILE_NAME_CACHE_KEY + sid, fileName,
                EXPORT_CACHE_TIME_OUT, EXPORT_CACHE_TIME_OUT_UNIT);
    }

    /**
     * 同步导出状态
     *
     * @param sid        导出唯一标识
     * @param statusEnum 导出状态
     * @param url        导出的excel文件地址
     */
    private void syncResult(String sid, ExportStatusEnum statusEnum, String url) {
        ExportResultModel exportResultModel = new ExportResultModel();
        exportResultModel.setStatus(statusEnum);
        exportResultModel.setUrl(url);
        exportResultModel.setSid(sid);
        redisTemplate.opsForValue().set(EXPORT_CACHE_KEY + sid, exportResultModel,
                EXPORT_CACHE_TIME_OUT, EXPORT_CACHE_TIME_OUT_UNIT);
    }

    /**
     * 上传excel并获取文件地址
     *
     * @param stream 数据流
     * @return 文件地址
     */
    private String processUpload(ByteArrayOutputStream stream) {
        // TODO 2020-03-22 18:12 待文件上传模块完成后添加上传逻辑
        return "http://img.waynechu.cn/1.excel";
    }


    /**
     * 导出限制每次查询的数据行数
     */
    public static final int QUERY_LIMIT = 1000;

    /**
     * 缓存KEY前缀
     */
    public static final String EXPORT_CACHE_KEY = "export_cache_";

    /**
     * 导出文件名缓存KEY前缀
     */
    public static final String EXPORT_FILE_NAME_CACHE_KEY = "export_file_name_cache_";

    /**
     * 缓存过期时间
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
}
