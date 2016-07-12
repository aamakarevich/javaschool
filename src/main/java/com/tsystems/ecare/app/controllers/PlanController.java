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

import javax.servlet.ServletException;
import java.io.IOException;

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
     * Create
     * /plan POST creates new plan
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    protected void addPlan(@RequestBody PlanDTO plan) {
        planService.savePlan(0L, plan.getTitle(), plan.getDescription(), plan.getMonthlyFee());
    }

    /**
     * Read
     * /rest/plan GET returns all plans
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public PlansDTO getAllPlans() {
        SearchResult<Plan> result = planService.getAllPlans();
        return new PlansDTO(result.getResultsCount(), PlanDTO.mapFromPlansEntities(result.getResult()));
    }

    /**
     * Read
     * /plan/ID GET returns plan with id = ID
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PlanRichDTO getPlanInfoById(@PathVariable Long id) {
        return PlanRichDTO.mapFromPlanEntity(planService.getPlan(id));
    }

    /**
     * Update
     * /plan PUT updates plan
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT)
    protected void updatePlan(@RequestBody PlanDTO plan) {
        planService.savePlan(plan.getId(), plan.getTitle(), plan.getDescription(), plan.getMonthlyFee());
    }

    /**
     * Delete
     * /plan/ID DELETE deletes plan with id = ID
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    protected void deletePlan(@PathVariable Long id) throws ServletException, IOException {
        planService.deletePlan(id);
    }
}
