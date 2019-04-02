package cn.waynechu.spirngcloud.common.util;

import cn.waynechu.facade.common.excel.ModelExcel;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhuwei
 * @date 2019/2/22 11:09
 */
@Slf4j
@UtilityClass
public class ExcelUtil {

    @SuppressWarnings("unchecked")
    public static void exportExcel(HttpServletResponse response, ModelExcel modelExcel) {
        try {
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + modelExcel.getFileName());
            SXSSFWorkbook workbook = generateSXSSFWorkbook(modelExcel.getExcelData(), modelExcel.getTitles(), modelExcel.getZhTitles(), modelExcel.getSheetName());
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            log.error("导出Excel失败", e);
        }
    }

    public static SXSSFWorkbook generateSXSSFWorkbook(List<?> data, List<String> title, List<String> zhTitle, String sheetName) {
        SXSSFWorkbook swb = null;
        try {
            swb = new SXSSFWorkbook();
            Sheet sheet0 = swb.createSheet(sheetName);
            // 如果中文标题为空，则默认使用英文标题
            if (CollectionUtil.isNotNullOrEmpty(zhTitle)) {
                createTitle(sheet0, zhTitle);
            } else {
                createTitle(sheet0, title);
            }

            Class<?> dataClass = null;
            if (CollectionUtil.isNotNullOrEmpty(data)) {
                // 获取类
                dataClass = data.get(0).getClass();
            }
            for (int i = 0; i < data.size(); i++) {
                // 获取一个对象
                Object instance = data.get(i);
                // 获取属性
                Method method = null;
                // 数据封装
                Row row = sheet0.createRow(i + 1);
                for (int j = 0; j < title.size(); j++) {
                    method = dataClass.getMethod(getMethod(title.get(j)));
                    Cell cell = row.createCell(j);
                    if (method.invoke(instance) != null) {
                        if (method.getReturnType().isAssignableFrom(Date.class)) {
                            cell.setCellValue(DateUtil.dateToString((Date) method.invoke(instance)));
                        } else {
                            cell.setCellValue(method.invoke(instance) + "");
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("自定义对象对象数生成流对象失败", e);
        }
        return swb;
    }

    /**
     * 多Sheet导出
     *
     * @param dates    导出的数据
     * @param titles   标题行
     * @param zhTitles 中文标题行
     * @return 包含多Sheet的.xslx文件
     */
    public static SXSSFWorkbook generateSXSSFWorkbook(Map<String, List<?>> dates, Map<String, List<String>> titles, Map<String, List<String>> zhTitles) {
        SXSSFWorkbook swb = new SXSSFWorkbook();
        // 1.载入模板
        try {
            if (CollectionUtil.isNotNullOrEmpty(dates)) {
                for (Map.Entry<String, List<?>> entry : dates.entrySet()) {
                    Sheet sheet0 = swb.createSheet(entry.getKey());
                    // 如果中文标题为空,则默认使用英文标题
                    if (CollectionUtil.isNotNullOrEmpty(zhTitles.get(entry.getKey()))) {
                        createTitle(sheet0, zhTitles.get(entry.getKey()));
                    } else {
                        createTitle(sheet0, titles.get(entry.getKey()));
                    }

                    Class<?> dataClass = null;
                    if (CollectionUtil.isNotNullOrEmpty(entry.getValue())) {
                        // 获取类
                        dataClass = entry.getValue().get(0).getClass();
                    }
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        // 获取一个对象
                        Object instance = entry.getValue().get(i);
                        // 获取属性
                        Method method = null;
                        // 数据封装
                        Row row = sheet0.createRow(i + 1);
                        for (int j = 0; j < titles.get(entry.getKey()).size(); j++) {
                            method = dataClass.getMethod(getMethod(titles.get(entry.getKey()).get(j)));
                            Cell cell = row.createCell(j);
                            if (method.invoke(instance) != null) {
                                if (method.getReturnType().isAssignableFrom(Date.class)) {
                                    cell.setCellValue(DateUtil.dateToString((Date) method.invoke(instance)));
                                } else {
                                    cell.setCellValue(method.invoke(instance) + "");
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("多sheet导出失败", e);
        }
        return swb;
    }

    /**
     * 通过属性名称获取方法名
     *
     * @return 方法名
     */
    private static String getMethod(String field) {
        if (StringUtil.isBlank(field)) {
            return null;
        }
        return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    /**
     * 创建标题信息栏
     *
     * @param sheet0 第一行sheet
     * @param title  标题列表
     */
    private static void createTitle(Sheet sheet0, List<String> title) {
        // 创建 第一行
        Row row0 = sheet0.createRow(0);
        for (int index = 0; index < title.size(); index++) {
            Cell ti = row0.createCell(index);
            ti.setCellValue(title.get(index));
        }
    }
}
