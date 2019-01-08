package org.itcgae.siga.cen.services.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.BusquedaJuridicaDTO;
import org.itcgae.siga.cen.services.IFichaColegialRegTelService;
import org.itcgae.siga.services.impl.DocushareHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FichaColegialRegTelServiceImpl implements IFichaColegialRegTelService{

	private Logger LOGGER = Logger.getLogger(FichaColegialRegTelServiceImpl.class);
	
	 
	private DocushareHelper docushareHelper;
	
	 

	@Override
	public BusquedaJuridicaDTO searchListDoc(int numPagina, String idPersona, HttpServletRequest request) throws Exception {
		docushareHelper.buscaCollectionCenso("");
		return null;
	}	
	
	
}
