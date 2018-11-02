package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.datacontract.schemas._2004._07.ArrayOfIntegracionColegiadosBloque;
import org.datacontract.schemas._2004._07.IntegracionColegiadosBloque;
import org.datacontract.schemas._2004._07.IntegracionEnumsCombos;
import org.datacontract.schemas._2004._07.IntegracionPersona;
import org.datacontract.schemas._2004._07.IntegracionSolicitudRespuesta;
import org.datacontract.schemas._2004._07.IntegracionTextoValor;
import org.itcgae.siga.DTOs.cen.CuotaYCapObjetivoDTO;
import org.itcgae.siga.DTOs.cen.DatosSolicitudGratuitaDTO;
import org.itcgae.siga.DTOs.cen.EstadoMutualistaDTO;
import org.itcgae.siga.DTOs.cen.EstadoSolicitudDTO;
import org.itcgae.siga.DTOs.cen.MutualidadCombosDTO;
import org.itcgae.siga.DTOs.cen.MutualidadResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.cen.services.IMutualidadService;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.ws.client.ClientMutualidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import samples.servicemodel.microsoft.EstadoMutualistaDocument;
import samples.servicemodel.microsoft.EstadoMutualistaDocument.EstadoMutualista;
import samples.servicemodel.microsoft.EstadoSolicitudDocument;
import samples.servicemodel.microsoft.EstadoSolicitudDocument.EstadoSolicitud;
import samples.servicemodel.microsoft.GetEnumsDocument;
import samples.servicemodel.microsoft.GetEnumsDocument.GetEnums;
import samples.servicemodel.microsoft.MGASolicitudPolizaAccuGratuitosBloqueDocument;
import samples.servicemodel.microsoft.MGASolicitudPolizaAccuGratuitosBloqueDocument.MGASolicitudPolizaAccuGratuitosBloque;
import samples.servicemodel.microsoft.ObtenerCuotaYCapObjetivoDocument;
import samples.servicemodel.microsoft.impl.ObtenerCuotaYCapObjetivoDocumentImpl;

@Service
public class MutualidadServiceImpl implements IMutualidadService{

	private Logger LOGGER = Logger.getLogger(MutualidadServiceImpl.class);
	
	@Autowired
	private GenParametrosMapper _genParametrosMapper;
	
	@Autowired
	private ClientMutualidad _clientMutualidad;
	
	
	@Override
	public MutualidadResponseDTO getEstadoSolicitud(EstadoSolicitudDTO estadoSolicitud) {
		LOGGER.info("getEstadoSolicitud() --> Entrada al servicio para obtener el estado de la solicitud");
		
		MutualidadResponseDTO response = new MutualidadResponseDTO();
		
		GenParametrosExample example = new GenParametrosExample();
		example.createCriteria().andIdrecursoEqualTo("administracion.parametro.url_ws_mutualidad");
		
		List<GenParametros> config = _genParametrosMapper.selectByExample(example);
		
		if(config != null && config.size() > 0){
			
			String uriService = config.get(0).getValor();
			
			EstadoSolicitudDocument request = EstadoSolicitudDocument.Factory.newInstance();
			EstadoSolicitud requestBody = EstadoSolicitud.Factory.newInstance();
			requestBody.setIdSolicitud(estadoSolicitud.getIdSolicitud());
			requestBody.setQuiereCertificado(estadoSolicitud.isDuplicado());
			
			try {
			IntegracionSolicitudRespuesta responseWS = _clientMutualidad.getEstadoSolicitud(request, uriService);
			
			response.setIdSolicitud(responseWS.getIdSolicitud());
			response.setNMutualista(responseWS.getNMutualista());
			response.setPDF(responseWS.getPDF());
			response.setValorRespuesta(response.getValorRespuesta());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		LOGGER.info("getEstadoSolicitud() --> Salida del servicio para obtener el estado de la solicitud");
		return null;
	}

	@Override
	public MutualidadResponseDTO getEstadoMutualista(EstadoMutualistaDTO estadoMutualistaDTO) {
		LOGGER.info("getEstadoMutualista() --> Entrada al servicio para obtener el estado del mutualista");
		
		MutualidadResponseDTO response = new MutualidadResponseDTO();
		
		GenParametrosExample example = new GenParametrosExample();
		example.createCriteria().andIdrecursoEqualTo("administracion.parametro.url_ws_mutualidad");
		
		List<GenParametros> config = _genParametrosMapper.selectByExample(example);
		
		if(config != null && config.size() > 0){
			
			String uriService = config.get(0).getValor();
			
			EstadoMutualistaDocument request = EstadoMutualistaDocument.Factory.newInstance();
			EstadoMutualista requestBody = EstadoMutualista.Factory.newInstance();
			requestBody.setNIF(estadoMutualistaDTO.getIdentificador());
			Calendar cal = Calendar.getInstance();
			cal.setTime(estadoMutualistaDTO.getFechaNacimiento());
			requestBody.setFNacimiento(cal);
			request.setEstadoMutualista(requestBody);
			
			try {
				IntegracionSolicitudRespuesta responseWS = _clientMutualidad.getEstadoMutualista(request, uriService);
				response.setIdSolicitud(responseWS.getIdSolicitud());
				response.setNMutualista(responseWS.getNMutualista());
				response.setPDF(responseWS.getPDF());
				response.setValorRespuesta(response.getValorRespuesta());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		LOGGER.info("getEstadoMutualista() --> Salida servicio para obtener el estado del mutualista");
		return response;
	}

	@Override
	public MutualidadCombosDTO getEnums() {
		LOGGER.info("getEnums() --> Entrada al servicio para obtener los enumerados");
		
		MutualidadCombosDTO combosResponse = new MutualidadCombosDTO();
		try {
			
			GenParametrosExample example = new GenParametrosExample();
			example.createCriteria().andIdrecursoEqualTo("administracion.parametro.url_ws_mutualidad");
			
			List<GenParametros> config = _genParametrosMapper.selectByExample(example);
			
			if(config != null && config.size() > 0){
				
				String uriService = config.get(0).getValor();
				
				GetEnumsDocument request = GetEnumsDocument.Factory.newInstance();
				GetEnums enums = GetEnums.Factory.newInstance();
				request.setGetEnums(enums);
				IntegracionEnumsCombos responseWS = _clientMutualidad.getEnums(request, uriService);
				
				if(responseWS != null){
					
					//combo asistencia sanitaria
					for(int i = 0; i < responseWS.getAsistenciaSanitaria().sizeOfIntegracionTextoValorArray();i++){
						IntegracionTextoValor[] textoValor = responseWS.getAsistenciaSanitaria().getIntegracionTextoValorArray();
						ComboDTO combo = new ComboDTO();
						List<ComboItem> items = new ArrayList<ComboItem>();
						for(int x = 0; x < textoValor.length ; i++){
							ComboItem item = new ComboItem();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor()+"");
							items.add(item);
						}
						combo.combooItems(items);
						combosResponse.setAsistenciaSanitaria(combo);
					}
					
					//designacion beneficiarios
					for(int i = 0; i < responseWS.getDesignacionBeneficiarios().sizeOfIntegracionTextoValorArray();i++){
						IntegracionTextoValor[] textoValor = responseWS.getDesignacionBeneficiarios().getIntegracionTextoValorArray();
						ComboDTO combo = new ComboDTO();
						List<ComboItem> items = new ArrayList<ComboItem>();
						for(int x = 0; x < textoValor.length ; i++){
							ComboItem item = new ComboItem();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor()+"");
							items.add(item);
						}
						combo.combooItems(items);
						combosResponse.setDesignacionBeneficiarios(combo);
					}
					
					//combos designacion ejercientes
					for(int i = 0; i < responseWS.getEjerciente().sizeOfIntegracionTextoValorArray();i++){
						IntegracionTextoValor[] textoValor = responseWS.getEjerciente().getIntegracionTextoValorArray();
						ComboDTO combo = new ComboDTO();
						List<ComboItem> items = new ArrayList<ComboItem>();
						for(int x = 0; x < textoValor.length ; i++){
							ComboItem item = new ComboItem();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor()+"");
							items.add(item);
						}
						combo.combooItems(items);
						combosResponse.setEjerciente(combo);
					}
					
					//combos estados civiles
					for(int i = 0; i < responseWS.getEstadosCiviles().sizeOfIntegracionTextoValorArray();i++){
						IntegracionTextoValor[] textoValor = responseWS.getEstadosCiviles().getIntegracionTextoValorArray();
						ComboDTO combo = new ComboDTO();
						List<ComboItem> items = new ArrayList<ComboItem>();
						for(int x = 0; x < textoValor.length ; i++){
							ComboItem item = new ComboItem();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor()+"");
							items.add(item);
						}
						combo.combooItems(items);
						combosResponse.setEstadosCiviles(combo);
					}
					
					//combos forma de pago
					for(int i = 0; i < responseWS.getFormaPago().sizeOfIntegracionTextoValorArray();i++){
						IntegracionTextoValor[] textoValor = responseWS.getFormaPago().getIntegracionTextoValorArray();
						ComboDTO combo = new ComboDTO();
						List<ComboItem> items = new ArrayList<ComboItem>();
						for(int x = 0; x < textoValor.length ; i++){
							ComboItem item = new ComboItem();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor()+"");
							items.add(item);
						}
						combo.combooItems(items);
						combosResponse.setFormasPago(combo);
					}
					
					//combos opciones coberturas
					for(int i = 0; i < responseWS.getOpcionesCoberturas().sizeOfIntegracionTextoValorArray();i++){
						IntegracionTextoValor[] textoValor = responseWS.getOpcionesCoberturas().getIntegracionTextoValorArray();
						ComboDTO combo = new ComboDTO();
						List<ComboItem> items = new ArrayList<ComboItem>();
						for(int x = 0; x < textoValor.length ; i++){
							ComboItem item = new ComboItem();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor()+"");
							items.add(item);
						}
						combo.combooItems(items);
						combosResponse.setOpcionesCoberturas(combo);
					}
					
					//combos sexos
					for(int i = 0; i < responseWS.getSexos().sizeOfIntegracionTextoValorArray();i++){
						IntegracionTextoValor[] textoValor = responseWS.getSexos().getIntegracionTextoValorArray();
						ComboDTO combo = new ComboDTO();
						List<ComboItem> items = new ArrayList<ComboItem>();
						for(int x = 0; x < textoValor.length ; i++){
							ComboItem item = new ComboItem();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor()+"");
							items.add(item);
						}
						combo.combooItems(items);
						combosResponse.setSexos(combo);
					}
					
					
					//combos tipo direccion
					for(int i = 0; i < responseWS.getTiposDireccion().sizeOfIntegracionTextoValorArray();i++){
						IntegracionTextoValor[] textoValor = responseWS.getTiposDireccion().getIntegracionTextoValorArray();
						ComboDTO combo = new ComboDTO();
						List<ComboItem> items = new ArrayList<ComboItem>();
						for(int x = 0; x < textoValor.length ; i++){
							ComboItem item = new ComboItem();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor()+"");
							items.add(item);
						}
						combo.combooItems(items);
						combosResponse.setTiposDireccion(combo);
					}
					
					
					//combos tipo domicilio
					for(int i = 0; i < responseWS.getTiposDomicilio().sizeOfIntegracionTextoValorArray();i++){
						IntegracionTextoValor[] textoValor = responseWS.getTiposDomicilio().getIntegracionTextoValorArray();
						ComboDTO combo = new ComboDTO();
						List<ComboItem> items = new ArrayList<ComboItem>();
						for(int x = 0; x < textoValor.length ; i++){
							ComboItem item = new ComboItem();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor()+"");
							items.add(item);
						}
						combo.combooItems(items);
						combosResponse.setTiposDomicilio(combo);
					}
					
					
					//combos tipo identificador
					for(int i = 0; i < responseWS.getTiposIdentificador().sizeOfIntegracionTextoValorArray();i++){
						IntegracionTextoValor[] textoValor = responseWS.getTiposIdentificador().getIntegracionTextoValorArray();
						ComboDTO combo = new ComboDTO();
						List<ComboItem> items = new ArrayList<ComboItem>();
						for(int x = 0; x < textoValor.length ; i++){
							ComboItem item = new ComboItem();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor()+"");
							items.add(item);
						}
						combo.combooItems(items);
						combosResponse.setTiposIdentificador(combo);
					}
					
				}
				
			}
		} catch (Exception e) {
			LOGGER.error("getEnums() --> error en el servicio: " + e.getMessage());
			e.printStackTrace();
		}
		
		
		
		LOGGER.info("getEnums() --> Salida del servicio para obtener los enumerados");
		return combosResponse;
	}

	@Override
	public MutualidadResponseDTO MGASolicitudPolizaAccuGratuitos(DatosSolicitudGratuitaDTO solicitud) {
		LOGGER.info("MGASolicitudPolizaAccuGratuitos() --> Entrada al servicio para solicitar la poliza gratuita");
		
		MutualidadResponseDTO response = new MutualidadResponseDTO();
		
		GenParametrosExample example = new GenParametrosExample();
		example.createCriteria().andIdrecursoEqualTo("administracion.parametro.url_ws_mutualidad");
		
		List<GenParametros> config = _genParametrosMapper.selectByExample(example);
		
		if(config != null && config.size() > 0){
			
			String uriService = config.get(0).getValor();
			
			MGASolicitudPolizaAccuGratuitosBloqueDocument request = MGASolicitudPolizaAccuGratuitosBloqueDocument.Factory.newInstance();
			MGASolicitudPolizaAccuGratuitosBloque requestBody = MGASolicitudPolizaAccuGratuitosBloque.Factory.newInstance();
			IntegracionColegiadosBloque colegiadosBloque = IntegracionColegiadosBloque.Factory.newInstance();
			IntegracionPersona persona = IntegracionPersona.Factory.newInstance();
			persona.setApellido1(solicitud.getDatosPersona().getApellido1());
			persona.setApellido2(solicitud.getDatosPersona().getApellido2());
			//persona.setAsistenciaSanitaria(solicitud.getDatos);
			
			try {
				IntegracionSolicitudRespuesta responseWS = _clientMutualidad.MGASolicitudPolizaAccuGratuitos(request, uriService);
				response.setIdSolicitud(responseWS.getIdSolicitud());
				response.setNMutualista(responseWS.getNMutualista());
				response.setPDF(responseWS.getPDF());
				response.setValorRespuesta(response.getValorRespuesta());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		LOGGER.info("MGASolicitudPolizaAccuGratuitos() --> Salida del servicio para solicitar la poliza gratuita");
		return null;
	}

	@Override
	public MutualidadResponseDTO MGASolicitudPolizaProfesional(DatosSolicitudGratuitaDTO datosSolicitud) {
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
	public MutualidadResponseDTO ObtenerCuotaYCapObjetivo(CuotaYCapObjetivoDTO datosCuota) {
		LOGGER.info("MGASolicitudPolizaProfesional() --> Entrada al servicio para obtener la cuota y capital objetivo");
		
		MutualidadResponseDTO response = new MutualidadResponseDTO();
		
		GenParametrosExample example = new GenParametrosExample();
		example.createCriteria().andIdrecursoEqualTo("administracion.parametro.url_ws_mutualidad");
		
		List<GenParametros> config = _genParametrosMapper.selectByExample(example);
		
		if(config != null && config.size() > 0){
			
			String uriService = config.get(0).getValor();
			
			ObtenerCuotaYCapObjetivoDocument request = ObtenerCuotaYCapObjetivoDocument.Factory.newInstance();
			
			//request.setObtenerCuotaYCapObjetivo(arg0);
		}
		
		LOGGER.info("MGASolicitudPolizaProfesional() --> Salida del servicio para obtener la cuota y capital objetivo");
		return null;
	}

}
