package com.tsystems.ecare.app.dto;

import com.tsystems.ecare.app.model.Feature;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FeatureRichDTO extends FeatureDTO {

    private List<Long> blockers = new ArrayList<>();
    private List<Long> prerequisites = new ArrayList<>();

    public FeatureRichDTO(Long id, String title, String description, BigDecimal additionFee, BigDecimal monthlyFee, List<Long> prerequisites, List<Long> blockers) {
        super(id, title, description, additionFee, monthlyFee);
        this.prerequisites = prerequisites;
        this.blockers = blockers;
    }

    public static FeatureRichDTO mapFromFeatureEntity(Feature feature) {
        List<Long> blockers = new ArrayList<>();
        List<Long> prerequisites = new ArrayList<>();
        feature.getNeededFeatures().stream().mapToLong(Feature::getId).forEach(prerequisites::add);
        feature.getBlockers().stream().mapToLong(Feature::getId).forEach(blockers::add);
        return new FeatureRichDTO(feature.getId(), feature.getTitle(), feature.getDescription(),
                feature.getAdditionFee(), feature.getMonthlyFee(), prerequisites, blockers);
    }

    public List<Long> getBlockers() {
        return blockers;
    }

    public void setBlockers(List<Long> blockers) {
        this.blockers = blockers;
    }

    public List<Long> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<Long> prerequisites) {
        this.prerequisites = prerequisites;
    }
}
