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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Andrei Makarevich
 */
@Entity
@Table(name = "plan", schema = "ecare")
public class Plan implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private BigDecimal monthlyFee;
    private List<Feature> allowedFeatures;

    private transient List<Contract> contracts;

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

        Plan plan = (Plan) o;

        if (id != null ? !id.equals(plan.id) : plan.id != null) return false;
        if (title != null ? !title.equals(plan.title) : plan.title != null) return false;
        if (description != null ? !description.equals(plan.description) : plan.description != null) return false;
        if (monthlyFee != null ? !monthlyFee.equals(plan.monthlyFee) : plan.monthlyFee != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (monthlyFee != null ? monthlyFee.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "plan", fetch = FetchType.LAZY)
    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "allowed_feature", schema = "ecare", joinColumns = @JoinColumn(name = "plan_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "feature_id", referencedColumnName = "id", nullable = false))
    public List<Feature> getAllowedFeatures() {
        return allowedFeatures;
    }

    public void setAllowedFeatures(List<Feature> allowedFeatures) {
        this.allowedFeatures = allowedFeatures;
    }
}
