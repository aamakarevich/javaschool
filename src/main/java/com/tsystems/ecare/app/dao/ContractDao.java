package com.tsystems.ecare.app.dao;

import com.tsystems.ecare.app.model.Contract;

/**
 * @author Andrei Makarevich
 */
public interface ContractDao extends GenericDao<Contract, Integer> {

    public Contract findByNumber(String number);
}
