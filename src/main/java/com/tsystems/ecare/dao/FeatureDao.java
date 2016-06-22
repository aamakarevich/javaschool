package com.tsystems.ecare.dao;

import com.tsystems.ecare.entities.Feature;

import java.util.List;

/**
 * @author Andrei Makarevich
 */
public interface FeatureDao extends GenericDao<Feature, Integer> {

    List<Feature> getListed(List<Integer> ids);
}
