package org.redabogacia.ecom.services.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.constants.SigaConstants.ECOM_OPERACION;
import org.itcgae.siga.db.entities.EcomCola;
import org.itcgae.siga.db.entities.EcomColaExample;
import org.itcgae.siga.db.entities.EcomOperacion;
import org.itcgae.siga.db.mappers.EcomOperacionMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.exception.ValidationException;
import org.redabogacia.ecom.db.services.ecom.mappers.EcomColaExtendsMapper;
import org.redabogacia.ecom.interfaces.EcomColaServiceInterface;
import org.redabogacia.ecom.services.EcomColaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EcomColaServiceImpl implements EcomColaService {
	
	private static Logger LOGGER = Logger.getLogger(EcomColaServiceImpl.class);
	
	@Autowired
	private EcomColaExtendsMapper ecomColaExtendsMapper;
	
	@Autowired
	private EcomOperacionMapper ecomOperacionMapper;
	
	@Autowired
	private AutowireCapableBeanFactory autowireCapableBeanFactory;
	
	
	@Override
	@Scheduled(cron = "${cron.pattern.scheduled.ecom.cola}")
	public void ejecuta() {
		LOGGER.debug("Ejecutando la cola de eCOM...");
		try {
			//recuperamos los pendientes
			List<EcomCola> listEcomCola = getPendientes();
			//actualizamos el estado para que en el siguiente ciclo no se ejecuten los mismos registros
			actualizaEstadoEjecutando(listEcomCola);
			//ejecutamos cada uno de los registros de la cola
			ejecutaOperacion(listEcomCola);
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}
	
	private void ejecutaOperacion(List<EcomCola> listEcomCola) {
		if (listEcomCola != null && listEcomCola.size() > 0) {
			
			for (EcomCola ecomCola : listEcomCola) {
				LOGGER.debug("Ejecutando el proceso de la cola con id " + ecomCola.getIdecomcola());
				//cada registro se ejecutará dependiendo de su operación. habrá un proceso por cada operación
					//procesoOperacion23(ecomcola)
				ejecutaOperacion(ecomCola);
			}	
		}
	}

	private void ejecutaOperacion(EcomCola ecomCola) {
		try {
			if (ecomCola != null) {
				EcomColaServiceInterface service = getService(ecomCola.getIdoperacion());
				
				if (service != null) {						
					ecomCola.setReintento(ecomCola.getReintento() + 1);
					service.execute(ecomCola);
					ecomCola.setIdestadocola(SigaConstants.ECOM_ESTADOSCOLA.FINAL.getId());
				}
			}
		} catch (ValidationException e) {
			LOGGER.error("Se ha producido un error de validación. IdOperacion = " + ecomCola.getIdoperacion(), e);
			ecomCola.setIdestadocola(SigaConstants.ECOM_ESTADOSCOLA.ERROR_VALIDACION.getId());
		} catch (Exception e) {
			LOGGER.error(String.format("Se ha producido un error en el envío de webservice. IdEcomcola = %s, IdOperacion = %s y causa = %s", ecomCola.getIdecomcola(), ecomCola.getIdoperacion(), e.getMessage()), e);
			EcomOperacion ecomOperacion = ecomOperacionMapper.selectByPrimaryKey(ecomCola.getIdoperacion());					
			
			if (ecomCola.getReintento() >= ecomOperacion.getMaxreintentos()) {
				ecomCola.setIdestadocola(SigaConstants.ECOM_ESTADOSCOLA.ERROR.getId());
			} else {
				ecomCola.setIdestadocola(SigaConstants.ECOM_ESTADOSCOLA.REINTENTANDO.getId());
			}
		}
		actualizaEcomCola(ecomCola);
				
		
	}
	
	private EcomColaServiceInterface getService(Integer idoperacion) {
		EcomColaServiceInterface service = null;
		ECOM_OPERACION operacion = ECOM_OPERACION.getEnum(idoperacion);
		
		switch (operacion) {
		case ECOM2_INIT_PARAMETROS_GENERALES:
			service = new InitEcom2ServiceImpl();
			break;

		default:
			break;
		}
		
		if (service != null) {
			//para que cargue los autowire de su implementacion
			autowireCapableBeanFactory.autowireBean(service);
		}
		return service;
	}

	@Transactional
	private void actualizaEcomCola(EcomCola ecomCola) {
		if (ecomCola != null) {
			ecomCola.setFechamodificacion(new Date());
			ecomCola.setUsumodificacion(SigaConstants.USUMODIFICACION_0);
			ecomColaExtendsMapper.updateByPrimaryKey(ecomCola);
		}
	}

	@Transactional
	private void actualizaEstadoEjecutando(List<EcomCola> listEcomCola) {
		if (listEcomCola != null && listEcomCola.size() > 0) {
			LOGGER.debug("Actualizando el estado de los registros que se van a ejecutar. Nuevo estado: " + SigaConstants.ECOM_ESTADOSCOLA.EJECUTANDOSE);
			for (EcomCola ecomCola : listEcomCola) {
				ecomCola.setIdestadocola(SigaConstants.ECOM_ESTADOSCOLA.EJECUTANDOSE.getId());
				ecomCola.setFechamodificacion(Calendar.getInstance().getTime());
				ecomColaExtendsMapper.updateByPrimaryKey(ecomCola);
			}
		}
	}

	public List<EcomCola> getPendientes() {
		List<EcomCola> listaCola = null;
				
		try {
			
			EcomColaExample ecomColaExample = new EcomColaExample();
//			operacionesActivas();
			listaCola = ecomColaExtendsMapper.getPendientes(ecomColaExample, SigaConstants.ECOM_COLA_HORAS_EN_EJECUCION_MAXIMAS);
			
		} catch (Exception e) {			
			LOGGER.error("Se ha producido un error al recuperar la lista de la cola", e);
			throw new BusinessException("Se ha producido un error al recuperar la lista de la cola", e);
		}
		return listaCola;
	}

	
}
