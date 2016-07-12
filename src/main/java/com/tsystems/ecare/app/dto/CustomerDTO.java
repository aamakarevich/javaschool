package com.tsystems.ecare.app.dto;

import com.tsystems.ecare.app.model.Address;
import com.tsystems.ecare.app.model.Contract;
import com.tsystems.ecare.app.model.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerDTO {

    private Long id;

    private String email;
    private String lastName;
    private String firstName;
    private Date birthdate;
    private String passport;

    private AddressDTO address;

    private List<ContractDTO> contracts = new ArrayList<>();

    public CustomerDTO() {};

    public CustomerDTO(Long id, String email, String lastName, String firstName, Date birthdate, String passport,
                       Address address, List<Contract> contracts) {
        this.id = id;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthdate = birthdate;
        this.passport = passport;
        this.address = AddressDTO.mapFromAddressEntity(address);
        this.contracts = contracts.stream().map(ContractDTO::mapFromContractEntity).collect(Collectors.toList());
    }

    public static CustomerDTO mapFromCustomerEntity(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getEmail(),
                customer.getLastName(),
                customer.getFirstName(),
                customer.getBirthdate(),
                customer.getPassport(),
                customer.getAddress(),
                customer.getContracts());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public List<ContractDTO> getContracts() {
        return contracts;
    }

    public void setContracts(List<ContractDTO> contracts) {
        this.contracts = contracts;
    }
}
