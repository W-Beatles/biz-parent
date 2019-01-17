package cn.waynechu.renting.dal.renting.entity;

public class HouseDetail {
    private Long id;

    private Long houseId;

    private String description;

    private String layoutDesc;

    public HouseDetail(Long id, Long houseId, String description, String layoutDesc) {
        this.id = id;
        this.houseId = houseId;
        this.description = description;
        this.layoutDesc = layoutDesc;
    }

    public HouseDetail() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getLayoutDesc() {
        return layoutDesc;
    }

    public void setLayoutDesc(String layoutDesc) {
        this.layoutDesc = layoutDesc == null ? null : layoutDesc.trim();
    }
}