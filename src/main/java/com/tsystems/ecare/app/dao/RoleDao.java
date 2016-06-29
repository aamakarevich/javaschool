package com.tsystems.ecare.app.dao;

import com.tsystems.ecare.app.model.Role;

/**
 * @author Andrei Makarevich
 */
public interface RoleDao extends GenericDao<Role, Integer> {

    Role findByTitle(String title);
}
