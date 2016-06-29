package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.model.Address;

/**
 * @author Andrei Makarevich
 */
public interface AddressService {

    Address saveNewAddress(Address address);

    Address updateAddress(Address address);
}
