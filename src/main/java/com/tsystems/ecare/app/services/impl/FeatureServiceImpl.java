package com.tsystems.ecare.app.services.impl;

import com.tsystems.ecare.app.dao.FeatureDao;
import com.tsystems.ecare.app.dao.PlanDao;
import com.tsystems.ecare.app.model.Feature;
import com.tsystems.ecare.app.model.Plan;
import com.tsystems.ecare.app.model.SearchResult;
import com.tsystems.ecare.app.services.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.tsystems.ecare.app.utils.ValidationUtils.assertMaximumLength;
import static com.tsystems.ecare.app.utils.ValidationUtils.assertNotBlank;
import static org.springframework.util.Assert.notNull;

@Service
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    private FeatureDao featureDao;

    @Autowired
    private PlanDao planDao;

    /**
     * Saves option (new or not) to database.
     *
     * @param id id of option
     * @param title title of option
     * @param description description of option
     * @param additionFee additionFee for applying option
     * @param monthlyFee monthlyFee for using option
     *
     * @return saved option entity
     */
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

    /**
     * Searches option in database by id.
     *
     * @param id id of option
     *
     * @return entity with option data
     */
    @Override
    @Transactional
    public Feature getFeature(Long findFeatureId) {
        notNull(findFeatureId, "findFeatureId is mandatory");
        Feature feature = featureDao.findById(Feature.class, findFeatureId);
        notNull(feature, "features is not found by id");
        return feature;
    }

    /**
     * Searches for all options in database.
     *
     * @return all options data and total count
     */
    @Override
    @Transactional(readOnly = true)
    public SearchResult<Feature> getAllFeatures() {
        Long resultsCount = featureDao.getTotalCount(Feature.class);
        List<Feature> features = featureDao.findAll(Feature.class);
        return new SearchResult<>(resultsCount, features);
    }

    @Override
    @Transactional
    public List<Feature> getListedFeatures(String ids) {
        return new ArrayList<>(new HashSet<>(featureDao.getListed(idsFromStringToList(ids))));
    }

    /**
     * Resolves options available for contract with some plan and some already activated options.
     *
     * @param ids string with comma separated ids of already active options
     * @param planId id of plan applied to contract
     *
     * @return available options ids list
     */
    @Override
    @Transactional
    public List<Long> getAvailableFeatures(String ids, Long planId) {
        notNull(planId, "planId is mandatory");
        notNull(ids, "ids is mandatory");
        List<Feature> features = featureDao.getListed(idsFromStringToList(ids));
        Plan plan = planDao.findById(Plan.class, planId);
        notNull(plan, "plan is not found by id");
        List<Feature> avaiableFeatures = new ArrayList<>(plan.getAllowedFeatures());
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
        Set<Long> numbers = new HashSet<>();
        avaiableFeatures.stream().forEach(f -> numbers.add(f.getId()));
        return new ArrayList<>(numbers);
    }

    /**
     * Deletes option by id.
     *
     * @param deleteFeatureId id of option to delete
     */
    @Override
    @Transactional
    public void deleteFeature(Long deleteFeatureId) {
        notNull(deleteFeatureId, "deleteFeatureId is mandatory");

        Feature feature = featureDao.findById(Feature.class, deleteFeatureId);

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

    /**
     * Updates link between two options.
     *
     * @param feature1Id id of the first option
     * @param feature2Id id of the second option
     * @param block true if block link bust be updated, false if - dependency link
     * @param linked true if link must be set, false - if deleted
     */
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

    private List<Long> idsFromStringToList(String ids) {
        List<Long> numbers = new ArrayList<>();
        try {
            String[] splitted = "".equals(ids.trim()) ? new String[]{} : ids.split(",");
            Arrays.asList(splitted).stream().forEach(s -> numbers.add(Long.parseLong(s)));
        } catch (Exception ex) {
            throw new IllegalArgumentException("wrong ids string format", ex);
        }
        return numbers;
    }
}
