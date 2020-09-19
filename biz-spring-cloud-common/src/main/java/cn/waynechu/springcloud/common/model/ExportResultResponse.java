package cn.waynechu.springcloud.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuwei
 * @since 2020-03-22 17:58
 */
@Data
@ApiModel
public class ExportResultResponse implements Serializable {

    @ApiModelProperty("导出唯一标识")
    private String sid;

    @ApiModelProperty(value = "导出文件名", example = "原型列表 2020-07-03 11:37:17.xlsx")
    private String fileName;

    @ApiModelProperty("导出状态: -1失败 0生成中 1生成成功")
    private Integer status;

    @ApiModelProperty("文件下载地址")
    private String url;
}
