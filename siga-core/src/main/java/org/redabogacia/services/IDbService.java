package org.redabogacia.services;

import java.util.List;

import org.redabogacia.db.entities.DemoCustomers;

public interface IDbService {

	
	public List<DemoCustomers>findAllCustomer();

	
	public List<DemoCustomers> findCustomersByName(String name);
}
