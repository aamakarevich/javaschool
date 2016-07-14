package com.tsystems.ecare.app.services.impl;

import com.tsystems.ecare.app.dao.ContractDao;
import com.tsystems.ecare.app.dao.CustomerDao;
import com.tsystems.ecare.app.dao.FeatureDao;
import com.tsystems.ecare.app.dao.PlanDao;
import com.tsystems.ecare.app.model.Contract;
import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.model.Feature;
import com.tsystems.ecare.app.model.Plan;
import com.tsystems.ecare.app.services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

import static com.tsystems.ecare.app.utils.ValidationUtils.assertMatches;
import static com.tsystems.ecare.app.utils.ValidationUtils.assertNotBlank;
import static org.springframework.util.Assert.notNull;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private FeatureDao featureDao;

    @Autowired
    private PlanDao planDao;

    @Override
    @Transactional
    public Contract getContract(Long id) {
        return contractDao.findById(Contract.class, id);
    }

    @Override
    @Transactional
    public void saveContract(Long id, String number, List<Long> activeFeatures, Long planId, Long customerId) {
        Contract contract;
        if (id == null) {
            contract = contractDao.findByNumber(number);
            if (contract != null) {
                throw new IllegalArgumentException("number is not free");
            }

            contract = new Contract();
            notNull(customerId, "customerId is mandatory when creating new contract");

            Customer customer = customerDao.findById(customerId);
            notNull(customer, "customer is not found by id");

            contract.setCustomer(customer);

            contract.setNumberLock(Contract.Lock.UNLOCKED);
        }
        else {
            contract = contractDao.findById(Contract.class, id);
            notNull(contract, "contract is not found by id");
        }

        notNull(number, "number is mandatory");
        assertMatches(number, Pattern.compile("\\d{10}"), "number must contain 10 digits");
        contract.setNumber(number);

        notNull(planId, "planId is mandatory");
        Plan plan = planDao.findById(Plan.class, planId);
        notNull(plan, "plan is not found by id");
        contract.setPlan(plan);

        List<Feature> features =  featureDao.getListed(activeFeatures);
        if (features.size() != activeFeatures.size()) {
            throw new IllegalArgumentException("activeFeatures are not consistent");
        }

        String incompatibleMessage = "some of active features are incompatible";
        for (Feature f : features) {
            for (Feature b : f.getBlockers()) {
                if (features.contains(b)) {
                    throw new IllegalArgumentException(incompatibleMessage);
                }
            }
            for (Feature n : f.getNeededFeatures()) {
                if (!features.contains(n)) {
                    throw new IllegalArgumentException(incompatibleMessage);
                }
            }
            if (!plan.getAllowedFeatures().contains(f)) {
                throw new IllegalArgumentException(incompatibleMessage);
            }
        }

        contract.setActiveFeatures(features);
        contractDao.save(contract);
    }

    @Override
    @Transactional
    public void changeLock(Long contractId, boolean locked, String customerEmail) {
        notNull(contractId, "contractId is mandatory");

        Contract contract = contractDao.findById(Contract.class, contractId);
        notNull(contract, "contract must exist");

        if (customerEmail != null) {
            assertNotBlank(customerEmail, "customerEmail must not be empty");
            Customer customer = customerDao.findByEmail(customerEmail);
            notNull(customer, "customer must exist");

            if (!customer.getContracts().contains(contract)) {
                throw new IllegalArgumentException("contract must be owned by customer");
            }

            if(contract.getNumberLock() != Contract.Lock.LOCKED) {
                contract.setNumberLock(locked ? Contract.Lock.USERLOCKED : Contract.Lock.UNLOCKED);
                contractDao.save(contract);
            }
        } else {
            contract.setNumberLock(locked ? Contract.Lock.LOCKED : Contract.Lock.UNLOCKED);
            contractDao.save(contract);
        }
    }
}
