package com.tsystems.ecare.app.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity that represents address.
 */
@Entity
@Table(name = "address")
public class Address extends AbstractEntity {

    @Basic
    @Column(name = "address1", nullable = false, length = 100)
    private String address1;

    @Basic
    @Column(name = "address2", nullable = true, length = 100)
    private String address2;

    @Basic
    @Column(name = "city", nullable = false, length = 40)
    private String city;

    public Address() {
        // constractor with no parameters
    }

    public Address(String address1, String city) {
        this.address1 = address1;
        this.city = city;
    }

    public Address(String address1, String address2, String city) {
        this.address1 = address1;
        this.address2 = address2;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
