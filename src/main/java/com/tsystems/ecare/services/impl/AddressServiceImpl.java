package com.tsystems.ecare.services.impl;

import com.tsystems.ecare.dao.AddressDao;
import com.tsystems.ecare.dao.impl.AddressDaoImpl;
import com.tsystems.ecare.entities.Address;
import com.tsystems.ecare.services.AddressService;
import com.tsystems.ecare.utils.PersistenceProvider;

import javax.persistence.EntityManager;

/**
 * @author Andrei Makarevich
 */
public class AddressServiceImpl implements AddressService {

    private AddressDao addressDao = new AddressDaoImpl();

    public Address saveNewAddress(Address address) {
        addressDao.beginTransaction();
        address = addressDao.save(address);
        addressDao.commitTransaction();
        return address;
    }

    public Address updateAddress(Address address) {
        addressDao.beginTransaction();
        address = addressDao.merge(address);
        addressDao.commitTransaction();
        return address;
    }
}
