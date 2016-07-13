package com.tsystems.ecare.app.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "feature")
public class Feature extends AbstractEntity {

    @Basic
    @Column(name = "title", nullable = false, length = 40)
    private String title;

    @Basic
    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Basic
    @Column(name = "addition_fee", nullable = false, precision = 2)
    private BigDecimal additionFee;

    @Basic
    @Column(name = "monthly_fee", nullable = false, precision = 2)
    private BigDecimal monthlyFee;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "block",
            joinColumns = @JoinColumn(name = "blocked", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "blocker", referencedColumnName = "id", nullable = false))
    private List<Feature> blockers;

    @ManyToMany(mappedBy = "blockers", fetch = FetchType.LAZY)
    private List<Feature> blockedFeatures;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "need",
            joinColumns = @JoinColumn(name = "dependent", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "needed", referencedColumnName = "id", nullable = false))
    private List<Feature> neededFeatures;

    @ManyToMany(mappedBy = "neededFeatures", fetch = FetchType.LAZY)
    private List<Feature> dependentFeatures;

    @ManyToMany(mappedBy = "activeFeatures", fetch = FetchType.LAZY)
    private List<Contract> contracts;

    @ManyToMany(mappedBy = "allowedFeatures", fetch = FetchType.LAZY)
    private List<Plan> plans;

    public Feature() {}

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

    public BigDecimal getAdditionFee() {
        return additionFee;
    }

    public void setAdditionFee(BigDecimal additionFee) {
        this.additionFee = additionFee;
    }

    public BigDecimal getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(BigDecimal monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public List<Feature> getBlockers() {
        return blockers != null ? blockers : new ArrayList<>(); }

    public void setBlockers(List<Feature> blockers) {
        this.blockers = blockers;
    }

    public List<Feature> getBlockedFeatures() {
        return blockedFeatures != null ? blockedFeatures : new ArrayList<>();
    }

    public void setBlockedFeatures(List<Feature> blockedFeatures) {
        this.blockedFeatures = blockedFeatures;
    }

    public List<Feature> getNeededFeatures() {
        return neededFeatures != null ? neededFeatures : new ArrayList<>();
    }

    public void setNeededFeatures(List<Feature> neededFeatures) {
        this.neededFeatures = neededFeatures;
    }

    public List<Feature> getDependentFeatures() {
        return dependentFeatures != null ? dependentFeatures : new ArrayList<>();
    }

    public void setDependentFeatures(List<Feature> dependentFeatures) {
        this.dependentFeatures = dependentFeatures;
    }

    public List<Contract> getContracts() {
        return contracts != null ? contracts : new ArrayList<>();
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public List<Plan> getPlans() {
        return plans != null ? plans : new ArrayList<>();
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }
}
