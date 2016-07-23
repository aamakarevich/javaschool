package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.dto.PlanReportDTO;
import com.tsystems.ecare.app.model.Plan;
import com.tsystems.ecare.app.model.SearchResult;

import java.math.BigDecimal;

/**
 * Service operations interface for Plan entity.
 */
public interface PlanService {

    /**
     * Saves plan (new or not) to database.
     *
     * @param id id of plan
     * @param title title of plan
     * @param description description of plan
     * @param monthlyFee monthly fee for plan
     * @return entity with saved plan info
     */
    public Plan savePlan(Long id, String title, String description, BigDecimal monthlyFee);

    /**
     * Searches plan in database by id.
     *
     * @param id id of plan
     * @return entity with plan data
     */
    public Plan getPlan(Long id);

    /**
     * Searches for all plans in database.
     *
     * @return all plans data and total count
     */
    public SearchResult<Plan> getAllPlans();

    /**
     * Deletes plan from database.
     *
     * @param id id of plan to delete
     */
    public void deletePlan(Long id);

    /**
     * Changes allowed option status for plan.
     *
     * @param planId id of plan to change allowed option status
     * @param featureId id of option to change allowed status
     * @param allowed true if option must be allowed for plan
     */
    public void changeAllowedFeature(Long planId, Long featureId, Boolean allowed);

    /**
     * Calculates and returns statistics information about plan.
     *
     * @param planId id of plan to collect statistics for.
     *
     * @return DTO with plan statistics data
     */
    public PlanReportDTO getFeatureUsers(Long planId);
}
