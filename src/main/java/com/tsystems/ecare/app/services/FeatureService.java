package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Feature;
import com.tsystems.ecare.app.model.SearchResult;

import java.math.BigDecimal;
import java.util.List;

public interface FeatureService {

    public Feature saveFeature(Long id, String title, String description, BigDecimal additionFee, BigDecimal monthlyFee);

    public Feature getFeature(Long id);

    public void deleteFeature(Long id);

    public SearchResult<Feature> getAllFeatures();

    public List<Feature> getListedFeatures(List<Long> ids);

    public List<Feature> getAvailableFeatures(List<Long> ids, Integer planId);

    public void changeLinkedFeature(Long feature1Id, Long feature2Id, Boolean block, Boolean linked);
}
