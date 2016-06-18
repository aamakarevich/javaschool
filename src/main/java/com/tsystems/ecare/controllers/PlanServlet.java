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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("count") != null) {
            JsonUtil.writeObjectToJson(resp, new PlanServiceImpl().getPlansCount());
            return;
        }

        if(req.getParameter("page") != null && req.getParameter("size") != null) {
            JsonUtil.writeObjectToJson(resp,
                    new PlanServiceImpl().getPlansPaged(
                            Integer.parseInt(req.getParameter("page")),
                            Integer.parseInt(req.getParameter("size"))));
            return;
        }

        JsonUtil.writeObjectToJson(resp, new PlanServiceImpl().getAllPlans());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Plan plan = JsonUtil.getObjectFromJson(req, Plan.class);

        PlanService planService = new PlanServiceImpl();
        planService.saveNewPlan(plan);

        resp.getWriter().write("ok");
    }
}
