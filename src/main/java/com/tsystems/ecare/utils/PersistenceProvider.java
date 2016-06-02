package com.tsystems.ecare.utils;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Andrei Makarevich
 */
public final class PersistenceProvider {

    private static Logger logger = Logger.getLogger(PersistenceProvider.class);

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = createEntityManagerFactory();

    private PersistenceProvider() { /* never create instance of this utility class */ }

    private static EntityManagerFactory createEntityManagerFactory() {

        try {
            return Persistence.createEntityManagerFactory("ECARE");
        } catch (Exception ex) {
            logger.error(ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Creates and returns new EntityManager.
     *
     * @return created EntityManager
     */
    public static EntityManager createEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    /**
     * Closes the only {@code EntityManagerFactory} instance.
     */
    public static void shutdown() {
        ENTITY_MANAGER_FACTORY.close();
    }
}
