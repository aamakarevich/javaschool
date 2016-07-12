package com.tsystems.ecare.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * DTO used only for posting new customers for creation
 */
public class NewCustomerDTO {

    private String email;
    private String plainPassword;

    private String firstName;
    private String lastName;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date birthdate;

    private String passport;

    private String city;
    private String address1;
    private String address2;

    public NewCustomerDTO() {
        // empty constructor to instantiate object from JSON
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }
}
