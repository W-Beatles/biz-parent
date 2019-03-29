package com.waynechu.renting.facade.dto.condition;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuwei
 * @date 2019/3/25 14:48
 */
@Data
public class SysDictionarySearchCondition implements Serializable {

    private Long id;

    private Long parentId;

    private String type;

    private String code;

    private String displayName;

    private String description;

    private Integer pageNum = 1;

    private Integer pageSize = 10;
}
