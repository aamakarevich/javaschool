package com.tsystems.ecare.app.services.impl;

import com.tsystems.ecare.app.dao.FeatureDao;
import com.tsystems.ecare.app.dao.PlanDao;
import com.tsystems.ecare.app.dto.FeatureDTO;
import com.tsystems.ecare.app.dto.PlanReportDTO;
import com.tsystems.ecare.app.model.Contract;
import com.tsystems.ecare.app.model.Feature;
import com.tsystems.ecare.app.model.Plan;
import com.tsystems.ecare.app.model.SearchResult;
import com.tsystems.ecare.app.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tsystems.ecare.app.utils.ValidationUtils.assertMaximumLength;
import static com.tsystems.ecare.app.utils.ValidationUtils.assertNotBlank;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

/**
 * Spring specific PlanService implementation.
 */
@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanDao planDao;

    @Autowired
    private FeatureDao featureDao;

    /**
     * Saves plan (new or not) to database.
     *
     * @param id id of plan
     * @param title title of plan
     * @param description description of plan
     * @param monthlyFee monthly fee for plan
     *
     * @return entity with saved plan info
    */
    @Override
    @Transactional
    public Plan savePlan(Long id, String title, String description, BigDecimal monthlyFee) {
        Plan plan;
        if (id == null) {
            plan = new Plan();
        } else {
            plan = planDao.findById(Plan.class, id);
            notNull(plan, "plan is not found by id");
        }
        assertNotBlank(title, "title must not be blank");
        assertMaximumLength(title, 40, "title must not be more than 40 characters length");
        assertNotBlank(description, "description must not be blank");
        assertMaximumLength(description, 1000, "description must not be more than 1000 characters length");
        notNull(monthlyFee, "monthlyFee is mandatory");
        isTrue(monthlyFee.compareTo(new BigDecimal(0)) != -1, "monthlyFee must not be negative");

        plan.setTitle(title);
        plan.setDescription(description);
        plan.setMonthlyFee(monthlyFee);
        return planDao.save(plan);
    }

    /**
     * Searches plan in database by id.
     *
     * @param id id of plan
     *
     * @return entity with plan data
     */
    @Override
    @Transactional
    public Plan getPlan(Long id) {
        notNull(id, "plan is mandatory");
        Plan plan = planDao.findById(Plan.class, id);
        notNull(plan, "plan is not found by id");
        return plan;
    }

    /**
     * Searches for all plans in database.
     *
     * @return all plans data and total count
     */
    @Override
    @Transactional(readOnly = true)
    public SearchResult<Plan> getAllPlans() {
        Long resultsCount = planDao.getTotalCount(Plan.class);
        List<Plan> plans = planDao.findAll(Plan.class);
        return new SearchResult<>(resultsCount, plans);
    }

    /**
     * Deletes plan from database if this plan is not used in any contract.
     *
     * @param id id of plan to delete
     */
    @Override
    @Transactional
    public void deletePlan(Long id) {
        notNull(id, "idToDelete is mandatory");
        Plan plan = planDao.findById(Plan.class, id);
        if(!plan.getContracts().isEmpty()) {
            throw new IllegalArgumentException("forbidden to delete plan that is used by at least one contract");
        }
        planDao.delete(plan);
    }

    /**
     * Changes allowed option status for plan.
     *
     * @param planId id of plan to change allowed option status
     * @param featureId id of option to change allowed status
     * @param allowed true if option must be allowed for plan
     */
    @Override
    @Transactional
    public void changeAllowedFeature(Long planId, Long featureId, Boolean allowed) {
        notNull(planId, "planId is mandatory");
        notNull(featureId, "featureId is mandatory");
        notNull(allowed, "available is mandatory");

        Plan plan = planDao.findById(Plan.class, planId);
        Feature feature = featureDao.findById(Feature.class, featureId);
        if (plan == null || feature == null) {
            throw new IllegalArgumentException("both plan and feature must exist to set available option");
        }

        if (allowed) {
            if (!plan.getAllowedFeatures().contains(feature)) {
                plan.getAllowedFeatures().add(feature);
            }
        } else {
            plan.getAllowedFeatures().remove(feature);
        }
        planDao.save(plan);
    }

    /**
     * Calculates and returns statistics information about plan.
     *
     * @param planId id of plan to collect statistics for.
     *
     * @return DTO with plan statistics data
     */
    @Override
    @Transactional
    public PlanReportDTO getFeatureUsers(Long planId) {
        notNull(planId, "planId is mandatory");

        Plan plan = planDao.findById(Plan.class, planId);
        PlanReportDTO planReportDTO =
                new PlanReportDTO(plan.getId(), plan.getTitle(), plan.getDescription(), plan.getMonthlyFee(),
                        FeatureDTO.mapFromFeaturesEntities(plan.getAllowedFeatures()));
        planReportDTO.setTotalContracts(plan.getContracts().size());
        Map<Long, Long> features = new HashMap<>();
        plan.getAllowedFeatures().forEach(f -> features.put(f.getId(), 0L));
        for (Contract contract : plan.getContracts()) {
            for (Feature feature : contract.getActiveFeatures()) {
                long featureId = feature.getId();
                if (features.containsKey(featureId)) {
                    features.put(featureId, features.get(featureId) + 1);
                }
            }
        }
        planReportDTO.setFeaturesUsers(features);
        return planReportDTO;
    }
}
