package com.tsystems.ecare.app.services.impl;

import com.tsystems.ecare.app.dao.RoleDao;
import com.tsystems.ecare.app.model.Role;
import com.tsystems.ecare.app.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    @Transactional
    public Role getRoleById(Long id) {
        return roleDao.findById(Role.class, id);
    }

    @Override
    @Transactional
    public Role getRoleByTitle(String title) {
        return roleDao.findByTitle(title);
    }

    @Override
    @Transactional
    public Role saveRole(Role role) {
        return roleDao.save(role);
    }
}
