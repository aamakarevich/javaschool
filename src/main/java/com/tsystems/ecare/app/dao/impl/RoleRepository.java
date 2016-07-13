package com.tsystems.ecare.app.dao.impl;

import com.tsystems.ecare.app.dao.RoleDao;
import com.tsystems.ecare.app.model.Role;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Spring specific RoleDao implementation.
 */
@Repository
public class RoleRepository extends GenericRepository<Role, Long> implements RoleDao {

    Logger logger = Logger.getLogger(ContractRepository.class);

    @Override
    public Role findByTitle(String title) {
        Query query = em.createQuery(
                "from " + Role.class.getName() + " where title = :title")
                .setParameter("title", title);
        try {
            return (Role) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.info("role is not found by title", ex);
            return null;
        }
    }
}
