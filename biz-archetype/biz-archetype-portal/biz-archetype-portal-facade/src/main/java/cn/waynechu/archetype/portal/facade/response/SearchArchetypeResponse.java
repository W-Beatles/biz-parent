package cn.waynechu.archetype.portal.facade.response;

import cn.waynechu.springcloud.common.excel.convert.BooleanConvert;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhuwei
 * @since 2020-06-18 00:27
 */
@Data
@ApiModel
public class SearchArchetypeResponse {

    @ExcelProperty("原型id")
    @ApiModelProperty("原型id")
    private Long id;

    @ExcelProperty("AppId")
    @ApiModelProperty("AppId")
    private String appId;

    @ExcelProperty("App名称")
    @ApiModelProperty("App名称")
    private String appName;

    @ExcelIgnore
    @ApiModelProperty("项目类型: 0Service 1SDK")
    private Integer appType;

    @ExcelProperty("项目类型")
    @ApiModelProperty("项目类型描述")
    private String appTypeDesc;

    @ExcelProperty("包名")
    @ApiModelProperty("包名")
    private String packageName;

    @ExcelProperty("项目描述")
    @ApiModelProperty("项目描述")
    private String description;

    @ExcelIgnore
    @ApiModelProperty("状态: 0生成中 1成功 2失败")
    private Integer statusCode;

    @ExcelProperty("状态")
    @ApiModelProperty("状态描述")
    private String statusCodeDesc;

    @ExcelProperty(value = "上传git", converter = BooleanConvert.class)
    @ApiModelProperty("上传git: 0否 1是")
    private Boolean gitUploadType;

    @ExcelProperty("git仓库地址")
    @ApiModelProperty("git仓库地址")
    private String gitUrl;

    @ExcelProperty("原型下载地址")
    @ApiModelProperty("原型下载地址")
    private String downloadUrl;

    @ExcelProperty("创建人")
    @ApiModelProperty("创建人")
    private String createdUser;

    @ExcelProperty("创建时间")
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime createdTime;
}
