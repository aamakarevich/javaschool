package com.tsystems.ecare.app.dao.impl;

import com.tsystems.ecare.app.dao.FeatureDao;
import com.tsystems.ecare.app.model.Feature;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class FeatureRepository extends GenericRepository<Feature, Integer> implements FeatureDao {

    @Override
    public List<Feature> getListed(List<Integer> ids) {
        if(ids.size() == 0) return null;
        Query query = em.createQuery(
                "from " + Feature.class.getName() + " c where c.id in (:ids)")
                .setParameter("ids", ids);
        try {
            return query.getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
