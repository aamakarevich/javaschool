package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Contract;
import com.tsystems.ecare.app.model.Customer;

/**
 * @author Andrei Makarevich
 */
public interface ContractService {

    Contract getContract(Integer id);

    Contract getContractByNumber(String number);

    Contract saveNewContract(Contract contract);

    Contract updateContract(Contract contract);

    void lock(Integer id, Customer user);

    void unlock(Integer id, Customer user);

}
