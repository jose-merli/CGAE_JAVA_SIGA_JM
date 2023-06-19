package org.itcgae.siga.gen.services;

import org.itcgae.siga.db.entities.CenSolicitudincorporacion;

public interface IControlResidenciaService {

	boolean compruebaColegiacionEnVigor(CenSolicitudincorporacion solicitudIncorporacion);
	
}