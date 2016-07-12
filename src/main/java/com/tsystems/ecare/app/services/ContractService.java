package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Contract;

public interface ContractService {

    Contract getContract(Long id);

    Contract getContractByNumber(String number);

    void changeLock(Long contractId, boolean locked, String customerEmail);
}
