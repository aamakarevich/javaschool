package com.tsystems.ecare.app.dao;

import com.tsystems.ecare.app.model.Role;


public interface RoleDao extends GenericDao<Role, Integer> {

    Role findByTitle(String title);
}
