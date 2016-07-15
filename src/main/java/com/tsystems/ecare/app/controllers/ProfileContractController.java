package com.tsystems.ecare.app.controllers;

import com.tsystems.ecare.app.dto.ContractRichDTO;
import com.tsystems.ecare.app.services.ContractService;
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

import java.security.Principal;

/**
 * Provides REST-service for changing customers own contracts.
 */
@Controller
@RequestMapping("profile/contract")
public class ProfileContractController extends AbstractController {

    @Autowired
    private ContractService contractService;

    public ProfileContractController() {
        super(Logger.getLogger(ProfileContractController.class));
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
    public ContractRichDTO getContract(Principal principal, @PathVariable Long id) {
        return ContractRichDTO.mapFromContractEntity(contractService.getContract(id, principal.getName()));
    }

    /**
     * Updates single existing contract data.
     *
     * @param principal current user
     * @param contract DTO with data of contract to create
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT)
    public void updateContract(Principal principal, @RequestBody ContractRichDTO contract) {
        contractService.saveContract(
                contract.getId(),
                contract.getNumber(),
                contract.getActiveFeatures(),
                contract.getPlan().getId(),
                null,
                principal.getName());
    }
}
