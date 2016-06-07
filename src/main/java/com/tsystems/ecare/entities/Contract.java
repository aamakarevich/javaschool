package com.tsystems.ecare.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * @author Andrei Makarevich
 */
@Entity
@Table(name = "contract", schema = "ecare")
public class Contract implements Serializable {
    private Integer id;
    private String number;
    private Lock numberLock;
    private Customer customer;
    private Plan plan;
    private List<Feature> activeFeatures;

    enum Lock {
        UNLOCKED,
        USERLOCKED,
        LOCKED
    }

    public Contract() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "number", nullable = false, length = 10)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "number_lock", nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    public Lock getNumberLock() {
        return numberLock;
    }

    public void setNumberLock(Lock numberLock) {
        this.numberLock = numberLock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        if (id != null ? !id.equals(contract.id) : contract.id != null) return false;
        if (number != null ? !number.equals(contract.number) : contract.number != null) return false;
        if (numberLock != null ? !numberLock.equals(contract.numberLock) : contract.numberLock != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (numberLock != null ? numberLock.hashCode() : 0);
        return result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", referencedColumnName = "id", nullable = false)
    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "active_feature", schema = "ecare", joinColumns = @JoinColumn(name = "contract_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "feature_id", referencedColumnName = "id", nullable = false))
    public List<Feature> getActiveFeatures() {
        return activeFeatures;
    }

    public void setActiveFeatures(List<Feature> activeFeatures) {
        this.activeFeatures = activeFeatures;
    }
}
