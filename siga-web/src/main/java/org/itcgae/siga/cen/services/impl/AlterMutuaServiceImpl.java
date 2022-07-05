package org.itcgae.siga.cen.services.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.AlterMutuaResponseDTO;
import org.itcgae.siga.DTOs.cen.EstadoColegiadoDTO;
import org.itcgae.siga.DTOs.cen.EstadoSolicitudDTO;
import org.itcgae.siga.DTOs.cen.MaxIdDto;
import org.itcgae.siga.DTOs.cen.PersonaDTO;
import org.itcgae.siga.DTOs.cen.PropuestaDTO;
import org.itcgae.siga.DTOs.cen.PropuestasDTO;
import org.itcgae.siga.DTOs.cen.SolicitudDTO;
import org.itcgae.siga.cen.services.IAlterMutuaService;
import org.itcgae.siga.db.entities.CenInstitucion;
import org.itcgae.siga.db.entities.CenInstitucionExample;
import org.itcgae.siga.db.entities.CenSolicitudalter;
import org.itcgae.siga.db.entities.CenSolicitudalterExample;
import org.itcgae.siga.db.entities.CenSolicitudincorporacion;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.mappers.CenInstitucionMapper;
import org.itcgae.siga.db.mappers.CenSolicitudalterMapper;
import org.itcgae.siga.db.mappers.CenSolicitudincorporacionMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitudAlterExtendsMapper;
import org.itcgae.siga.ws.client.ClientAlterMutua;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altermutua.www.wssiga.GetEstadoColegiadoDocument;
import com.altermutua.www.wssiga.GetEstadoColegiadoDocument.GetEstadoColegiado;
import com.altermutua.www.wssiga.GetEstadoSolicitudDocument;
import com.altermutua.www.wssiga.GetEstadoSolicitudDocument.GetEstadoSolicitud;
import com.altermutua.www.wssiga.GetPropuestasDocument;
import com.altermutua.www.wssiga.GetPropuestasDocument.GetPropuestas;
import com.altermutua.www.wssiga.GetTarifaSolicitudDocument;
import com.altermutua.www.wssiga.GetTarifaSolicitudDocument.GetTarifaSolicitud;
import com.altermutua.www.wssiga.SetSolicitudAlterDocument;
import com.altermutua.www.wssiga.SetSolicitudAlterDocument.SetSolicitudAlter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.altermutua.www.wssiga.WSAsegurado;
import com.altermutua.www.wssiga.WSCuentaBancaria;
import com.altermutua.www.wssiga.WSDireccion;
import com.altermutua.www.wssiga.WSPersona;
import com.altermutua.www.wssiga.WSRespuesta;
import com.altermutua.www.wssiga.WSSolicitud;




@Service
public class AlterMutuaServiceImpl implements IAlterMutuaService{
	
	private Logger LOGGER = Logger.getLogger(AlterMutuaServiceImpl.class);
	
	@Autowired
	private ClientAlterMutua _clientAlterMutua;
	
	@Autowired
	private GenParametrosMapper _genParametrosMapper;
	
	@Autowired
	private CenSolicitudAlterExtendsMapper _cenSolicitudAlterExtendsMapper;
	
	@Autowired
	private CenInstitucionMapper _cenInstitucionMapper;
	
	@Autowired
	private CenSolicitudalterMapper _cenSolicitudalterMapper;
	
	@Autowired
	private CenSolicitudincorporacionMapper _cenSolicitudincorporacionMapper;
	

	@Override
	public AlterMutuaResponseDTO getEstadoSolicitud(EstadoSolicitudDTO estadosolicitudDTO) {
		
		LOGGER.info("getEstadoSolicitud() --> Entrada al servicio para obtener el estado de la solicitud");
		AlterMutuaResponseDTO responseDTO = new AlterMutuaResponseDTO();
		try{
			
			GenParametrosExample example = new GenParametrosExample();
			example.createCriteria().andIdrecursoEqualTo("administracion.parametro.api_alterm_url");
			
			List<GenParametros> config = _genParametrosMapper.selectByExample(example);
			
			if(config != null && config.size() > 0){
				
				String uriService = config.get(0).getValor();
				
				// Preparamos el JSON que enviaremos a la API
				JSONObject jsonObject = new JSONObject();
				
				if(estadosolicitudDTO.getIdSolicitud() != 0) {
					GetEstadoSolicitudDocument request = GetEstadoSolicitudDocument.Factory.newInstance();
					GetEstadoSolicitud requestBody = GetEstadoSolicitud.Factory.newInstance();
					CenSolicitudincorporacion solIncorporacion = _cenSolicitudincorporacionMapper.selectByPrimaryKey(estadosolicitudDTO.getIdSolicitud());
					
					CenSolicitudalterExample solAlterExample = new CenSolicitudalterExample();
					solAlterExample.createCriteria().andNombreEqualTo(solIncorporacion.getNombre()).andDomicilioEqualTo(solIncorporacion.getDomicilio())
						.andCodigopostalEqualTo(solIncorporacion.getCodigopostal());
					
					List<CenSolicitudalter> solAlter = _cenSolicitudalterMapper.selectByExample(solAlterExample);
					if(solAlter.size() > 0){
						CenSolicitudalter solicitud = solAlter.get(0);
						jsonObject.put("intIdSolicitud", Math.toIntExact(solicitud.getIdsolicitudalter()));
						jsonObject.put("bolDuplicado", estadosolicitudDTO.isDuplicado());
						requestBody.setIntIdSolicitud(Math.toIntExact(solicitud.getIdsolicitudalter()));
						requestBody.setBolDuplicado(estadosolicitudDTO.isDuplicado());
						request.setGetEstadoSolicitud(requestBody);
						LOGGER.info("solicitud.getIdsolicitudalter() --> " + solicitud.getIdsolicitudalter());
						LOGGER.info("estadosolicitudDTO.isDuplicado() --> " + estadosolicitudDTO.isDuplicado());
						
						JsonNode responseAPI = _clientAlterMutua.APICall(jsonObject, uriService + "/AlterApp/GetRequestStatusSIGA");
						
						if (responseAPI != null) {
							
							responseDTO.setDocumento(responseAPI.get("Documento").binaryValue());
							responseDTO.setError(responseAPI.get("Error").asBoolean());
							responseDTO.setMensaje(responseAPI.get("Mensaje").asText());
						}
						
					}else{
						responseDTO.setError(true);
						responseDTO.setMensaje("No existe solicitud de Alter mutua");
					}
				}else{
//				AÑADIR BÚSQUEDA EN CEN_COLEGIADO DONDE BUSQUE LOS DATOS DE LA PERSONA IGUAL QUE HACE ARRIBA EN CASO DE LA SOLICITUD DE INCORP. PARA ASÍ CONTROLAR LA 
//				LLEGADA DESDE FICHA-COLEGIAL AL EXISTIR LA SOLICITUD, QUE DEBE MOSTRAR UN MENSAJE Y NO DEJARME CONTINUAR.
					GetEstadoSolicitudDocument request = GetEstadoSolicitudDocument.Factory.newInstance();
					GetEstadoSolicitud requestBody = GetEstadoSolicitud.Factory.newInstance();
					
					CenSolicitudalterExample solAlterExample = new CenSolicitudalterExample();
					solAlterExample.createCriteria().andNumeroidentificadorEqualTo(estadosolicitudDTO.getIdentificador());
					solAlterExample.setOrderByClause("FECHAMODIFICACION DESC");
					
					List<CenSolicitudalter> solAlter = _cenSolicitudalterMapper.selectByExample(solAlterExample);
					if(solAlter.size() > 0){
						CenSolicitudalter solicitud = solAlter.get(0);
						if(solicitud.getIdsolicitudalter() != null) {
							jsonObject.put("intIdSolicitud", Math.toIntExact(solicitud.getIdsolicitudalter()));
							requestBody.setIntIdSolicitud(Math.toIntExact(solicitud.getIdsolicitudalter()));							
						}
						jsonObject.put("bolDuplicado", estadosolicitudDTO.isDuplicado());
						requestBody.setBolDuplicado(estadosolicitudDTO.isDuplicado());
						request.setGetEstadoSolicitud(requestBody);
						
						JsonNode responseAPI = _clientAlterMutua.APICall(jsonObject, uriService + "/AlterApp/GetRequestStatusSIGA");
						
						if (responseAPI != null) {
							
							responseDTO.setDocumento(responseAPI.get("Documento").binaryValue());
							responseDTO.setError(responseAPI.get("Error").asBoolean());
							responseDTO.setMensaje(responseAPI.get("Mensaje").asText());
						}
					}else{
						responseDTO.setError(true);
						responseDTO.setMensaje("No existe solicitud de Alter mutua");
					}
				}
				
			}
		}catch(Exception e){
			LOGGER.error("getEstadoSolicitud() --> error en el servicio: " + e.getMessage());
			responseDTO.setError(true);
			responseDTO.setMensaje(e.getMessage());
		}
		
		LOGGER.info("getEstadoSolicitud() --> Salida del servicio para obtener el estado de la solicitud");
		return responseDTO;
	}

	@Override
	public AlterMutuaResponseDTO getEstadoColegiado(EstadoColegiadoDTO estadoColegiadoDTO) {
		LOGGER.info("getEstadoColegiado() --> Entrada al servicio para obtener el estado del colegiado");
		
		AlterMutuaResponseDTO responseDTO = new AlterMutuaResponseDTO();
		try{
			GenParametrosExample example = new GenParametrosExample();
			example.createCriteria().andIdrecursoEqualTo("administracion.parametro.api_alterm_url");
			
			List<GenParametros> config = _genParametrosMapper.selectByExample(example);
			
			if(config != null && config.size() > 0){
				
				String uriService = config.get(0).getValor();
				
				// Preparamos el JSON que enviaremos a la API
				JSONObject jsonObject = new JSONObject();
				
				GetEstadoColegiadoDocument request = GetEstadoColegiadoDocument.Factory.newInstance();
				GetEstadoColegiado requestBody = GetEstadoColegiado.Factory.newInstance();
				
				jsonObject.put("intTipoIdentificador", estadoColegiadoDTO.getTipoIdentificador());
				jsonObject.put("strIdentificador", estadoColegiadoDTO.getIdentificador());
				requestBody.setIntTipoIdentificador(estadoColegiadoDTO.getTipoIdentificador());
				requestBody.setStrIdentificador(estadoColegiadoDTO.getIdentificador());
				request.setGetEstadoColegiado(requestBody);
				LOGGER.info("estadoColegiadoDTO.getTipoIdentificador() --> " + estadoColegiadoDTO.getTipoIdentificador());
				LOGGER.info("estadoColegiadoDTO.getIdentificador() --> " + estadoColegiadoDTO.getIdentificador());
				
				JsonNode responseAPI = _clientAlterMutua.APICall(jsonObject, uriService + "/AlterApp/GetMemberStatusSIGA");
				
				if (responseAPI != null) {
					
					responseDTO.setError(responseAPI.get("Error").asBoolean());
					responseDTO.setMensaje(responseAPI.get("Mensaje").asText());
				}
			}
		}catch(Exception e){
			LOGGER.error("getEstadoColegiado() --> error en el servicio: " + e.getMessage());
			responseDTO.setError(true);
			responseDTO.setMensaje(e.getMessage());
			
		}
		LOGGER.info("getEstadoColegiado() --> Salida del servicio para obtener el estado del colegiado");
		return responseDTO;
	}

	@Override
	public AlterMutuaResponseDTO getPropuestas(PropuestasDTO PropuestasDTO) {
		LOGGER.info("getPropuestas() --> Entrada al servicio para obtener las propuestas");
		WSRespuesta responseWS = null;
		AlterMutuaResponseDTO response = new AlterMutuaResponseDTO();
		
		try{
			GenParametrosExample example = new GenParametrosExample();
			example.createCriteria().andIdrecursoEqualTo("administracion.parametro.api_alterm_url");
			
			List<GenParametros> config = _genParametrosMapper.selectByExample(example);
			
			if(config != null && config.size() > 0){
				
				String uriService = config.get(0).getValor();
				GetPropuestasDocument request = GetPropuestasDocument.Factory.newInstance();
				GetPropuestas requestBody = GetPropuestas.Factory.newInstance();
				
				// Preparamos el JSON que enviaremos a la API
				JSONObject jsonObject = new JSONObject();
				
				if(PropuestasDTO.getTipoIdentificador().equals("NIF")){
					jsonObject.put("intTipoIdentificador", 1);
					requestBody.setIntTipoIdentificador(1);
				}else if(PropuestasDTO.getTipoIdentificador().equals("Pasaporte")){
					jsonObject.put("intTipoIdentificador", 0);
					requestBody.setIntTipoIdentificador(0);
				}
				jsonObject.put("strIdentificador", PropuestasDTO.getIdentificador());
				requestBody.setStrIdentificador(PropuestasDTO.getIdentificador());
				
				Calendar cal = Calendar.getInstance();
				if(PropuestasDTO.getFechaNacimiento() != null) {
					cal.setTime(PropuestasDTO.getFechaNacimiento());
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					jsonObject.put("dtFechaNacimiento", sdf.format(PropuestasDTO.getFechaNacimiento()));
					requestBody.setDtFechaNacimiento(cal);
				}
				if(PropuestasDTO.getSexo() != null) {
					if(PropuestasDTO.getSexo().equals("H")){
						jsonObject.put("intSexo", 1);
						requestBody.setIntSexo(1);
					}else if (PropuestasDTO.getSexo().equals("M")){
						jsonObject.put("intSexo", 2);
						requestBody.setIntSexo(2);
					}
				}
				
				jsonObject.put("intTipoPropuesta", PropuestasDTO.getTipoPropuesta());
				requestBody.setIntTipoPropuesta(PropuestasDTO.getTipoPropuesta());
				request.setGetPropuestas(requestBody);
				
				LOGGER.info("requestBody.setStrIdentificador --> " + requestBody.getStrIdentificador());
				LOGGER.info("requestBody.setIntTipoIdentificador --> " + requestBody.getIntTipoIdentificador());
				LOGGER.info("requestBody.setIntSexo --> " + requestBody.getIntSexo());
				
				JsonNode responseAPI = _clientAlterMutua.APICall(jsonObject, uriService + "/AlterApp/GetProposalsSIGA");
				
				if(responseAPI != null){
				
					response.setIdentificador(responseAPI.get("Identificador").asInt());
					response.setError(responseAPI.get("Error").asBoolean());
					response.setMensaje(responseAPI.get("Mensaje").asText());
					
					if(!responseAPI.get("Propuestas").isNull()){
						
						List<PropuestaDTO> propuestas = new ArrayList<PropuestaDTO>();
						ArrayNode propuestasJSON = (ArrayNode) responseAPI.get("Propuestas");
						
						Iterator<JsonNode> propuestasIterator = propuestasJSON.iterator();
						
						while(propuestasIterator.hasNext()) {
							JsonNode propuesta = propuestasIterator.next();
							
							PropuestaDTO propuestaItem = new PropuestaDTO();
							
							propuestaItem.setBreve(propuesta.get("Breve").asText());
							propuestaItem.setDescripcion(propuesta.get("Descripcion").asText());
							propuestaItem.setFamiliares(propuesta.get("Familiares").asBoolean());
							propuestaItem.setHerederos(propuesta.get("Herederos").asBoolean());
							propuestaItem.setIdPaquete(propuesta.get("IdPaquete").asInt());
							propuestaItem.setNombre(propuesta.get("Nombre").asText());
							propuestaItem.setTarifa(propuesta.get("Tarifa").decimalValue());
							propuestas.add(propuestaItem);
						}
						
						response.setPropuestas(propuestas);
					}
				}
				
			}
		}catch(Exception e){
			LOGGER.error("getPropuestas() --> error en el servicio: " + e.getMessage());
			response.setError(true);
			response.setMensaje(e.getMessage());
		}
		LOGGER.info("getPropuestas() --> Salida del servicio para obtener las propuestas");
		return response;
	}

	@Override
	public AlterMutuaResponseDTO getTarifaSolicitud(SolicitudDTO solicitud) {
		
		LOGGER.info("getTarifaSolicitud() --> Entrada al servicio para obtener las tarifas");
		AlterMutuaResponseDTO responseDTO = new AlterMutuaResponseDTO();
		
		try{
			GenParametrosExample example = new GenParametrosExample();
			example.createCriteria().andIdrecursoEqualTo("administracion.parametro.api_alterm_url");
			
			List<GenParametros> config = _genParametrosMapper.selectByExample(example);
			
			if(config != null && config.size() > 0){
				if(config != null && config.size() > 0){
					
					String uriService = config.get(0).getValor();
					
					// Preparamos el JSON que enviaremos a la API
					JSONObject jsonObject = new JSONObject();
					
					GetTarifaSolicitudDocument request = GetTarifaSolicitudDocument.Factory.newInstance();
					GetTarifaSolicitud requestBody = GetTarifaSolicitud.Factory.newInstance();
					WSSolicitud WSsolicitud = WSSolicitud.Factory.newInstance();
					jsonObject.put("IdPaquete", solicitud.getIdPaquete());
					WSsolicitud.setIdPaquete(solicitud.getIdPaquete());
					
					JSONObject aseguradoJSON = new JSONObject();
					WSAsegurado asegurado = WSAsegurado.Factory.newInstance();
					aseguradoJSON.put("Apellidos", solicitud.getAsegurado().getApellidos());
					aseguradoJSON.put("Colegio", solicitud.getAsegurado().getColegio());
					asegurado.setApellidos(solicitud.getAsegurado().getApellidos());
					asegurado.setColegio(solicitud.getAsegurado().getColegio());
					
					JSONObject datosBancariosJSON = new JSONObject();
					WSCuentaBancaria datosBancarios = WSCuentaBancaria.Factory.newInstance();
					datosBancariosJSON.put("IBAN", solicitud.getAsegurado().getIban());
					datosBancariosJSON.put("DC", solicitud.getAsegurado().getIban().substring(12, 14));
					datosBancariosJSON.put("Pais", solicitud.getAsegurado().getIban().substring(0, 2));
					datosBancariosJSON.put("Cuenta", solicitud.getAsegurado().getIban().substring(14, 24));
					datosBancarios.setIBAN(solicitud.getAsegurado().getIban());
					datosBancarios.setDC(solicitud.getAsegurado().getIban().substring(12, 14));
					datosBancarios.setPais(solicitud.getAsegurado().getIban().substring(0, 2));
					datosBancarios.setCuenta(solicitud.getAsegurado().getIban().substring(14, 24));
					aseguradoJSON.put("CuentaBancaria", datosBancariosJSON);
					asegurado.setCuentaBancaria(datosBancarios);
					
					JSONObject direccionJSON = new JSONObject();
					WSDireccion direccion = WSDireccion.Factory.newInstance();
					direccionJSON.put("CodigoPostal", solicitud.getAsegurado().getCp());
					direccionJSON.put("DireccionPostal", solicitud.getAsegurado().getDomicilio());
					direccionJSON.put("Email", solicitud.getAsegurado().getMail());
					direccion.setCodigoPostal(solicitud.getAsegurado().getCp());
					direccion.setDireccionPostal(solicitud.getAsegurado().getDomicilio());
					direccion.setEmail(solicitud.getAsegurado().getMail());
					if(solicitud.getAsegurado().getFax()!= null){
						direccionJSON.put("Fax", solicitud.getAsegurado().getFax());
						direccion.setFax(solicitud.getAsegurado().getFax());
					}
					direccionJSON.put("Movil", solicitud.getAsegurado().getMovil());
					direccionJSON.put("Pais", solicitud.getAsegurado().getPais());
					direccion.setMovil(solicitud.getAsegurado().getFax());
					direccion.setPais(solicitud.getAsegurado().getPais());
					if(solicitud.getAsegurado().getProvincia()!= null){
						direccionJSON.put("Provincia", solicitud.getAsegurado().getProvincia());
						direccion.setProvincia(solicitud.getAsegurado().getProvincia());
					}
					if(solicitud.getAsegurado().getPoblacion()!= null){
						direccionJSON.put("Poblacion", solicitud.getAsegurado().getPoblacion());
						direccion.setPoblacion(solicitud.getAsegurado().getPoblacion());
					}
					if(solicitud.getAsegurado().getTelefono()!= null){
						direccionJSON.put("Telefono1", solicitud.getAsegurado().getTelefono());
						direccion.setTelefono1(solicitud.getAsegurado().getTelefono());
					}
					if(solicitud.getAsegurado().getTelefono2()!= null){
						direccionJSON.put("Telefono2", solicitud.getAsegurado().getTelefono2());
						direccion.setTelefono2(solicitud.getAsegurado().getTelefono2());
					}
					direccionJSON.put("TipoDireccion", Integer.parseInt(solicitud.getAsegurado().getTipoDireccion()));
					direccion.setTipoDireccion(Integer.parseInt(solicitud.getAsegurado().getTipoDireccion()));	
					aseguradoJSON.put("Direccion", direccionJSON);
					aseguradoJSON.put("EstadoCivil", Integer.parseInt(solicitud.getAsegurado().getEstadoCivil()));
					asegurado.setDireccion(direccion);
					asegurado.setEstadoCivil(Integer.parseInt(solicitud.getAsegurado().getEstadoCivil()));
					
					
					if(solicitud.getHerederos() != null){
						if(solicitud.getHerederos().size() > 0){
							JSONArray herederosJSON = new JSONArray();
							WSPersona[] herederos = new WSPersona[solicitud.getHerederos().size()];
							int index = 0;
							for (PersonaDTO persona : solicitud.getHerederos()) {
								JSONObject herederoJSON = new JSONObject();
								WSPersona heredero = WSPersona.Factory.newInstance();
								Calendar cal = Calendar.getInstance();
								
								herederoJSON.put("Parentesco", getParentesco(persona.getParentesco()));
								herederoJSON.put("Apellidos", persona.getApellido());
								herederoJSON.put("Nombre", persona.getNombre());
								heredero.setParentesco(getParentesco(persona.getParentesco()));
								heredero.setApellidos(persona.getApellido());
								heredero.setNombre(persona.getNombre());
								if(persona.getSexo().equals("H")==true){
									herederoJSON.put("Sexo", 1);
									heredero.setSexo(1);
								}else{
									herederoJSON.put("Sexo", 2);
									heredero.setSexo(2);
								}
								herederoJSON.put("Identificador", persona.getIdentificacion());
								heredero.setIdentificador(persona.getIdentificacion());
								if(persona.getTipoIdentificacion().equals("NIF")){
									herederoJSON.put("TipoIdentificador", 0);
									heredero.setTipoIdentificador(0);
								}else{
									herederoJSON.put("TipoIdentificador", 1);
									heredero.setTipoIdentificador(1);
								}
								cal.setTime(persona.getFechaNacimiento());
								herederoJSON.put("FechaNacimiento", cal.toInstant());
								heredero.setFechaNacimiento(cal);
								herederosJSON.put(herederoJSON);
								herederos[index] = heredero;
								index++;
							}
							jsonObject.put("Herederos", herederosJSON);
							WSsolicitud.setHerederosArray(herederos);
						}
					}else{
						if(solicitud.getFamiliares().size() > 0){
							JSONArray familiaresJSON = new JSONArray();
							WSPersona[] familiares = new WSPersona[solicitud.getFamiliares().size()];
							int index = 0;
							for (PersonaDTO persona : solicitud.getFamiliares()) {
								JSONObject familiarJSON = new JSONObject();
								WSPersona familiar = WSPersona.Factory.newInstance();
								Calendar cal = Calendar.getInstance();
								
								familiarJSON.put("Parentesco", getParentesco(persona.getParentesco()));
								familiarJSON.put("Apellidos", persona.getApellido());
								familiarJSON.put("Nombre", persona.getNombre());
								familiar.setParentesco(getParentesco(persona.getParentesco()));
								familiar.setApellidos(persona.getApellido());
								familiar.setNombre(persona.getNombre());
								if(persona.getSexo().equals("H")==true){
									familiarJSON.put("Sexo", 1);
									asegurado.setSexo(1);
								}else{
									familiarJSON.put("Sexo", 2);
									asegurado.setSexo(2);
								}
								familiarJSON.put("Identificador", persona.getIdentificacion());
								familiar.setIdentificador(persona.getIdentificacion());
								if(persona.getTipoIdentificacion().equals("NIF")){
									familiarJSON.put("TipoIdentificador", 0);
									familiar.setTipoIdentificador(0);
								}else{
									familiarJSON.put("TipoIdentificador", 1);
									familiar.setTipoIdentificador(1);
								}
								cal.setTime(persona.getFechaNacimiento());
								familiarJSON.put("FechaNacimiento", cal.toInstant());
								familiar.setFechaNacimiento(cal);
								familiaresJSON.put(familiarJSON);
								familiares[index] = familiar;
								index++;
							}
							aseguradoJSON.put("Familiares", familiaresJSON);
							asegurado.setFamiliaresArray(familiares);
						}
					}
					Calendar cal = Calendar.getInstance();
					cal.setTime(solicitud.getAsegurado().getFechaNacimiento());
					aseguradoJSON.put("FechaNacimiento", cal.toInstant());
					aseguradoJSON.put("Identificador", solicitud.getAsegurado().getIdentificador());
					aseguradoJSON.put("Idioma", Integer.parseInt(solicitud.getAsegurado().getIdioma()));
					aseguradoJSON.put("Nombre", solicitud.getAsegurado().getNombre());
					aseguradoJSON.put("Publicidad", solicitud.getAsegurado().isPublicidad());
					asegurado.setFechaNacimiento(cal);
					asegurado.setIdentificador(solicitud.getAsegurado().getIdentificador());
					asegurado.setIdioma(Integer.parseInt(solicitud.getAsegurado().getIdioma()));
					asegurado.setNombre(solicitud.getAsegurado().getNombre());
					asegurado.setPublicidad(solicitud.getAsegurado().isPublicidad());
					if(solicitud.getAsegurado().getSexo().equals("H")==true){
						aseguradoJSON.put("Sexo", 1);
						asegurado.setSexo(1);
					}else{
						aseguradoJSON.put("Sexo", 2);
						asegurado.setSexo(2);
					}
					aseguradoJSON.put("TipoComunicacion", Integer.parseInt(solicitud.getAsegurado().getMedioComunicacion()));
					asegurado.setTipoComunicacion(Integer.parseInt(solicitud.getAsegurado().getMedioComunicacion()));
					
					if(solicitud.getAsegurado().getTipoIdentificador().equals("NIF")==true || solicitud.getAsegurado().getTipoIdentificador().equals("NIE")==true){
						aseguradoJSON.put("TipoIdentificador", 0);
						asegurado.setTipoIdentificador(0);
					}else{
						aseguradoJSON.put("TipoIdentificador", 1);
						asegurado.setTipoIdentificador(1);
					}
					
					jsonObject.put("Asegurado", aseguradoJSON);
					WSsolicitud.setAsegurado(asegurado);
					requestBody.setWsSolicitud(WSsolicitud);
					request.setGetTarifaSolicitud(requestBody);
					
					JsonNode responseAPI = _clientAlterMutua.APICall(jsonObject, uriService + "/AlterApp/GetRequestTariffSIGA");
					
					if(responseAPI != null){
					
						responseDTO.setIdentificador(responseAPI.get("Identificador").asInt());
						responseDTO.setDocumento(responseAPI.get("Documento").binaryValue());
						responseDTO.setError(responseAPI.get("Error").asBoolean());
						responseDTO.setMensaje(responseAPI.get("Mensaje").asText());
						
						if(!responseAPI.get("Propuestas").isNull()){
							
							List<PropuestaDTO> propuestas = new ArrayList<PropuestaDTO>();
							ArrayNode propuestasJSON = (ArrayNode) responseAPI.get("Propuestas");
							
							Iterator<JsonNode> propuestasIterator = propuestasJSON.iterator();
							
							while(propuestasIterator.hasNext()) {
								JsonNode propuesta = propuestasIterator.next();
								
								PropuestaDTO propuestaItem = new PropuestaDTO();
								
								propuestaItem.setBreve(propuesta.get("Breve").asText());
								propuestaItem.setDescripcion(propuesta.get("Descripcion").asText());
								propuestaItem.setFamiliares(propuesta.get("Familiares").asBoolean());
								propuestaItem.setHerederos(propuesta.get("Herederos").asBoolean());
								propuestaItem.setIdPaquete(propuesta.get("IdPaquete").asInt());
								propuestaItem.setNombre(propuesta.get("Nombre").asText());
								propuestaItem.setTarifa(propuesta.get("Tarifa").decimalValue());
								propuestas.add(propuestaItem);
							}
							
							responseDTO.setPropuestas(propuestas);
						}
					}
					
				}
				
			}
			
		}catch(Exception e){
			LOGGER.error("getTarifaSolicitud() --> error en el servicio: " + e.getMessage());
			responseDTO.setError(true);
			responseDTO.setMensaje(e.getMessage());
		}
		
		LOGGER.info("getTarifaSolicitud() --> Salida del servicio para obtener las tarifas");
		return responseDTO;
	}

	@Override
	public AlterMutuaResponseDTO setSolicitudAlter(SolicitudDTO solicitud) {

		LOGGER.info("setSolicitudAlter() -->  Entrada al servicio para enviar datos alter");
		AlterMutuaResponseDTO responseDTO = new AlterMutuaResponseDTO();
		
		try{
			GenParametrosExample example = new GenParametrosExample();
			example.createCriteria().andIdrecursoEqualTo("administracion.parametro.api_alterm_url");
			
			List<GenParametros> config = _genParametrosMapper.selectByExample(example);
			
			if(config != null && config.size() > 0){
				if(config != null && config.size() > 0){
					
					String uriService = config.get(0).getValor();
					
					// Preparamos el JSON que enviaremos a la API
					JSONObject jsonObject = new JSONObject();
					
					SetSolicitudAlterDocument request = SetSolicitudAlterDocument.Factory.newInstance();
					SetSolicitudAlter requestBody = SetSolicitudAlter.Factory.newInstance();
					WSSolicitud WSsolicitud = WSSolicitud.Factory.newInstance();
					jsonObject.put("IdPaquete", solicitud.getIdPaquete());
					WSsolicitud.setIdPaquete(solicitud.getIdPaquete());
					
					JSONObject aseguradoJSON = new JSONObject();
					WSAsegurado asegurado = WSAsegurado.Factory.newInstance();
					aseguradoJSON.put("Apellidos", solicitud.getAsegurado().getApellidos());
					asegurado.setApellidos(solicitud.getAsegurado().getApellidos());
					
					CenInstitucionExample institucionExample = new CenInstitucionExample();
					institucionExample.createCriteria().andIdinstitucionEqualTo(Short.parseShort(solicitud.getAsegurado().getColegio()));
					List<CenInstitucion> inst = _cenInstitucionMapper.selectByExample(institucionExample);
					aseguradoJSON.put("Colegio", inst.get(0).getCodigoext());
					asegurado.setColegio(inst.get(0).getCodigoext());
					
					JSONObject datosBancariosJSON = new JSONObject();
					WSCuentaBancaria datosBancarios = WSCuentaBancaria.Factory.newInstance();
					datosBancariosJSON.put("IBAN", solicitud.getAsegurado().getIban());
					datosBancariosJSON.put("DC", solicitud.getAsegurado().getIban().substring(12, 14));
					datosBancariosJSON.put("Pais", solicitud.getAsegurado().getIban().substring(0, 2));
					datosBancariosJSON.put("Cuenta", solicitud.getAsegurado().getIban().substring(14, 24));
					datosBancariosJSON.put("SWIFT", solicitud.getAsegurado().getBic());
					datosBancariosJSON.put("Sucursal", solicitud.getAsegurado().getSucursal());
					datosBancarios.setIBAN(solicitud.getAsegurado().getIban());
					datosBancarios.setDC(solicitud.getAsegurado().getIban().substring(12, 14));
					datosBancarios.setPais(solicitud.getAsegurado().getIban().substring(0, 2));
					datosBancarios.setCuenta(solicitud.getAsegurado().getIban().substring(14, 24));
					datosBancarios.setSWIFT(solicitud.getAsegurado().getBic());
					datosBancarios.setSucursal(solicitud.getAsegurado().getSucursal());
					aseguradoJSON.put("CuentaBancaria", datosBancariosJSON);
					asegurado.setCuentaBancaria(datosBancarios);
					
					JSONObject direccionJSON = new JSONObject();
					WSDireccion direccion = WSDireccion.Factory.newInstance();
					direccionJSON.put("CodigoPostal", solicitud.getAsegurado().getCp());
					direccionJSON.put("DireccionPostal", solicitud.getAsegurado().getDomicilio());
					direccionJSON.put("Email", solicitud.getAsegurado().getMail());
					direccion.setCodigoPostal(solicitud.getAsegurado().getCp());
					direccion.setDireccionPostal(solicitud.getAsegurado().getDomicilio());
					direccion.setEmail(solicitud.getAsegurado().getMail());
					if(solicitud.getAsegurado().getFax()!= null){
						direccionJSON.put("Fax", solicitud.getAsegurado().getFax());
						direccion.setFax(solicitud.getAsegurado().getFax());
					}
					direccionJSON.put("Movil", solicitud.getAsegurado().getMovil());
					direccionJSON.put("Pais", solicitud.getAsegurado().getPais());
					direccion.setMovil(solicitud.getAsegurado().getFax());
					direccion.setPais(solicitud.getAsegurado().getPais());
					if(solicitud.getAsegurado().getProvincia()!= null){
						direccionJSON.put("Provincia", solicitud.getAsegurado().getProvincia());
						direccion.setProvincia(solicitud.getAsegurado().getProvincia());
					}
					if(solicitud.getAsegurado().getPoblacion()!= null){
						direccionJSON.put("Poblacion", solicitud.getAsegurado().getPoblacion());
						direccion.setPoblacion(solicitud.getAsegurado().getPoblacion());
					}
					if(solicitud.getAsegurado().getTelefono()!= null){
						direccionJSON.put("Telefono1", solicitud.getAsegurado().getTelefono());
						direccion.setTelefono1(solicitud.getAsegurado().getTelefono());
					}
					if(solicitud.getAsegurado().getTelefono2()!= null){
						direccionJSON.put("Telefono2", solicitud.getAsegurado().getTelefono2());
						direccion.setTelefono2(solicitud.getAsegurado().getTelefono2());
					}
					direccionJSON.put("TipoDireccion", Integer.parseInt(solicitud.getAsegurado().getTipoDireccion()));
					direccion.setTipoDireccion(Integer.parseInt(solicitud.getAsegurado().getTipoDireccion()));	
					aseguradoJSON.put("Direccion", direccionJSON);			
					asegurado.setDireccion(direccion);
					if(solicitud.getAsegurado().getEstadoCivil() != null) {
						aseguradoJSON.put("EstadoCivil", Integer.parseInt(solicitud.getAsegurado().getEstadoCivil()));
						asegurado.setEstadoCivil(Integer.parseInt(solicitud.getAsegurado().getEstadoCivil()));
	}
					
					
					if(solicitud.getHerederos() != null){
						if(solicitud.getHerederos().size() > 0){
							JSONArray herederosJSON = new JSONArray();
							WSPersona[] herederos = new WSPersona[solicitud.getHerederos().size()];
							int index = 0;
							for (PersonaDTO persona : solicitud.getHerederos()) {
								JSONObject herederoJSON = new JSONObject();
								WSPersona heredero = WSPersona.Factory.newInstance();
								Calendar cal = Calendar.getInstance();
								
								herederoJSON.put("Parentesco", getParentesco(persona.getParentesco()));
								herederoJSON.put("Apellidos", persona.getApellido());
								herederoJSON.put("Nombre", persona.getNombre());
								heredero.setParentesco(getParentesco(persona.getParentesco()));
								heredero.setApellidos(persona.getApellido());
								heredero.setNombre(persona.getNombre());
								if(persona.getSexo().equals("H")==true){
									herederoJSON.put("Sexo", 1);
									heredero.setSexo(1);
								}else{
									herederoJSON.put("Sexo", 2);
									heredero.setSexo(2);
								}
								herederoJSON.put("Identificador", persona.getIdentificacion());
								heredero.setIdentificador(persona.getIdentificacion());
								if(persona.getTipoIdentificacion().equals("NIF")){
									herederoJSON.put("TipoIdentificador", 0);
									heredero.setTipoIdentificador(0);
								}else{
									herederoJSON.put("TipoIdentificador", 1);
									heredero.setTipoIdentificador(1);
								}
								cal.setTime(persona.getFechaNacimiento());
								herederoJSON.put("FechaNacimiento", cal.toInstant());
								heredero.setFechaNacimiento(cal);
								herederosJSON.put(herederoJSON);
								herederos[index] = heredero;
								index++;
							}
							jsonObject.put("herederos", herederosJSON);
							WSsolicitud.setHerederosArray(herederos);
						}
					}else if(solicitud.getFamiliares() != null){
						if(solicitud.getFamiliares().size() > 0){
							JSONArray familiaresJSON = new JSONArray();
							WSPersona[] familiares = new WSPersona[solicitud.getFamiliares().size()];
							int index = 0;
							for (PersonaDTO persona : solicitud.getFamiliares()) {
								JSONObject familiarJSON = new JSONObject();
								WSPersona familiar = WSPersona.Factory.newInstance();
								Calendar cal = Calendar.getInstance();
								
								familiarJSON.put("Parentesco", getParentesco(persona.getParentesco()));
								familiarJSON.put("Apellidos", persona.getApellido());
								familiarJSON.put("Nombre", persona.getNombre());
								familiar.setParentesco(getParentesco(persona.getParentesco()));
								familiar.setApellidos(persona.getApellido());
								familiar.setNombre(persona.getNombre());
								if(persona.getSexo().equals("H")==true){
									familiarJSON.put("Sexo", 1);
									asegurado.setSexo(1);
								}else{
									familiarJSON.put("Sexo", 2);
									asegurado.setSexo(2);
								}
								familiarJSON.put("Identificador", persona.getIdentificacion());
								familiar.setIdentificador(persona.getIdentificacion());
								if(persona.getTipoIdentificacion().equals("NIF")){
									familiarJSON.put("TipoIdentificador", 0);
									familiar.setTipoIdentificador(0);
								}else{
									familiarJSON.put("TipoIdentificador", 1);
									familiar.setTipoIdentificador(1);
								}
								cal.setTime(persona.getFechaNacimiento());
								familiarJSON.put("FechaNacimiento", cal.toInstant());
								familiar.setFechaNacimiento(cal);
								familiaresJSON.put(familiarJSON);
								familiares[index] = familiar;
								index++;
							}
							aseguradoJSON.put("Familiares", familiaresJSON);
							asegurado.setFamiliaresArray(familiares);
						}
					}
					Calendar cal = Calendar.getInstance();
					cal.setTime(solicitud.getAsegurado().getFechaNacimiento());
					aseguradoJSON.put("FechaNacimiento", cal.toInstant());
					aseguradoJSON.put("Identificador", solicitud.getAsegurado().getIdentificador());
					aseguradoJSON.put("Idioma", Integer.parseInt(solicitud.getAsegurado().getIdioma()));
					aseguradoJSON.put("Nombre", solicitud.getAsegurado().getNombre());
					aseguradoJSON.put("Publicidad", solicitud.getAsegurado().isPublicidad());
					asegurado.setFechaNacimiento(cal);
					asegurado.setIdentificador(solicitud.getAsegurado().getIdentificador());
					asegurado.setIdioma(Integer.parseInt(solicitud.getAsegurado().getIdioma()));
					asegurado.setNombre(solicitud.getAsegurado().getNombre());
					asegurado.setPublicidad(solicitud.getAsegurado().isPublicidad());
					if(solicitud.getAsegurado().getSexo() != null && solicitud.getAsegurado().getSexo().equals("H")==true){
						aseguradoJSON.put("Sexo", 1);
						asegurado.setSexo(1);
					}else{
						aseguradoJSON.put("Sexo", 2);
						asegurado.setSexo(2);
					}
					aseguradoJSON.put("TipoComunicacion", Integer.parseInt(solicitud.getAsegurado().getMedioComunicacion()));
					asegurado.setTipoComunicacion(Integer.parseInt(solicitud.getAsegurado().getMedioComunicacion()));
					
					if(solicitud.getAsegurado().getTipoIdentificador().equals("NIF")==true || solicitud.getAsegurado().getTipoIdentificador().equals("NIE")==true){
						aseguradoJSON.put("TipoIdentificador", 0);
						asegurado.setTipoIdentificador(0);
					}else{
						aseguradoJSON.put("TipoIdentificador", 1);
						asegurado.setTipoIdentificador(1);
					}
					
					
					jsonObject.put("Asegurado", aseguradoJSON);
					WSsolicitud.setAsegurado(asegurado);
					requestBody.setWsSolicitud(WSsolicitud);
					request.setSetSolicitudAlter(requestBody);
					
					JsonNode responseAPI = _clientAlterMutua.APICall(jsonObject, uriService + "/AlterApp/GenerateRequestSIGA");
					
					if(responseAPI != null){
					
						responseDTO.setIdentificador(responseAPI.get("Identificador").asInt());
						
						if(responseDTO.getIdentificador() > 0){
							insertarSolicitudJSON(solicitud, responseAPI, responseAPI.get("Identificador").asInt());
						}

						responseDTO.setDocumento(responseAPI.get("Documento").binaryValue());
						responseDTO.setError(responseAPI.get("Error").asBoolean());
						responseDTO.setMensaje(responseAPI.get("Mensaje").asText());
						
						if(!responseAPI.get("Propuestas").isNull()){
							
							List<PropuestaDTO> propuestas = new ArrayList<PropuestaDTO>();
							ArrayNode propuestasJSON = (ArrayNode) responseAPI.get("Propuestas");
							
							Iterator<JsonNode> propuestasIterator = propuestasJSON.iterator();
							
							while(propuestasIterator.hasNext()) {
								JsonNode propuesta = propuestasIterator.next();
								
								PropuestaDTO propuestaItem = new PropuestaDTO();
								
								propuestaItem.setBreve(propuesta.get("Breve").asText());
								propuestaItem.setDescripcion(propuesta.get("Descripcion").asText());
								propuestaItem.setFamiliares(propuesta.get("Familiares").asBoolean());
								propuestaItem.setHerederos(propuesta.get("Herederos").asBoolean());
								propuestaItem.setIdPaquete(propuesta.get("IdPaquete").asInt());
								propuestaItem.setNombre(propuesta.get("Nombre").asText());
								propuestaItem.setTarifa(propuesta.get("Tarifa").decimalValue());
								propuestas.add(propuestaItem);
							}
							
							responseDTO.setPropuestas(propuestas);
						}
					}
					
				}
				
			}
			
		}catch(Exception e){
			LOGGER.error("setSolicitudAlter() --> error en el servicio: " + e.getMessage());
			responseDTO.setError(true);
			responseDTO.setMensaje(e.getMessage());
		}
		
		LOGGER.info("setSolicitudAlter() --> Salida del servicio para enviar datos alter");
		return responseDTO;
	}
	
	private int insertarSolicitud(SolicitudDTO solicitud, WSRespuesta responseWS){
		
		CenSolicitudalter solAlter = new CenSolicitudalter();
		MaxIdDto idMAx = new MaxIdDto();
		idMAx = _cenSolicitudAlterExtendsMapper.getMaxIdRecurso();
		
		solAlter.setIdsolicitud(idMAx.getIdMax());
		solAlter.setPropuesta("1");
		solAlter.setNombre(solicitud.getAsegurado().getNombre());
		solAlter.setApellidos(solicitud.getAsegurado().getApellidos());
		solAlter.setNumeroidentificador(solicitud.getAsegurado().getIdentificador());
		solAlter.setDomicilio(solicitud.getAsegurado().getDomicilio());
		solAlter.setCodigopostal(solicitud.getAsegurado().getCp());
		if(solicitud.getAsegurado().getTelefono()!= null){
			solAlter.setTelefono1(solicitud.getAsegurado().getTelefono());
		}
		if(solicitud.getAsegurado().getTelefono2()!= null){
			solAlter.setTelefono2(solicitud.getAsegurado().getTelefono2());
		}
		solAlter.setCorreoelectronico(solicitud.getAsegurado().getMail());
		solAlter.setIdinstitucion(Short.parseShort(solicitud.getAsegurado().getColegio()));
		solAlter.setFechamodificacion(new Date());
		solAlter.setFechanacimiento(solicitud.getAsegurado().getFechaNacimiento());
		solAlter.setIdtipoidentificacion(Short.parseShort(solicitud.getAsegurado().getTipoIdentificador()));
		solAlter.setIdprovincia(solicitud.getAsegurado().getIdProvincia());
		solAlter.setMovil(solicitud.getAsegurado().getMovil());
		if(solicitud.getAsegurado().getFax() != null){
			solAlter.setFax(solicitud.getAsegurado().getFax());
		}
		if(solicitud.getAsegurado().getEstadoCivil() != null) {
					solAlter.setIdestadocivil(Short.parseShort(solicitud.getAsegurado().getEstadoCivil()));
		}
		solAlter.setIdpais(solicitud.getAsegurado().getIdPais());
		solAlter.setIdsexo(solicitud.getAsegurado().getSexo());
		solAlter.setCodigosucursal(solicitud.getAsegurado().getIban().substring(4, 8));
		solAlter.setCboCodigo(solicitud.getAsegurado().getIban().substring(8, 12));
		solAlter.setDigitocontrol(solicitud.getAsegurado().getIban().substring(12, 14));
		solAlter.setNumerocuenta(solicitud.getAsegurado().getIban().substring(14, 24));
		solAlter.setIban(solicitud.getAsegurado().getIban());
		if(solicitud.getAsegurado().getProvincia()!= null){
			solAlter.setProvincia(solicitud.getAsegurado().getProvincia());	
		}
		if(solicitud.getAsegurado().getIdPoblacion() != null){
			solAlter.setPoblacion(solicitud.getAsegurado().getIdPoblacion());	
		}
		solAlter.setIdpaquete(solicitud.getIdPaquete()+"");
		if(solicitud.getAsegurado().getTipoEjercicio() != null) {
			solAlter.setIdtipoejercicio(solicitud.getAsegurado().getTipoEjercicio());
		}
		if(solicitud.getFamiliares()!=null){
			solAlter.setIdpersona(Long.parseLong(solicitud.getFamiliares().size()+""));
		}
		if(solicitud.getHerederos()!=null){
			solAlter.setIdpersona(Long.parseLong(solicitud.getHerederos().size()+""));
		}
		
		if(responseWS.getPropuestas() != null) {
			solAlter.setBrevepaquete(responseWS.getPropuestas().getWSPropuestaArray(0).getBreve());
			solAlter.setDescripcionpaquete(responseWS.getPropuestas().getWSPropuestaArray(0).getDescripcion());
			solAlter.setTarifapaquete(responseWS.getPropuestas().getWSPropuestaArray(0).getTarifa().toString());
			solAlter.setNombrepaquete(responseWS.getPropuestas().getWSPropuestaArray(0).getNombre());
		}
		
		return _cenSolicitudalterMapper.insert(solAlter);
	}
	
	private int insertarSolicitudJSON(SolicitudDTO solicitud, JsonNode responseAPI, int idSolicitudAlter){
		
		CenSolicitudalter solAlter = new CenSolicitudalter();
		MaxIdDto idMAx = new MaxIdDto();
		idMAx = _cenSolicitudAlterExtendsMapper.getMaxIdRecurso();
		
		solAlter.setIdsolicitud(idMAx.getIdMax());
		solAlter.setIdsolicitudalter(Long.valueOf(idSolicitudAlter));
		solAlter.setPropuesta("1");
		solAlter.setNombre(solicitud.getAsegurado().getNombre());
		solAlter.setApellidos(solicitud.getAsegurado().getApellidos());
		solAlter.setNumeroidentificador(solicitud.getAsegurado().getIdentificador());
		solAlter.setDomicilio(solicitud.getAsegurado().getDomicilio());
		solAlter.setCodigopostal(solicitud.getAsegurado().getCp());
		if(solicitud.getAsegurado().getTelefono()!= null){
			solAlter.setTelefono1(solicitud.getAsegurado().getTelefono());
		}
		if(solicitud.getAsegurado().getTelefono2()!= null){
			solAlter.setTelefono2(solicitud.getAsegurado().getTelefono2());
		}
		solAlter.setCorreoelectronico(solicitud.getAsegurado().getMail());
		solAlter.setIdinstitucion(Short.parseShort(solicitud.getAsegurado().getColegio()));
		solAlter.setFechamodificacion(new Date());
		solAlter.setFechanacimiento(solicitud.getAsegurado().getFechaNacimiento());
		solAlter.setIdtipoidentificacion(Short.parseShort(solicitud.getAsegurado().getTipoIdentificador()));
		solAlter.setIdprovincia(solicitud.getAsegurado().getIdProvincia());
		solAlter.setMovil(solicitud.getAsegurado().getMovil());
		if(solicitud.getAsegurado().getFax() != null){
			solAlter.setFax(solicitud.getAsegurado().getFax());
		}
		if(solicitud.getAsegurado().getEstadoCivil() != null) {
					solAlter.setIdestadocivil(Short.parseShort(solicitud.getAsegurado().getEstadoCivil()));
		}
		solAlter.setIdpais(solicitud.getAsegurado().getIdPais());
		solAlter.setIdsexo(solicitud.getAsegurado().getSexo());
		solAlter.setCodigosucursal(solicitud.getAsegurado().getIban().substring(4, 8));
		solAlter.setCboCodigo(solicitud.getAsegurado().getIban().substring(8, 12));
		solAlter.setDigitocontrol(solicitud.getAsegurado().getIban().substring(12, 14));
		solAlter.setNumerocuenta(solicitud.getAsegurado().getIban().substring(14, 24));
		solAlter.setIban(solicitud.getAsegurado().getIban());
		if(solicitud.getAsegurado().getProvincia()!= null){
			solAlter.setProvincia(solicitud.getAsegurado().getProvincia());	
		}
		if(solicitud.getAsegurado().getIdPoblacion() != null){
			solAlter.setPoblacion(solicitud.getAsegurado().getIdPoblacion());	
		}
		solAlter.setIdpaquete(solicitud.getIdPaquete()+"");
		if(solicitud.getAsegurado().getTipoEjercicio() != null) {
			solAlter.setIdtipoejercicio(solicitud.getAsegurado().getTipoEjercicio());
		}
		if(solicitud.getFamiliares()!=null){
			solAlter.setIdpersona(Long.parseLong(solicitud.getFamiliares().size()+""));
		}
		if(solicitud.getHerederos()!=null){
			solAlter.setIdpersona(Long.parseLong(solicitud.getHerederos().size()+""));
		}
		
		if(!responseAPI.get("Propuestas").isNull()){
			
			ArrayNode propuestasJSON = (ArrayNode) responseAPI.get("Propuestas");
			
			Iterator<JsonNode> propuestasIterator = propuestasJSON.iterator();
			JsonNode propuesta = propuestasIterator.next();
			
			solAlter.setBrevepaquete(propuesta.get("Breve").asText());
			solAlter.setDescripcionpaquete(propuesta.get("Descripcion").asText());
			solAlter.setTarifapaquete(propuesta.get("Tarifa").decimalValue().toString());
			solAlter.setNombrepaquete(propuesta.get("Nombre").asText());
		}
		
		return _cenSolicitudalterMapper.insert(solAlter);
	}
	
	int getParentesco (String parentesco){
		int id =0;
		
		switch(parentesco){
		case "Hij@":
			id= 1;
		case "Suegr@":
			id = 14;
		case "Otra Relacion":
			id =  16;
		case "Pareja":
			id =  17;
		case "No Familiar":
			id =  18;
		case "Conyuje":
			id =  3;
		case "Padre":
			id =  4;
		}
		return id;
	}

}
