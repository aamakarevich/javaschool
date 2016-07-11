package com.tsystems.ecare.app.dto;

import com.tsystems.ecare.app.model.Plan;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JSON serializable DTO containing Plan data
 */
public class PlanDTO {

    private Long id;

    private String title;
    private String description;
    private BigDecimal monthlyFee;

    public PlanDTO() {}

    public PlanDTO(Long id, String title, String description, BigDecimal monthlyFee) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.monthlyFee = monthlyFee;
    }

    public static PlanDTO mapFromPlanEntity(Plan plan) {
        return new PlanDTO(plan.getId(), plan.getTitle(), plan.getDescription(), plan.getMonthlyFee());
    }

    public static List<PlanDTO> mapFromPlansEntities(List<Plan> plans) {
        return plans.stream().map((plan) -> mapFromPlanEntity(plan)).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(BigDecimal monthlyFee) {
        this.monthlyFee = monthlyFee;
    }
}
