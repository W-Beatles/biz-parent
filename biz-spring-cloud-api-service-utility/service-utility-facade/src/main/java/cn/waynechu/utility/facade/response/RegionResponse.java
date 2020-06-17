package cn.waynechu.utility.facade.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhuwei
 * @date 2019-08-11 16:36
 */
@Data
@ApiModel(description = "行政区信息返回对象")
public class RegionResponse {

    @ApiModelProperty("区域id")
    private Integer id;

    @ApiModelProperty("区域名称")
    private String regionName;
}
