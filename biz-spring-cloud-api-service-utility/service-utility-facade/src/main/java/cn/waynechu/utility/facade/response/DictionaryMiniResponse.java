package cn.waynechu.utility.facade.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhuwei
 * @since 2020-07-05 16:40
 */
@Data
@ApiModel
public class DictionaryMiniResponse {

    @ApiModelProperty("字典编码")
    private String dicCode;

    @ApiModelProperty("字典值")
    private String dicValue;

    @ApiModelProperty("字典描述")
    private String dicDesc;
}
