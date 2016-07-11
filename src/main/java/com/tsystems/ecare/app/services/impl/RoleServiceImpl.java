package com.tsystems.ecare.app.services.impl;

import com.tsystems.ecare.app.dao.impl.RoleRepository;
import com.tsystems.ecare.app.model.Role;
import com.tsystems.ecare.app.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Override
    @Transactional
    public Role getRoleById(Integer id) {
        return repository.findById(Role.class, id);
    }

    @Override
    @Transactional
    public Role getRoleByTitle(String title) {
        return repository.findByTitle(title);
    }

    @Override
    @Transactional
    public Role saveRole(Role role) {
        return repository.save(role);
    }
}
