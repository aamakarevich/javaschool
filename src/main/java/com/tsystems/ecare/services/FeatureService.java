package com.tsystems.ecare.services;

import com.tsystems.ecare.entities.Feature;

import java.util.List;

/**
 * @author Andrei Makarevich
 */
public interface FeatureService {

    void deleteFeature(Feature feature);

    List<Feature> getAllFeatures();

    Feature getFeature(Integer id);

    Feature saveNewFeature(Feature feature);

    Feature udpateFeature(Feature feature);
}
