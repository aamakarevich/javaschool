package com.tsystems.ecare.app.dao;

import com.tsystems.ecare.app.model.Contract;

/**
 * Data access object interface for Contract entity.
 */
public interface ContractDao extends GenericDao<Contract, Long> {

    /**
     *
     *
     * @param number
     * @return
     */
    public Contract findByNumber(String number);
}
