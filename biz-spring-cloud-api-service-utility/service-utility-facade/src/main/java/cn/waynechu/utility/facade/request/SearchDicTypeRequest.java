package cn.waynechu.utility.facade.request;

import cn.waynechu.facade.common.request.BizPageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhuwei
 * @since 2020/7/3 17:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SearchDicTypeRequest extends BizPageRequest {

    @ApiModelProperty("类型编码")
    private String typeCodeLike;

    @ApiModelProperty("所属AppID")
    private String appIdLike;
}
