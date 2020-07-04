package cn.waynechu.springcloud.common.excel.convert;

import cn.waynechu.springcloud.common.util.StringUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * 布尔类型转换器
 *
 * @author zhuwei
 * @date 2020-07-04 10:17
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
            return false;
        }
        return booleanStr.equals("是");
    }

    @Override
    public CellData<String> convertToExcelData(Boolean value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return new CellData<>(value == null ? "" : (Boolean.TRUE.equals(value) ? "是" : "否"));
    }
}
