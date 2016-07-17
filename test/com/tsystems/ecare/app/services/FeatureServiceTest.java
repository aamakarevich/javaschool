package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Feature;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.tsystems.ecare.app.utils.TestUtils.getStringOfLength;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for FeatureService.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes={TestConfiguration.class, RootContextConfig.class})
public class FeatureServiceTest {

    @Autowired
    private FeatureService featureService;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void saveFeatureNew() {
        Feature feature = featureService.saveFeature(null, "title", "description", new BigDecimal(10), new BigDecimal(10));
        Feature gotFeature = em.find(Feature.class, feature.getId());
        assertEquals(gotFeature.getTitle(), "title");
        assertEquals(gotFeature.getDescription(), "description");
        assertEquals(gotFeature.getAdditionFee(), new BigDecimal(10));
        assertEquals(gotFeature.getMonthlyFee(), new BigDecimal(10));
    }

    @Test
    public void saveFeatureExisting() {
        featureService.saveFeature(1l, "title", "description", new BigDecimal(10), new BigDecimal(10));
        Feature gotFeature = em.find(Feature.class, 1l);
        assertEquals(gotFeature.getTitle(), "title");
        assertEquals(gotFeature.getDescription(), "description");
        assertEquals(gotFeature.getMonthlyFee(), new BigDecimal(10));
        assertEquals(gotFeature.getAdditionFee(), new BigDecimal(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveFeatureWithNonexistentId() {
        featureService.saveFeature(0l, "title", "description", new BigDecimal(10), new BigDecimal(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveFeatureWithBlankTitle() {
        featureService.saveFeature(null, "", "description", new BigDecimal(10), new BigDecimal(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveFeatureWithTooLongTitle() {
        featureService.saveFeature(null, getStringOfLength(41), "description", new BigDecimal(10), new BigDecimal(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveFeatureWithBlankDescription() {
        featureService.saveFeature(null, "title", "", new BigDecimal(10), new BigDecimal(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveFeatureWithTooLongDescription() {
        featureService.saveFeature(null, "title", getStringOfLength(1001), new BigDecimal(10), new BigDecimal(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveFeatureWithNullAdditionFee() {
        featureService.saveFeature(null, "title", "description", null, new BigDecimal(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveFeatureWithNullMonthlyFee() {
        featureService.saveFeature(null, "title", "description", new BigDecimal(10), null);
    }

    @Test
    public void getFeature() {
        Feature feature = featureService.getFeature(2l);
        assertEquals(feature.getTitle(), "feature2");
        assertEquals(feature.getDescription(), "description2");
        assertEquals(feature.getAdditionFee(), new BigDecimal(2));
        assertEquals(feature.getMonthlyFee(), new BigDecimal(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFeatureWithNonexistentId() {
        featureService.getFeature(0l);
    }

    @Test
    public void getAllFeatures() {
        List<Feature> expectedFeatures = em.createQuery("from " + Feature.class.getName()).getResultList();

        SearchResult<Feature> gotFeatures = featureService.getAllFeatures();

        assertEquals(gotFeatures.getResult().size(), gotFeatures.getResultsCount());
        assertEquals(gotFeatures.getResult().size(), expectedFeatures.size());
        assertTrue(gotFeatures.getResult().containsAll(expectedFeatures));
    }

    @Test
    public void getListedFeaturesExact() {
        Set<Feature> expectedFeatures = new HashSet<>();
        expectedFeatures.add(em.find(Feature.class, 1l));
        expectedFeatures.add(em.find(Feature.class, 2l));
        expectedFeatures.add(em.find(Feature.class, 3l));
        expectedFeatures.remove(null);

        List<Feature> gotFeatures = featureService.getListedFeatures("1,2,3");
        assertEquals(gotFeatures.size(), expectedFeatures.size());
        assertTrue(gotFeatures.containsAll(expectedFeatures));
    }

    @Test
    public void getListedFeaturesMore() {
        Set<Feature> expectedFeatures = new HashSet<>();
        expectedFeatures.add(em.find(Feature.class, 1l));
        expectedFeatures.add(em.find(Feature.class, 2l));
        expectedFeatures.add(em.find(Feature.class, 3l));
        expectedFeatures.remove(null);

        List<Feature> gotFeatures = featureService.getListedFeatures("1,2,3,100,200,300");
        assertEquals(gotFeatures.size(), expectedFeatures.size());
        assertTrue(gotFeatures.containsAll(expectedFeatures));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getListedFeaturesWithIngalidIds() {
        featureService.getListedFeatures("invalid");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getListedFeaturesWithNullIds() {
        featureService.getListedFeatures(null);
    }

    @Test
    public void getAvailableFeatures() {
        List<Long> expectedIds1 = new ArrayList<>();
        expectedIds1.add(7l);
        List<Long> expectedIds2 = new ArrayList<>();
        List<Long> expectedIds3 = new ArrayList<>();
        expectedIds3.add(5l);
        expectedIds3.add(6l);
        List<Long> expectedIds4 = new ArrayList<>();
        expectedIds4.add(6l);
        expectedIds4.add(7l);

        List<Long> gotIds1 = featureService.getAvailableFeatures("6", 4l);
        assertEquals(gotIds1.size(), expectedIds1.size());
        assertTrue(gotIds1.containsAll(expectedIds1));
        List<Long> gotIds2 = featureService.getAvailableFeatures("6,7", 4l);
        assertEquals(gotIds2.size(), expectedIds2.size());
        assertTrue(gotIds2.containsAll(expectedIds2));
        List<Long> gotIds3 = featureService.getAvailableFeatures("7", 4l);
        assertEquals(gotIds3.size(), expectedIds3.size());
        assertTrue(gotIds3.containsAll(expectedIds3));
        List<Long> gotIds4 = featureService.getAvailableFeatures("", 4l);
        assertEquals(gotIds4.size(), expectedIds4.size());
        assertTrue(gotIds4.containsAll(expectedIds4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAvailableFeaturesWithNullPlanId() {
        featureService.getAvailableFeatures("", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAvailableFeaturesWithNonexistentPlanId() {
        featureService.getAvailableFeatures("", 0L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAvailableFeaturesWithNullIds() {
        featureService.getAvailableFeatures(null, 4L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAvailableFeaturesWithInvalidIds() {
        featureService.getAvailableFeatures("invalid", 4L);
    }

    @Test
    public void deleteFeature() {
        featureService.deleteFeature(4L);
        assertNull(em.find(Feature.class, 4L));
    }

    @Test
    public void changeLinkedFeatureBlock() {
        Feature feature1 = em.find(Feature.class, 8L);
        Feature feature2 = em.find(Feature.class, 12L);
        assertTrue(!feature1.getBlockers().contains(feature2));
        assertTrue(!feature2.getBlockers().contains(feature1));
        featureService.changeLinkedFeature(8L, 12L, true, true);
        feature1 = em.find(Feature.class, 8L);
        feature2 = em.find(Feature.class, 12L);
        assertTrue(feature1.getBlockers().contains(feature2));
        assertTrue(feature2.getBlockers().contains(feature1));
    }

    @Test
    public void changeLinkedFeatureUnblock() {
        Feature feature1 = em.find(Feature.class, 8L);
        Feature feature2 = em.find(Feature.class, 9L);
        assertTrue(feature1.getBlockers().contains(feature2));
        assertTrue(feature2.getBlockers().contains(feature1));
        featureService.changeLinkedFeature(8L, 9L, true, false);
        feature1 = em.find(Feature.class, 8L);
        feature2 = em.find(Feature.class, 9L);
        assertTrue(!feature1.getBlockers().contains(feature2));
        assertTrue(!feature2.getBlockers().contains(feature1));
    }

    @Test
    public void changeLinkedFeatureNeed() {
        Feature feature1 = em.find(Feature.class, 8L);
        Feature feature2 = em.find(Feature.class, 11L);
        assertTrue(!feature1.getNeededFeatures().contains(feature2));
        assertTrue(!feature2.getNeededFeatures().contains(feature1));
        featureService.changeLinkedFeature(11L, 8L, false, true);
        feature1 = em.find(Feature.class, 8L);
        feature2 = em.find(Feature.class, 11L);
        assertTrue(feature1.getNeededFeatures().contains(feature2));
    }

    @Test
    public void changeLinkedFeatureNoneed() {
        Feature feature1 = em.find(Feature.class, 8L);
        Feature feature2 = em.find(Feature.class, 10L);
        assertTrue(feature1.getNeededFeatures().contains(feature2));
        featureService.changeLinkedFeature(8L, 10L, false, false);
        feature1 = em.find(Feature.class, 8L);
        feature2 = em.find(Feature.class, 10L);
        assertTrue(!feature1.getNeededFeatures().contains(feature2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeLinkedFeatureWithNullFeature1() {
        featureService.changeLinkedFeature(null, 8l, true, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeLinkedFeatureWithNonexistentFeature1() {
        featureService.changeLinkedFeature(0l, 8l, true, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeLinkedFeatureWithNullFeature2() {
        featureService.changeLinkedFeature(8l, null, true, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeLinkedFeatureWithNonexistentFeature2() {
        featureService.changeLinkedFeature(8l, 0l, true, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeLinkedFeatureWithNullBlock() {
        featureService.changeLinkedFeature(8l, 9l, null, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeLinkedFeatureWithNullLinked() {
        featureService.changeLinkedFeature(8l, 9l, true, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeLinkedFeatureLinkToItself() {
        featureService.changeLinkedFeature(8l, 8l, true, true);
    }
}