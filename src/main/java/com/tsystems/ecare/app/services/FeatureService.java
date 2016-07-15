package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Feature;
import com.tsystems.ecare.app.model.SearchResult;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service operations interface for Feature entity.
 */
public interface FeatureService {

    /**
     * Saves option (new or not) to database.
     *
     * @param id id of option
     * @param title title of option
     * @param description description of option
     * @param additionFee additionFee for applying option
     * @param monthlyFee monthlyFee for using option
     *
     * @return saved option entity
     */
    public Feature saveFeature(Long id, String title, String description, BigDecimal additionFee, BigDecimal monthlyFee);

    public Feature getFeature(Long id);

    public SearchResult<Feature> getAllFeatures();

    public List<Feature> getListedFeatures(String ids);

    public List<Long> getAvailableFeatures(String ids, Long planId);

    public void deleteFeature(Long id);

    public void changeLinkedFeature(Long feature1Id, Long feature2Id, Boolean block, Boolean linked);
}
