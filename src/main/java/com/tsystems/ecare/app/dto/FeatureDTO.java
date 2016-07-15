package com.tsystems.ecare.app.dto;

import com.tsystems.ecare.app.model.Feature;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JSON serializable DTO containing Feature data.
 */
public class FeatureDTO {

    private Long id;

    private String title;
    private String description;
    private BigDecimal additionFee;
    private BigDecimal monthlyFee;

    public FeatureDTO() {
        // empty constructor to instantiate object from JSON
    }

    public FeatureDTO(Long id, String title, String description, BigDecimal additionFee, BigDecimal monthlyFee) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.additionFee = additionFee;
        this.monthlyFee = monthlyFee;
    }

    /**
     * Maps Feature entity to FeatureDTO object.
     *
     * @param feature object to map from
     *
     * @return maped DTO
     */
    public static FeatureDTO mapFromFeatureEntity(Feature feature) {
        return new FeatureDTO(feature.getId(), feature.getTitle(), feature.getDescription(),
                feature.getAdditionFee(), feature.getMonthlyFee());
    }

    /**
     * Maps list of Feature entities to list of FeatureDTO objects.
     *
     * @param features list to map from
     *
     * @return list of maped DTO
     */
    public static List<FeatureDTO> mapFromFeaturesEntities(List<Feature> features) {
        return features.stream().map(FeatureDTO::mapFromFeatureEntity).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
