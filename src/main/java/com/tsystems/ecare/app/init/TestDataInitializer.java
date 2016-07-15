package com.tsystems.ecare.app.init;

import com.tsystems.ecare.app.model.Address;
import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.model.Feature;
import com.tsystems.ecare.app.model.Plan;
import com.tsystems.ecare.app.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.Date;

/**
 * This is a initializing bean that inserts some test data in the database.
 */
@Component
public class TestDataInitializer {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    /**
     * Fills out database with bench of test data.
     */
    public void init() {
        entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Customer customer1 = createTestCustomer(1);
        Customer customer2 = createTestCustomer(2);
        Customer customer3 = createTestCustomer(3);

        Feature feature1 = createTestFeature(1);
        Feature feature2 = createTestFeature(2);
        Feature feature3 = createTestFeature(3);

        Feature feature4 = createTestFeature(4);

        Feature feature5 = createTestFeature(5);
        Feature feature6 = createTestFeature(6);
        Feature feature7 = createTestFeature(7);
        feature5.getBlockers().add(feature6);
        feature6.getBlockers().add(feature5);
        feature5.getNeededFeatures().add(feature7);

        Plan plan1 = createTestPlan(1);
        Plan plan2 = createTestPlan(2);
        Plan plan3 = createTestPlan(3);
        plan2.getAllowedFeatures().add(feature1);
        plan2.getAllowedFeatures().add(feature2);

        Plan plan4 = createTestPlan(4);
        Plan plan5 = createTestPlan(5);
        Plan plan6 = createTestPlan(6);
        plan4.getAllowedFeatures().add(feature5);
        plan4.getAllowedFeatures().add(feature6);
        plan4.getAllowedFeatures().add(feature7);

        Feature feature8 = createTestFeature(8);
        Feature feature9 = createTestFeature(9);
        Feature feature10 = createTestFeature(10);
        Feature feature11 = createTestFeature(11);
        Feature feature12 = createTestFeature(12);
        feature8.getBlockers().add(feature9);
        feature9.getBlockers().add(feature8);
        feature8.getNeededFeatures().add(feature10);

        entityManager.getTransaction().commit();
    }

    private Customer createTestCustomer(int id) {
        Customer customer = new Customer("firstname" + id, "lastname" + id, new Date(), "passport" + id,
                "user"  + id + "@ecare.com", HashUtils.sha256("user" + id),
                new Address("street" + id, Integer.toString(id), "city" + id));
        entityManager.persist(customer);
        return customer;
    }

    private Feature createTestFeature(int id) {
        Feature feature = new Feature("feature" + id, "description" + id, new BigDecimal(id), new BigDecimal(id));
        entityManager.persist(feature);
        return feature;
    }

    private Plan createTestPlan(int id) {
        Plan plan = new Plan("plan" + id, "description" + id, new BigDecimal(id));
        entityManager.persist(plan);
        return plan;
    }


}
