package cn.waynechu.springcloud.common.excel;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import com.alibaba.excel.EasyExcel;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author zhuwei
 * @date 2020/1/9 10:40
 */
@UtilityClass
public class ExcelUtil {

    /**
     * 解析excel，推荐数据行数小于3000行时使用
     *
     * @param file  MultipartFile
     * @param clazz 解析的数据模型
     * @return 数据列表
     */
    public <T> List<T> parse(MultipartFile file, Class<T> clazz) {
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
            response.addHeader("Content-Disposition", "attachment; filename=" + filename + ".xlsx");
            EasyExcel.write(outputStream, clazz).sheet(filename).doWrite(list);
        } catch (IOException e) {
            throw new BizException(BizErrorCodeEnum.OPERATION_FAILED, filename + "导出失败");
        }
    }
}
