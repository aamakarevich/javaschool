package com.tsystems.ecare.app.controllers;

import com.tsystems.ecare.app.dto.AvailableFeaturesDTO;
import com.tsystems.ecare.app.dto.FeatureDTO;
import com.tsystems.ecare.app.model.Feature;
import com.tsystems.ecare.app.model.SearchResult;
import com.tsystems.ecare.app.services.FeatureService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Provides REST-service for retreiving available features for contracts.
 */
@Controller
@RequestMapping("/available")
public class AvailableFeatureController extends AbstractController {

    @Autowired
    private FeatureService featureService;

    public AvailableFeatureController() {
        super(Logger.getLogger(AvailableFeatureController.class));
    }

    /**
     * Returns data about what features are available for contract.
     * It gives count on plan's available features and currently enabled features.
     *
     * @param listedIds currently enabled features ids
     * @param planId    id of plan applied to contract
     * @return DTO with available features
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public AvailableFeaturesDTO getAvailableFeatures(
            @RequestParam(value = "listedIds", required = false) String listedIds,
            @RequestParam(value = "planId") Long planId) {
        SearchResult<Feature> allFeaturesResult = featureService.getAllFeatures();

        return new AvailableFeaturesDTO(
                allFeaturesResult.getResultsCount(),
                FeatureDTO.mapFromFeaturesEntities(allFeaturesResult.getResult()),
                featureService.getAvailableFeatures(listedIds, planId),
                FeatureDTO.mapFromFeaturesEntities(featureService.getListedFeatures(listedIds)));
    }
}
