package com.tsystems.ecare.dao;

import com.tsystems.ecare.entities.Role;

/**
 * @author Andrei Makarevich
 */
public interface RoleDao extends GenericDao<Role, Integer> {

    Role findByTitle(String title);
}
