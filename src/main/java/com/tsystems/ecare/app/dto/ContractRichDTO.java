package com.tsystems.ecare.app.dto;

import com.tsystems.ecare.app.model.Contract;
import com.tsystems.ecare.app.model.Plan;

import java.util.ArrayList;
import java.util.List;

public class ContractRichDTO extends ContractDTO {

    private Long customerId;

    private List<Long> activeFeatures = new ArrayList<>();

    public ContractRichDTO() {
        // constructor for creating object from JSON
    }

    public ContractRichDTO(Long id, String number, Contract.Lock numberLock, Plan plan, List<Long> activeFeatures, Long customerId) {
        super(id, number, numberLock, plan);
        this.customerId = customerId;
        this.activeFeatures = activeFeatures;
    }

    public static ContractRichDTO mapFromContractEntity(Contract contract) {
        List<Long> ids = new ArrayList<>();
        contract.getActiveFeatures().stream().forEach(f -> ids.add(f.getId()));
        return new ContractRichDTO(contract.getId(), contract.getNumber(), contract.getNumberLock(), contract.getPlan(), ids, contract.getCustomer().getId());
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getActiveFeatures() {
        return activeFeatures;
    }

    public void setActiveFeatures(List<Long> activeFeatures) {
        this.activeFeatures = activeFeatures;
    }
}
