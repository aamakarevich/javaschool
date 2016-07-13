package com.tsystems.ecare.app.dto;

import com.tsystems.ecare.app.model.Plan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PlanRichDTO extends PlanDTO {

    private List<FeatureDTO> allowedFeatures = new ArrayList<>();

    public PlanRichDTO(Long id, String title, String description, BigDecimal monthlyFee, List<FeatureDTO> allowedFeatures) {
        super(id, title, description, monthlyFee);
        this.allowedFeatures = allowedFeatures;
    }

    public static PlanRichDTO mapFromPlanEntity(Plan plan) {
        return new PlanRichDTO(plan.getId(), plan.getTitle(), plan.getDescription(), plan.getMonthlyFee(),
                FeatureDTO.mapFromFeaturesEntities(plan.getAllowedFeatures()));
    }

    public List<FeatureDTO> getAllowedFeatures() {
        return allowedFeatures;
    }

    public void setAllowedFeatures(List<FeatureDTO> allowedFeatures) {
        this.allowedFeatures = allowedFeatures;
    }
}