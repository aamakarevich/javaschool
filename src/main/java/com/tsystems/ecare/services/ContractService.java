package com.tsystems.ecare.services;

import com.tsystems.ecare.entities.Contract;
import com.tsystems.ecare.entities.Customer;

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
