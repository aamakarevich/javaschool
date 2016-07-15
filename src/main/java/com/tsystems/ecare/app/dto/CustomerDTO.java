package com.tsystems.ecare.app.dto;

import com.tsystems.ecare.app.model.Address;
import com.tsystems.ecare.app.model.Contract;
import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.model.Role;

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

    private List<RoleDTO> roles = new ArrayList<>();

    public CustomerDTO() {
        // empty constructor to instantiate object from JSON
    };

    public CustomerDTO(Long id, String email, String lastName, String firstName, Date birthdate, String passport,
                       Address address, List<Contract> contracts, List<Role> roles) {
        this.id = id;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthdate = birthdate;
        this.passport = passport;
        this.address = AddressDTO.mapFromAddressEntity(address);
        this.contracts = contracts.stream().map(ContractDTO::mapFromContractEntity).collect(Collectors.toList());
        this.roles = roles.stream().map(RoleDTO::mapFromRoleEntity).collect(Collectors.toList());
    }

    /**
     * Maps Customer entity to CustomerDTO object.
     *
     * @param customer object to map from
     *
     * @return maped DTO
     */
    public static CustomerDTO mapFromCustomerEntity(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getEmail(),
                customer.getLastName(),
                customer.getFirstName(),
                customer.getBirthdate(),
                customer.getPassport(),
                customer.getAddress(),
                customer.getContracts(),
                customer.getRoles());
    }

    /**
     * Maps list of Customer entities to list of CustomerDTO objects.
     *
     * @param customers list to map from
     *
     * @return list of maped DTO
     */
    public static List<CustomerDTO> mapFromCustomersEntities(List<Customer> customers) {
        return customers.stream().map(CustomerDTO::mapFromCustomerEntity).collect(Collectors.toList());
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

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }
}
