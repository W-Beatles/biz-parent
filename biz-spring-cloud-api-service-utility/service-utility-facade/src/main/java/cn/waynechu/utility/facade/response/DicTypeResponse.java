package cn.waynechu.utility.facade.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhuwei
 * @since 2020/7/3 17:48
 */
@Data
@ApiModel
public class DicTypeResponse {

    @ApiModelProperty("类型id")
    private Long id;

    @ApiModelProperty("类型编码")
    private String typeCode;

    @ApiModelProperty("所属AppID")
    private String appId;

    @ApiModelProperty("类型描述")
    private String description;
}
