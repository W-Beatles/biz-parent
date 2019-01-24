package cn.waynechu.renting.facade.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author zhuwei
 * @date 2019/1/18 14:08
 */
@Data
public class SysDictionaryDTO {
    private Long id;

    private Integer typeCode;

    private String typeName;

    private Integer code;

    private String name;

    private Long parentId;

    private String createdUser;

    private Date createdTime;

    private String updatedUser;

    private Date updatedTime;

    private Boolean isDeleted;
}