package com.tsystems.ecare.app.services.impl;

import com.tsystems.ecare.app.dao.FeatureDao;
import com.tsystems.ecare.app.dao.PlanDao;
import com.tsystems.ecare.app.model.Feature;
import com.tsystems.ecare.app.model.Plan;
import com.tsystems.ecare.app.model.SearchResult;
import com.tsystems.ecare.app.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.util.Assert.notNull;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanDao planDao;

    @Autowired
    private FeatureDao featureDao;

    @Override
    @Transactional
    public Plan savePlan(Long id, String title, String description, BigDecimal monthlyFee) {
        Plan plan = planDao.findById(Plan.class, id);
        if (plan == null) {
            plan = new Plan();
        }
        plan.setTitle(title);
        plan.setDescription(description);
        plan.setMonthlyFee(monthlyFee);
        return planDao.save(plan);
    }

    @Override
    @Transactional
    public Plan getPlan(Long id) {
        return planDao.findById(Plan.class, id);
    }

    @Override
    @Transactional
    public void deletePlan(Long id) {
        notNull(id, "idToDelete is mandatory");
        Plan plan = planDao.findById(Plan.class, id);
        if(!plan.getContracts().isEmpty()) {
            throw new IllegalArgumentException("forbidden to delete plan that is used by at least one contract.");
        }
        planDao.delete(plan);
    }

    @Override
    @Transactional(readOnly = true)
    public SearchResult<Plan> getAllPlans() {
        Long resultsCount = planDao.getTotalCount(Plan.class);
        List<Plan> plans = planDao.findAll(Plan.class);
        return new SearchResult<>(resultsCount, plans);
    }

    @Override
    @Transactional
    public Long getPlansCount() {
        return planDao.getTotalCount(Plan.class);
    }

    @Override
    @Transactional
    public void changeAllowedFeature(Long planId, Long featureId, Boolean available) {
        notNull(planId, "planId is mandatory");
        notNull(featureId, "featureId is mandatory");
        notNull(available, "available is mandatory");

        Plan plan = planDao.findById(Plan.class, planId);
        Feature feature = featureDao.findById(Feature.class, featureId);
        if (plan == null || feature == null) {
            throw new IllegalArgumentException("both plan and feature must exist to set available option");
        }

        if (available) {
            if (!plan.getAllowedFeatures().contains(feature)) {
                plan.getAllowedFeatures().add(feature);
            }
        } else {
            plan.getAllowedFeatures().remove(feature);
        }
        planDao.save(plan);
    }
}
