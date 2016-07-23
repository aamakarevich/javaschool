package com.tsystems.ecare.app.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * JSON serializable DTO containing Plan report data.
 */
public class PlanReportDTO extends PlanRichDTO {

    private long totalContracts;
    private Map<Long, Long> featuresUsers;

    public PlanReportDTO(Long id, String title, String description, BigDecimal monthlyFee, List<FeatureDTO> allowedFeatures) {
        super(id, title, description, monthlyFee, allowedFeatures);
    }

    public long getTotalContracts() {
        return totalContracts;
    }

    public void setTotalContracts(long totalContracts) {
        this.totalContracts = totalContracts;
    }

    public Map<Long, Long> getFeaturesUsers() {
        return featuresUsers;
    }

    public void setFeaturesUsers(Map<Long, Long> featuresUsers) {
        this.featuresUsers = featuresUsers;
    }
}
