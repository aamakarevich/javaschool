package com.tsystems.ecare.app.dao;

import com.tsystems.ecare.app.model.Contract;


public interface ContractDao extends GenericDao<Contract, Long> {

    public Contract findByNumber(String number);
}
