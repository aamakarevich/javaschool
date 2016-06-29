package com.tsystems.ecare.app.servlets;

import com.tsystems.ecare.app.model.Feature;
import com.tsystems.ecare.app.services.FeatureService;
import com.tsystems.ecare.app.services.impl.FeatureServiceImpl;
import com.tsystems.ecare.app.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides advanced REST-full CRUD for Feature entity.
 *
 * @author Andrei Makarevich
 */
public class FeatureServlet extends HttpServlet {

    /**
     * Create
     * /rest/option POST creates new feature
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Feature feature = JsonUtil.getObjectFromJson(req, Feature.class);
        new FeatureServiceImpl().saveNewFeature(feature);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    /**
     * Read
     * /rest/option                                     GET returns all features
     * /rest/option/ID                                  GET returns feature with id = ID
     * /rest/option/?blockers=ID                        GET returns blocking features for feature with id = ID
     * /rest/option/?needs=ID                           GET returns needed features for feature with id = ID
     * /rest/option/?listed=ID1,ID2,...,IDN             GET returns features with ids contained in listed IDs
     * /rest/option/?available=ID1,ID2,...,IDN&for=ID   GET returns features available for plan with id = ID
     *                                                      and active features listed in IDs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("blockers") != null) {
            Feature feature = new FeatureServiceImpl().getFeature(Integer.parseInt(req.getParameter("blockers")));
            List<Integer> ids = new ArrayList<>();
//            feature.getBlockers().forEach(b -> ids.add(b.getId()));
            JsonUtil.writeObjectToJson(resp, ids);
            return;
        }

        if (req.getParameter("needs") != null) {
            Feature feature = new FeatureServiceImpl().getFeature(Integer.parseInt(req.getParameter("needs")));
            List<Integer> ids = new ArrayList<>();
//            feature.getNeededFeatures().forEach(b -> ids.add(b.getId()));
            JsonUtil.writeObjectToJson(resp, ids);
            return;
        }

        if (req.getParameter("listed") != null) {
            List<Integer> ids = new ArrayList<>();
            if (req.getParameter("listed").length() > 0) {
                Arrays.stream(req.getParameter("listed").split(","))
                        .mapToInt(Integer::parseInt)
                        .forEach(ids::add);
            }
            JsonUtil.writeObjectToJson(resp, new FeatureServiceImpl().getListedFeatures(ids));
            return;
        }

        String available = req.getParameter("available");
        String planId = req.getParameter("for");
        if (available != null && planId != null) {
            List<Integer> ids = new ArrayList<>();
            if (available.length() > 0) {
                Arrays.stream(available.split(","))
                        .mapToInt(Integer::parseInt)
                        .forEach(ids::add);
            }
            List<Integer> availableIds = new ArrayList<>();
//            new FeatureServiceImpl()
//                    .getAvailableFeatures(ids, Integer.parseInt(planId))
//                    .forEach(b -> availableIds.add(b.getId()));
            JsonUtil.writeObjectToJson(resp, availableIds);
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

    /**
     * Update
     * /rest/option/                    PUT updates feature
     * /rest/option/?block=A&option=B   PUT makes features with ids A and B bi-directionaly blocked
     * /rest/option/?unblock=A&option=B PUT releases block between features with ids A and B
     * /rest/option/?link=A&option=B    PUT makes feature with id = B dependent from feature with id = A
     * /rest/option/?unlink=A&option=B  PUT releases dependency between features with ids A and B
     */
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

    /**
     * Delete
     * /rest/option/ID  DELETE deletes feature with id = ID
     */
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
}
