package com.tsystems.ecare.app.services.impl;

import com.tsystems.ecare.app.dao.FeatureDao;
import com.tsystems.ecare.app.dao.impl.FeatureDaoImpl;
import com.tsystems.ecare.app.model.Feature;
import com.tsystems.ecare.app.model.Plan;
import com.tsystems.ecare.app.services.FeatureService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Andrei Makarevich
 */
public class FeatureServiceImpl implements FeatureService {

    FeatureDao featureDao = new FeatureDaoImpl();

    @Override
    public Feature saveNewFeature(Feature feature) {
        featureDao.beginTransaction();
        feature = featureDao.save(feature);
        featureDao.commitTransaction();
        return feature;
    }

    @Override
    public Feature getFeature(Integer id) {
        return featureDao.findById(Feature.class, id);
    }

    @Override
    public Feature updateFeature(Feature feature) {
        featureDao.beginTransaction();
        Feature oldFeature = null;// getFeature(feature.getId());
        oldFeature.setTitle(feature.getTitle());
        oldFeature.setMonthlyFee(feature.getMonthlyFee());
        oldFeature.setAdditionFee(feature.getAdditionFee());
        oldFeature.setDescription(feature.getDescription());
        feature = featureDao.merge(oldFeature);
        featureDao.commitTransaction();
        return feature;
    }

    @Override
    public void deleteFeature(Feature feature) {

        try {
            featureDao.beginTransaction();
            for (Feature f : feature.getBlockedFeatures()) {
                f.getBlockers().remove(feature);
                featureDao.merge(f);
            }
            for (Feature f : feature.getBlockers()) {
                f.getBlockedFeatures().remove(feature);
                featureDao.merge(f);
            }
            for (Feature f : feature.getNeededFeatures()) {
                f.getDependentFeatures().remove(feature);
                featureDao.merge(f);
            }
            for (Feature f : feature.getDependentFeatures()) {
                f.getNeededFeatures().remove(feature);
                featureDao.merge(f);
            }
            for (Plan p : feature.getPlans()) {
                p.getAllowedFeatures().remove(feature);
            }
            feature = featureDao.merge(feature);
            featureDao.delete(feature);

            featureDao.commitTransaction();
        } catch (Exception ex) {

            featureDao.rollbackTransaction();
        }
    }

    @Override
    public List<Feature> getAllFeatures() {
        return featureDao.findAll(Feature.class);
    }

    @Override
    public List<Feature> getListedFeatures(List<Integer> ids) {
        return featureDao.getListed(ids);
    }

    @Override
    public List<Feature> getAvailableFeatures(List<Integer> ids, Integer planId) {

        List<Feature> features = getListedFeatures(ids);
        if(features == null) {
            features = new ArrayList<>();
        }
        Plan plan = new PlanServiceImpl().getPlan(planId);
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

    @Override
    public void setBlock(Feature f1, Feature f2) {
        try {
            featureDao.beginTransaction();

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

            featureDao.merge(f1);
            featureDao.merge(f2);

            featureDao.commitTransaction();
        } catch (Exception ex) {
            featureDao.rollbackTransaction();
        }
    }

    @Override
    public void deleteBlock(Feature f1, Feature f2) {
        try {
            featureDao.beginTransaction();

            f1.getBlockedFeatures().remove(f2);
            f1.getBlockers().remove(f2);

            f2.getBlockedFeatures().remove(f1);
            f2.getBlockers().remove(f1);

            featureDao.merge(f1);
            featureDao.merge(f2);

            featureDao.commitTransaction();
        } catch (Exception ex) {
            featureDao.rollbackTransaction();
        }
    }

    @Override
    public void setDependency(Feature dependent, Feature dependence) {
        try {
            featureDao.beginTransaction();

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

            featureDao.merge(dependent);
            featureDao.merge(dependence);

            featureDao.commitTransaction();
        } catch (Exception ex) {
            featureDao.rollbackTransaction();
        }
    }

    @Override
    public void deleteDependency(Feature dependent, Feature dependence) {
        try {
            featureDao.beginTransaction();

            dependent.getNeededFeatures().remove(dependence);
            dependent.getDependentFeatures().remove(dependence);

            dependence.getNeededFeatures().remove(dependent);
            dependence.getDependentFeatures().remove(dependent);

            featureDao.merge(dependent);
            featureDao.merge(dependence);

            featureDao.commitTransaction();
        } catch (Exception ex) {
            featureDao.rollbackTransaction();
        }
    }
}
