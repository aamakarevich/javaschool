package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.model.Role;
import com.tsystems.ecare.app.utils.TestUtils;
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
import java.util.Date;

import static com.tsystems.ecare.app.utils.TestUtils.getStringOfLength;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for CustomerService.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes={TestConfiguration.class, RootContextConfig.class})
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void saveCustomerNew() {
        Customer customer = customerService.saveCustomer(null, "lastname", "firstname", new Date(), "passport", "city", "street", "building");
        Customer gotCustomer = em.find(Customer.class, customer.getId());
        assertEquals(customer.getFirstName(), gotCustomer.getFirstName());
        assertEquals(customer.getLastName(), gotCustomer.getLastName());
        assertEquals(customer.getPassport(), gotCustomer.getPassport());
        assertEquals(customer.getAddress().getCity(), gotCustomer.getAddress().getCity());
        assertEquals(customer.getAddress().getAddress1(), gotCustomer.getAddress().getAddress1());
        assertEquals(customer.getAddress().getAddress2(), gotCustomer.getAddress().getAddress2());
        assertEquals("flastnam@ecare.com", gotCustomer.getEmail());
        assertEquals(64, gotCustomer.getPassword().length());
    }

    @Test
    public void saveCustomerExisting() {
        Customer beforeUpdate = em.find(Customer.class, 1L);;
        Customer customer = customerService.saveCustomer(1L, "lastname", "firstname", new Date(), "passport", "city", "street", "building");
        Customer gotCustomer = em.find(Customer.class, customer.getId());
        assertEquals(customer.getFirstName(), gotCustomer.getFirstName());
        assertEquals(customer.getLastName(), gotCustomer.getLastName());
        assertEquals(customer.getPassport(), gotCustomer.getPassport());
        assertEquals(customer.getAddress().getCity(), gotCustomer.getAddress().getCity());
        assertEquals(customer.getAddress().getAddress1(), gotCustomer.getAddress().getAddress1());
        assertEquals(customer.getAddress().getAddress2(), gotCustomer.getAddress().getAddress2());
        assertEquals(beforeUpdate.getEmail(), gotCustomer.getEmail());
        assertEquals(64, gotCustomer.getPassword().length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveCustomerWithNonexistentId() {
        customerService.saveCustomer(0L, "lastname", "firstname", new Date(), "passport", "city", "street", "building");
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveCustomerWithBlankLastName() {
        customerService.saveCustomer(1L, "", "firstname", new Date(), "passport", "city", "street", "building");
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveCustomerWithBlankFirstName() {
        customerService.saveCustomer(1L, "lastname", "", new Date(), "passport", "city", "street", "building");
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveCustomerWithBlankPassport() {
        customerService.saveCustomer(1L, "lastname", "firstname", new Date(), "", "city", "street", "building");
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveCustomerWithBlankCity() {
        customerService.saveCustomer(1L, "lastname", "firstname", new Date(), "passport", "", "street", "building");
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveCustomerWithBlankAddress1() {
        customerService.saveCustomer(1L, "lastname", "firstname", new Date(), "passport", "city", "", "building");
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveCustomerWithLastNameTooLong() {
        customerService.saveCustomer(1L, getStringOfLength(41), "firstname", new Date(), "passport", "city", "street", "building");
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveCustomerWithFirstNameTooLong() {
        customerService.saveCustomer(1L, "lastname", getStringOfLength(41), new Date(), "passport", "city", "street", "building");
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveCustomerWithPassportTooLong() {
        customerService.saveCustomer(1L, "lastname", "firstname", new Date(), getStringOfLength(61), "city", "street", "building");
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveCustomerWithCityTooLong() {
        customerService.saveCustomer(1L, "lastname", "firstname", new Date(), "passport", getStringOfLength(41), "street", "building");
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveCustomerWithAddress1TooLong() {
        customerService.saveCustomer(1L, "lastname", "firstname", new Date(), "passport", "city", getStringOfLength(101), "building");
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveCustomerWithAddress2TooLong() {
        customerService.saveCustomer(1L, "lastname", "firstname", new Date(), "passport", "city", getStringOfLength(101), "building");
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveCustomerWithNullBirthate() {
        customerService.saveCustomer(1L, "lastname", "firstname", null, "passport", "city", "street", "building");
    }

    @Test
    public void getCustomer() {
        Customer expectedCustomer = em.find(Customer.class, 2L);
        Customer gotCustomer = customerService.getCustomer(2L);
        assertEquals(expectedCustomer.getFirstName(), gotCustomer.getFirstName());
        assertEquals(expectedCustomer.getLastName(), gotCustomer.getLastName());
        assertEquals(expectedCustomer.getPassport(), gotCustomer.getPassport());
        assertEquals(expectedCustomer.getBirthdate(), gotCustomer.getBirthdate());
        assertEquals(expectedCustomer.getAddress().getCity(), gotCustomer.getAddress().getCity());
        assertEquals(expectedCustomer.getAddress().getAddress1(), gotCustomer.getAddress().getAddress1());
        assertEquals(expectedCustomer.getAddress().getAddress2(), gotCustomer.getAddress().getAddress2());
        assertEquals(expectedCustomer.getEmail(), gotCustomer.getEmail());
        assertEquals(expectedCustomer.getPassword(), gotCustomer.getPassword());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCustomerWithNullId() {
        customerService.getCustomer(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCustomerWithNonexistentId() {
        customerService.getCustomer(0L);
    }

    @Test
    public void getCustomerByEmail() {
        Customer expectedCustomer = em.find(Customer.class, 2L);
        Customer gotCustomer = customerService.getCustomerByEmail("user2@ecare.com");
        assertEquals(expectedCustomer.getFirstName(), gotCustomer.getFirstName());
        assertEquals(expectedCustomer.getLastName(), gotCustomer.getLastName());
        assertEquals(expectedCustomer.getPassport(), gotCustomer.getPassport());
        assertEquals(expectedCustomer.getBirthdate(), gotCustomer.getBirthdate());
        assertEquals(expectedCustomer.getAddress().getCity(), gotCustomer.getAddress().getCity());
        assertEquals(expectedCustomer.getAddress().getAddress1(), gotCustomer.getAddress().getAddress1());
        assertEquals(expectedCustomer.getAddress().getAddress2(), gotCustomer.getAddress().getAddress2());
        assertEquals(expectedCustomer.getEmail(), gotCustomer.getEmail());
        assertEquals(expectedCustomer.getPassword(), gotCustomer.getPassword());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCustomerByEmailWithNullEmail() {
        customerService.getCustomerByEmail(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCustomerByEmailWithNonexistentEmail() {
        customerService.getCustomerByEmail("invalid@mail.com");
    }

    @Test
    public void findCustomers() {
        customerService.findCustomers("", 1, 10);
    }

    @Test
    public void deleteCustomer() {
        assertNotNull(em.find(Customer.class, 3L));
        customerService.deleteCustomer(3L);
        assertNull(em.find(Customer.class, 3L));
    }

    @Test
    public void activateRoleActivate() {
        Role role1 = em.find(Role.class, 1L);
        Role role2 = em.find(Role.class, 2L);
        assertTrue(!em.find(Customer.class, 5L).getRoles().contains(role1));
        assertTrue(em.find(Customer.class, 5L).getRoles().contains(role2));
        customerService.activateRole(1L, 5L, true);
        assertTrue(em.find(Customer.class, 5L).getRoles().contains(role1));
        assertTrue(em.find(Customer.class, 5L).getRoles().contains(role2));
    }

    @Test
    public void activateRoleDeactivate() {
        Role role1 = em.find(Role.class, 1L);
        Role role2 = em.find(Role.class, 2L);
        assertTrue(em.find(Customer.class, 4L).getRoles().contains(role1));
        assertTrue(!em.find(Customer.class, 4L).getRoles().contains(role2));
        customerService.activateRole(1L, 4L, false);
        assertTrue(!em.find(Customer.class, 4L).getRoles().contains(role1));
        assertTrue(!em.find(Customer.class, 4L).getRoles().contains(role2));
    }
}