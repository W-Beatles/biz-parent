package cn.waynechu.webcommon.web.excel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author zhuwei
 * @date 2019/2/22 13:45
 */
@Setter
@Getter
@ToString
@ApiModel(description = "单excel对象（含英文、中文列名")
public class ModelExcel<T> {
    public static final String SUFFIX_XLSX = ".xlsx";

    @ApiModelProperty(value = "excel导出数据")
    private List<T> excelData;

    @ApiModelProperty(value = "英文名")
    private List<String> titles;
    @ApiModelProperty(value = "中文名")
    private List<String> zhTitles;

    @ApiModelProperty(value = "文件名")
    private String fileName;
    @ApiModelProperty(value = "文件sheetName")
    private String sheetName;
}
