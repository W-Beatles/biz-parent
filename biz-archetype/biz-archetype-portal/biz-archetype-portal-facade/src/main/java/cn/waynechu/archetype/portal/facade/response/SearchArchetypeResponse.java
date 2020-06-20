package cn.waynechu.archetype.portal.facade.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhuwei
 * @date 2020-06-18 00:27
 */
@Data
@ApiModel(description = "任务列表查询返回对象")
public class SearchArchetypeResponse {

    @ApiModelProperty("原型id")
    private Long id;

    @ApiModelProperty("AppId")
    private String appId;

    @ApiModelProperty("App名称")
    private String appName;

    @ApiModelProperty("项目类型: 0Service 1SDK")
    private Integer appType;

    @ApiModelProperty("项目类型描述")
    private String appTypeDesc;

    @ApiModelProperty("包名")
    private String packageName;

    @ApiModelProperty("项目描述")
    private String description;

    @ApiModelProperty("状态: 0生成中 1成功 2失败")
    private Integer statusCode;

    @ApiModelProperty("状态描述")
    private String statusCodeDesc;

    @ApiModelProperty("上传git: 0否 1是")
    private Boolean gitUploadStatus;

    @ApiModelProperty("git仓库地址")
    private String gitUrl;

    @ApiModelProperty("原型下载地址")
    private String downloadUrl;

    @ApiModelProperty("创建人")
    private String createdUser;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdTime;
}
