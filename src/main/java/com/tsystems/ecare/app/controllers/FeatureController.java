package com.tsystems.ecare.app.controllers;

import com.tsystems.ecare.app.dto.FeatureDTO;
import com.tsystems.ecare.app.dto.FeatureRichDTO;
import com.tsystems.ecare.app.dto.FeaturesDTO;
import com.tsystems.ecare.app.model.Feature;
import com.tsystems.ecare.app.model.SearchResult;
import com.tsystems.ecare.app.services.FeatureService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Provides REST-full CRUD for Feature entity.
 */
@Controller
@RequestMapping("/option")
public class FeatureController extends AbstractController {

    @Autowired
    private FeatureService featureService;

    public FeatureController() {
        super(Logger.getLogger(FeatureController.class));
    }

    /**
     * Creates new option.
     *
     * @param feature DTO with option to create data
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    protected void addPlan(@RequestBody FeatureDTO feature) {
        featureService.saveFeature(
                null,
                feature.getTitle(),
                feature.getDescription(),
                feature.getAdditionFee(),
                feature.getMonthlyFee());
    }

    /**
     * Returns all options.
     *
     * @return DTO with all existing options data
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public FeaturesDTO getAllFeatures() {
        SearchResult<Feature> result = featureService.getAllFeatures();
        return new FeaturesDTO(result.getResultsCount(), FeatureDTO.mapFromFeaturesEntities(result.getResult()));
    }

    /**
     * Returns single option.
     *
     * @param id id of option to return
     * @return DTO with single option data
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    protected FeatureRichDTO getFeature(@PathVariable Long id) {
        return FeatureRichDTO.mapFromFeatureEntity(featureService.getFeature(id));
    }

    /**
     * Updates existing option.
     *
     * @param feature DTO with option to update data
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT)
    protected void updatePlan(@RequestBody FeatureDTO feature) {
        featureService.saveFeature(
                feature.getId(),
                feature.getTitle(),
                feature.getDescription(),
                feature.getAdditionFee(),
                feature.getMonthlyFee());
    }

    /**
     * Deletes option.
     *
     * @param id id of option to delete
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    protected void deleteFeature(@PathVariable Long id) {
        featureService.deleteFeature(id);
    }

}
