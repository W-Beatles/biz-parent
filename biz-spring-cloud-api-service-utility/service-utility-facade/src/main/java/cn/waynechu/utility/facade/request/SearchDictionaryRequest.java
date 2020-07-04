package cn.waynechu.utility.facade.request;

import cn.waynechu.facade.common.request.BizPageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhuwei
 * @since 2020/7/3 18:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class SearchDictionaryRequest extends BizPageRequest {

    @ApiModelProperty("父节点id")
    private Long pid;

    @ApiModelProperty("类型编码")
    private String dicTypeCodeLike;

    @ApiModelProperty("字典编码")
    private String dicCodeLike;

    @ApiModelProperty("字典描述")
    private String dicDescLike;
}
