package org.itcgae.siga.fac.services.impl;

import java.util.List;

import org.itcgae.siga.db.entities.FacFacturacionprogramada;
import org.itcgae.siga.db.services.fac.mappers.FacFacturacionprogramadaExtendsMapper;
import org.itcgae.siga.fac.services.IFacturacionProgramadaPySService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacturacionProgramadaPySServiceImpl implements IFacturacionProgramadaPySService {

	@Autowired
	private FacFacturacionprogramadaExtendsMapper facProgMapper;

	
	@Override
	public void ejecutaProcesoFacturacionPyS() {
		List<FacFacturacionprogramada> lFAc = facProgMapper.getListaNFacturacionesProgramadasPorOrdenEjecucion(10);
		

	}

}
