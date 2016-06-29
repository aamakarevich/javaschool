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
import java.util.List;

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
    @Column(name = "monthly_fee", nullable = false, precision = 2)
    private BigDecimal monthlyFee;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "allowed_feature",
            joinColumns = @JoinColumn(name = "plan_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "feature_id", referencedColumnName = "id", nullable = false))
    private List<Feature> allowedFeatures;

    @OneToMany(mappedBy = "plan", fetch = FetchType.LAZY)
    private transient List<Contract> contracts;

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
        return allowedFeatures != null ? allowedFeatures : new ArrayList<>();
    }

    public void setAllowedFeatures(List<Feature> allowedFeatures) {
        this.allowedFeatures = allowedFeatures;
    }
}
