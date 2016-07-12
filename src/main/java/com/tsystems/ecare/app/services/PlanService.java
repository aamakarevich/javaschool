package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Plan;
import com.tsystems.ecare.app.model.SearchResult;

import java.math.BigDecimal;
import java.util.List;

public interface PlanService {

    public Plan savePlan(Long id, String title, String description, BigDecimal monthlyFee);

    public Plan getPlan(Long id);

    public void deletePlan(Long id);

    public SearchResult<Plan> getAllPlans();

    public List<Plan> getPlansPaged(Integer pageNumber, Integer pageSize);

    public Long getPlansCount();

    public void changeAllowedFeature(Long planId, Long featureId, Boolean available);
}
