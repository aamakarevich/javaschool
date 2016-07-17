package com.tsystems.ecare.app.services;

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

    }

    @Test
    public void saveContract() {

    }

    @Test
    public void getContract() {

    }

    @Test
    public void changeLock() {
//        contractService.changeLock();
    }

}