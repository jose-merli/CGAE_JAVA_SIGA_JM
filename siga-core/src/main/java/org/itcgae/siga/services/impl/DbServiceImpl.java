package org.itcgae.siga.services.impl;

import java.util.List;

import org.itcgae.siga.db.entities.DemoCustomers;
import org.itcgae.siga.db.entities.DemoCustomersExample;
import org.itcgae.siga.db.mappers.DemoCustomersMapper;
import org.itcgae.siga.services.IDbService;
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
