package com.tsystems.ecare.app.dto;

/**
 * JSON serializable DTO containing data about changing link between two features.
 */
public class LinkFeatureDTO {

    private Long feature1Id;
    private Long feature2Id;

    private Boolean block;
    private Boolean linked;

    public LinkFeatureDTO() {
        // empty constructor to instantiate object from JSON
    }

    public LinkFeatureDTO(Long feature1Id, Long feature2Id, Boolean block, Boolean linked) {
        this.feature1Id = feature1Id;
        this.feature2Id = feature2Id;
        this.block = block;
        this.linked = linked;
    }

    public Long getFeature1Id() {
        return feature1Id;
    }

    public void setFeature1Id(Long feature1Id) {
        this.feature1Id = feature1Id;
    }

    public Long getFeature2Id() {
        return feature2Id;
    }

    public void setFeature2Id(Long feature2Id) {
        this.feature2Id = feature2Id;
    }

    public Boolean getBlock() {
        return block;
    }

    public void setBlock(Boolean block) {
        this.block = block;
    }

    public Boolean getLinked() {
        return linked;
    }

    public void setLinked(Boolean linked) {
        this.linked = linked;
    }
}
