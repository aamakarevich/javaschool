package com.tsystems.ecare.managers;

import com.tsystems.ecare.dao.AddressDao;
import com.tsystems.ecare.dao.AddressDaoImpl;
import com.tsystems.ecare.entities.Address;

/**
 * @author Andrei Makarevich
 */
public class AddressManagerImpl implements AddressManager {

    private AddressDao addressDao = new AddressDaoImpl();

    public Address saveNewAddress(Address address) {

        return null;
    }
}
