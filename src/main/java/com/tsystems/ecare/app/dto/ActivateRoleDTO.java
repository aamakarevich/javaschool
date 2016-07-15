package com.tsystems.ecare.app.dto;

/**
 * JSON serializable DTO containing data about role activation for customer.
 */
public class ActivateRoleDTO {

    private Long roleId;
    private Long customerId;

    private boolean active;

    public ActivateRoleDTO() {
        // empty constructor to instantiate object from JSON
    }

    public ActivateRoleDTO(Long roleId, Long customerId, Boolean active) {
        this.roleId = roleId;
        this.customerId = customerId;
        this.active = active;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
