package com.tsystems.ecare.services;

import com.tsystems.ecare.entities.Contract;

/**
 * @author Andrei Makarevich
 */
public interface ContractService {

    Contract getContract(Integer id);

    Contract saveNewContract(Contract contract);

    Contract updateContract(Contract contract);
}
