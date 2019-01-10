package org.itcgae.siga.cen.services.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.datacontract.schemas._2004._07.ArrayOfIntegracionColegiadosBloque;
import org.datacontract.schemas._2004._07.ArrayOfIntegracionDomicilio;
import org.datacontract.schemas._2004._07.ArrayOfIntegracionSolicitudRespuesta;
import org.datacontract.schemas._2004._07.IntegracionColegiadosBloque;
import org.datacontract.schemas._2004._07.IntegracionCuotaYCapitalObjetivoJubilacion;
import org.datacontract.schemas._2004._07.IntegracionDomicilio;
import org.datacontract.schemas._2004._07.IntegracionEnumsCombos;
import org.datacontract.schemas._2004._07.IntegracionPersona;
import org.datacontract.schemas._2004._07.IntegracionSolicitud;
import org.datacontract.schemas._2004._07.IntegracionSolicitudRespuesta;
import org.datacontract.schemas._2004._07.IntegracionTextoValor;
import org.itcgae.siga.DTOs.cen.CuotaYCapObjetivoDTO;
import org.itcgae.siga.DTOs.cen.CuotaYCapitalObjetivoResponseDTO;
import org.itcgae.siga.DTOs.cen.DatosSolicitudGratuitaDTO;
import org.itcgae.siga.DTOs.cen.EstadoMutualistaDTO;
import org.itcgae.siga.DTOs.cen.EstadoSolicitudDTO;
import org.itcgae.siga.DTOs.cen.MutualidadCombosDTO;
import org.itcgae.siga.DTOs.cen.MutualidadResponseDTO;
import org.itcgae.siga.DTOs.gen.ComboItemMutualidad;
import org.itcgae.siga.DTOs.gen.ComboMutualidadDTO;
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
import samples.servicemodel.microsoft.MGASolicitudPolizaAccuGratuitosDocument;
import samples.servicemodel.microsoft.MGASolicitudPolizaProfesionalDocument;
import samples.servicemodel.microsoft.ObtenerCuotaYCapObjetivoDocument;

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
			request.setEstadoSolicitud(requestBody);
			
			try {
			IntegracionSolicitudRespuesta responseWS = _clientMutualidad.getEstadoSolicitud(request, uriService);
			
			response.setIdSolicitud(responseWS.getIdSolicitud());
			response.setIdSolicitudRespuesta(String.valueOf(responseWS.getIdSolicitudRespuesta()));
			response.setNMutualista(responseWS.getNMutualista());
			response.setPDF(responseWS.getPDF());
			response.setValorRespuesta(responseWS.getValorRespuesta());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		LOGGER.info("getEstadoSolicitud() --> Salida del servicio para obtener el estado de la solicitud");
		return response;
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
				response.setIdSolicitudRespuesta(String.valueOf(responseWS.getIdSolicitudRespuesta()));
				response.setNMutualista(responseWS.getNMutualista());
				response.setPDF(responseWS.getPDF());
				response.setValorRespuesta(responseWS.getValorRespuesta());
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
						ComboMutualidadDTO combo = new ComboMutualidadDTO();
						List<ComboItemMutualidad> items = new ArrayList<ComboItemMutualidad>();
						for(int x = 0; x < textoValor.length ; x++){
							ComboItemMutualidad item = new ComboItemMutualidad();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor());
							items.add(item);
						}
						combo.setCombooItems(items);
						combosResponse.setAsistenciaSanitaria(combo);
					}
					
					//designacion beneficiarios
					for(int i = 0; i < responseWS.getDesignacionBeneficiarios().sizeOfIntegracionTextoValorArray();i++){
						IntegracionTextoValor[] textoValor = responseWS.getDesignacionBeneficiarios().getIntegracionTextoValorArray();
						ComboMutualidadDTO combo = new ComboMutualidadDTO();
						List<ComboItemMutualidad> items = new ArrayList<ComboItemMutualidad>();
						for(int x = 0; x < textoValor.length ; x++){
							ComboItemMutualidad item = new ComboItemMutualidad();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor());
							items.add(item);
						}
						combo.setCombooItems(items);
						combosResponse.setDesignacionBeneficiarios(combo);
					}
					
					//combos designacion ejercientes
					for(int i = 0; i < responseWS.getEjerciente().sizeOfIntegracionTextoValorArray();i++){
						IntegracionTextoValor[] textoValor = responseWS.getEjerciente().getIntegracionTextoValorArray();
						ComboMutualidadDTO combo = new ComboMutualidadDTO();
						List<ComboItemMutualidad> items = new ArrayList<ComboItemMutualidad>();
						for(int x = 0; x < textoValor.length ; x++){
							ComboItemMutualidad item = new ComboItemMutualidad();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor());
							items.add(item);
						}
						combo.setCombooItems(items);
						combosResponse.setEjerciente(combo);
					}
					
					//combos estados civiles
					for(int i = 0; i < responseWS.getEstadosCiviles().sizeOfIntegracionTextoValorArray();i++){
						IntegracionTextoValor[] textoValor = responseWS.getEstadosCiviles().getIntegracionTextoValorArray();
						ComboMutualidadDTO combo = new ComboMutualidadDTO();
						List<ComboItemMutualidad> items = new ArrayList<ComboItemMutualidad>();
						for(int x = 0; x < textoValor.length ; x++){
							ComboItemMutualidad item = new ComboItemMutualidad();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor());
							items.add(item);
						}
						combo.setCombooItems(items);
						combosResponse.setEstadosCiviles(combo);
					}
					
					//combos forma de pago
					for(int i = 0; i < responseWS.getFormaPago().sizeOfIntegracionTextoValorArray();i++){
						IntegracionTextoValor[] textoValor = responseWS.getFormaPago().getIntegracionTextoValorArray();
						ComboMutualidadDTO combo = new ComboMutualidadDTO();
						List<ComboItemMutualidad> items = new ArrayList<ComboItemMutualidad>();
						for(int x = 0; x < textoValor.length ; x++){
							ComboItemMutualidad item = new ComboItemMutualidad();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor());
							items.add(item);
						}
						combo.setCombooItems(items);
						combosResponse.setFormasPago(combo);
					}
					
					//combos opciones coberturas
					for(int i = 0; i < responseWS.getOpcionesCoberturas().sizeOfIntegracionTextoValorArray();i++){
						IntegracionTextoValor[] textoValor = responseWS.getOpcionesCoberturas().getIntegracionTextoValorArray();
						ComboMutualidadDTO combo = new ComboMutualidadDTO();
						List<ComboItemMutualidad> items = new ArrayList<ComboItemMutualidad>();
						for(int x = 0; x < textoValor.length ; x++){
							ComboItemMutualidad item = new ComboItemMutualidad();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor());
							items.add(item);
						}
						combo.setCombooItems(items);
						combosResponse.setOpcionesCoberturas(combo);
					}
					
					//combos sexos
					for(int i = 0; i < responseWS.getSexos().sizeOfIntegracionTextoValorArray();i++){
						IntegracionTextoValor[] textoValor = responseWS.getSexos().getIntegracionTextoValorArray();
						ComboMutualidadDTO combo = new ComboMutualidadDTO();
						List<ComboItemMutualidad> items = new ArrayList<ComboItemMutualidad>();
						for(int x = 0; x < textoValor.length ; x++){
							ComboItemMutualidad item = new ComboItemMutualidad();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor());
							items.add(item);
						}
						combo.setCombooItems(items);
						combosResponse.setSexos(combo);
					}
					
					
					//combos tipo direccion
					for(int i = 0; i < responseWS.getTiposDireccion().sizeOfIntegracionTextoValorArray();i++){
						IntegracionTextoValor[] textoValor = responseWS.getTiposDireccion().getIntegracionTextoValorArray();
						ComboMutualidadDTO combo = new ComboMutualidadDTO();
						List<ComboItemMutualidad> items = new ArrayList<ComboItemMutualidad>();
						for(int x = 0; x < textoValor.length ; x++){
							ComboItemMutualidad item = new ComboItemMutualidad();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor());
							items.add(item);
						}
						combo.setCombooItems(items);
						combosResponse.setTiposDireccion(combo);
					}
					
					
					//combos tipo domicilio
					for(int i = 0; i < responseWS.getTiposDomicilio().sizeOfIntegracionTextoValorArray();i++){
						IntegracionTextoValor[] textoValor = responseWS.getTiposDomicilio().getIntegracionTextoValorArray();
						ComboMutualidadDTO combo = new ComboMutualidadDTO();
						List<ComboItemMutualidad> items = new ArrayList<ComboItemMutualidad>();
						for(int x = 0; x < textoValor.length ; x++){
							ComboItemMutualidad item = new ComboItemMutualidad();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor());
							items.add(item);
						}
						combo.setCombooItems(items);
						combosResponse.setTiposDomicilio(combo);
					}
					
					
					//combos tipo identificador
					for(int i = 0; i < responseWS.getTiposIdentificador().sizeOfIntegracionTextoValorArray();i++){
						IntegracionTextoValor[] textoValor = responseWS.getTiposIdentificador().getIntegracionTextoValorArray();
						ComboMutualidadDTO combo = new ComboMutualidadDTO();
						List<ComboItemMutualidad> items = new ArrayList<ComboItemMutualidad>();
						for(int x = 0; x < textoValor.length ; x++){
							ComboItemMutualidad item = new ComboItemMutualidad();
							item.setLabel(textoValor[x].getOpcion());
							item.setValue(textoValor[x].getValor());
							items.add(item);
						}
						combo.setCombooItems(items);
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
			
			MGASolicitudPolizaAccuGratuitosDocument request = MGASolicitudPolizaAccuGratuitosDocument.Factory.newInstance();
			samples.servicemodel.microsoft.MGASolicitudPolizaAccuGratuitosDocument.MGASolicitudPolizaAccuGratuitos requestBody = samples.servicemodel.microsoft.MGASolicitudPolizaAccuGratuitosDocument.MGASolicitudPolizaAccuGratuitos.Factory.newInstance();
			
			IntegracionPersona persona = IntegracionPersona.Factory.newInstance();
			persona.setApellido1(solicitud.getDatosPersona().getApellido1());
			persona.setApellido2(solicitud.getDatosPersona().getApellido2());
			persona.setColegio(solicitud.getDatosPersona().getColegio());
			persona.setEjerciente(solicitud.getDatosPersona().getEjerciente());
			persona.setEstadoCivil(Integer.parseInt(solicitud.getDatosPersona().getEstadoCivil()));
			Calendar cal = Calendar.getInstance();
			cal.setTime(solicitud.getDatosPersona().getFechaNacimiento());
			persona.setFNacimiento(cal);
			cal.setTime(solicitud.getDatosPersona().getFechaNacConyuge());
			persona.setFNacimientoConyuge(cal);
			persona.setNIF(solicitud.getDatosPersona().getNIF());
			persona.setNacionalidad(solicitud.getDatosPersona().getNacionalidad());
			persona.setNombre(solicitud.getDatosPersona().getNombre());
			persona.setNumColegiado(solicitud.getDatosPersona().getNumColegiado());
			persona.setProfesion(solicitud.getDatosPersona().getProfesion());
			//Ver como pasarle el sexo
			persona.setSexo(Integer.parseInt(solicitud.getDatosPersona().getSexo()));
			//colegiadosBloque.setDatosPersona(persona);
			ArrayOfIntegracionDomicilio domicilios = ArrayOfIntegracionDomicilio.Factory.newInstance();
			IntegracionDomicilio domicilio = IntegracionDomicilio.Factory.newInstance();
			domicilio.setBloque(solicitud.getDatosDireccion().getBloque());
			domicilio.setCP(solicitud.getDatosDireccion().getCp());
			domicilio.setDireccion(solicitud.getDatosDireccion().getDireccion());
			domicilio.setEmail(solicitud.getDatosDireccion().getEmail());
			domicilio.setEsc(solicitud.getDatosDireccion().getEsc());
			domicilio.setLetra(solicitud.getDatosDireccion().getLetra());
			domicilio.setMovil(solicitud.getDatosDireccion().getMovil());
			domicilio.setNum(solicitud.getDatosDireccion().getNum());
			domicilio.setPiso(solicitud.getDatosDireccion().getPiso());
			domicilio.setPoblacion(solicitud.getDatosDireccion().getPoblacion());
			domicilio.setProvincia(solicitud.getDatosDireccion().getProvincia());
			domicilio.setTfno(solicitud.getDatosDireccion().getTelefono());
			domicilio.setTipoDireccion(solicitud.getDatosDireccion().getTipoDireccion());
			domicilio.setTipoDomicilio(solicitud.getDatosDireccion().getTipoDomicilio());
			domicilio.setTipoVia(solicitud.getDatosDireccion().getTipoVia());
			
			domicilios.setIntegracionDomicilioArray(0,domicilio);
			
			IntegracionSolicitud solicitudIntegracion = IntegracionSolicitud.Factory.newInstance();
			Calendar date = new GregorianCalendar();
			solicitudIntegracion.setFecha(date);
			solicitudIntegracion.setValorEntrada("53054856C");
			solicitudIntegracion.setIdTipoIdentificador(1);
			solicitudIntegracion.setIdTipoSolicitud(1);

			
//			IntegracionPersona persona = IntegracionPersona.Factory.newInstance();
//			persona.setApellido1("SAIZ");
//			persona.setApellido2("USANO");
//			persona.setColegio("Ilustre Colegio De Abogados De Alcoy");
//			persona.setEjerciente(1);
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(new Date());
//			persona.setFNacimiento(cal);
//			cal.setTime(new Date());
//			persona.setFNacimientoConyuge(cal);
//			persona.setNIF("53054856C");
//			persona.setNombre("JUAN ANTONIO");
//			persona.setNumColegiado("5562");
//			persona.setProfesion("ABOGADO");
//			//Ver como pasarle el sexo
//			persona.setSexo(1);
//
//			ArrayOfIntegracionDomicilio domicilios = ArrayOfIntegracionDomicilio.Factory.newInstance();
//			IntegracionDomicilio domicilio = IntegracionDomicilio.Factory.newInstance();
//
//			domicilio.setCP("46015");
//			domicilio.setDireccion("asdf");
//			domicilio.setEmail("asdf@asdf.es");
//			domicilio.setMovil("635351542");
//			domicilio.setNum("1");
//
//			domicilio.setPoblacion("Valencia");
//			domicilio.setProvincia("Valencia/València");
//			domicilio.setTfno("123123123");
//			domicilio.setTipoDireccion(1);
//			domicilio.setTipoDomicilio(1);
//			
//			IntegracionDomicilio[] domi = (IntegracionDomicilio[]) Array.newInstance(IntegracionDomicilio.class, 1);
//			domi[0] = IntegracionDomicilio.Factory.newInstance();
//			domi[0] = domicilio;
//			domicilios.setIntegracionDomicilioArray(domi);
//			
//			IntegracionSolicitud solicitudIntegracion = IntegracionSolicitud.Factory.newInstance();
//			Calendar date = new GregorianCalendar();
//			solicitudIntegracion.setFecha(date);
//			solicitudIntegracion.setValorEntrada("53054856C");
//			solicitudIntegracion.setIdTipoIdentificador(1);
//			solicitudIntegracion.setIdTipoSolicitud(1);
			
			requestBody.setDatosDomicilio(domicilios);
			requestBody.setDatosSolicitud(solicitudIntegracion);
			requestBody.setDatosPersona(persona);
			request.setMGASolicitudPolizaAccuGratuitos(requestBody);
			
			try {
				IntegracionSolicitudRespuesta responseWS = _clientMutualidad.MGASolicitudPolizaAccuGratuitos(request, uriService);
				response.setIdSolicitud(responseWS.getIdSolicitud());
				response.setIdSolicitudRespuesta(String.valueOf(responseWS.getIdSolicitudRespuesta()));
				response.setNMutualista(responseWS.getNMutualista());
				response.setPDF(responseWS.getPDF());
				response.setValorRespuesta(responseWS.getValorRespuesta());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		LOGGER.info("MGASolicitudPolizaAccuGratuitos() --> Salida del servicio para solicitar la poliza gratuita");
		return response;
	}

	@Override
	public MutualidadResponseDTO MGASolicitudPolizaProfesional(DatosSolicitudGratuitaDTO solicitud) {
		LOGGER.info("MGASolicitudPolizaProfesional() --> Entrada al servicio para solicitar la poliza profesional");
		
		MutualidadResponseDTO response = new MutualidadResponseDTO();
		
		GenParametrosExample example = new GenParametrosExample();
		example.createCriteria().andIdrecursoEqualTo("administracion.parametro.url_ws_mutualidad");
		
		List<GenParametros> config = _genParametrosMapper.selectByExample(example);
		
		if(config != null && config.size() > 0){
			
			String uriService = config.get(0).getValor();
			
			MGASolicitudPolizaProfesionalDocument request = MGASolicitudPolizaProfesionalDocument.Factory.newInstance();
			samples.servicemodel.microsoft.MGASolicitudPolizaProfesionalDocument.MGASolicitudPolizaProfesional requestBody = samples.servicemodel.microsoft.MGASolicitudPolizaProfesionalDocument.MGASolicitudPolizaProfesional.Factory.newInstance();
			
			IntegracionPersona persona = IntegracionPersona.Factory.newInstance();
			persona.setApellido1(solicitud.getDatosPersona().getApellido1());
			if(solicitud.getDatosPersona().getApellido2() != null) {
				persona.setApellido2(solicitud.getDatosPersona().getApellido2());
			}
			persona.setColegio(solicitud.getDatosPersona().getColegio());
			persona.setEjerciente(solicitud.getDatosPersona().getEjerciente());
			persona.setEstadoCivil(Integer.parseInt(solicitud.getDatosPersona().getEstadoCivil()));
			Calendar cal = Calendar.getInstance();
			cal.setTime(solicitud.getDatosPersona().getFechaNacimiento());
			persona.setFNacimiento(cal);
			if(null != solicitud.getDatosPersona().getFechaNacConyuge()) {
				cal.setTime(solicitud.getDatosPersona().getFechaNacConyuge());
				persona.setFNacimientoConyuge(cal);
			}
			persona.setNIF(solicitud.getDatosPersona().getNIF());
			persona.setNacionalidad(solicitud.getDatosPersona().getNacionalidad());
			persona.setNombre(solicitud.getDatosPersona().getNombre());
			persona.setNumColegiado(solicitud.getDatosPersona().getNumColegiado());
			persona.setProfesion(solicitud.getDatosPersona().getProfesion());
			//Ver como pasarle el sexo
			persona.setSexo(Integer.parseInt(solicitud.getDatosPersona().getSexo()));
			//colegiadosBloque.setDatosPersona(persona);
			ArrayOfIntegracionDomicilio domicilios = ArrayOfIntegracionDomicilio.Factory.newInstance();
			IntegracionDomicilio domicilio = IntegracionDomicilio.Factory.newInstance();
//			domicilio.setBloque(solicitud.getDatosDireccion().getBloque());
			domicilio.setCP(solicitud.getDatosDireccion().getCp());
			domicilio.setDireccion(solicitud.getDatosDireccion().getDireccion());
			domicilio.setEmail(solicitud.getDatosDireccion().getEmail());
//			domicilio.setEsc(solicitud.getDatosDireccion().getEsc());
//			domicilio.setLetra(solicitud.getDatosDireccion().getLetra());
			domicilio.setMovil(solicitud.getDatosDireccion().getMovil());
			domicilio.setNum(solicitud.getDatosDireccion().getNum());
//			domicilio.setPiso(solicitud.getDatosDireccion().getPiso());
			domicilio.setPoblacion(solicitud.getDatosDireccion().getPoblacion());
			domicilio.setProvincia(solicitud.getDatosDireccion().getProvincia());
			domicilio.setTfno(solicitud.getDatosDireccion().getTelefono());
//			domicilio.setTipoDireccion(solicitud.getDatosDireccion().getTipoDireccion());
//			domicilio.setTipoDomicilio(solicitud.getDatosDireccion().getTipoDomicilio());
//			domicilio.setTipoVia(solicitud.getDatosDireccion().getTipoVia());
			
			IntegracionDomicilio[] domi = (IntegracionDomicilio[]) Array.newInstance(IntegracionDomicilio.class, 1);
			domi[0] = IntegracionDomicilio.Factory.newInstance();
			domi[0] = domicilio;
			domicilios.setIntegracionDomicilioArray(domi);
			
			IntegracionSolicitud solicitudIntegracion = IntegracionSolicitud.Factory.newInstance();
			Calendar date = new GregorianCalendar();
			solicitudIntegracion.setFecha(date);
			solicitudIntegracion.setValorEntrada(persona.getNIF());
			solicitudIntegracion.setIdTipoIdentificador(1);
			solicitudIntegracion.setIdTipoSolicitud(1);

			
//			IntegracionPersona persona = IntegracionPersona.Factory.newInstance();
//			persona.setApellido1("SAIZ");
//			persona.setApellido2("USANO");
//			persona.setColegio("Ilustre Colegio De Abogados De Alcoy");
//			persona.setEjerciente(1);
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(new Date());
//			persona.setFNacimiento(cal);
//			cal.setTime(new Date());
//			persona.setFNacimientoConyuge(cal);
//			persona.setNIF("53054856C");
//			persona.setNombre("JUAN ANTONIO");
//			persona.setNumColegiado("5562");
//			persona.setProfesion("ABOGADO");
//			//Ver como pasarle el sexo
//			persona.setSexo(1);
//
//			ArrayOfIntegracionDomicilio domicilios = ArrayOfIntegracionDomicilio.Factory.newInstance();
//			IntegracionDomicilio domicilio = IntegracionDomicilio.Factory.newInstance();
//
//			domicilio.setCP("46015");
//			domicilio.setDireccion("asdf");
//			domicilio.setEmail("asdf@asdf.es");
//			domicilio.setMovil("635351542");
//			domicilio.setNum("1");
//
//			domicilio.setPoblacion("Valencia");
//			domicilio.setProvincia("Valencia/València");
//			domicilio.setTfno("123123123");
//			domicilio.setTipoDireccion(1);
//			domicilio.setTipoDomicilio(1);
//			
//			IntegracionDomicilio[] domi = (IntegracionDomicilio[]) Array.newInstance(IntegracionDomicilio.class, 1);
//			domi[0] = IntegracionDomicilio.Factory.newInstance();
//			domi[0] = domicilio;
//			domicilios.setIntegracionDomicilioArray(domi);
//			
//			IntegracionSolicitud solicitudIntegracion = IntegracionSolicitud.Factory.newInstance();
//			Calendar date = new GregorianCalendar();
//			solicitudIntegracion.setFecha(date);
//			solicitudIntegracion.setValorEntrada("53054856C");
//			solicitudIntegracion.setIdTipoIdentificador(1);
//			solicitudIntegracion.setIdTipoSolicitud(1);
			
			requestBody.setDatosDomicilio(domicilios);
			requestBody.setDatosSolicitud(solicitudIntegracion);
			requestBody.setDatosPersona(persona);
			request.setMGASolicitudPolizaProfesional(requestBody);
			try {
				IntegracionSolicitudRespuesta responseWS = _clientMutualidad.MGASolicitudPolizaProfesional(request, uriService);
				response.setIdSolicitud(responseWS.getIdSolicitud());
				response.setIdSolicitudRespuesta(String.valueOf(responseWS.getIdSolicitudRespuesta()));
				response.setNMutualista(responseWS.getNMutualista());
				response.setPDF(responseWS.getPDF());
				response.setValorRespuesta(responseWS.getValorRespuesta());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		LOGGER.info("MGASolicitudPolizaAccuGratuitos() --> Salida del servicio para solicitar la poliza gratuita");
		return response;
	}

	@Override
	public CuotaYCapitalObjetivoResponseDTO ObtenerCuotaYCapObjetivo(CuotaYCapObjetivoDTO datosCuota) {
		LOGGER.info("MGASolicitudPolizaProfesional() --> Entrada al servicio para obtener la cuota y capital objetivo");
		
		MutualidadResponseDTO response = new MutualidadResponseDTO();
		
		GenParametrosExample example = new GenParametrosExample();
		example.createCriteria().andIdrecursoEqualTo("administracion.parametro.url_ws_mutualidad");
		CuotaYCapitalObjetivoResponseDTO cuotaCapitalResponse = new CuotaYCapitalObjetivoResponseDTO();
		List<GenParametros> config = _genParametrosMapper.selectByExample(example);
		
		if(config != null && config.size() > 0){
			
			String uriService = config.get(0).getValor();
			
			ObtenerCuotaYCapObjetivoDocument request = ObtenerCuotaYCapObjetivoDocument.Factory.newInstance();
			
			samples.servicemodel.microsoft.ObtenerCuotaYCapObjetivoDocument.ObtenerCuotaYCapObjetivo obtenerCuota = samples.servicemodel.microsoft.ObtenerCuotaYCapObjetivoDocument.ObtenerCuotaYCapObjetivo.Factory.newInstance();
			obtenerCuota.setSexo(datosCuota.getSexo());
			obtenerCuota.setCobertura(datosCuota.getCobertura());
			Calendar arg0 = new GregorianCalendar();
			arg0.setTime(datosCuota.getFechaNacimiento());
			obtenerCuota.setFNacimiento(arg0);
			request.setObtenerCuotaYCapObjetivo(obtenerCuota);
			try {
				IntegracionCuotaYCapitalObjetivoJubilacion responseWS = _clientMutualidad.ObtenerCuotaYCapObjetivo(request, uriService);
				
				cuotaCapitalResponse.setCapitalObjetivo(responseWS.getCapitalObjetivo());
				cuotaCapitalResponse.setCuota(responseWS.getCuota());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//request.setObtenerCuotaYCapObjetivo(arg0);
		}
		
		LOGGER.info("MGASolicitudPolizaProfesional() --> Salida del servicio para obtener la cuota y capital objetivo");
		return cuotaCapitalResponse;
	}

}
