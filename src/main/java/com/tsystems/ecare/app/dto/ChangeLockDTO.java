package com.tsystems.ecare.app.dto;

public class ChangeLockDTO {

    private Long contractId;
    private boolean locked;

    public ChangeLockDTO() {}

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
