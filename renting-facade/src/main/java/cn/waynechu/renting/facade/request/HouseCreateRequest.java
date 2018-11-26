package cn.waynechu.renting.facade.request;

import cn.waynechu.renting.facade.request.common.AbstractRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhuwei
 * @date 2018/11/15 11:54
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "添加房屋信息请求对象")
public class HouseCreateRequest extends AbstractRequest {
    private static final long serialVersionUID = -2283304138049180155L;

    private String name;

    private String city;
}
