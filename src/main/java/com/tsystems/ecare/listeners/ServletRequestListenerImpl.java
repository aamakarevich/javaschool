package com.tsystems.ecare.listeners;

import com.tsystems.ecare.utils.PersistenceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * Triggers entity manager initialization at the start
 * of servlet work and releases it at the end of work.
 *
 * @author Andrei Makarevich
 */
public class ServletRequestListenerImpl implements ServletRequestListener {

    public void requestInitialized(ServletRequestEvent sre) {
        PersistenceProvider.getEntityManager();
    }

    public void requestDestroyed(ServletRequestEvent sre) {
        PersistenceProvider.closeEntityManager();
    }
}
