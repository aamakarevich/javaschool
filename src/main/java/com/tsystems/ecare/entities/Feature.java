package com.tsystems.ecare.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Andrei Makarevich
 */
@Entity
@Table(name = "feature", schema = "ecare")
public class Feature implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private BigDecimal additionFee;
    private BigDecimal monthlyFee;
    private List<Feature> blockers;
    private List<Feature> blockedFeatures;
    private List<Feature> neededFeatures;
    private List<Feature> dependentFeatures;

    private transient List<Contract> contracts;
    private transient List<Plan> plans;

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
    @Column(name = "title", nullable = false, length = 40)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 1000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "addition_fee", nullable = false, precision = 2)
    public BigDecimal getAdditionFee() {
        return additionFee;
    }

    public void setAdditionFee(BigDecimal additionFee) {
        this.additionFee = additionFee;
    }

    @Basic
    @Column(name = "monthly_fee", nullable = false, precision = 2)
    public BigDecimal getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(BigDecimal monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feature feature = (Feature) o;

        if (id != null ? !id.equals(feature.id) : feature.id != null) return false;
        if (title != null ? !title.equals(feature.title) : feature.title != null) return false;
        if (description != null ? !description.equals(feature.description) : feature.description != null) return false;
        if (additionFee != null ? !additionFee.equals(feature.additionFee) : feature.additionFee != null) return false;
        if (monthlyFee != null ? !monthlyFee.equals(feature.monthlyFee) : feature.monthlyFee != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (additionFee != null ? additionFee.hashCode() : 0);
        result = 31 * result + (monthlyFee != null ? monthlyFee.hashCode() : 0);
        return result;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "block", schema = "ecare", joinColumns = @JoinColumn(name = "blocked", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "blocker", referencedColumnName = "id", nullable = false))
    public List<Feature> getBlockers() {
        return blockers;
    }

    public void setBlockers(List<Feature> blockers) {
        this.blockers = blockers;
    }

    @ManyToMany(mappedBy = "blockers", fetch = FetchType.LAZY)
    public List<Feature> getBlockedFeatures() {
        return blockedFeatures;
    }

    public void setBlockedFeatures(List<Feature> blockedFeatures) {
        this.blockedFeatures = blockedFeatures;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "need", schema = "ecare", joinColumns = @JoinColumn(name = "dependent", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "needed", referencedColumnName = "id", nullable = false))
    public List<Feature> getNeededFeatures() {
        return neededFeatures;
    }

    public void setNeededFeatures(List<Feature> neededFeatures) {
        this.neededFeatures = neededFeatures;
    }

    @ManyToMany(mappedBy = "neededFeatures", fetch = FetchType.LAZY)
    public List<Feature> getDependentFeatures() {
        return dependentFeatures;
    }

    public void setDependentFeatures(List<Feature> dependentFeatures) {
        this.dependentFeatures = dependentFeatures;
    }

    @ManyToMany(mappedBy = "activeFeatures", fetch = FetchType.LAZY)
    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    @ManyToMany(mappedBy = "allowedFeatures", fetch = FetchType.LAZY)
    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }
}
