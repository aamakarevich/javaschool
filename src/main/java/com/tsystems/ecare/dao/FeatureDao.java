package com.tsystems.ecare.dao;

import com.tsystems.ecare.entities.Feature;

import java.util.List;

/**
 * @author Andrei Makarevich
 */
public interface FeatureDao extends GenericDao<Feature, Integer> {

    /**
     * Returns only features with ids contained in passed list.
     *
     * @param ids id values to filter features
     * @return features filtered by id
     */
    List<Feature> getListed(List<Integer> ids);
}
