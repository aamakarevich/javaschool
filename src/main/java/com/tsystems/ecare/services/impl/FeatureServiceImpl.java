package com.tsystems.ecare.services.impl;

import com.tsystems.ecare.dao.FeatureDao;
import com.tsystems.ecare.dao.impl.FeatureDaoImpl;
import com.tsystems.ecare.entities.Feature;
import com.tsystems.ecare.services.FeatureService;

import java.util.List;

/**
 * @author Andrei Makarevich
 */
public class FeatureServiceImpl implements FeatureService {

    FeatureDao featureDao = new FeatureDaoImpl();

    @Override
    public void deleteFeature(Feature feature) {

        try {
            featureDao.beginTransaction();
            for (Feature f : feature.getBlockedFeatures()) {
                f.getBlockers().remove(feature);
                featureDao.merge(f);
            }
            for (Feature f : feature.getBlockers()) {
                f.getBlockedFeatures().remove(feature);
                featureDao.merge(f);
            }
            for (Feature f : feature.getNeededFeatures()) {
                f.getDependentFeatures().remove(feature);
                featureDao.merge(f);
            }
            for (Feature f : feature.getDependentFeatures()) {
                f.getNeededFeatures().remove(feature);
                featureDao.merge(f);
            }
            feature = featureDao.merge(feature);
            featureDao.delete(feature);

            featureDao.commitTransaction();
        } catch (Exception ex) {

            featureDao.rollbackTransaction();
        }
    }

    @Override
    public List<Feature> getAllFeatures() {
        return featureDao.findAll(Feature.class);
    }

    @Override
    public Feature getFeature(Integer id) {
        return featureDao.findById(Feature.class, id);
    }

    @Override
    public Feature saveNewFeature(Feature feature) {
        featureDao.beginTransaction();
        feature = featureDao.save(feature);
        featureDao.commitTransaction();
        return feature;
    }

    @Override
    public Feature updateFeature(Feature feature) {
        featureDao.beginTransaction();
        feature = featureDao.merge(feature);
        featureDao.commitTransaction();
        return feature;
    }
}
