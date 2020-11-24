package cn.waynechu.springcloud.common.excel.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalTime时间类型转换器
 * <p>
 * 默认时间转化格式为: HH:mm:ss
 * 时间格式读取 {@code JsonFormat} 注解的pattern属性
 *
 * @author zhuwei
 * @since 2020-07-03 01:05
 */
public class LocalTimeConvert extends BaseLocalDateTimeConvert implements Converter<LocalTime> {

    private static final String DEFAULT_PATTERN = "HH:mm:ss";

    @Override
    public Class<LocalTime> supportJavaTypeKey() {
        return LocalTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalTime convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (contentProperty == null) {
            return null;
        }
        DateTimeFormatter formatter = getDateTimeFormatter(contentProperty.getField(), DEFAULT_PATTERN);
        return LocalTime.parse(cellData.getStringValue(), formatter);
    }

    @Override
    public CellData<String> convertToExcelData(LocalTime value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (value == null || contentProperty == null) {
            return new CellData<>("");
        }
        DateTimeFormatter formatter = getDateTimeFormatter(contentProperty.getField(), DEFAULT_PATTERN);
        return new CellData<>(value.format(formatter));
    }
}
