package com.tsystems.ecare.app.services.impl;

import com.tsystems.ecare.app.dao.AddressDao;
import com.tsystems.ecare.app.model.Address;
import com.tsystems.ecare.app.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Override
    @Transactional
    public Address saveAddress(Address address) {
        return addressDao.save(address);
    }
}
