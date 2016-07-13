package com.tsystems.ecare.app.dao;

import com.tsystems.ecare.app.model.Role;

/**
 * Data access object interface for Role entity.
 */
public interface RoleDao extends GenericDao<Role, Long> {

    Role findByTitle(String title);
}
