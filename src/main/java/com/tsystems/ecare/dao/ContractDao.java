package com.tsystems.ecare.dao;

import com.tsystems.ecare.entities.Contract;

/**
 * @author Andrei Makarevich
 */
public interface ContractDao extends GenericDao<Contract, Integer> {

    public Contract findByNumber(String number);
}
