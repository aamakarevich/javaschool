package com.tsystems.ecare.controllers;

import com.tsystems.ecare.entities.Feature;
import com.tsystems.ecare.entities.Plan;
import com.tsystems.ecare.services.PlanService;
import com.tsystems.ecare.services.impl.FeatureServiceImpl;
import com.tsystems.ecare.services.impl.PlanServiceImpl;
import com.tsystems.ecare.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Andrei Makarevich
 */
public class PlanServlet extends HttpServlet {

    /* REST create */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Plan plan = JsonUtil.getObjectFromJson(req, Plan.class);
        new PlanServiceImpl().saveNewPlan(plan);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    /* REST read */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!req.getPathInfo().equals("/")) {
            Integer idToGet = Integer.parseInt(req.getPathInfo().replace("/", ""));
            Plan plan = new PlanServiceImpl().getPlan(idToGet);
            JsonUtil.writeObjectToJson(resp, plan);
            return;
        }

        JsonUtil.writeObjectToJson(resp, new PlanServiceImpl().getAllPlans());
    }

    /* REST update */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("link") != null && req.getParameter("to") != null) {
            Plan plan = new PlanServiceImpl().getPlan(Integer.parseInt(req.getParameter("to")));
            Feature feature = new FeatureServiceImpl().getFeature(Integer.parseInt(req.getParameter("link")));
            if(!plan.getAllowedFeatures().contains(feature)) {
                plan.getAllowedFeatures().add(feature);
                new PlanServiceImpl().updatePlan(plan);
            }
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (req.getParameter("unlink") != null && req.getParameter("from") != null) {
            Plan plan = new PlanServiceImpl().getPlan(Integer.parseInt(req.getParameter("from")));
            Integer featureToUnlinkId = Integer.parseInt(req.getParameter("unlink"));
            for (Feature feature : plan.getAllowedFeatures()) {
                if (feature.getId().equals(featureToUnlinkId)) {
                    plan.getAllowedFeatures().remove(feature);
                    new PlanServiceImpl().updatePlan(plan);
                    break;
                }
            }
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        Plan gotPlan = JsonUtil.getObjectFromJson(req, Plan.class);

        Plan plan = new PlanServiceImpl().getPlan(gotPlan.getId());
        plan.setTitle(gotPlan.getTitle());
        plan.setDescription(gotPlan.getDescription());
        plan.setMonthlyFee(gotPlan.getMonthlyFee());

        new PlanServiceImpl().updatePlan(plan);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    /* REST delete */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer idToDelete = Integer.parseInt(req.getPathInfo().replace("/", ""));
        PlanService service = new PlanServiceImpl();
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
