package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Role;

/**
 * Service operations interface for Role entity.
 */
public interface RoleService {

    public Role getRoleById(Long id);

    public Role getRoleByTitle(String title);

    public Role saveRole(Role role);
}
