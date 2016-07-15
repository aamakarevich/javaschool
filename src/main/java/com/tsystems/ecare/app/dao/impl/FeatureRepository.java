package com.tsystems.ecare.app.dao.impl;

import com.tsystems.ecare.app.dao.FeatureDao;
import com.tsystems.ecare.app.model.Feature;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring specific FeatureDao implementation.
 */
@Repository
public class FeatureRepository extends GenericRepository<Feature, Long> implements FeatureDao {

    Logger logger = Logger.getLogger(FeatureRepository.class);

    /**
     * Searches for roles with ids contained in passed list.
     *
     * @param ids id values to filter features
     *
     * @return list of features
     */
    @Override
    public List<Feature> getListed(List<Long> ids) {
        if(ids.isEmpty()) {
            return new ArrayList<>();
        }
        Query query = em.createQuery(
                "from " + Feature.class.getName() + " where id in (:ids)")
                .setParameter("ids", ids);
        try {
            return query.getResultList();
        } catch (NoResultException ex) {
            logger.info("0 features are found by ids", ex);
            return new ArrayList<>();
        }
    }
}
