package cn.waynechu.springcloud.common.util;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.experimental.UtilityClass;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * excel导出工具类
 *
 * @author zhuwei
 * @date 2019/7/29 17:37
 */
@UtilityClass
public class ExportUtil {

    /**
     * 导出单sheet Excel文件
     *
     * @param list     数据列表
     * @param clazz    导出模型类
     * @param filename 导出文件名
     * @param response HttpServletResponse
     */
    public static void export(List<? extends BaseRowModel> list, Class<? extends BaseRowModel> clazz, String filename, HttpServletResponse response) {
        try {
            filename = URLDecoder.decode(filename, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // do nothing here.
        }

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            ExcelWriter writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX, true);
            Sheet sheet = new Sheet(1, 0, clazz);
            sheet.setSheetName(filename);
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            response.addHeader("Content-Disposition", "attachment; filename=" + filename + ".xlsx");
            writer.write(list, sheet);
            writer.finish();
            outputStream.flush();
        } catch (IOException e) {
            throw new BizException(BizErrorCodeEnum.OPERATION_FAILED, filename + "导出失败");
        }
    }
}
