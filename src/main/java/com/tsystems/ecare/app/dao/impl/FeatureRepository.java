package com.tsystems.ecare.app.dao.impl;

import com.tsystems.ecare.app.dao.FeatureDao;
import com.tsystems.ecare.app.model.Feature;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FeatureRepository extends GenericRepository<Feature, Long> implements FeatureDao {

    Logger logger = Logger.getLogger(FeatureRepository.class);

    @Override
    public List<Feature> getListed(List<Long> ids) {
        if(ids.isEmpty()) {
            return new ArrayList<>();
        }
        Query query = em.createQuery(
                "from " + Feature.class.getName() + " c where c.id in (:ids)")
                .setParameter("ids", ids);
        try {
            return query.getResultList();
        } catch (NoResultException ex) {
            logger.info("0 features are found by ids", ex);
            return new ArrayList<>();
        }
    }
}
