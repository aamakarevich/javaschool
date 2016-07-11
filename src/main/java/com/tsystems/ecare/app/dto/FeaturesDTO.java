package com.tsystems.ecare.app.dto;

import java.util.List;

public class FeaturesDTO {

    private long count;
    private List<FeatureDTO> features;

    public FeaturesDTO(long count, List<FeatureDTO> features) {
        this.count = count;
        this.features = features;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<FeatureDTO> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureDTO> features) {
        this.features = features;
    }
}
