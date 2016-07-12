package com.tsystems.ecare.app.dao.impl;

import com.tsystems.ecare.app.dao.ContractDao;
import com.tsystems.ecare.app.model.Contract;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
public class ContractRepository extends GenericRepository<Contract, Long> implements ContractDao {

    @Override
    public Contract findByNumber(String number) {
        Query query = em.createQuery(
                "from " + Contract.class.getName() + " c where c.number = :number")
                .setParameter("number", number);
        try { /* return contract if have match and null if don't */
            return (Contract) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
