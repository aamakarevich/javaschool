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

    /**
     * Searches option in database by id.
     *
     * @param id id of option
     *
     * @return entity with option data
     */
    public Feature getFeature(Long id);

    /**
     * Searches for all options in database.
     *
     * @return all options data and total count
     */
    public SearchResult<Feature> getAllFeatures();

    /**
     * Searches for options with ids contained in passed list.
     *
     * @param ids id values to filter options
     *
     * @return options filtered by id
     */
    public List<Feature> getListedFeatures(String ids);

    /**
     * Resolves options available for contract with some plan and some already activated options.
     *
     * @param ids string with comma separated ids of already active options
     * @param planId id of plan applied to contract
     *
     * @return available options ids list
     */
    public List<Long> getAvailableFeatures(String ids, Long planId);

    /**
     * Deletes option by id.
     *
     * @param deleteFeatureId id of option to delete
     */
    public void deleteFeature(Long deleteFeatureId);

    /**
     * Updates link between two options.
     *
     * @param feature1Id id of the first option
     * @param feature2Id id of the second option
     * @param block true if block link bust be updated, false if - dependency link
     * @param linked true if link must be set, false - if deleted
     */
    public void changeLinkedFeature(Long feature1Id, Long feature2Id, Boolean block, Boolean linked);
}
