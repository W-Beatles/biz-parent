package cn.waynechu.renting.facade.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhuwei
 * @date 2019/2/21 16:43
 */
@Data
@ApiModel(description = "日志导出请求对象")
public class ExportLogRequest {

    @ApiModelProperty(name = "开始日期")
    @NotNull(message = "开始日期不能为空")
    private String startDate;

    @ApiModelProperty(name = "结束日期")
    @NotNull(message = "结束日期不能为空")
    private String endDate;
}
