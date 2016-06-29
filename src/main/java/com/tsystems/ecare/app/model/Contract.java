package com.tsystems.ecare.app.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contract")
public class Contract extends AbstractEntity {

    @Basic
    @Column(name = "number", nullable = false, length = 10)
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "plan_id", referencedColumnName = "id", nullable = false)
    private Plan plan;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "active_feature",  joinColumns = @JoinColumn(name = "contract_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "feature_id", referencedColumnName = "id", nullable = false))
    private List<Feature> activeFeatures;

    @Basic
    @Column(name = "number_lock", nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    private Lock numberLock;
    public enum Lock {
        UNLOCKED,
        USERLOCKED,
        LOCKED
    }

    public Contract() {}

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public List<Feature> getActiveFeatures() {
        return activeFeatures != null ? activeFeatures : new ArrayList<>();
    }

    public void setActiveFeatures(List<Feature> activeFeatures) {
        this.activeFeatures = activeFeatures;
    }

    public Lock getNumberLock() {
        return numberLock;
    }

    public void setNumberLock(Lock numberLock) {
        this.numberLock = numberLock;
    }
}
