package com.tsystems.ecare.app.dao;

import com.tsystems.ecare.app.model.Contract;

/**
 * Data access object interface for Contract entity.
 */
public interface ContractDao extends GenericDao<Contract, Long> {

    /**
     * Returns contract entry searching it by phone number.
     *
     * @param number phone number to find contract by
     *
     * @return contract or null if contract was not found
     */
    public Contract findByNumber(String number);
}
