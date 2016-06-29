package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.dao.impl.RoleRepository;
import com.tsystems.ecare.app.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    @Transactional
    public Role getRoleById(Integer id) {
        return repository.findById(Role.class, id);
    }

    @Transactional
    public Role getRoleByTitle(String title) {
        return repository.findByTitle(title);
    }

    @Transactional
    public Role saveRole(Role role) {
        return repository.save(role);
    }
}
