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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This is a initializing bean that inserts some test data in the database.
 */
@Component
public class TestDataInitializer {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    private static Map<String, Integer> emails = new HashMap<>();
    private static List<String> firstnames = new ArrayList<>();
    private static List<String> lastnames = new ArrayList<>();
    private static List<String> cities = new ArrayList<>();
    private static List<String> streets = new ArrayList<>();

    /**
     * Fills out database with initial data for demonstration.
     */
    public void initDataForDemo() {
        entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Role roleAdmin = new Role("admin");
        entityManager.persist(roleAdmin);
        Role roleManager = new Role("manager");
        entityManager.persist(roleManager);

        Customer customerAlbusDumbledore = new Customer("Albus", "Dumbledore", new Date(-19, 7, 26), "6775 524422",
                "adumble@ecare.com", HashUtils.sha256("admin"), new Address("Headmaster's room", null, "Hogwarts"));
        customerAlbusDumbledore.getRoles().add(roleAdmin);
        customerAlbusDumbledore.getRoles().add(roleManager);
        entityManager.persist(customerAlbusDumbledore);

        Customer customerHarryPotter = new Customer("Harry", "Potter", new Date(80, 6, 31), "0812 124838",
                "hpotter@ecare.com", HashUtils.sha256("admin"), new Address("Private drive", "4, closet under stairs", "Little Winging"));
        customerHarryPotter.getRoles().add(roleManager);
        entityManager.persist(customerHarryPotter);

        Customer customerRonWeasley = new Customer("Ron", "Weasley", new Date(80, 2, 1), "7654 433456",
                "rweasle@ecare.com", HashUtils.sha256("admin"), new Address("The Burrow", null, "Ottery St Catchpole"));
        entityManager.persist(customerRonWeasley);

        Customer customerHermioneGranger = new Customer("Hermione", "Granger", new Date(79, 8, 19), "0812 555555",
                "hgrange@ecare.com", HashUtils.sha256("admin"), new Address("Diagon alley", "13", "London"));
        entityManager.persist(customerHermioneGranger);

        Feature feature1 = new Feature("Care everywhere", "Free calls and sms inside eCare network in roaming. Best choice for touristic trip. Call your grandmother every evening!", new BigDecimal(5), new BigDecimal(20));
        Feature feature2 = new Feature("Forever Alone", "Unlimited traffic. Really. For sure. Watch TV series, play WoW, do whatever you want to do.", new BigDecimal(0), new BigDecimal(20));
        Feature feature3 = new Feature("V for Vendetta", "All your calls become anonymised. No one never ever knows that you are the person who called.", new BigDecimal(10), new BigDecimal(10));
        Feature feature4 = new Feature("One shot one kill", "First call every day is free of charge, no matter how long it is. That's really cool, isn't it?", new BigDecimal(5), new BigDecimal(5));
        Feature feature5 = new Feature("Race conditions", "All outgoing calls are free of charge, but incoming cost due to price. Always be the one who calls first!", new BigDecimal(0), new BigDecimal(20));
        Feature feature6 = new Feature("Count on T", "Free calls to numbers of T-mobile network. Work and grow together with your T-friends.", new BigDecimal(5), new BigDecimal(10));
        entityManager.persist(feature1);
        entityManager.persist(feature2);
        entityManager.persist(feature3);
        entityManager.persist(feature4);
        entityManager.persist(feature5);
        entityManager.persist(feature6);
        feature3.getBlockers().add(feature4);
        feature4.getBlockers().add(feature3);
        feature3.getBlockers().add(feature5);
        feature5.getBlockers().add(feature3);
        feature6.getBlockedFeatures().add(feature1);
        feature3.getNeededFeatures().add(feature2);


        Plan plan1 = new Plan("Limitless", "Unlimited calls, SMS, and traffic. Never ever stop in this life.", new BigDecimal(60));
        plan1.getAllowedFeatures().add(feature1);
        plan1.getAllowedFeatures().add(feature2);
        plan1.getAllowedFeatures().add(feature3);
        plan1.getAllowedFeatures().add(feature4);
        plan1.getAllowedFeatures().add(feature5);
        plan1.getAllowedFeatures().add(feature6);
        Plan plan2 = new Plan("Munchkin", "Unlimited traffic, calls and sms according to normal price.", new BigDecimal(30));
        plan2.getAllowedFeatures().add(feature1);
        plan2.getAllowedFeatures().add(feature3);
        plan2.getAllowedFeatures().add(feature4);
        plan2.getAllowedFeatures().add(feature6);
        Plan plan3 = new Plan("Big and fat plan", "Most expensive plan ever. Just for customers who are rich enough.", new BigDecimal(1000));
        plan3.getAllowedFeatures().add(feature1);
        plan3.getAllowedFeatures().add(feature2);
        plan3.getAllowedFeatures().add(feature3);
        plan3.getAllowedFeatures().add(feature4);
        plan3.getAllowedFeatures().add(feature5);
        plan3.getAllowedFeatures().add(feature6);
        Plan plan4 = new Plan("Zero tolerance", "Calls, sms and traffic are tarifficated due to price. But zero fee for month.", new BigDecimal(0));
        plan4.getAllowedFeatures().add(feature1);
        plan4.getAllowedFeatures().add(feature2);
        plan4.getAllowedFeatures().add(feature3);
        plan4.getAllowedFeatures().add(feature4);
        plan4.getAllowedFeatures().add(feature5);
        plan4.getAllowedFeatures().add(feature6);
        Plan plan5 = new Plan("Last man standing", "Unlimited sms. Internet access and voice calls are totally switched off.", new BigDecimal(15));
        entityManager.persist(plan1);
        entityManager.persist(plan2);
        entityManager.persist(plan3);
        entityManager.persist(plan4);
        entityManager.persist(plan5);

        Contract contract9142212666 = new Contract("9142212666", customerRonWeasley, plan4, Contract.Lock.UNLOCKED);
        Contract contract9142222667 = new Contract("9142222667", customerHarryPotter, plan4, Contract.Lock.UNLOCKED);
        entityManager.persist(contract9142212666);
        entityManager.persist(contract9142222667);

        for (int i = 0; i < 100; i++) {
            createTestCustomer();
        }

        entityManager.getTransaction().commit();
    }

    /**
     * Fills out database with bench of test data.
     */
    public void initDataForUnitTests() {
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
        plan8.getAllowedFeatures().add(feature1);
        plan8.getAllowedFeatures().add(feature2);
        createTestContract(95, customer100, plan8);
        Contract contract96 = createTestContract(96, customer100, plan8);
        contract96.setNumberLock(Contract.Lock.LOCKED);
        Contract contract97 = createTestContract(97, customer100, plan8);
        contract97.setNumberLock(Contract.Lock.USERLOCKED);
        Contract contract98 = createTestContract(98, customer100, plan8);
        contract98.setNumberLock(Contract.Lock.LOCKED);
        Contract contract99 = createTestContract(99, customer100, plan8);
        contract99.setNumberLock(Contract.Lock.USERLOCKED);
        contract99.getActiveFeatures().add(feature1);
        contract99.getActiveFeatures().add(feature2);
        Contract contract100 = new Contract("9142212666", customer100, plan8, Contract.Lock.UNLOCKED);
        entityManager.persist(contract100);

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
                "user" + id + "@ecare.com", HashUtils.sha256("user" + id),
                new Address("street" + id, "building" + id, "city" + id));
        entityManager.persist(customer);
        return customer;
    }

    private void createTestCustomer() {
        Customer customer = new Customer();
        Collections.shuffle(firstnames);
        String firstname = firstnames.get(0);
        customer.setFirstName(firstname);
        Collections.shuffle(lastnames);
        String lastname = lastnames.get(0);
        customer.setLastName(lastname);
        Collections.shuffle(cities);
        Collections.shuffle(streets);
        customer.setAddress(new Address(streets.get(0), String.valueOf(new Random().nextInt(200) + 1), cities.get(0)));
        customer.setPassport((1000 + new Random().nextInt(8000)) + " " + (10000000 + new Random().nextInt(80000000)));
        customer.setPassword(HashUtils.sha256(HashUtils.generatePassword()));
        customer.setBirthdate(new Date(50 + new Random().nextInt(50), new Random().nextInt(11), 1 + new Random().nextInt(27)));
        String uniqueEmail = (firstname.substring(0, 1) + (lastname.length() > 7 ? lastname.substring(0, 7) : lastname)).toLowerCase();
        if(!emails.containsKey(uniqueEmail)) {
            emails.put(uniqueEmail, 1);
        } else {
            int i = emails.get(uniqueEmail);
            emails.put(uniqueEmail, i + 1);
            uniqueEmail += Integer.toString(i);
        }
        uniqueEmail += "@ecare.com";
        customer.setEmail(uniqueEmail);
        entityManager.persist(customer);
        int count = 1;
        if (new Random().nextInt(10) < 2) {
            count = 2;
        }
        for (int i = 0; i < count; i++) {
            Contract contract = new Contract();
            contract.setCustomer(customer);
            contract.setNumberLock(Contract.Lock.UNLOCKED);
            Plan plan = entityManager.find(Plan.class, new Random().nextInt(5) + 1L);
            contract.setPlan(plan);
            List<Feature> toAdd = new ArrayList<>();
            for (Feature feature : plan.getAllowedFeatures()) {
                if (new Random().nextInt(2) == 1) {
                    toAdd.add(feature);
                }
            }
            for (Feature feature : toAdd) {
                boolean ok = true;
                if (toAdd.containsAll(feature.getNeededFeatures())) {
                    for (Feature b : feature.getBlockers()) {
                        if (toAdd.contains(b)) {
                            ok = false;
                            continue;
                        }
                    }
                    if (ok) {
                        contract.getActiveFeatures().add(feature);
                    }
                }
            }
            contract.setNumber(String.valueOf(900 + new Random().nextInt(99)) + String.valueOf(1000000 + new Random().nextInt(8000000)));
            entityManager.persist(contract);
        }
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

    static {
        firstnames.add("John");
        firstnames.add("Howard");
        firstnames.add("Tracey");
        firstnames.add("Terry");
        firstnames.add("Sara");
        firstnames.add("William");
        firstnames.add("Benedict");
        firstnames.add("Abigail");

        lastnames.add("Dawson");
        lastnames.add("Einstein");
        lastnames.add("Holmes");
        lastnames.add("Harper");
        lastnames.add("Morgan");
        lastnames.add("Liamson");
        lastnames.add("Rowling");
        lastnames.add("Smith");

        cities.add("Edinburgh");
        cities.add("Cardiff");
        cities.add("London");

        streets.add("Fleet street");
        streets.add("Wild street");
        streets.add("Baker Street");
        streets.add("Kingsley street");
        streets.add("Oxford street");
    }

}
