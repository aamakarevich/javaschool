package com.tsystems.ecare.services;

import com.tsystems.ecare.entities.Role;

/**
 * @author Andrei Makarevich
 */
public interface RoleService {

    Role getRoleById(Integer id);

    Role getRoleByTitle(String title);

    Role saveNewRole(Role role);
}
