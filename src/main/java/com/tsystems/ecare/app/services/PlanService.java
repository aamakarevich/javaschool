package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Plan;

import java.util.List;

/**
 * @author Andrei Makarevich
 */
public interface PlanService {

    void deletePlan(Plan plan);

    Plan getPlan(Integer id);

    Plan saveNewPlan(Plan plan);

    List<Plan> getAllPlans();

    List<Plan> getPlansPaged(Integer pageNumber, Integer pageSize);

    Long getPlansCount();

    Plan updatePlan(Plan plan);
}
