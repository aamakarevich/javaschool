package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Contract;

import java.util.List;

/**
 * Service operations interface for Contract entity.
 */
public interface ContractService {

    /**
     * Saves contract (new or not) to database.
     *
     * @param id id of contract to save
     * @param number phone number for contract
     * @param activeFeatures ids of features to activate for contract
     * @param planId id of plan for contract
     * @param customerId id of customer for whom contract belongs
     *
     * @return newPassword if customer must be informed about password change, otherwise null
     */
    public String saveContract(Long id, String number, List<Long> activeFeatures, Long planId, Long customerId);

    public void saveNewContract(Long id, String number, List<Long> activeFeatures, Long planId, Long customerId);

    /**
     * Searches for single contract by id.
     *
     * @param id id of contract to find
     * @return single contract entity
     */
    public Contract getContract(Long id);

    /**
     * Changes lock state for contract in database.
     *
     * @param contractId id of contract to change lock
     * @param locked true if contract must be locked
     * @param customerEmail customer email if contract MUST be owned by customer
     *                      or null if there is no difference
     */
    public void changeLock(Long contractId, boolean locked, String customerEmail);
}
