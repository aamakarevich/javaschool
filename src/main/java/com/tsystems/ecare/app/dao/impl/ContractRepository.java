package com.tsystems.ecare.app.dao.impl;

import com.tsystems.ecare.app.dao.ContractDao;
import com.tsystems.ecare.app.model.Contract;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
public class ContractRepository extends GenericRepository<Contract, Long> implements ContractDao {

    Logger logger = Logger.getLogger(ContractRepository.class);

    @Override
    public Contract findByNumber(String number) {
        Query query = em.createQuery(
                "from " + Contract.class.getName() + " c where c.number = :number")
                .setParameter("number", number);
        try { /* return contract if have match and null if don't */
            return (Contract) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.info("contract is not found by number", ex);
            return null;
        }
    }
}
