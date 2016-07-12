package com.tsystems.ecare.app.dto;

import java.util.List;

/**
 * JSON serializable DTO containing data concerning a plans search request.
 */
public class PlansDTO {

    private long count;
    private List<PlanDTO> plans;

    public PlansDTO(long count, List<PlanDTO> plans) {
        this.count = count;
        this.plans = plans;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<PlanDTO> getPlans() {
        return plans;
    }

    public void setPlans(List<PlanDTO> plans) {
        this.plans = plans;
    }
}
