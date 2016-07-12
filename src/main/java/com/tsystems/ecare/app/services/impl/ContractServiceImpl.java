package com.tsystems.ecare.app.services.impl;

import com.tsystems.ecare.app.dao.ContractDao;
import com.tsystems.ecare.app.dao.CustomerDao;
import com.tsystems.ecare.app.model.Contract;
import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tsystems.ecare.app.services.ValidationUtils.assertNotBlank;
import static org.springframework.util.Assert.notNull;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private CustomerDao customerDao;

    @Override
    @Transactional
    public Contract getContract(Long id) {
        return contractDao.findById(Contract.class, id);
    }

    @Override
    @Transactional
    public Contract getContractByNumber(String number) {
        return contractDao.findByNumber(number);
    }

    @Override
    @Transactional
    public void changeLock(Long contractId, boolean locked, String customerEmail) {
        notNull(contractId, "contractId is mandatory");
        assertNotBlank(customerEmail, "customerEmail must not be empty");

        Customer customer = customerDao.findByEmail(customerEmail);
        notNull(customer, "customer must exist");

        Contract contract = contractDao.findById(Contract.class, contractId);
        notNull(contract, "contract must exist");

        if (!customer.getContracts().contains(contract)) {
            throw new IllegalArgumentException("contract must be owned by customer");
        }

        contract.setNumberLock(locked ? Contract.Lock.USERLOCKED : Contract.Lock.UNLOCKED);
        contractDao.save(contract);
    }

}
