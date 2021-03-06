package com.tsystems.ecare.app.dto;

/**
 * JSON serializable DTO containing data about changing locking state for contract.
 */
public class ChangeLockDTO {

    private Long contractId;
    private boolean locked;

    public ChangeLockDTO() {
        // empty constructor to instantiate object from JSON
    }

    public ChangeLockDTO(boolean locked, Long contractId) {
        this.locked = locked;
        this.contractId = contractId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
