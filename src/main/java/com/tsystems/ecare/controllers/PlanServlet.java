package com.tsystems.ecare.controllers;

import com.tsystems.ecare.entities.Plan;
import com.tsystems.ecare.services.PlanService;
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Plan plan = JsonUtil.getObjectFromJson(req, Plan.class);
        new PlanServiceImpl().saveNewPlan(plan);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Plan plan = JsonUtil.getObjectFromJson(req, Plan.class);
        new PlanServiceImpl().updatePlan(plan);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
