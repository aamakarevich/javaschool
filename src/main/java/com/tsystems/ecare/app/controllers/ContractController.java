package com.tsystems.ecare.app.controllers;

import com.tsystems.ecare.app.dto.ContractRichDTO;
import com.tsystems.ecare.app.services.ContractService;
import com.tsystems.ecare.app.services.FeatureService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Provides REST-full CRUD for Contract entity.
 */
@Controller
@RequestMapping("/contract")
public class ContractController extends AbstractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private FeatureService featureService;

    public ContractController() {
        super(Logger.getLogger(ContractController.class));
    }

    /**
     * Creates new contract.
     *
     * @param contract DTO with data of contract to create
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void addContract(@RequestBody ContractRichDTO contract) {
        contractService.saveNewContract(
                contract.getNumber(),
                contract.getActiveFeatures(),
                contract.getPlan().getId(),
                contract.getCustomerId());
    }

    /**
     * Returns single contract data.
     *
     * @param id id of contract to return
     * @return DTO with single contract data
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ContractRichDTO getContract(@PathVariable Long id) {
        return ContractRichDTO.mapFromContractEntity(contractService.getContract(id, null));
    }

    /**
     * Updates single existing contract data.
     *
     * @param contract DTO with contract data to update
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT)
    public void updateContract(@RequestBody ContractRichDTO contract) {
        contractService.saveContract(
                contract.getId(),
                contract.getNumber(),
                contract.getActiveFeatures(),
                contract.getPlan().getId(),
                contract.getCustomerId(),
                null);
    }
}