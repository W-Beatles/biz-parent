package cn.waynechu.utility.facade.request;

import cn.waynechu.facade.common.request.BizPageRequest;
import io.swagger.annotations.ApiModel;
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

    // TODO 2020/7/3 18:54
}
