package cn.waynechu.springcloud.common.excel.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime时间类型转换器
 * <p>
 * 默认时间转化格式为: yyyy-MM-dd HH:mm:ss
 * 时间格式读取 {@code JsonFormat} 注解的pattern属性
 *
 * @author zhuwei
 * @date 2020-07-03 01:05
 */
public class LocalDateTimeConvert implements Converter<LocalDateTime> {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Class<LocalDateTime> supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDateTime convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        JsonFormat pattern = contentProperty.getField().getAnnotation(JsonFormat.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern != null ? pattern.pattern() : DEFAULT_PATTERN);
        return LocalDateTime.parse(cellData.getStringValue(), formatter);
    }

    @Override
    public CellData<String> convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        JsonFormat pattern = contentProperty.getField().getAnnotation(JsonFormat.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern != null ? pattern.pattern() : DEFAULT_PATTERN);
        return new CellData<>(value.format(formatter));
    }
}
