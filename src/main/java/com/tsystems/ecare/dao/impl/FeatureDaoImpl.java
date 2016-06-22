package com.tsystems.ecare.dao.impl;

import com.tsystems.ecare.dao.FeatureDao;
import com.tsystems.ecare.entities.Customer;
import com.tsystems.ecare.entities.Feature;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

/**
 * @author Andrei Makarevich
 */
public class FeatureDaoImpl extends GenericDaoImpl<Feature, Integer> implements FeatureDao {

    @Override
    public List<Feature> getListed(List<Integer> ids) {
        if(ids.size() == 0) return null;
        Query query = entityManager.createQuery(
                "from " + Feature.class.getName() + " c where c.id in (:ids)")
                .setParameter("ids", ids);
        try {
            return query.getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
