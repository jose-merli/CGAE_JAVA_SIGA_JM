package org.redabogacia.services.impl;

import java.util.List;

import org.redabogacia.db.entities.DemoCustomers;
import org.redabogacia.db.entities.DemoCustomersExample;
import org.redabogacia.db.mappers.DemoCustomersMapper;
import org.redabogacia.services.IDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class DbServiceImpl implements IDbService {
	
	@Autowired
	DemoCustomersMapper customerRepo;
	
	@Override
	public List<DemoCustomers>findAllCustomer(){
		return customerRepo.selectByExample(new DemoCustomersExample());
	}

	@Override
	public List<DemoCustomers> findCustomersByName(String name) {
		DemoCustomersExample example = new DemoCustomersExample();
		example.createCriteria().andCustFirstNameEqualTo(name);
		
		return customerRepo.selectByExample(example);
	}

}
