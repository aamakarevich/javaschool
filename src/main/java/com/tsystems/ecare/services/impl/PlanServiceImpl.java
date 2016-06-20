package com.tsystems.ecare.services.impl;

import com.tsystems.ecare.dao.PlanDao;
import com.tsystems.ecare.dao.impl.PlanDaoImpl;
import com.tsystems.ecare.entities.Customer;
import com.tsystems.ecare.entities.Plan;
import com.tsystems.ecare.services.PlanService;

import java.util.List;

/**
 * @author Andrei Makarevich
 */
public class PlanServiceImpl implements PlanService {

    private PlanDao planDao = new PlanDaoImpl();

    @Override
    public void deletePlan(Plan plan) {
        planDao.beginTransaction();
        planDao.delete(plan);
        planDao.commitTransaction();
    }

    @Override
    public Plan getPlan(Integer id) {
        return planDao.findById(Plan.class, id);
    }

    @Override
    public Plan saveNewPlan(Plan plan) {
        planDao.beginTransaction();
        plan = planDao.save(plan);
        planDao.commitTransaction();
        return plan;
    }

    @Override
    public List<Plan> getAllPlans() {
        return planDao.findAll(Plan.class);
    }

    @Override
    public List<Plan> getPlansPaged(Integer pageNumber, Integer pageSize) {
        return planDao.findAllPaged(Plan.class, pageNumber, pageSize);
    }

    @Override
    public Long getPlansCount() {
        return planDao.getTotalCount(Plan.class);
    }

    @Override
    public Plan updatePlan(Plan plan) {
        planDao.beginTransaction();
        plan = planDao.merge(plan);
        planDao.commitTransaction();
        return plan;
    }
}
