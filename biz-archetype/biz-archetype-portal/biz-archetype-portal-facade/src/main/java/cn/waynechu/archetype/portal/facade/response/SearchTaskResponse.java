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
public class SearchTaskResponse {

    @ApiModelProperty("任务id")
    private Long taskId;

    @ApiModelProperty("骨架类型: 1Service 2SDK")
    private Integer archetypeCode;

    @ApiModelProperty("AppId")
    private String appId;

    @ApiModelProperty("App名称")
    private String appName;

    @ApiModelProperty("状态code: 1生成中 2成功 3失败")
    private String statusCode;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty("文件地址")
    private String url;
}
