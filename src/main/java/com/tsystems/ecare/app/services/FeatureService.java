package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.dao.impl.FeatureRepository;
import com.tsystems.ecare.app.model.Feature;
import com.tsystems.ecare.app.model.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class FeatureService {

    @Autowired
    FeatureRepository repository;

    @Transactional
    public Feature saveFeature(Feature feature) {
        return repository.save(feature);
    }

    @Transactional
    public Feature getFeature(Integer id) {
        return repository.findById(Feature.class, id);
    }

    @Transactional
    public Feature updateFeature(Feature feature) {
        Feature oldFeature = null;// getFeature(feature.getId());
        oldFeature.setTitle(feature.getTitle());
        oldFeature.setMonthlyFee(feature.getMonthlyFee());
        oldFeature.setAdditionFee(feature.getAdditionFee());
        oldFeature.setDescription(feature.getDescription());
        feature = repository.save(oldFeature);
        return feature;
    }

    @Transactional
    public void deleteFeature(Feature feature) {

        for (Feature f : feature.getBlockedFeatures()) {
            f.getBlockers().remove(feature);
            repository.save(f);
        }
        for (Feature f : feature.getBlockers()) {
            f.getBlockedFeatures().remove(feature);
            repository.save(f);
        }
        for (Feature f : feature.getNeededFeatures()) {
            f.getDependentFeatures().remove(feature);
            repository.save(f);
        }
        for (Feature f : feature.getDependentFeatures()) {
            f.getNeededFeatures().remove(feature);
            repository.save(f);
        }
        for (Plan p : feature.getPlans()) {
            p.getAllowedFeatures().remove(feature);
        }
        feature = repository.save(feature);
        repository.delete(feature);
    }

    @Transactional
    public List<Feature> getAllFeatures() {
        return repository.findAll(Feature.class);
    }

    @Transactional
    public List<Feature> getListedFeatures(List<Integer> ids) {
        return repository.getListed(ids);
    }

    @Transactional
    public List<Feature> getAvailableFeatures(List<Integer> ids, Integer planId) {

        List<Feature> features = getListedFeatures(ids);
        if (features == null) {
            features = new ArrayList<>();
        }
        Plan plan = new PlanService().getPlan(planId);
        List<Feature> avaiableFeatures = plan.getAllowedFeatures();
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

    @Transactional
    public void setBlock(Feature f1, Feature f2) {

        f1.getBlockedFeatures().remove(f2);
        f1.getBlockers().remove(f2);
        f1.getNeededFeatures().remove(f2);
        f1.getDependentFeatures().remove(f2);

        f2.getBlockedFeatures().remove(f1);
        f2.getBlockers().remove(f1);
        f2.getNeededFeatures().remove(f1);
        f2.getDependentFeatures().remove(f1);

        f1.getBlockers().add(f2);
        f1.getBlockedFeatures().add(f2);

        f2.getBlockers().add(f1);
        f2.getBlockedFeatures().add(f1);

        repository.save(f1);
        repository.save(f2);
    }

    @Transactional
    public void deleteBlock(Feature f1, Feature f2) {
        f1.getBlockedFeatures().remove(f2);
        f1.getBlockers().remove(f2);

        f2.getBlockedFeatures().remove(f1);
        f2.getBlockers().remove(f1);

        repository.save(f1);
        repository.save(f2);
    }

    @Transactional
    public void setDependency(Feature dependent, Feature dependence) {

        dependent.getBlockedFeatures().remove(dependence);
        dependent.getBlockers().remove(dependence);
        dependent.getNeededFeatures().remove(dependence);
        dependent.getDependentFeatures().remove(dependence);

        dependence.getBlockedFeatures().remove(dependent);
        dependence.getBlockers().remove(dependent);
        dependence.getNeededFeatures().remove(dependent);
        dependence.getDependentFeatures().remove(dependent);

        dependent.getNeededFeatures().add(dependence);
        dependence.getDependentFeatures().add(dependent);

        repository.save(dependent);
        repository.save(dependence);

    }

    @Transactional
    public void deleteDependency(Feature dependent, Feature dependence) {

        dependent.getNeededFeatures().remove(dependence);
        dependent.getDependentFeatures().remove(dependence);

        dependence.getNeededFeatures().remove(dependent);
        dependence.getDependentFeatures().remove(dependent);

        repository.save(dependent);
        repository.save(dependence);
    }
}
