package com.tsystems.ecare.app.dto;

import com.tsystems.ecare.app.model.Plan;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JSON serializable DTO containing Plan data.
 */
public class PlanDTO {

    private Long id;

    private String title;
    private String description;
    private BigDecimal monthlyFee;

    public PlanDTO() {
        // empty constructor to instantiate object from JSON
    }

    public PlanDTO(Long id, String title, String description, BigDecimal monthlyFee) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.monthlyFee = monthlyFee;
    }

    /**
     * Maps Plan entity to PlanDTO object.
     *
     * @param plan object to map from
     *
     * @return maped DTO
     */
    public static PlanDTO mapFromPlanEntity(Plan plan) {
        return new PlanDTO(plan.getId(), plan.getTitle(), plan.getDescription(), plan.getMonthlyFee());
    }

    /**
     * Maps list of Plan entities to list of PlanDTO objects.
     *
     * @param plans list to map from
     *
     * @return list of maped DTO
     */
    public static List<PlanDTO> mapFromPlansEntities(List<Plan> plans) {
        return plans.stream().map(PlanDTO::mapFromPlanEntity).collect(Collectors.toList());
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
