package com.tsystems.ecare.app.dto;

import com.tsystems.ecare.app.model.Contract;
import com.tsystems.ecare.app.model.Plan;

public class ContractDTO {

    private Long id;

    private String number;
    private Contract.Lock numberLock;

    private PlanDTO plan;

    public ContractDTO() {}

    public ContractDTO(Long id, String number, Contract.Lock numberLock, Plan plan) {
        this.id = id;
        this.number = number;
        this.numberLock = numberLock;
        this.plan = PlanDTO.mapFromPlanEntity(plan);
    }

    public static ContractDTO mapFromContractEntity(Contract contract) {
        return new ContractDTO(contract.getId(), contract.getNumber(), contract.getNumberLock(), contract.getPlan());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Contract.Lock getNumberLock() {
        return numberLock;
    }

    public void setNumberLock(Contract.Lock numberLock) {
        this.numberLock = numberLock;
    }

    public PlanDTO getPlan() {
        return plan;
    }

    public void setPlan(PlanDTO plan) {
        this.plan = plan;
    }
}
