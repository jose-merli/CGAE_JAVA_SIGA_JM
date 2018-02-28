package org.itcgae.siga.services;

import java.util.List;

import org.itcgae.siga.db.entities.DemoCustomers;

public interface IDbService {

	
	public List<DemoCustomers>findAllCustomer();

	
	public List<DemoCustomers> findCustomersByName(String name);


//	public List<DemoCustomers> findAllCustomerSql();
}
