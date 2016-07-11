package com.tsystems.ecare.app.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer extends AbstractEntity {

    @Basic
    @Column(name = "first_name", nullable = false, length = 40)
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = false, length = 40)
    private String lastName;

    @Basic
    @Column(name = "birthdate", nullable = false)
    private Date birthdate;

    @Basic
    @Column(name = "passport", nullable = false, length = 60)
    private String passport;

    @Basic
    @Column(name = "email", nullable = false, length = 40)
    private String email;

    @Basic
    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Contract> contracts;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "customer_role",  joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false))
    private List<Role> roles;

    public Customer() {}

    public Customer(String firstName, String lastName, Date birthdate, String passport,
                    String email, String password, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.passport = passport;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public List<Contract> getContracts() {
        return contracts != null ? contracts : new ArrayList<>();
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Role> getRoles() {
        return roles != null ? roles : new ArrayList<>();
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
