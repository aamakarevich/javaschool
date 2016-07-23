package com.tsystems.ecare.app.controllers;

import com.tsystems.ecare.app.dto.AllowFeatureDTO;
import com.tsystems.ecare.app.services.PlanService;
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
 * Provides REST-service for changing allowed features for plans.
 */
@Controller
@RequestMapping("/allow")
public class AllowedFeatureController extends AbstractController {

    @Autowired
    private PlanService planService;

    public AllowedFeatureController() {
        super(Logger.getLogger(AllowedFeatureController.class));
    }

    /**
     * Changes allowed feature of plan.
     *
     * @param allowFeature DTO with allowed feature change data
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT)
    protected void changeAllowedFeature(@RequestBody AllowFeatureDTO allowFeature) {
        planService.changeAllowedFeature(allowFeature.getPlanId(), allowFeature.getFeatureId(), allowFeature.getAvailable());
    }
}
