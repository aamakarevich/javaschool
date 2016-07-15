package com.tsystems.ecare.app.dao;

import com.tsystems.ecare.app.model.Feature;

import java.util.List;

/**
 * Data access object interface for Feature entity.
 */
public interface FeatureDao extends GenericDao<Feature, Long> {

    /**
     * Returns only features with ids contained in passed list.
     *
     * @param ids id values to filter features
     *
     * @return features filtered by id
     */
    List<Feature> getListed(List<Long> ids);
}
