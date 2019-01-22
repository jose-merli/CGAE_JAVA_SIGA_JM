package org.itcgae.siga.cen.services;

import org.itcgae.siga.DTOs.cen.FicheroVo;
import org.itcgae.siga.exception.BusinessException;

public interface FicherosService {
	
	public FicheroVo getFichero(FicheroVo ficheroVo) throws  BusinessException;
	public void insert(FicheroVo ficheroVo) throws  BusinessException;
	
}
