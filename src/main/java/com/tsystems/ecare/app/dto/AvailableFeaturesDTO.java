package com.tsystems.ecare.app.dto;

import java.util.ArrayList;
import java.util.List;

public class AvailableFeaturesDTO extends FeaturesDTO {

    private List<Long> availableFeatures = new ArrayList<>();
    private List<FeatureDTO> listedFeatures = new ArrayList<>();

    public AvailableFeaturesDTO(long count, List<FeatureDTO> features, List<Long> availableFeatures, List<FeatureDTO> listedFeatures) {
        super(count, features);
        this.availableFeatures = availableFeatures;
        this.listedFeatures = listedFeatures;
    }

    public List<Long> getAvailableFeatures() {
        return availableFeatures;
    }

    public void setAvailableFeatures(List<Long> availableFeatures) {
        this.availableFeatures = availableFeatures;
    }

    public List<FeatureDTO> getListedFeatures() {
        return listedFeatures;
    }

    public void setListedFeatures(List<FeatureDTO> listedFeatures) {
        this.listedFeatures = listedFeatures;
    }
}
