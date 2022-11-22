package org.itcgae.siga.gen.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.db.entities.GenRecursos;
import org.itcgae.siga.db.entities.GenRecursosExample;
import org.itcgae.siga.db.mappers.GenRecursosMapper;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.gen.services.IControlResidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.apache.log4j.Logger;

@Service
public class ControlResidenciaServiceImpl implements IControlResidenciaService {

	private Logger LOGGER = Logger.getLogger(ControlResidenciaServiceImpl.class);
	
	@Autowired
    private CenPersonaExtendsMapper cenPersonaExtendsMapper;
	
	@Autowired
    private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;
	
	@Autowired
	private GenRecursosMapper genRecursosMapper;
	
	public boolean compruebaColegiacionEnVigor(String nif) {
		
		Boolean hasErr = false;
		Long idPersona = null;
		
		// TODO: Comprobar si existen datos de colegiacion para el idPersona
		idPersona = cenPersonaExtendsMapper.getIdPersonaWithNif(nif);
		
		// TODO: Comprobar si la colegiación está en vigor como ejerciente residente o como inscrito
		if (idPersona != null) {
			Date actualDate = new Date();
			GenRecursosExample recursosExample = new GenRecursosExample();			
			recursosExample.createCriteria().andIdrecursoEqualTo("censo.ws.situacionejerciente.BAJA_COLEGIAL");
			List<GenRecursos> recursoIdiomasBajas = genRecursosMapper.selectByExample(recursosExample);
			
			List<ColegiadoItem> listColegiadosByPersona = cenColegiadoExtendsMapper
					.selectColegiadosByIdPersona(Short.parseShort("2000"), idPersona.toString());
						
			for (ColegiadoItem colegiadoItem : listColegiadosByPersona) {	
				Date bajaColegDate = colegiadoItem.getFechaBajaDate();
				
				//for (GenRecursos recIdiomaBaja : recursoIdiomasBajas) {
					LOGGER.info("+++++++++++++++++ [SMB] [ID_PERSONA = " + idPersona + "] --> ACTUAL DATE = " + actualDate + " | BAJA DATE = " + bajaColegDate + " | ESTADO COLEGIAL = " + colegiadoItem.getEstadoColegial());
					
					//if (!recIdiomaBaja.getDescripcion().equalsIgnoreCase(colegiadoItem.getEstadoColegial())
					if ( ("Ejerciente".equalsIgnoreCase(colegiadoItem.getEstadoColegial()) 
							&& (!StringUtils.isEmpty(colegiadoItem.getSituacionResidente())
							&& ("1").equalsIgnoreCase(colegiadoItem.getSituacionResidente())))
						|| ("Inscrito".equalsIgnoreCase(colegiadoItem.getEstadoColegial()))
						&& (bajaColegDate == null || (bajaColegDate.after(actualDate) || bajaColegDate.equals(actualDate)))) {
						hasErr = true;
						break;
					}
				//}
			}

		}
		
		return hasErr;
	}

}
