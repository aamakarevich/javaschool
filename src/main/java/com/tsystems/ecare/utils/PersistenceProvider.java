package com.tsystems.ecare.utils;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/**
 * @author Andrei Makarevich
 */
public final class PersistenceProvider {

    private static Logger logger = Logger.getLogger(PersistenceProvider.class);

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = createEntityManagerFactory();

    private static ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<EntityManager>();

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
     * Returns thread local entity manager if it exists and
     * it is open and creates and reterns new one otherways.
     *
     * @return thread local entity manager
     */
    public static EntityManager getEntityManager() {
        if(entityManagerThreadLocal.get() == null || !entityManagerThreadLocal.get().isOpen()) {
            entityManagerThreadLocal.set(ENTITY_MANAGER_FACTORY.createEntityManager());
        }
        return entityManagerThreadLocal.get();
    }

    /**
     * Closes thread local entity manager and removes thread value.
     */
    public static void closeEntityManager() {
        entityManagerThreadLocal.get().close();
        entityManagerThreadLocal.remove();
    }

    /**
     * Triggers entity manager factory instance initialization
     * if it is not initialized yet. Otherways does nothing.
     */
    public static void initialize() { }

    /**
     * Closes the only {@code EntityManagerFactory} instance.
     */
    public static void shutdown() {
        ENTITY_MANAGER_FACTORY.close();
    }
}
