package cn.waynechu.springcloud.common.excel.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.github.pagehelper.util.StringUtil;

/**
 * 布尔类型转换器
 * <p>
 * 是 <=> true
 * 否 <=> false
 * "" <=> null
 *
 * @author zhuwei
 * @since 2020-07-04 10:17
 */
public class BooleanConvert implements Converter<Boolean> {

    @Override
    public Class<Boolean> supportJavaTypeKey() {
        return Boolean.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Boolean convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String booleanStr = cellData.getStringValue();
        if (StringUtil.isEmpty(booleanStr)) {
            return null;
        }
        return "是".equals(booleanStr);
    }

    @Override
    public CellData<String> convertToExcelData(Boolean value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (value == null) {
            return new CellData<>("");
        }
        return new CellData<>(Boolean.TRUE.equals(value) ? "是" : "否");
    }
}
