package com.tsystems.ecare.app.dao;

import com.tsystems.ecare.app.model.Role;

/**
 * Data access object interface for Role entity.
 */
public interface RoleDao extends GenericDao<Role, Long> {

    /**
     * Searches role in database by its title.
     *
     * @param title title of the role
     *
     * @return role entity or null if role was not found
     */
    Role findByTitle(String title);
}
