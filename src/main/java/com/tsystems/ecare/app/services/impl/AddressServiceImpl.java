package com.tsystems.ecare.app.services.impl;

import com.tsystems.ecare.app.dao.AddressDao;
import com.tsystems.ecare.app.dao.impl.AddressDaoImpl;
import com.tsystems.ecare.app.model.Address;
import com.tsystems.ecare.app.services.AddressService;

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
