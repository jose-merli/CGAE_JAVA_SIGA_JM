package org.redabogacia.ecom.interfaces;

import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.exception.ValidationException;

public interface EcomColaServiceInterface {

   	public void execute(EcomCola ecomCola) throws ValidationException, BusinessException;

}
