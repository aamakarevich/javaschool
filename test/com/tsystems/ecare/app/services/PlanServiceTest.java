package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Feature;
import com.tsystems.ecare.app.model.Plan;
import com.tsystems.ecare.app.model.SearchResult;
import com.tsystems.ecare.config.root.RootContextConfig;
import com.tsystems.ecare.config.root.TestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.tsystems.ecare.app.utils.TestUtils.getStringOfLength;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes={TestConfiguration.class, RootContextConfig.class})
public class PlanServiceTest {

    @Autowired
    private PlanService planService;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void savePlanNew() {
        Plan plan = planService.savePlan(null, "title", "description", new BigDecimal(10));
        Plan gotPlan = em.find(Plan.class, plan.getId());
        assertEquals(gotPlan.getTitle(), "title");
        assertEquals(gotPlan.getDescription(), "description");
        assertEquals(gotPlan.getMonthlyFee(), new BigDecimal(10));
    }

    @Test
    public void savePlanExisting() {
        planService.savePlan(1l, "title", "description", new BigDecimal(10));
        Plan gotPlan = em.find(Plan.class, 1l);
        assertEquals(gotPlan.getTitle(), "title");
        assertEquals(gotPlan.getDescription(), "description");
        assertEquals(gotPlan.getMonthlyFee(), new BigDecimal(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void savePlanWithNonexistentId() {
        planService.savePlan(0l, "title", "description", new BigDecimal(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void savePlanWithBlankTitle() {
        planService.savePlan(null, "", "description", new BigDecimal(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void savePlanWithTooLongTitle() {
        planService.savePlan(null, getStringOfLength(41), "description", new BigDecimal(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void savePlanWithBlankDescription() {
        planService.savePlan(null, "title", "", new BigDecimal(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void savePlanWithTooLongDescription() {
        planService.savePlan(null, "title", getStringOfLength(1001), new BigDecimal(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void savePlanWithNullMonthlyFee() {
        planService.savePlan(null, "title", "description", null);
    }

    @Test
    public void getPlan() {
        Plan plan = planService.getPlan(2l);
        assertEquals(plan.getTitle(), "plan2");
        assertEquals(plan.getDescription(), "description2");
        assertEquals(plan.getMonthlyFee(), new BigDecimal(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getPlanWithNonexistentId() {
        planService.getPlan(0l);
    }

    @Test
    public void getAllPlans() {
        List<Plan> expectedPlans = em.createQuery("from " + Plan.class.getName()).getResultList();

        SearchResult<Plan> gotPlans = planService.getAllPlans();

        assertEquals(gotPlans.getResult().size(), gotPlans.getResultsCount());
        assertEquals(gotPlans.getResult().size(), expectedPlans.size());
        assertTrue(gotPlans.getResult().containsAll(expectedPlans));
    }

    @Test
    public void deletePlan() {
        planService.deletePlan(1l);
        assertNull(em.find(Plan.class, 1l));
    }

    @Test
    public void changeAllowedFeature() {
        Plan plan = em.find(Plan.class, 2l);
        Feature feature1 = em.find(Feature.class, 1l);
        Feature feature2 = em.find(Feature.class, 2l);
        Feature feature3 = em.find(Feature.class, 3l);

        assertTrue(plan.getAllowedFeatures().contains(feature1));
        assertTrue(plan.getAllowedFeatures().contains(feature2));
        assertFalse(plan.getAllowedFeatures().contains(feature3));

        planService.changeAllowedFeature(2l, 1l, false);
        planService.changeAllowedFeature(2l, 3l, true);

        plan = em.find(Plan.class, 2l);
        feature1 = em.find(Feature.class, 1l);
        feature2 = em.find(Feature.class, 2l);
        feature3 = em.find(Feature.class, 3l);

        assertFalse(plan.getAllowedFeatures().contains(feature1));
        assertTrue(plan.getAllowedFeatures().contains(feature2));
        assertTrue(plan.getAllowedFeatures().contains(feature3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeAllowedFeatureWithNonexistentPlanId() {
        planService.changeAllowedFeature(0l, 1l, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeAllowedFeatureWithNonexistentFeatureId() {
        planService.changeAllowedFeature(2l, 0l, false);
    }
}