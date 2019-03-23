package cn.waynechu.renting.dal.common.entity;

import java.io.Serializable;
import java.util.Date;

public class SysDictionary implements Serializable {
    private Long id;

    private Integer typeCode;

    private String typeName;

    private Integer code;

    private String name;

    private Long parentId;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private Boolean isDeleted;

    public SysDictionary(Long id, Integer typeCode, String typeName, Integer code, String name, Long parentId, String createUser, Date createTime, String updateUser, Date updateTime, Boolean isDeleted) {
        this.id = id;
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.code = code;
        this.name = name;
        this.parentId = parentId;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.isDeleted = isDeleted;
    }

    public SysDictionary() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}