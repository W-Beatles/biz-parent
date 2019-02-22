package cn.waynechu.renting.dal.common.entity;

import java.util.Date;

public class ShopConfigLog {
    private Long id;

    private Long shopId;

    private String changeItem;

    private Integer beforeValue;

    private Integer afterValue;

    private String createdUser;

    private Date createdTime;

    private String updatedUser;

    private Date updatedTime;

    private Boolean isDelete;

    public ShopConfigLog(Long id, Long shopId, String changeItem, Integer beforeValue, Integer afterValue, String createdUser, Date createdTime, String updatedUser, Date updatedTime, Boolean isDelete) {
        this.id = id;
        this.shopId = shopId;
        this.changeItem = changeItem;
        this.beforeValue = beforeValue;
        this.afterValue = afterValue;
        this.createdUser = createdUser;
        this.createdTime = createdTime;
        this.updatedUser = updatedUser;
        this.updatedTime = updatedTime;
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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}