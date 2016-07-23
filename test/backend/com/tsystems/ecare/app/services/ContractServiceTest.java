package backend.com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Contract;
import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.services.ContractService;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for ContractService.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes={TestConfiguration.class, RootContextConfig.class})
public class ContractServiceTest {

    @Autowired
    private ContractService contractService;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void saveNewContract() {
        contractService.saveNewContract("0123456789", null, 8L, 100L);
        Customer customer = em.find(Customer.class, 100L);
        Contract contract = em.find(Contract.class, 100L);
        assertTrue(customer.getContracts().contains(contract));
        assertEquals("0123456789", contract.getNumber());
        assertEquals(new Long(8), contract.getPlan().getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNewContractWithBlankNumber() {
        contractService.saveNewContract("", null, 8L, 100L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNewContractWithNullNumber() {
        contractService.saveNewContract(null, null, 8L, 100L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNewContractWithNullPlan() {
        contractService.saveNewContract("0123456789", null, null, 100L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNewContractWithNonexistentPlan() {
        contractService.saveNewContract("0123456789", null, 0L, 100L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNewContractWithNullCustomer() {
        contractService.saveNewContract("0123456789", null, 8L, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNewContractWithNonexistentCustomer() {
        contractService.saveNewContract("0123456789", null, 8L, 0L);
    }

    @Test
    public void saveContract() {
        Contract contract;
        contract = em.find(Contract.class, 95L);
        assertEquals("0000000095", contract.getNumber());
        assertEquals(new Long(8), contract.getPlan().getId());
        contractService.saveContract(95L, "0123456789", null, 8L, 100L, null);
        contract = em.find(Contract.class, 95L);
        assertEquals("0000000095", contract.getNumber());
        assertEquals(new Long(8), contract.getPlan().getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveContractWithNonexistentId() {
        contractService.saveContract(0L, "", null, 8L, 100L, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveContractWithNullPlan() {
        contractService.saveContract(96L, "0123456789", null, null, 100L, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveContractWithNonexistentPlan() {
        contractService.saveContract(96L, "0123456789", null, 0L, 100L, null);
    }

    @Test
    public void getContract() {
        Contract expectedContract = em.find(Contract.class, 96L);
        Contract gotContract = contractService.getContract(96L, null);
        assertEquals(expectedContract, gotContract);
    }

    @Test
    public void getContractOwn() {
        Contract expectedContract = em.find(Contract.class, 96L);
        Contract gotContract = contractService.getContract(96L, "user100@ecare.com");
        assertEquals(expectedContract, gotContract);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getContractOwnWithWrongEmail() {
        contractService.getContract(96L, "user99@ecare.com");
    }

    @Test
    public void changeLockLock() {
        Contract contract;
        contract = em.find(Contract.class, 95L);
        assertEquals(Contract.Lock.UNLOCKED, contract.getNumberLock());
        contractService.changeLock(95L, true, null);
        contract = em.find(Contract.class, 95L);
        assertEquals(Contract.Lock.LOCKED, contract.getNumberLock());
    }

    @Test
    public void changeLockUnlock() {
        Contract contract;
        contract = em.find(Contract.class, 96L);
        assertEquals(Contract.Lock.LOCKED, contract.getNumberLock());
        contractService.changeLock(96L, false, null);
        contract = em.find(Contract.class, 96L);
        assertEquals(Contract.Lock.UNLOCKED, contract.getNumberLock());
    }

    @Test
    public void changeLockUnlockOwnUserlocked() {
        Contract contract;
        contract = em.find(Contract.class, 97L);
        assertEquals(Contract.Lock.USERLOCKED, contract.getNumberLock());
        contractService.changeLock(97L, false, "user100@ecare.com");
        contract = em.find(Contract.class, 97L);
        assertEquals(Contract.Lock.UNLOCKED, contract.getNumberLock());
    }

    @Test
    public void changeLockUnlockOwnLocked() {
        Contract contract;
        contract = em.find(Contract.class, 98L);
        assertEquals(Contract.Lock.LOCKED, contract.getNumberLock());
        contractService.changeLock(98L, false, "user100@ecare.com");
        contract = em.find(Contract.class, 98L);
        assertEquals(Contract.Lock.LOCKED, contract.getNumberLock());
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeLockUnlockOwnLockedWrongEmail() {
        contractService.changeLock(99L, false, "user99@ecare.com");
    }
}