package cn.waynechu.common.facade.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuwei
 * @date 2018/11/15 10:52
 */
@Data
@ApiModel(description = "请求对象")
public abstract class AbstractRequest implements Serializable {
    private static final long serialVersionUID = 2688827622955322894L;
}
