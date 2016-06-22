package com.tsystems.ecare.controllers;

import com.tsystems.ecare.entities.Feature;
import com.tsystems.ecare.services.FeatureService;
import com.tsystems.ecare.services.impl.FeatureServiceImpl;
import com.tsystems.ecare.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrei Makarevich
 */
public class FeatureServlet extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer idToDelete = Integer.parseInt(req.getPathInfo().replace("/", ""));
        FeatureService service = new FeatureServiceImpl();
        Feature feature = service.getFeature(idToDelete);
        if (feature != null) {
            if (feature.getContracts().size() == 0) {
                service.deleteFeature(feature);
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            }
        }
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("blockers") != null) {
            Feature feature = new FeatureServiceImpl().getFeature(Integer.parseInt(req.getParameter("blockers")));
            List<Integer> ids = new ArrayList<>();
            feature.getBlockers().forEach(b -> ids.add(b.getId()));
            JsonUtil.writeObjectToJson(resp, ids);
            return;
        }

        if (req.getParameter("needs") != null) {
            Feature feature = new FeatureServiceImpl().getFeature(Integer.parseInt(req.getParameter("needs")));
            List<Integer> ids = new ArrayList<>();
            feature.getNeededFeatures().forEach(b -> ids.add(b.getId()));
            JsonUtil.writeObjectToJson(resp, ids);
            return;
        }

        if (!req.getPathInfo().equals("/")) {
            Integer idToGet = Integer.parseInt(req.getPathInfo().replace("/", ""));
            Feature feature = new FeatureServiceImpl().getFeature(idToGet);
            JsonUtil.writeObjectToJson(resp, feature);
            return;
        }

        JsonUtil.writeObjectToJson(resp, new FeatureServiceImpl().getAllFeatures());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Feature feature = JsonUtil.getObjectFromJson(req, Feature.class);
        new FeatureServiceImpl().saveNewFeature(feature);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("block") != null && req.getParameter("option") != null) {
            Feature f1 = new FeatureServiceImpl().getFeature(Integer.parseInt(req.getParameter("option")));
            Feature f2 = new FeatureServiceImpl().getFeature(Integer.parseInt(req.getParameter("block")));
            new FeatureServiceImpl().setBlock(f1, f2);
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (req.getParameter("unblock") != null && req.getParameter("option") != null) {
            Feature f1 = new FeatureServiceImpl().getFeature(Integer.parseInt(req.getParameter("option")));
            Feature f2 = new FeatureServiceImpl().getFeature(Integer.parseInt(req.getParameter("unblock")));
            new FeatureServiceImpl().deleteBlock(f1, f2);
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (req.getParameter("link") != null && req.getParameter("option") != null) {
            Feature f1 = new FeatureServiceImpl().getFeature(Integer.parseInt(req.getParameter("option")));
            Feature f2 = new FeatureServiceImpl().getFeature(Integer.parseInt(req.getParameter("link")));
            new FeatureServiceImpl().setDependency(f1, f2);
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (req.getParameter("unlink") != null && req.getParameter("option") != null) {
            Feature f1 = new FeatureServiceImpl().getFeature(Integer.parseInt(req.getParameter("option")));
            Feature f2 = new FeatureServiceImpl().getFeature(Integer.parseInt(req.getParameter("unlink")));
            new FeatureServiceImpl().deleteDependency(f1, f2);
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        Feature feature = JsonUtil.getObjectFromJson(req, Feature.class);
        new FeatureServiceImpl().updateFeature(feature);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
