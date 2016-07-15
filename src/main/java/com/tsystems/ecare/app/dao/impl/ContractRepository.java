package com.tsystems.ecare.app.dao.impl;

import com.tsystems.ecare.app.dao.ContractDao;
import com.tsystems.ecare.app.model.Contract;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Spring specific ContractDao implementation.
 */
@Repository
public class ContractRepository extends GenericRepository<Contract, Long> implements ContractDao {

    Logger logger = Logger.getLogger(ContractRepository.class);

    /**
     * Returns contract entry searching it by phone number.
     *
     * @param number phone number to find contract by
     *
     * @return contract or null if contract was not found
     */
    @Override
    public Contract findByNumber(String number) {
        Query query = em.createQuery(
                "from " + Contract.class.getName() + " where number = :number")
                .setParameter("number", number);
        try {
            return (Contract) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.info("contract is not found by number", ex);
            return null;
        }
    }
}
