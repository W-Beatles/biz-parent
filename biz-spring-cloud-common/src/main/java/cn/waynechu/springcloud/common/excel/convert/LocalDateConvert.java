package cn.waynechu.springcloud.common.excel.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * LocalDate时间类型转换器
 * <p>
 * 默认时间转化格式为: yyyy-MM-dd
 * 时间格式读取 {@code JsonFormat} 注解的pattern属性
 *
 * @author zhuwei
 * @date 2020-07-03 01:05
 */
public class LocalDateConvert implements Converter<LocalDate> {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd";

    @Override
    public Class<LocalDate> supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDate convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        JsonFormat pattern = contentProperty.getField().getAnnotation(JsonFormat.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern != null ? pattern.pattern() : DEFAULT_PATTERN);
        return LocalDate.parse(cellData.getStringValue(), formatter);
    }

    @Override
    public CellData<String> convertToExcelData(LocalDate value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        JsonFormat pattern = contentProperty.getField().getAnnotation(JsonFormat.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern != null ? pattern.pattern() : DEFAULT_PATTERN);
        return new CellData<>(value.format(formatter));
    }
}
