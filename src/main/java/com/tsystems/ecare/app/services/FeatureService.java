package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Feature;

import java.util.List;

/**
 * @author Andrei Makarevich
 */
public interface FeatureService {

    /**
     * Creates new feature.
     *
     * @param feature feature to create
     * @return
     */
    Feature saveNewFeature(Feature feature);

    /**
     * Retrieves feature by id
     *
     * @param id id of feature to get
     * @return feature with passed id
     */
    Feature getFeature(Integer id);

    /**
     * Updates feature data without changing dependencies.
     *
     * @param feature feature to update
     * @return
     */
    Feature updateFeature(Feature feature);

    /**
     * Releases all links using this feature and then deletes feature.
     *
     * @param feature feature to delete
     */
    void deleteFeature(Feature feature);

    /**
     * Retrieves all features.
     *
     * @return all features
     */
    List<Feature> getAllFeatures();

    /**
     * Retrieves features with ids contained in passed list.
     *
     * @param ids ids of features to retrieve
     * @return features with ids contained in passed list
     */
    List<Feature> getListedFeatures(List<Integer> ids);

    /**
     * Returns features available for plan with active features
     * in passed list resolving availability and all dependencies.
     *
     * @param ids active features
     * @param planId id of plan
     * @return available features
     */
    List<Feature> getAvailableFeatures(List<Integer> ids, Integer planId);

    void setBlock(Feature f1, Feature f2);

    void deleteBlock(Feature f1, Feature f2);

    void setDependency(Feature dependent, Feature dependence);

    void deleteDependency(Feature dependent, Feature dependence);
}
