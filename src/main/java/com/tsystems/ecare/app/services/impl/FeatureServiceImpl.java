package com.tsystems.ecare.app.services.impl;

import com.tsystems.ecare.app.dao.FeatureDao;
import com.tsystems.ecare.app.model.Feature;
import com.tsystems.ecare.app.model.Plan;
import com.tsystems.ecare.app.model.SearchResult;
import com.tsystems.ecare.app.services.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.tsystems.ecare.app.services.ValidationUtils.assertMaximumLength;
import static com.tsystems.ecare.app.services.ValidationUtils.assertNotBlank;
import static org.springframework.util.Assert.notNull;

@Service
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    private FeatureDao featureDao;

    @Override
    @Transactional
    public Feature saveFeature(Long id, String title, String description, BigDecimal additionFee, BigDecimal monthlyFee) {
        assertNotBlank(title, "title must not be blank");
        assertNotBlank(description, "title must not be blank");
        notNull(additionFee, "additionFee is mandatory");
        notNull(monthlyFee, "monthlyFee is mandatory");

        assertMaximumLength(title, 40, "title must be not more then 40 characters");
        assertMaximumLength(description, 1000, "title must be not more then 1000 characters");

        Feature feature;
        if (id == null) {
            feature = new Feature();
        } else {
            feature = featureDao.findById(Feature.class, id);
            notNull(feature, "can't update feature that doesn't exist");
        }
        feature.setTitle(title);
        feature.setDescription(description);
        feature.setAdditionFee(additionFee);
        feature.setMonthlyFee(monthlyFee);
        return featureDao.save(feature);
    }

    @Override
    @Transactional
    public Feature getFeature(Long findFeatureId) {
        notNull(findFeatureId, "findFeatureId is mandatory");
        return featureDao.findById(Feature.class, findFeatureId);
    }

    @Override
    @Transactional
    public void deleteFeature(Long deleteFeatureid) {
        notNull(deleteFeatureid, "deleteFeatureid is mandatory");

        Feature feature = featureDao.findById(Feature.class, deleteFeatureid);

        for (Feature f : feature.getBlockedFeatures()) {
            f.getBlockers().remove(feature);
            featureDao.save(f);
        }
        for (Feature f : feature.getBlockers()) {
            f.getBlockedFeatures().remove(feature);
            featureDao.save(f);
        }
        for (Feature f : feature.getNeededFeatures()) {
            f.getDependentFeatures().remove(feature);
            featureDao.save(f);
        }
        for (Feature f : feature.getDependentFeatures()) {
            f.getNeededFeatures().remove(feature);
            featureDao.save(f);
        }
        for (Plan p : feature.getPlans()) {
            p.getAllowedFeatures().remove(feature);
        }
        feature = featureDao.save(feature);
        featureDao.delete(feature);
    }

    @Override
    @Transactional(readOnly = true)
    public SearchResult<Feature> getAllFeatures() {
        Long resultsCount = featureDao.getTotalCount(Feature.class);
        List<Feature> features = featureDao.findAll(Feature.class);
        return new SearchResult<>(resultsCount, features);
    }

    @Override
    @Transactional
    public List<Feature> getListedFeatures(List<Long> ids) {
        return featureDao.getListed(ids);
    }

    @Override
    @Transactional
    public List<Feature> getAvailableFeatures(List<Long> ids, Integer planId) {

        List<Feature> features = getListedFeatures(ids);
        if (features == null) {
            features = new ArrayList<>();
        }
        Plan plan = null;// new PlanServiceImpl().getPlan(planId);
        List<Feature> avaiableFeatures;// = plan.getAllowedFeatures();
        avaiableFeatures = new ArrayList<>();
        Iterator<Feature> iterator = avaiableFeatures.iterator();
        while (iterator.hasNext()) {
            Feature af = iterator.next();
            if (features.contains(af)
                    || !features.containsAll(af.getNeededFeatures())) {
                iterator.remove();
                continue;
            }
            for (Feature feature : features) {
                if (feature.getBlockedFeatures().contains(af)) {
                    iterator.remove();
                    break;
                }
            }
        }
        return avaiableFeatures;
    }

    @Override
    @Transactional
    public void changeLinkedFeature(Long feature1Id, Long feature2Id, Boolean block, Boolean linked) {
        notNull(feature1Id, "feature1Id is mandatory");
        notNull(feature2Id, "feature2Id is mandatory");
        notNull(block, "block is mandatory");
        notNull(linked, "linked is mandatory");

        if (feature1Id == feature2Id) {
            throw new IllegalArgumentException("it is not possible to link feature to itself");
        }

        Feature feature1 = featureDao.findById(Feature.class, feature1Id);
        Feature feature2 = featureDao.findById(Feature.class, feature2Id);
        if (feature1 == null || feature2 == null) {
            throw new IllegalArgumentException("both feature1 and feature2 must exist to update link between them");
        }

        unlinkFeatures(feature1, feature2);
        if (block) {
            if (linked) {
                feature1.getBlockers().add(feature2);
                feature1.getBlockedFeatures().add(feature2);
                feature2.getBlockers().add(feature1);
                feature2.getBlockedFeatures().add(feature1);
            }
        } else {
            if (linked) {
                feature1.getDependentFeatures().add(feature2);
                feature2.getNeededFeatures().add(feature1);
            }
        }
        featureDao.save(feature1);
        featureDao.save(feature2);
    }

    private void unlinkFeatures(Feature feature1, Feature feature2) {
        feature1.getDependentFeatures().remove(feature2);
        feature1.getNeededFeatures().remove(feature2);
        feature2.getDependentFeatures().remove(feature1);
        feature2.getNeededFeatures().remove(feature1);
        feature1.getBlockers().remove(feature2);
        feature1.getBlockedFeatures().remove(feature2);
        feature2.getBlockers().remove(feature1);
        feature2.getBlockedFeatures().remove(feature1);
    }
}
