package cn.waynechu.renting.dal.common.entity;

import java.io.Serializable;
import java.util.Date;

public class ShopConfigLog implements Serializable {
    private Long id;

    private Long shopId;

    private String changeItem;

    private Integer beforeValue;

    private Integer afterValue;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private Boolean isDelete;

    public ShopConfigLog(Long id, Long shopId, String changeItem, Integer beforeValue, Integer afterValue, String createUser, Date createTime, String updateUser, Date updateTime, Boolean isDelete) {
        this.id = id;
        this.shopId = shopId;
        this.changeItem = changeItem;
        this.beforeValue = beforeValue;
        this.afterValue = afterValue;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
    }

    public ShopConfigLog() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getChangeItem() {
        return changeItem;
    }

    public void setChangeItem(String changeItem) {
        this.changeItem = changeItem == null ? null : changeItem.trim();
    }

    public Integer getBeforeValue() {
        return beforeValue;
    }

    public void setBeforeValue(Integer beforeValue) {
        this.beforeValue = beforeValue;
    }

    public Integer getAfterValue() {
        return afterValue;
    }

    public void setAfterValue(Integer afterValue) {
        this.afterValue = afterValue;
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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}