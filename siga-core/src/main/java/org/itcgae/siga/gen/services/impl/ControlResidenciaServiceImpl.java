package org.itcgae.siga.gen.services.impl;

import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.gen.services.IControlResidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ControlResidenciaServiceImpl implements IControlResidenciaService {

	@Autowired
    private CenPersonaExtendsMapper cenPersonaExtendsMapper;
	
	private boolean permitir;
	
	public boolean compruebaColegiacionEnVigor(String nif) {
		permitir = true;
		Long idPersona = null;
		
		// TODO: Comprobar si existen datos de colegiacion para el idPersona
		idPersona = cenPersonaExtendsMapper.getIdPersonaWithNif(nif);
		
		// TODO: Comprobar si la colegiación está en vigor como ejerciente residente o como inscrito
		
		return permitir;
	}

}
