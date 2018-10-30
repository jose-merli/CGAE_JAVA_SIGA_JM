package org.itcgae.siga.cen.services.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.datacontract.schemas._2004._07.IntegracionEnumsCombos;
import org.itcgae.siga.DTOs.cen.MutualidadResponseDTO;
import org.itcgae.siga.cen.services.IMutualidadService;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.ws.client.ClientMutualidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import samples.servicemodel.microsoft.GetEnumsDocument;
import samples.servicemodel.microsoft.GetEnumsDocument.GetEnums;
import samples.servicemodel.microsoft.GetEnumsResponseDocument;

@Service
public class MutualidadServiceImpl implements IMutualidadService{

	private Logger LOGGER = Logger.getLogger(MutualidadServiceImpl.class);
	
	@Autowired
	private GenParametrosMapper _genParametrosMapper;
	
	@Autowired
	private ClientMutualidad _clientMutualidad;
	
	
	@Override
	public MutualidadResponseDTO getEstadoSolicitud() {
		LOGGER.info("getEstadoSolicitud() --> Entrada al servicio para obtener el estado de la solicitud");
		
		MutualidadResponseDTO response = new MutualidadResponseDTO();
		
		GenParametrosExample example = new GenParametrosExample();
		example.createCriteria().andIdrecursoEqualTo("administracion.parametro.url_ws_mutualidad");
		
		List<GenParametros> config = _genParametrosMapper.selectByExample(example);
		
		if(config != null && config.size() > 0){
			
			String uriService = config.get(0).getValor();
			
		}
		
		LOGGER.info("getEstadoSolicitud() --> Salida del servicio para obtener el estado de la solicitud");
		return null;
	}

	@Override
	public MutualidadResponseDTO getEstadoMutualista() {
		LOGGER.info("getEstadoMutualista() --> Entrada al servicio para obtener el estado del mutualista");
		
		MutualidadResponseDTO response = new MutualidadResponseDTO();
		
		GenParametrosExample example = new GenParametrosExample();
		example.createCriteria().andIdrecursoEqualTo("administracion.parametro.url_ws_mutualidad");
		
		List<GenParametros> config = _genParametrosMapper.selectByExample(example);
		
		if(config != null && config.size() > 0){
			
			String uriService = config.get(0).getValor();
			
		}
		
		LOGGER.info("getEstadoMutualista() --> Salida servicio para obtener el estado del mutualista");
		return null;
	}

	@Override
	public MutualidadResponseDTO getEnums() {
		LOGGER.info("getEnums() --> Entrada al servicio para obtener los enumerados");
		
		MutualidadResponseDTO response = new MutualidadResponseDTO();
		try {
			
			GenParametrosExample example = new GenParametrosExample();
			example.createCriteria().andIdrecursoEqualTo("administracion.parametro.url_ws_mutualidad");
			
			List<GenParametros> config = _genParametrosMapper.selectByExample(example);
			
			if(config != null && config.size() > 0){
				
				String uriService = config.get(0).getValor();
				
				GetEnumsDocument request = GetEnumsDocument.Factory.newInstance();
				GetEnums enums = GetEnums.Factory.newInstance();
				request.setGetEnums(enums);
				GetEnumsResponseDocument responseWS = _clientMutualidad.getEnums(request, uriService);
				if(responseWS != null){
					IntegracionEnumsCombos combos = responseWS.getGetEnumsResponse().getGetEnumsResult();
				}
				responseWS.getGetEnumsResponse().getGetEnumsResult();
			}
		} catch (Exception e) {
			LOGGER.error("getEnums() --> error en el servicio: " + e.getMessage());
			e.printStackTrace();
		}
		
		
		
		LOGGER.info("getEnums() --> Salida del servicio para obtener los enumerados");
		return response;
	}

	@Override
	public MutualidadResponseDTO MGASolicitudPolizaAccuGratuitos() {
		LOGGER.info("MGASolicitudPolizaAccuGratuitos() --> Entrada al servicio para solicitar la poliza gratuita");
		
		MutualidadResponseDTO response = new MutualidadResponseDTO();
		
		GenParametrosExample example = new GenParametrosExample();
		example.createCriteria().andIdrecursoEqualTo("administracion.parametro.url_ws_mutualidad");
		
		List<GenParametros> config = _genParametrosMapper.selectByExample(example);
		
		if(config != null && config.size() > 0){
			
			String uriService = config.get(0).getValor();
			
		}
		
		LOGGER.info("MGASolicitudPolizaAccuGratuitos() --> Salida del servicio para solicitar la poliza gratuita");
		return null;
	}

	@Override
	public MutualidadResponseDTO MGASolicitudPolizaProfesional() {
		LOGGER.info("MGASolicitudPolizaProfesional() --> Entrada al servicio para solicitar la poliza profesional");
		
		MutualidadResponseDTO response = new MutualidadResponseDTO();
		
		GenParametrosExample example = new GenParametrosExample();
		example.createCriteria().andIdrecursoEqualTo("administracion.parametro.url_ws_mutualidad");
		
		List<GenParametros> config = _genParametrosMapper.selectByExample(example);
		
		if(config != null && config.size() > 0){
			
			String uriService = config.get(0).getValor();
			
		}
		
		LOGGER.info("MGASolicitudPolizaProfesional() --> Salida del servicio para solicitar la poliza profesional");
		return null;
	}

	@Override
	public MutualidadResponseDTO ObtenerCuotaYCapObjetivo() {
		LOGGER.info("MGASolicitudPolizaProfesional() --> Entrada al servicio para obtener la cuota y capital objetivo");
		
		MutualidadResponseDTO response = new MutualidadResponseDTO();
		
		GenParametrosExample example = new GenParametrosExample();
		example.createCriteria().andIdrecursoEqualTo("administracion.parametro.url_ws_mutualidad");
		
		List<GenParametros> config = _genParametrosMapper.selectByExample(example);
		
		if(config != null && config.size() > 0){
			
			String uriService = config.get(0).getValor();
			
		}
		
		LOGGER.info("MGASolicitudPolizaProfesional() --> Salida del servicio para obtener la cuota y capital objetivo");
		return null;
	}

}
