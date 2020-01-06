package cn.waynechu.springcloud.common.excel;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import com.alibaba.excel.EasyExcel;
import lombok.experimental.UtilityClass;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * excel导出工具类
 *
 * @author zhuwei
 * @date 2019/7/29 17:37
 */
@UtilityClass
public class ExcelExportUtil {

    /**
     * 导出单sheet Excel文件
     *
     * @param list     数据列表
     * @param clazz    导出模型类
     * @param filename 导出文件名
     * @param response HttpServletResponse
     */
    public static void export(List<?> list, Class<?> clazz, String filename, HttpServletResponse response) {
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
