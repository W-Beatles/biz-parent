package cn.waynechu.archetype.portal.facade.request;

import cn.waynechu.facade.common.request.BizPageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhuwei
 * @date 2020-06-18 00:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "任务列表查询请求对象")
public class SearchTaskRequest extends BizPageRequest {

    @ApiModelProperty("任务id")
    private Long taskId;

    @ApiModelProperty("AppId")
    private String appId;
}
