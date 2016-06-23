package com.tsystems.ecare.services.impl;

import com.tsystems.ecare.dao.RoleDao;
import com.tsystems.ecare.dao.impl.RoleDaoImpl;
import com.tsystems.ecare.entities.Role;
import com.tsystems.ecare.services.RoleService;

/**
 * @author Andrei Makarevich
 */
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao = new RoleDaoImpl();

    public Role getRoleById(Integer id) {
        return roleDao.findById(Role.class, id);
    }

    public Role getRoleByTitle(String title) {
        return roleDao.findByTitle(title);
    }

    public Role saveNewRole(Role role) {
        roleDao.beginTransaction();
        role = roleDao.save(role);
        roleDao.commitTransaction();
        return role;
    }
}
