package com.tsystems.ecare.services;

import com.tsystems.ecare.dao.AddressDao;
import com.tsystems.ecare.dao.AddressDaoImpl;
import com.tsystems.ecare.entities.Address;
import com.tsystems.ecare.utils.PersistenceProvider;

import javax.persistence.EntityManager;

/**
 * @author Andrei Makarevich
 */
public class AddressServiceImpl implements AddressService {

    private AddressDao addressDao = new AddressDaoImpl();

    public Address saveNewAddress(Address address) {
        EntityManager em = PersistenceProvider.getEntityManager();
        address = addressDao.save(address);
        PersistenceProvider.closeEntityManager();
        return address;
    }

    public Address merge(Address address) {
        EntityManager em = PersistenceProvider.getEntityManager();
        address = addressDao.merge(address);
        return address;
    }
}
