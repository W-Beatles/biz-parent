package cn.waynechu.facade.common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhuwei
 * @date 2018/11/15 11:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "分页请求对象")
public class BizPageRequest extends BizRequest {
    private static final long serialVersionUID = 2789603649153042060L;

    @ApiModelProperty(value = "当前页。默认第1页")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "页大小。默认10条")
    private Integer pageSize = 10;
}
