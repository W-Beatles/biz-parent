package cn.waynechu.renting.web.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author zhuwei
 * @date 2019/1/18 15:17
 */
@Data
@ApiModel(description = "搜索字典信息请求对象")
public class SysDictionarySearchRequest {
    private Integer typeCode;

    private String typeName;

    private Integer code;

    private String name;

    private Long parentId;
}
