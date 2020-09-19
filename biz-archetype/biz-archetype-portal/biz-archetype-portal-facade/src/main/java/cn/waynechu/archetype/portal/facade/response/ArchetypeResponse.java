package cn.waynechu.archetype.portal.facade.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhuwei
 * @since 2020-06-26 18:30
 */
@Data
@ApiModel
public class ArchetypeResponse {

    @ApiModelProperty("原型id")
    private Long id;

    @ApiModelProperty("AppId")
    private String appId;

    @ApiModelProperty("项目名称")
    private String appName;

    @ApiModelProperty("项目类型: 0Service 1SDK")
    private Integer appType;

    @ApiModelProperty("包名")
    private String packageName;

    @ApiModelProperty("项目描述")
    private String description;

    @ApiModelProperty("上传git: 0否 1是")
    private Boolean gitUploadType;
}
