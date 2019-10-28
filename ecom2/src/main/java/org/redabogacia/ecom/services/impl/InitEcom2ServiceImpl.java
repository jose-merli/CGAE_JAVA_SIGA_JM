package org.redabogacia.ecom.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.exception.ValidationException;
import org.redabogacia.ecom.interfaces.EcomColaServiceInterface;
import org.redabogacia.ecom.services.IRefrescaVariablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitEcom2ServiceImpl implements EcomColaServiceInterface {
	
	private static Logger LOGGER = Logger.getLogger(InitEcom2ServiceImpl.class);
	
	
	@Autowired
	private IRefrescaVariablesService iRefrescaVariablesService;

	@Override
	public void execute(EcomCola ecomCola) throws ValidationException, BusinessException {
		iRefrescaVariablesService.iniciaTodo();
	}

	
}
