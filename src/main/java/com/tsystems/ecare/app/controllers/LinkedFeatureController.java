package com.tsystems.ecare.app.controllers;

import com.tsystems.ecare.app.dto.LinkFeatureDTO;
import com.tsystems.ecare.app.services.FeatureService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Provides REST-service for changing links between features.
 */
@Controller
@RequestMapping("link")
public class LinkedFeatureController extends AbstractController {

    @Autowired
    private FeatureService featureService;

    public LinkedFeatureController() {
        super(Logger.getLogger(LinkedFeatureController.class));
    }

    /**
     * Changes link between options.
     *
     * @param linkFeature DTO with link creating/deleting data
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT)
    protected void changeAvailableFeature(@RequestBody LinkFeatureDTO linkFeature) {
        featureService.changeLinkedFeature(
                linkFeature.getFeature1Id(),
                linkFeature.getFeature2Id(),
                linkFeature.getBlock(),
                linkFeature.getLinked());
    }
}
