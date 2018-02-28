package org.itcgae.siga.services.impl;

import java.util.List;

import org.itcgae.siga.db.entities.DemoCustomers;
import org.itcgae.siga.db.entities.DemoCustomersExample;
import org.itcgae.siga.db.mappers.DemoCustomersMapper;
import org.itcgae.siga.logger.MethodLogging;
import org.itcgae.siga.services.IDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class DbServiceImpl implements IDbService {
	
	@Autowired
	DemoCustomersMapper customerRepo;
	
//	@Autowired
//	private SqlSession sqlSession;
	
	@Override
	@MethodLogging
	public List<DemoCustomers>findAllCustomer(){
		return customerRepo.selectByExample(new DemoCustomersExample());
	}

	@Override
	public List<DemoCustomers> findCustomersByName(String name) {
		DemoCustomersExample example = new DemoCustomersExample();
		example.createCriteria().andCustFirstNameEqualTo(name);
		
		return customerRepo.selectByExample(example);
	}
	
//	@Override
//	public List<DemoCustomers>findAllCustomerSql(){
//		
//		try {
//			sqlSession.getConnection();
//			return (List<DemoCustomers>) sqlSession.getConnection().prepareStatement("SELECT * FROM DEMO_SOCIEDADES.DEMO_CUSTOMERS").executeQuery();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return new ArrayList<DemoCustomers>();
//		}
//	}

}
