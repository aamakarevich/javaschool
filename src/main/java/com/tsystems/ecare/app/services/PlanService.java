package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.dao.impl.PlanRepository;
import com.tsystems.ecare.app.model.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlanService {

    @Autowired
    private PlanRepository repository;

    @Transactional
    public Plan savePlan(Plan plan) {
        return repository.save(plan);
    }

    @Transactional
    public Plan getPlan(Integer id) {
        return repository.findById(Plan.class, id);
    }

    @Transactional
    public void deletePlan(Plan plan) {
        repository.delete(plan);
    }

    @Transactional
    public List<Plan> getAllPlans() {
        return repository.findAll(Plan.class);
    }

    @Transactional
    public List<Plan> getPlansPaged(Integer pageNumber, Integer pageSize) {
        return repository.findAllPaged(Plan.class, pageNumber, pageSize);
    }

    @Transactional
    public Long getPlansCount() {
        return repository.getTotalCount(Plan.class);
    }
}
