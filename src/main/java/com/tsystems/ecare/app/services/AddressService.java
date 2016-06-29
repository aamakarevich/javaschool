package com.tsystems.ecare.app.services;

import com.tsystems.ecare.app.dao.impl.AddressRepository;
import com.tsystems.ecare.app.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    public Address saveAddress(Address address) {
        return repository.save(address);
    }
}
