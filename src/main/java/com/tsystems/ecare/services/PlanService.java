package com.tsystems.ecare.services;

import com.tsystems.ecare.entities.Customer;
import com.tsystems.ecare.entities.Plan;

import java.util.List;

/**
 * @author Andrei Makarevich
 */
public interface PlanService {

    Plan getPlan(Integer id);

    Plan saveNewPlan(Plan plan);

    List<Plan> getAllPlans();

    List<Plan> getPlansPaged(Integer pageNumber, Integer pageSize);

    Long getPlansCount();
}
