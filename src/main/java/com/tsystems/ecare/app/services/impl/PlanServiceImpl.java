package com.tsystems.ecare.app.services.impl;

import com.tsystems.ecare.app.dao.PlanDao;
import com.tsystems.ecare.app.dao.impl.PlanDaoImpl;
import com.tsystems.ecare.app.model.Plan;
import com.tsystems.ecare.app.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Andrei Makarevich
 */
@Service
public class PlanServiceImpl {

    @Autowired
    private PlanDaoImpl planDao;

    @Transactional
    public void deletePlan(Plan plan) {
        planDao.delete(plan);
    }

    @Transactional
    public Plan getPlan(Integer id) {
        return planDao.findById(Plan.class, id);
    }

    @Transactional
    public Plan saveNewPlan(Plan plan) {
        plan = planDao.save(plan);
        return plan;
    }

    @Transactional
    public List<Plan> getAllPlans() {
        return planDao.findAll(Plan.class);
    }

    @Transactional
    public List<Plan> getPlansPaged(Integer pageNumber, Integer pageSize) {
        return planDao.findAllPaged(Plan.class, pageNumber, pageSize);
    }

    @Transactional
    public Long getPlansCount() {
        return planDao.getTotalCount(Plan.class);
    }

    @Transactional
    public Plan updatePlan(Plan plan) {
        plan = planDao.merge(plan);
        return plan;
    }
}
