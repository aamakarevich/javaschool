package com.tsystems.ecare.app.dao.impl;

import com.tsystems.ecare.app.dao.CustomerDao;
import com.tsystems.ecare.app.model.Contract;
import com.tsystems.ecare.app.model.Customer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Spring specific CustomerDao implementation.
 */
@Repository
public class CustomerRepository extends GenericRepository<Customer, Long> implements CustomerDao {

    Logger logger = Logger.getLogger(CustomerRepository.class);

    /**
     * Finds customer by id.
     *
     * @param id id of customer to find
     *
     * @return found customer or null if customer was not found
     */
    @Override
    public Customer findById(Long id) {
        Query query = em.createQuery(
                "from " + Customer.class.getName() + " where id = :id")
                .setParameter("id", id);
        try { /* return customer if have match and null if don't */
            return (Customer) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.info("user is not found by id", ex);
            return null;
        }
    }

    /**
     * Finds customer by email.
     *
     * @param email email of customer to find
     *
     * @return found customer or null if customer was not found
     */
    @Override
    public Customer findByEmail(String email) {
        Query query = em.createQuery(
                "from " + Customer.class.getName() + " where email = :email")
                .setParameter("email", email);
        try { /* return customer if have match and null if don't */
            return (Customer) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.info("user is not found by email", ex);
            return null;
        }
    }

    /**
     *  Searches for paginated bench of customers.
     *
     * @param itemsCount items on page count
     * @param pageNumber number of page to retreive
     * @param filteredIds ids to filter customers by
     *
     * @return paginated bench of customers
     */
    @Override
    public List<Customer> findPaged(Integer itemsCount, Integer pageNumber, Set<Long> filteredIds) {
        List<Customer> customers;
        String queryString = "from " + Customer.class.getName();

        if(filteredIds != null) {
            if (!filteredIds.isEmpty()) {
                StringBuilder idList = new StringBuilder();
                filteredIds.stream().forEach(i -> idList.append(Long.toString(i) + ","));
                idList.delete(idList.length() - 1, idList.length());
                queryString += " where id in (" + idList.toString() + ")";
            } else {
                queryString += " where 1 <> 1";
            }
        }

        Query query = em.createQuery(queryString);
        query.setFirstResult((pageNumber - 1) * itemsCount);
        query.setMaxResults(itemsCount);
        customers = query.getResultList();
        for (Customer c : customers) {
            Set<Contract> contractSet = new HashSet<>();
            for (Contract contract : c.getContracts()) {
                contractSet.add(contract);
            }
            c.getContracts().clear();
            c.setContracts(new ArrayList<>(contractSet));
        }
        return customers;
    }

    /**
     * Resolves string filter value. It searches filter value
     * in customers last and first name and in phone numbers.
     *
     * @param filter filtering value
     *
     * @return ids of customers found with filter
     */
    @Override
    public Set<Long> getFilteredIds(String filter) {
        Set<Long> ids;
        String subQueryString = "from " + Customer.class.getName();
        subQueryString += " where last_name like '%" + filter
                + "%' or first_name like '%" + filter + "%'";

        Query query = em.createQuery(subQueryString);
        List<Customer> customers = query.getResultList();

        ids = new HashSet<>();
        customers.stream().forEach(c -> ids.add(c.getId()));

        subQueryString = "from " + Contract.class.getName();
        subQueryString += " where number like '%" + filter + "%'";
        query = em.createQuery(subQueryString);
        List<Contract> contracts = query.getResultList();
        contracts.stream().forEach(c -> ids.add(c.getCustomer().getId()));

        return ids;
    }
}
