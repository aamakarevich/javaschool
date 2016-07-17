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
import com.tsystems.ecare.app.utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import static com.tsystems.ecare.app.utils.HashUtils.generatePassword;
import static com.tsystems.ecare.app.utils.HashUtils.sha256;
import static com.tsystems.ecare.app.utils.ValidationUtils.assertMatches;
import static com.tsystems.ecare.app.utils.ValidationUtils.assertNotBlank;
import static org.springframework.util.Assert.notNull;

/**
 * Spring specific ContractService implementation
 */
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

    /**
     * Saves new contract and in case of need triggers sendind sms with password.
     *
     * @param id id of contract to save
     * @param number phone number for contract
     * @param activeFeatures ids of features to activate for contract
     * @param planId id of plan for contract
     * @param customerId id of customer for whom contract belongs
     */
    @Override
    @Transactional
    public void saveNewContract(String number, List<Long> activeFeatures, Long planId, Long customerId) {
        String newPassword = saveContract(null, number, activeFeatures, planId, customerId, null);
        if (newPassword != null) {
            SmsUtils.sendSms(number, newPassword);
        }
    }

    /**
     * Saves contract (new or not) to database.
     *
     * @param id id of contract to save
     * @param number phone number for contract (ignored updating existing contract)
     * @param activeFeatures ids of features to activate for contract
     * @param planId id of plan for contract
     * @param customerId id of customer for whom contract belongs
     * @param customerEmail if not null - only customer's own contracts can be saved
     *
     * @return newPassword if customer must be informed about password change, otherwise null
     */
    @Override
    @Transactional
    public String saveContract(Long id, String number, List<Long> activeFeatures, Long planId, Long customerId, String customerEmail) {
        Contract contract;
        String newPassword = null;
        if (activeFeatures == null) {
            activeFeatures = new ArrayList<>();
        }
        if (customerEmail != null) {
            notNull(id, "id must not be null if principal email is passed");
        }
        if (id == null) {
            contract = contractDao.findByNumber(number);
            if (contract != null) {
                throw new IllegalArgumentException("number is not free");
            }

            contract = new Contract();

            notNull(customerId, "customerId is mandatory when creating new contract");

            Customer customer = customerDao.findById(customerId);
            notNull(customer, "customer is not found by id");

            if (customer.getContracts().isEmpty()) {
                newPassword = generatePassword();
                customer.setPassword(sha256(newPassword));
            }
            contract.setCustomer(customer);

            notNull(number, "number is mandatory");
            assertMatches(number, Pattern.compile("\\d{10}"), "number must contain 10 digits");
            contract.setNumber(number);

            contract.setNumberLock(Contract.Lock.UNLOCKED);
        }
        else {
            contract = contractDao.findById(Contract.class, id);
            notNull(contract, "contract is not found by id");
            if (customerEmail != null && !customerDao.findByEmail(customerEmail).getContracts().contains(contract)) {
                throw new IllegalArgumentException("only owned contract data can be changed");
            }
        }

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
        return newPassword;
    }

    /**
     * Searches for single contract by id.
     *
     * @param id id of contract to find
     * @param customerEmail customer email if contract MUST be owned by customer
     *                      or null if there is no difference
     *
     * @return single contract entity
     */
    @Override
    @Transactional
    public Contract getContract(Long id, String customerEmail) {
        notNull(id, "id is mandatory");
        Contract contract = contractDao.findById(Contract.class, id);
        notNull(contract, "contract is not found by id");
        if (customerEmail != null) {
            Customer customer = customerDao.findByEmail(customerEmail);
            notNull(customer, "customer is not found by email");
            if (!customer.getContracts().contains(contract)) {
                throw new IllegalArgumentException("contract must be owned by customer");
            }
        }
        contract.setActiveFeatures(new ArrayList<>(new HashSet<Feature>(contract.getActiveFeatures())));
        return contract;
    }

    /**
     * Changes lock state for contract in database.
     *
     * @param contractId id of contract to change lock
     * @param locked true if contract must be locked
     * @param customerEmail customer email if contract MUST be owned by customer
     *                      or null if there is no difference
     */
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
