package com.tsystems.ecare.app.dao.impl;

import com.tsystems.ecare.app.dao.PlanDao;
import com.tsystems.ecare.app.model.Plan;
import org.springframework.stereotype.Repository;

/**
 * Spring specific PlanDao implementation.
 */
@Repository
public class PlanRepository extends GenericRepository<Plan, Long> implements PlanDao {}
