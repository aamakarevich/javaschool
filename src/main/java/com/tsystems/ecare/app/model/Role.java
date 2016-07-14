package com.tsystems.ecare.app.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity that representse superuser roles in system.
 */
@Entity
@Table(name = "role")
public class Role extends AbstractEntity {

    @Basic
    @Column(name = "title")
    private String title;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<Customer> customers;

    public Role() {
        // constractor with no parameters
    }

    public Role(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Customer> getCustomers() {
        return customers != null ? customers : new ArrayList<>();
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
