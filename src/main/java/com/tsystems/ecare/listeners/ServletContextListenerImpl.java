package com.tsystems.ecare.listeners;

import com.tsystems.ecare.utils.PersistenceProvider;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Preinitializes persistence provider and
 * releases it at the and of application work.
 *
 * @author Andrei Makarevich
 */
public class ServletContextListenerImpl implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        PersistenceProvider.initialize();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        PersistenceProvider.shutdown();
    }
}
