package com.tsystems.ecare.services;

import com.tsystems.ecare.entities.Feature;

import java.util.List;

/**
 * @author Andrei Makarevich
 */
public interface FeatureService {

    void setBlock(Feature f1, Feature f2);

    void deleteBlock(Feature f1, Feature f2);

    void setDependency(Feature dependent, Feature dependence);

    void deleteDependency(Feature dependent, Feature dependence);

    void deleteFeature(Feature feature);

    List<Feature> getAllFeatures();

    Feature getFeature(Integer id);

    Feature saveNewFeature(Feature feature);

    Feature updateFeature(Feature feature);
}
