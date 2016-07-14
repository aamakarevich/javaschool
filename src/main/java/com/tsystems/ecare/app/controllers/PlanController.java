package com.tsystems.ecare.app.controllers;

import com.tsystems.ecare.app.dto.PlanDTO;
import com.tsystems.ecare.app.dto.PlanRichDTO;
import com.tsystems.ecare.app.dto.PlansDTO;
import com.tsystems.ecare.app.model.Plan;
import com.tsystems.ecare.app.model.SearchResult;
import com.tsystems.ecare.app.services.PlanService;
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
 * Provides REST-full CRUD for Plan entity.
 */
@Controller
@RequestMapping("plan")
public class PlanController extends AbstractController {

    @Autowired
    private PlanService planService;

    public PlanController() {
        super(Logger.getLogger(PlanController.class));
    }

    /**
     * Creates new plan.
     *
     * @param plan DTO with data to create new plan
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void addPlan(@RequestBody PlanDTO plan) {
        planService.savePlan(null, plan.getTitle(), plan.getDescription(), plan.getMonthlyFee());
    }

    /**
     * Returns all plans.
     *
     * @return DTO with all existing plans data
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public PlansDTO getAllPlans() {
        SearchResult<Plan> result = planService.getAllPlans();
        return new PlansDTO(result.getResultsCount(), PlanDTO.mapFromPlansEntities(result.getResult()));
    }

    /**
     * Returns single plan.
     *
     * @param id id of plan to return
     * @return DTO with single plan data
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PlanRichDTO getPlanInfoById(@PathVariable Long id) {
        return PlanRichDTO.mapFromPlanEntity(planService.getPlan(id));
    }

    /**
     * Updates existing plan.
     *
     * @param plan DTO with plan data to update
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT)
    public void updatePlan(@RequestBody PlanDTO plan) {
        planService.savePlan(plan.getId(), plan.getTitle(), plan.getDescription(), plan.getMonthlyFee());
    }

    /**
     * Deletes plan.
     *
     * @param id id of plan to delete
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
    }
}
