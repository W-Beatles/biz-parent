package cn.waynechu.renting.dal.common.entity;

import java.util.Date;

public class SysDicitionary {
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

    public SysDicitionary(Long id, Integer typeCode, String typeName, Integer code, String name, Long parentId, String createdUser, Date createdTime, String updatedUser, Date updatedTime, Boolean isDeleted) {
        this.id = id;
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.code = code;
        this.name = name;
        this.parentId = parentId;
        this.createdUser = createdUser;
        this.createdTime = createdTime;
        this.updatedUser = updatedUser;
        this.updatedTime = updatedTime;
        this.isDeleted = isDeleted;
    }

    public SysDicitionary() {
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

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser == null ? null : createdUser.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser == null ? null : updatedUser.trim();
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}