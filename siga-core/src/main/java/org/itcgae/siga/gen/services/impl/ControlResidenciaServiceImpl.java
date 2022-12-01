package org.itcgae.siga.gen.services.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.ColegiadoItem;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.db.entities.CenSolicitudincorporacion;
import org.itcgae.siga.db.entities.GenRecursosExample;
import org.itcgae.siga.db.services.cen.mappers.CenColegiadoExtendsMapper;
import org.itcgae.siga.db.services.cen.mappers.CenPersonaExtendsMapper;
import org.itcgae.siga.gen.services.IControlResidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ControlResidenciaServiceImpl implements IControlResidenciaService {

	private Logger LOGGER = Logger.getLogger(ControlResidenciaServiceImpl.class);
	
	@Autowired
    private CenPersonaExtendsMapper cenPersonaExtendsMapper;
	
	@Autowired
    private CenColegiadoExtendsMapper cenColegiadoExtendsMapper;
	
//	@Autowired
//	private GenRecursosMapper genRecursosMapper;
	
	public boolean compruebaColegiacionEnVigor(CenSolicitudincorporacion solicitudIncorporacion) {
		
		Boolean hasErr = false;
		Long idPersona = null;
		
		idPersona = cenPersonaExtendsMapper.getIdPersonaWithNif(solicitudIncorporacion.getNumeroidentificador());
		
		if (idPersona != null) {
			Date actualDate = new Date();
			GenRecursosExample recursosExample = new GenRecursosExample();			
			recursosExample.createCriteria().andIdrecursoEqualTo("censo.ws.situacionejerciente.BAJA_COLEGIAL");
//			List<GenRecursos> recursoIdiomasBajas = genRecursosMapper.selectByExample(recursosExample);
			
			List<ColegiadoItem> listColegiadosByPersona = cenColegiadoExtendsMapper
					.selectColegiadosByIdPersona(Short.parseShort("2000"), idPersona.toString());
						
			Boolean tieneResidenciaActiva = Boolean.FALSE;
			for (ColegiadoItem colegiadoItem : listColegiadosByPersona) {	
				Date bajaColegDate = colegiadoItem.getFechaBajaDate();
				
				if ( (Short.parseShort("10") != solicitudIncorporacion.getIdtipocolegiacion()) 
						&& solicitudIncorporacion.getIdtipocolegiacion() != null && ("20").equalsIgnoreCase(solicitudIncorporacion.getIdtipocolegiacion().toString())
						&& this.esInscrito(colegiadoItem.getComunitario())){
					hasErr = true;
					break;
				}
				
				if ( (this.esEjerciente(colegiadoItem.getEstadoColegial()) 
						&& (this.esResidente(colegiadoItem.getSituacionResidente()) && colegiadoItem.getSituacionResidente().equalsIgnoreCase(solicitudIncorporacion.getResidente())))
					
					&& (bajaColegDate == null || (bajaColegDate.after(actualDate) || bajaColegDate.equals(actualDate)))) {
					hasErr = true;
					break;
				}
				
				// Se comprueba si la solicitud de aprobacion es ejer o rein.ejer y que la iterada sea residente.
				// En caso de no tener ninguna residencia activa, al final saltaria alarma porque no puede aprobarse
				// nada ejerciente sin una residencia minima.
				if (this.esSolicitudEjerOReincorporacionEjer(solicitudIncorporacion.getIdtiposolicitud()) 
						&& this.esResidente(colegiadoItem.getSituacionResidente()))  {
					tieneResidenciaActiva = Boolean.TRUE;
				}
			}
			
			if (CollectionUtils.isNotEmpty(listColegiadosByPersona) && !tieneResidenciaActiva) {
				hasErr = true;
			}

		} else if (idPersona == null && (solicitudIncorporacion.getIdtiposolicitud() != null
				&& (SigaConstants.INCORPORACION_EJERCIENTE == solicitudIncorporacion.getIdtiposolicitud().shortValue()
						|| SigaConstants.REINCORPORACION_EJERCIENTE == solicitudIncorporacion.getIdtiposolicitud().shortValue())
				&& (!this.esResidente(solicitudIncorporacion.getResidente())))) {
			hasErr = true;
		}
		
		return hasErr;
	}
	
	private Boolean esResidente(String situacionResidente) {
		return !StringUtils.isEmpty(situacionResidente) && (("1").equalsIgnoreCase(situacionResidente) || ("Si").equalsIgnoreCase(situacionResidente) || ("1").equalsIgnoreCase(situacionResidente)) ? Boolean.TRUE : Boolean.FALSE;
	}
	
	private Boolean esEjerciente(String estadoColegial) {
		return !StringUtils.isEmpty(estadoColegial) && ("Ejerciente").equalsIgnoreCase(estadoColegial) ? Boolean.TRUE : Boolean.FALSE;
	}
	
	private Boolean esSolicitudEjerOReincorporacionEjer(Short idTipoSolicitud) {
		return idTipoSolicitud != null
				&& (SigaConstants.INCORPORACION_EJERCIENTE == idTipoSolicitud.shortValue()
				|| SigaConstants.REINCORPORACION_EJERCIENTE == idTipoSolicitud.shortValue()) ? Boolean.TRUE : Boolean.FALSE;
	}

	// Si es comunitario es "1" para el caso de que sea "INSCRITO"
	private Boolean esInscrito(String tipoColegiacion) {
		return !StringUtils.isEmpty(tipoColegiacion) && ("1").equalsIgnoreCase(tipoColegiacion) ? Boolean.TRUE : Boolean.FALSE;
	}
}
