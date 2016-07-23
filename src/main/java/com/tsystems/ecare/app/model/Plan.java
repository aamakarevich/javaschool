package com.tsystems.ecare.app.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Entity that represents plan that can be applied to contracts.
 */
@Entity
@Table(name = "plan")
public class Plan extends AbstractEntity {

    @Basic
    @Column(name = "title", nullable = false, length = 40)
    private String title;

    @Basic
    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Basic
    @Column(name = "monthly_fee", nullable = false, scale = 2, precision = 10)
    private BigDecimal monthlyFee;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "allowed_feature",
            joinColumns = @JoinColumn(name = "plan_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "feature_id", referencedColumnName = "id", nullable = false))
    private List<Feature> allowedFeatures;

    @OneToMany(mappedBy = "plan", fetch = FetchType.LAZY)
    private List<Contract> contracts = new ArrayList<>();

    public Plan() {
        // constractor with no parameters
    }

    public Plan(String title, String description, BigDecimal monthlyFee) {
        this.title = title;
        this.description = description;
        this.monthlyFee = monthlyFee;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(BigDecimal monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public List<Contract> getContracts() {
        return contracts != null ? contracts : new ArrayList<>();
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public List<Feature> getAllowedFeatures() {
        if (allowedFeatures == null) {
            allowedFeatures = new ArrayList<>();
        }
        allowedFeatures = new ArrayList<>(new HashSet<>(allowedFeatures));
        return allowedFeatures;
    }

    public void setAllowedFeatures(List<Feature> allowedFeatures) {
        this.allowedFeatures = allowedFeatures;
    }
}
