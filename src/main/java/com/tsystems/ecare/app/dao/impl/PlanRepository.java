package com.tsystems.ecare.app.dao.impl;

import com.tsystems.ecare.app.dao.PlanDao;
import com.tsystems.ecare.app.model.Plan;
import org.springframework.stereotype.Repository;

@Repository
public class PlanRepository extends GenericRepository<Plan, Integer> implements PlanDao {}
