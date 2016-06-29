package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Role;

/**
 * @author Andrei Makarevich
 */
public interface RoleService {

    Role getRoleById(Integer id);

    Role getRoleByTitle(String title);

    Role saveNewRole(Role role);
}
