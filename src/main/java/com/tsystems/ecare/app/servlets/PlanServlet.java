package com.tsystems.ecare.app.servlets;

import com.tsystems.ecare.app.model.Feature;
import com.tsystems.ecare.app.model.Plan;
import com.tsystems.ecare.app.services.FeatureService;
import com.tsystems.ecare.app.services.PlanService;
import com.tsystems.ecare.app.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Provides advanced REST-full CRUD for Plan entity.
 *
 * @author Andrei Makarevich
 */
public class PlanServlet extends HttpServlet {

    /**
     * Create
     * /rest/plan   POST creates new plan
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Plan plan = JsonUtil.getObjectFromJson(req, Plan.class);
        new PlanService().savePlan(plan);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    /**
     * Read
     * /rest/plan       GET returns all plans
     * /rest/plan/ID    GET returns plan with id = ID
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!req.getPathInfo().equals("/")) {
            Integer idToGet = Integer.parseInt(req.getPathInfo().replace("/", ""));
            Plan plan = new PlanService().getPlan(idToGet);
            JsonUtil.writeObjectToJson(resp, plan);
            return;
        }

        JsonUtil.writeObjectToJson(resp, new PlanService().getAllPlans());
    }

    /**
     * Update
     * /rest/plan/                  PUT updates plan
     * /rest/plan/?link=A&to=B      PUT makes feature A available for plan B
     * /rest/plan/?unlink=A&from=B  PUT makes feature A unavailable for plan B
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("link") != null && req.getParameter("to") != null) {
            Plan plan = new PlanService().getPlan(Integer.parseInt(req.getParameter("to")));
            Feature feature = new FeatureService().getFeature(Integer.parseInt(req.getParameter("link")));
            if(!plan.getAllowedFeatures().contains(feature)) {
                plan.getAllowedFeatures().add(feature);
                new PlanService().savePlan(plan);
            }
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (req.getParameter("unlink") != null && req.getParameter("from") != null) {
            Plan plan = new PlanService().getPlan(Integer.parseInt(req.getParameter("from")));
            Integer featureToUnlinkId = Integer.parseInt(req.getParameter("unlink"));
            for (Feature feature : plan.getAllowedFeatures()) {
                if (feature.getId().equals(featureToUnlinkId)) {
                    plan.getAllowedFeatures().remove(feature);
                    new PlanService().savePlan(plan);
                    break;
                }
            }
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        Plan gotPlan = JsonUtil.getObjectFromJson(req, Plan.class);

        Plan plan = null;// new PlanServiceImpl().getPlan(gotPlan.getId());
        plan.setTitle(gotPlan.getTitle());
        plan.setDescription(gotPlan.getDescription());
        plan.setMonthlyFee(gotPlan.getMonthlyFee());

        new PlanService().savePlan(plan);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Delete
     * /rest/plan/ID    DELETE deletes plan with id = ID
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer idToDelete = Integer.parseInt(req.getPathInfo().replace("/", ""));
        PlanService service = null;//new PlanServiceImpl();
        Plan plan = service.getPlan(idToDelete);
        if (plan != null) {
            if (plan.getContracts().size() == 0) {
                service.deletePlan(plan);
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            }
        }
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}
