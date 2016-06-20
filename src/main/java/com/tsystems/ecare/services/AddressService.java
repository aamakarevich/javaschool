package com.tsystems.ecare.services;

import com.tsystems.ecare.entities.Address;

/**
 * @author Andrei Makarevich
 */
public interface AddressService {

    Address saveNewAddress(Address address);

    Address updateAddress(Address address);
}
