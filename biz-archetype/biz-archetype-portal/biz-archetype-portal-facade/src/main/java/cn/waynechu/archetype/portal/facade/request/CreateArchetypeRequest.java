package cn.waynechu.archetype.portal.facade.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhuwei
 * @since 2020-06-20 23:58
 */
@Data
@ApiModel
public class CreateArchetypeRequest {

    @ApiModelProperty(value = "AppID", required = true)
    @NotBlank(message = "AppID不能为空")
    @Length(max = 50, message = "AppID不能超过50个字符")
    private String appId;

    @ApiModelProperty(value = "项目名称", required = true)
    @NotBlank(message = "项目名称不能为空")
    @Length(max = 50, message = "项目名称不能超过50个字符")
    private String appName;

    @ApiModelProperty(value = "项目类型: 0Service 1SDK", required = true)
    @NotNull(message = "项目类型不能为空")
    private Integer appType;

    @ApiModelProperty(value = "包名", required = true)
    @NotBlank(message = "包名不能为空")
    @Length(max = 50, message = "包名不能超过50个字符")
    private String packageName;

    @ApiModelProperty("项目描述")
    @Length(max = 400, message = "项目描述不能超过400个字符")
    private String description;

    @ApiModelProperty(value = "上传git: 0否 1是", required = true)
    @NotNull(message = "上传git不能为空")
    private Boolean gitUploadType;

    @ApiModelProperty(value = "git仓库地址")
    private String gitUploadUrl;
}
