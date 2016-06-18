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

/**
 * @author Andrei Makarevich
 */
public class FeatureServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonUtil.writeObjectToJson(resp, new FeatureServiceImpl().getAllFeatures());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer idToDelete = Integer.parseInt(req.getPathInfo().replace("/", ""));
        FeatureService service = new FeatureServiceImpl();
        Feature feature = service.getFeature(idToDelete);
        if (feature != null) {
            if (feature.getContracts().size() == 0) {
                service.deleteFeature(feature);
                resp.getWriter().write("ok");
                return;
            }
        }
        resp.getWriter().write("fail");
    }
}
