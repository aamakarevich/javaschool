package com.tsystems.ecare.app.controllers;

import com.tsystems.ecare.app.dto.PlanReportDTO;
import com.tsystems.ecare.app.services.PlanService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Provides REST-service for retreiving statistics information.
 */
@Controller
@RequestMapping("/report")
public class ReportsController extends AbstractController {

    @Autowired
    private PlanService planService;

    public ReportsController() {
        super(Logger.getLogger(ReportsController.class));
    }

    /**
     * Returns statistics information about plan.
     *
     * @param id id of plan to collect statistics for.
     *
     * @return DTO with plan statistics data
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PlanReportDTO getPlanInfoById(@PathVariable Long id) {
        return planService.getFeatureUsers(id);
    }
}
