package cn.waynechu.renting.facade.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author zhuwei
 * @date 2019/1/18 15:09
 */
@Data
@ApiModel(description = "更新字典信息请求对象")
public class SysDictionaryUpdateReq {

    private Long id;

    private Integer typeCode;

    private String typeName;

    private Integer code;

    private String name;

    private Long parentId;
}
