package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Contract;

import java.util.List;

/**
 * Service operations interface for Contract entity.
 */
public interface ContractService {

    public Contract getContract(Long id);

    public void saveContract(Long id, String number, List<Long> activeFeatures, Long planId, Long customerId);

    public void changeLock(Long contractId, boolean locked, String customerEmail);
}
