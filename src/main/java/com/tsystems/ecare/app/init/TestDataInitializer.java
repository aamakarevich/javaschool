package com.tsystems.ecare.app.init;

import com.tsystems.ecare.app.model.Address;
import com.tsystems.ecare.app.model.Contract;
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

    private EntityManager entityManager;

    /**
     * Fills out database with bench of test data.
     */
    public void init() {
        entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        createTestCustomer(1);
        createTestCustomer(2);
        createTestCustomer(3);

        Role role1 = createTestRole(1);
        Role role2 = createTestRole(2);

        Customer customer4 = createTestCustomer(4);
        Customer customer5 = createTestCustomer(5);
        customer4.getRoles().add(role1);
        customer5.getRoles().add(role2);

        Feature feature1 = createTestFeature(1);
        Feature feature2 = createTestFeature(2);
        createTestFeature(3);

        createTestFeature(4);

        Feature feature5 = createTestFeature(5);
        Feature feature6 = createTestFeature(6);
        Feature feature7 = createTestFeature(7);
        feature5.getBlockers().add(feature6);
        feature6.getBlockers().add(feature5);
        feature5.getNeededFeatures().add(feature7);

        createTestPlan(1);
        Plan plan2 = createTestPlan(2);
        createTestPlan(3);
        plan2.getAllowedFeatures().add(feature1);
        plan2.getAllowedFeatures().add(feature2);

        Plan plan4 = createTestPlan(4);
        createTestPlan(5);
        createTestPlan(6);
        plan4.getAllowedFeatures().add(feature5);
        plan4.getAllowedFeatures().add(feature6);
        plan4.getAllowedFeatures().add(feature7);

        Feature feature8 = createTestFeature(8);
        Feature feature9 = createTestFeature(9);
        Feature feature10 = createTestFeature(10);
        createTestFeature(11);
        createTestFeature(12);
        feature8.getBlockers().add(feature9);
        feature9.getBlockers().add(feature8);
        feature8.getNeededFeatures().add(feature10);

        Plan plan7 = createTestPlan(7);
        for (int i = 6; i < 100; i++) {
            createTestContract(i, createTestCustomer(i), plan7);
        }

        Customer customer100 = createTestCustomer(100);
        Plan plan8 = createTestPlan(8);
        createTestContract(95, customer100, plan8);
        Contract contract96 = createTestContract(96, customer100, plan8);
        contract96.setNumberLock(Contract.Lock.LOCKED);
        Contract contract97 = createTestContract(97, customer100, plan8);
        contract97.setNumberLock(Contract.Lock.USERLOCKED);
        Contract contract98 = createTestContract(98, customer100, plan8);
        contract98.setNumberLock(Contract.Lock.LOCKED);
        Contract contract99 = createTestContract(99, customer100, plan8);
        contract99.setNumberLock(Contract.Lock.USERLOCKED);

        entityManager.getTransaction().commit();
    }

    private Contract createTestContract(int id, Customer customer, Plan plan) {
        String number = "";
        for (int i = 0; i < 10 - Integer.toString(id).length(); i++) {
            number += "0";
        }
        number += Integer.toString(id);
        Contract contract = new Contract(number, customer, plan, Contract.Lock.UNLOCKED);
        entityManager.persist(contract);
        return contract;
    }

    private Customer createTestCustomer(int id) {
        Customer customer = new Customer("firstname" + id, "lastname" + id, new Date(), "passport" + id,
                "user"  + id + "@ecare.com", HashUtils.sha256("user" + id),
                new Address("street" + id, "building" + id, "city" + id));
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

    private Role createTestRole(int id) {
        Role role = new Role("role" + id);
        entityManager.persist(role);
        return role;
    }


}
