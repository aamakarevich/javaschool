package com.tsystems.ecare.app.dto;

/**
 * JSON serializable DTO containing data about allowing option for plan.
 */
public class AllowFeatureDTO {

    private Long planId;
    private Long featureId;

    private Boolean available;

    public AllowFeatureDTO() {
        // empty constructor to instantiate object from JSON
    }

    public AllowFeatureDTO(Long planId, Long featureId, Boolean available) {
        this.planId = planId;
        this.featureId = featureId;
        this.available = available;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
