package com.tsystems.ecare.app.dto;

import com.tsystems.ecare.app.model.Role;

/**
 * JSON serializable DTO containing Role data.
 */
public class RoleDTO {

    private Long id;

    private String title;

    public RoleDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static RoleDTO mapFromRoleEntity(Role role) {
        return new RoleDTO(role.getId(), role.getTitle());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
