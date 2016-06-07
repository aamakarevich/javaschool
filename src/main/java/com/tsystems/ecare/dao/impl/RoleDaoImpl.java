package com.tsystems.ecare.dao.impl;

import com.tsystems.ecare.dao.RoleDao;
import com.tsystems.ecare.entities.Role;

import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * @author Andrei Makarevich
 */
public class RoleDaoImpl extends GenericDaoImpl<Role, Integer> implements RoleDao {

    public Role findByTitle(String title) {
        Query query = entityManager.createQuery(
                "from " + Role.class.getName() + " c where c.title = :title")
                .setParameter("title", title);
        try { /* return role if have match and null if don't */
            return (Role) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
