package com.tsystems.ecare.app.services.impl;

import com.tsystems.ecare.app.dao.impl.ContractRepository;
import com.tsystems.ecare.app.model.Contract;
import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.model.Role;
import com.tsystems.ecare.app.services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractRepository contractDao;

    @Override
    @Transactional
    public Contract getContract(Integer id) {
        return contractDao.findById(Contract.class, id);
    }

    @Override
    @Transactional
    public Contract getContractByNumber(String number) {
        return contractDao.findByNumber(number);
    }

    @Override
    @Transactional
    public void lock(Integer id, Customer user) {
        Contract contract = getContract(id);
        Role admin = new RoleServiceImpl().getRoleByTitle("admin");
        Role manager = new RoleServiceImpl().getRoleByTitle("manager");
        if (user.getRoles().contains(admin) || user.getRoles().contains(manager)) {
            contract.setNumberLock(Contract.Lock.LOCKED);
        } else {
            contract.setNumberLock(Contract.Lock.USERLOCKED);
        }
        contractDao.save(contract);
    }

    @Override
    @Transactional
    public void unlock(Integer id, Customer user) {
        Contract contract = getContract(id);
        Role admin = new RoleServiceImpl().getRoleByTitle("admin");
        Role manager = new RoleServiceImpl().getRoleByTitle("manager");
        if (user.getRoles().contains(admin)
                || user.getRoles().contains(manager)
                || contract.getNumberLock() == Contract.Lock.USERLOCKED) {
            contract.setNumberLock(Contract.Lock.UNLOCKED);
        }
        contractDao.save(contract);
    }

}
