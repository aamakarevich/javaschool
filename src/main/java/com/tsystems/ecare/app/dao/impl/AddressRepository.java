package com.tsystems.ecare.app.dao.impl;

import com.tsystems.ecare.app.dao.AddressDao;
import com.tsystems.ecare.app.model.Address;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepository extends GenericRepository<Address, Integer> implements AddressDao {

}
