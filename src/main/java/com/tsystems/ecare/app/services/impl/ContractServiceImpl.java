package com.tsystems.ecare.app.services.impl;

import com.tsystems.ecare.app.dao.ContractDao;
import com.tsystems.ecare.app.dao.impl.ContractDaoImpl;
import com.tsystems.ecare.app.model.Contract;
import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.model.Role;
import com.tsystems.ecare.app.services.ContractService;

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
    public Contract getContractByNumber(String number) {
        return contractDao.findByNumber(number);
    }

    @Override
    public Contract saveNewContract(Contract contract) {
        contractDao.beginTransaction();
        contract.setNumberLock(Contract.Lock.UNLOCKED);
//        contract.setPlan(new PlanServiceImpl().getPlan(contract.getPlan().getId()));
        contract = contractDao.save(contract);
        contractDao.commitTransaction();
        return contract;
    }

    @Override
    public Contract updateContract(Contract contract) {
        contractDao.beginTransaction();
//        Contract old = getContract(contract.getId());
//        old.setPlan(new PlanServiceImpl().getPlan(contract.getPlan().getId()));
//        old.setActiveFeatures(contract.getActiveFeatures());
//        contract = contractDao.merge(old);
        contractDao.commitTransaction();
        return contract;
    }

    @Override
    public void lock(Integer id, Customer user) {
        contractDao.beginTransaction();
        Contract contract = getContract(id);
        Role admin = new RoleServiceImpl().getRoleByTitle("admin");
        Role manager = new RoleServiceImpl().getRoleByTitle("manager");
        if (user.getRoles().contains(admin) || user.getRoles().contains(manager)) {
            contract.setNumberLock(Contract.Lock.LOCKED);
        } else {
            contract.setNumberLock(Contract.Lock.USERLOCKED);
        }
        contractDao.merge(contract);
        contractDao.commitTransaction();
    }

    @Override
    public void unlock(Integer id, Customer user) {
        contractDao.beginTransaction();
        Contract contract = getContract(id);
        Role admin = new RoleServiceImpl().getRoleByTitle("admin");
        Role manager = new RoleServiceImpl().getRoleByTitle("manager");
        if (user.getRoles().contains(admin)
                || user.getRoles().contains(manager)
                || contract.getNumberLock() == Contract.Lock.USERLOCKED) {
            contract.setNumberLock(Contract.Lock.UNLOCKED);
        }
        contractDao.merge(contract);
        contractDao.commitTransaction();
    }

}
