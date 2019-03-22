package cn.waynechu.renting.web.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author zhuwei
 * @date 2019/1/18 15:07
 */
@Data
@ApiModel(description = "添加字典信息请求对象")
public class SysDictionaryCreateRequest {

    private Integer typeCode;

    private String typeName;

    private Integer code;

    private String name;

    private Long parentId;
}
