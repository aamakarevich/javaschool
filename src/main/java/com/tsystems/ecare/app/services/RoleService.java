package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Role;

public interface RoleService {

    public Role getRoleById(Integer id);

    public Role getRoleByTitle(String title);

    public Role saveRole(Role role);
}
