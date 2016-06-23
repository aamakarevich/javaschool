package com.tsystems.ecare.dao.impl;

import com.tsystems.ecare.dao.ContractDao;
import com.tsystems.ecare.entities.Contract;

import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * @author Andrei Makarevich
 */
public class ContractDaoImpl extends GenericDaoImpl<Contract, Integer> implements ContractDao {

    @Override
    public Contract findByNumber(String number) {
        Query query = entityManager.createQuery(
                "from " + Contract.class.getName() + " c where c.number = :number")
                .setParameter("number", number);
        try { /* return contract if have match and null if don't */
            return (Contract) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
