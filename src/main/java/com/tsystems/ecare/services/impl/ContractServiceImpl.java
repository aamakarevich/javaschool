package com.tsystems.ecare.services.impl;

import com.tsystems.ecare.dao.ContractDao;
import com.tsystems.ecare.dao.impl.ContractDaoImpl;
import com.tsystems.ecare.dao.impl.PlanDaoImpl;
import com.tsystems.ecare.entities.Contract;
import com.tsystems.ecare.entities.Plan;
import com.tsystems.ecare.services.ContractService;

/**
 * @author Andrei Makarevich
 */
public class ContractServiceImpl implements ContractService {

    ContractDao contractDao = new ContractDaoImpl();

    @Override
    public Contract getContract(Integer id) {
        return contractDao.findById(Contract.class, id);
    }

    @Override
    public Contract saveNewContract(Contract contract) {
        contractDao.beginTransaction();
        contract.setNumberLock(Contract.Lock.UNLOCKED);
        contract.setPlan(new PlanServiceImpl().getPlan(contract.getPlan().getId()));
//        contract.setCustomer(new CustomerServiceImpl().getCustomer(contract.getCustomer().getId()));
        contract = contractDao.save(contract);
        contractDao.commitTransaction();
        return contract;
    }

    @Override
    public Contract updateContract(Contract contract) {
        contractDao.beginTransaction();
        Contract old = getContract(contract.getId());
        old.setPlan(new PlanServiceImpl().getPlan(contract.getPlan().getId()));
        old.setActiveFeatures(contract.getActiveFeatures());
        contract = contractDao.merge(old);
        contractDao.commitTransaction();
        return contract;
    }
}
