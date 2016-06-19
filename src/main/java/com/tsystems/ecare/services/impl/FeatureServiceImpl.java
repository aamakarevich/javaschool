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
    }

    @Override
    public List<Feature> getAllFeatures() {
        return featureDao.findAll(Feature.class);
    }

    @Override
    public Feature getFeature(Integer id) {
        Feature feature = featureDao.findById(Feature.class, id);
        return feature;
    }

    @Override
    public Feature saveNewFeature(Feature feature) {
        feature = featureDao.save(feature);
        return feature;
    }

    @Override
    public Feature updateFeature(Feature feature) {
        feature = featureDao.merge(feature);
        return feature;
    }
}
