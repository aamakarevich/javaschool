package com.tsystems.ecare.app.dao.impl;

import com.tsystems.ecare.app.dao.PlanDao;
import com.tsystems.ecare.app.model.Plan;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Andrei Makarevich
 */
@Repository
public class PlanDaoImpl extends GenericDaoImpl<Plan, Integer> implements PlanDao {

    @PersistenceContext
    EntityManager em;

}
