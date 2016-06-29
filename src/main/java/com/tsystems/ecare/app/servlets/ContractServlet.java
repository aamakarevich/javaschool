package com.tsystems.ecare.app.servlets;

import com.tsystems.ecare.app.model.Contract;
import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.model.Feature;
import com.tsystems.ecare.app.services.ContractService;
import com.tsystems.ecare.app.services.CustomerService;
import com.tsystems.ecare.app.services.FeatureService;
import com.tsystems.ecare.app.services.PlanService;
import com.tsystems.ecare.app.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
            Contract contract = new ContractService().getContract(idToGet);
            JsonUtil.writeObjectToJson(resp, contract);
            return;
        }

        if (req.getParameter("number") != null) {
            JsonUtil.writeObjectToJson(resp, new ContractService().getContractByNumber(req.getParameter("number")));
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

            List<Feature> features = new FeatureService().getListedFeatures(ids);

            Contract contract = new Contract();
            contract.setNumber(number);
            contract.setCustomer(new CustomerService().getCustomer(customerId));
            contract.setPlan(new PlanService().getPlan(planId));
            contract.setActiveFeatures(features);

            new ContractService().saveNewContract(contract);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(true);
        Customer customer = new CustomerService().getCustomerByEmail((String) session.getAttribute("currentuser"));

        if (req.getParameter("lock") != null) {
            new ContractService().lock(Integer.parseInt(req.getParameter("lock")), customer);
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (req.getParameter("unlock") != null) {
            new ContractService().unlock(Integer.parseInt(req.getParameter("unlock")), customer);
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        Contract contract = JsonUtil.getObjectFromJson(req, Contract.class);
        new ContractService().updateContract(contract);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
