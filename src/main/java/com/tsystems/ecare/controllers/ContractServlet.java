package com.tsystems.ecare.controllers;

import com.tsystems.ecare.entities.Contract;
import com.tsystems.ecare.entities.Feature;
import com.tsystems.ecare.services.impl.ContractServiceImpl;
import com.tsystems.ecare.services.impl.CustomerServiceImpl;
import com.tsystems.ecare.services.impl.FeatureServiceImpl;
import com.tsystems.ecare.services.impl.PlanServiceImpl;
import com.tsystems.ecare.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andrei Makarevich
 */
public class ContractServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getPathInfo().equals("/")) {
            Integer idToGet = Integer.parseInt(req.getPathInfo().replace("/", ""));
            Contract contract = new ContractServiceImpl().getContract(idToGet);
            JsonUtil.writeObjectToJson(resp, contract);
            return;
        }

        if (req.getParameter("create") != null) {
            Integer customerId = Integer.parseInt(req.getParameter("cid"));
            Integer planId = Integer.parseInt(req.getParameter("pid"));
            String activeFeatures = req.getParameter("opts");
            String number = req.getParameter("create");

            List<Integer> ids = new ArrayList<>();
            if (activeFeatures.length() > 0) {
                Arrays.stream(activeFeatures.split(","))
                        .mapToInt(Integer::parseInt)
                        .forEach(ids::add);
            }

            List<Feature> features = new FeatureServiceImpl().getListedFeatures(ids);

            Contract contract = new Contract();
            contract.setNumber(number);
            contract.setCustomer(new CustomerServiceImpl().getCustomer(customerId));
            contract.setPlan(new PlanServiceImpl().getPlan(planId));
            contract.setActiveFeatures(features);

            new ContractServiceImpl().saveNewContract(contract);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Contract contract = JsonUtil.getObjectFromJson(req, Contract.class);
        new ContractServiceImpl().updateContract(contract);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
