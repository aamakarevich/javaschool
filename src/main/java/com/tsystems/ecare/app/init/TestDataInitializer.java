package com.tsystems.ecare.app.init;

import com.tsystems.ecare.app.model.Address;
import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.model.Feature;
import com.tsystems.ecare.app.model.Plan;
import com.tsystems.ecare.app.model.Role;
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

    /**
     * Fills out database with bench of test data.
     */
    public void init() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Role roleAdmin = new Role("admin");
        entityManager.persist(roleAdmin);
        Role roleManager = new Role("manager");
        entityManager.persist(roleManager);

        Customer customerAdmin = new Customer("Admin", "Admin", new Date(),
                "passport", "aadmin@ecare.com", HashUtils.sha256("Admin"),
                new Address("Admin street", "10", "Admin city"));
        customerAdmin.getRoles().add(roleAdmin);
        entityManager.persist(customerAdmin);

        Customer customerManager = new Customer("Manager", "Manager", new Date(),
                "passport", "mmanager@ecare.com", HashUtils.sha256("Manager"),
                new Address("Manager street", "10", "Manager city"));
        customerManager.getRoles().add(roleManager);
        entityManager.persist(customerManager);

        Customer customerUser = new Customer("User", "User", new Date(),
                "passport", "uuser@ecare.com", HashUtils.sha256("User"),
                new Address("User street", "10", "User city"));
        entityManager.persist(customerUser);

        Feature feature1 = new Feature("feature1", "description1", new BigDecimal(1), new BigDecimal(1));
        entityManager.persist(feature1);
        Feature feature2 = new Feature("feature2", "description2", new BigDecimal(2), new BigDecimal(2));
        entityManager.persist(feature2);
        Feature feature3 = new Feature("feature3", "description3", new BigDecimal(3), new BigDecimal(3));
        entityManager.persist(feature3);

        Plan plan1 = new Plan("plan1", "description1", new BigDecimal(1));
        entityManager.persist(plan1);
        Plan plan2 = new Plan("plan2", "description2", new BigDecimal(2));
        plan2.getAllowedFeatures().add(feature1);
        plan2.getAllowedFeatures().add(feature2);
        entityManager.persist(plan2);
        Plan plan3 = new Plan("plan3", "description3", new BigDecimal(3));
        entityManager.persist(plan3);

        entityManager.getTransaction().commit();
    }
}
