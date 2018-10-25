package org.itcgae.siga.cen.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.MutualidadResponseDTO;
import org.itcgae.siga.cen.services.IMutualidadService;
import org.springframework.stereotype.Service;

@Service
public class MutualidadServiceImpl implements IMutualidadService{

	private Logger LOGGER = Logger.getLogger(MutualidadServiceImpl.class);
	
	
	@Override
	public MutualidadResponseDTO getEstadoSolicitud() {
		LOGGER.info("getEstadoSolicitud() --> Entrada al servicio para obtener el estado de la solicitud");
		
		LOGGER.info("getEstadoSolicitud() --> Salida del servicio para obtener el estado de la solicitud");
		return null;
	}

	@Override
	public MutualidadResponseDTO getEstadoMutualista() {
		LOGGER.info("getEstadoMutualista() --> Entrada al servicio para obtener el estado del mutualista");
		
		LOGGER.info("getEstadoMutualista() --> Salida servicio para obtener el estado del mutualista");
		return null;
	}

	@Override
	public MutualidadResponseDTO getEnums() {
		LOGGER.info("getEnums() --> Entrada al servicio para obtener los enumerados");
		
		LOGGER.info("getEnums() --> Salida del servicio para obtener los enumerados");
		return null;
	}

	@Override
	public MutualidadResponseDTO MGASolicitudPolizaAccuGratuitos() {
		LOGGER.info("MGASolicitudPolizaAccuGratuitos() --> Entrada al servicio para solicitar la poliza gratuita");
		
		LOGGER.info("MGASolicitudPolizaAccuGratuitos() --> Salida del servicio para solicitar la poliza gratuita");
		return null;
	}

	@Override
	public MutualidadResponseDTO MGASolicitudPolizaProfesional() {
		LOGGER.info("MGASolicitudPolizaProfesional() --> Entrada al servicio para solicitar la poliza profesional");
		
		LOGGER.info("MGASolicitudPolizaProfesional() --> Salida del servicio para solicitar la poliza profesional");
		return null;
	}

	@Override
	public MutualidadResponseDTO ObtenerCuotaYCapObjetivo() {
		LOGGER.info("MGASolicitudPolizaProfesional() --> Entrada al servicio para obtener la cuota y capital objetivo");
		
		LOGGER.info("MGASolicitudPolizaProfesional() --> Salida del servicio para obtener la cuota y capital objetivo");
		return null;
	}

}
