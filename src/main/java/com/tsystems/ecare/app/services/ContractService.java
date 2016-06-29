package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.dao.impl.ContractRepository;
import com.tsystems.ecare.app.model.Contract;
import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractDao;

    
    public Contract getContract(Integer id) {
        return contractDao.findById(Contract.class, id);
    }

    
    public Contract getContractByNumber(String number) {
        return contractDao.findByNumber(number);
    }

    
    public Contract saveNewContract(Contract contract) {
        contract.setNumberLock(Contract.Lock.UNLOCKED);
//        contract.setPlan(new PlanServiceImpl().getPlan(contract.getPlan().getId()));
        contract = contractDao.save(contract);
        return contract;
    }

    
    public Contract updateContract(Contract contract) {
//        Contract old = getContract(contract.getId());
//        old.setPlan(new PlanServiceImpl().getPlan(contract.getPlan().getId()));
//        old.setActiveFeatures(contract.getActiveFeatures());
//        contract = contractDao.save(old);
        return contract;
    }

    
    public void lock(Integer id, Customer user) {
        Contract contract = getContract(id);
        Role admin = new RoleService().getRoleByTitle("admin");
        Role manager = new RoleService().getRoleByTitle("manager");
        if (user.getRoles().contains(admin) || user.getRoles().contains(manager)) {
            contract.setNumberLock(Contract.Lock.LOCKED);
        } else {
            contract.setNumberLock(Contract.Lock.USERLOCKED);
        }
        contractDao.save(contract);
    }

    
    public void unlock(Integer id, Customer user) {
        Contract contract = getContract(id);
        Role admin = new RoleService().getRoleByTitle("admin");
        Role manager = new RoleService().getRoleByTitle("manager");
        if (user.getRoles().contains(admin)
                || user.getRoles().contains(manager)
                || contract.getNumberLock() == Contract.Lock.USERLOCKED) {
            contract.setNumberLock(Contract.Lock.UNLOCKED);
        }
        contractDao.save(contract);
    }

}
