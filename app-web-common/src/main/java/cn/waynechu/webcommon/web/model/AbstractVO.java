package cn.waynechu.webcommon.web.model;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @author zhuwei
 * @date 2018/11/15 13:38
 */
@ApiModel(description = "返回对象")
public abstract class AbstractVO implements Serializable {
    private static final long serialVersionUID = 9111238562185215154L;
}