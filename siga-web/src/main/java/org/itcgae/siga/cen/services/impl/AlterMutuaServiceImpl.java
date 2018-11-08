package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.itcgae.siga.db.entities.CenSolicitudalter;
import org.itcgae.siga.db.entities.CenSolicitudalterExample;
import org.itcgae.siga.db.entities.CenSolicitudincorporacion;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.mappers.CenSolicitudalterMapper;
import org.itcgae.siga.db.mappers.CenSolicitudincorporacionMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.services.cen.mappers.CenSolicitudAlterExtendsMapper;
import org.itcgae.siga.ws.client.ClientAlterMutua;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altermutua.www.wssiga.GetEstadoColegiadoDocument.GetEstadoColegiado;
import com.altermutua.www.wssiga.GetEstadoColegiadoDocument;
import com.altermutua.www.wssiga.GetEstadoSolicitudDocument;
import com.altermutua.www.wssiga.GetEstadoSolicitudDocument.GetEstadoSolicitud;
import com.altermutua.www.wssiga.GetPropuestasDocument;
import com.altermutua.www.wssiga.GetPropuestasDocument.GetPropuestas;
import com.altermutua.www.wssiga.GetTarifaSolicitudDocument;
import com.altermutua.www.wssiga.GetTarifaSolicitudDocument.GetTarifaSolicitud;
import com.altermutua.www.wssiga.SetSolicitudAlterDocument;
import com.altermutua.www.wssiga.WSAsegurado;
import com.altermutua.www.wssiga.WSCuentaBancaria;
import com.altermutua.www.wssiga.WSDireccion;
import com.altermutua.www.wssiga.WSPersona;
import com.altermutua.www.wssiga.SetSolicitudAlterDocument.SetSolicitudAlter;


import com.altermutua.www.wssiga.WSPropuesta;
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
	private CenSolicitudalterMapper _cenSolicitudalterMapper;
	
	@Autowired
	private CenSolicitudincorporacionMapper _cenSolicitudincorporacionMapper;
	

	@Override
	public AlterMutuaResponseDTO getEstadoSolicitud(EstadoSolicitudDTO estadosolicitudDTO) {
		
		LOGGER.info("getEstadoSolicitud() --> Entrada al servicio para obtener el estado de la solicitud");
		AlterMutuaResponseDTO responseDTO = new AlterMutuaResponseDTO();
		try{
			
			GenParametrosExample example = new GenParametrosExample();
			example.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_url");
			
			List<GenParametros> config = _genParametrosMapper.selectByExample(example);
			
			if(config != null && config.size() > 0){
				
				String uriService = config.get(0).getValor();
				
				GetEstadoSolicitudDocument request = GetEstadoSolicitudDocument.Factory.newInstance();
				GetEstadoSolicitud requestBody = GetEstadoSolicitud.Factory.newInstance();
				CenSolicitudincorporacion solIncorporacion = _cenSolicitudincorporacionMapper.selectByPrimaryKey(estadosolicitudDTO.getIdSolicitud());

				CenSolicitudalterExample solAlterExample = new CenSolicitudalterExample();
				solAlterExample.createCriteria().andNombreEqualTo(solIncorporacion.getNombre()).andDomicilioEqualTo(solIncorporacion.getDomicilio())
					.andCodigopostalEqualTo(solIncorporacion.getCodigopostal()).andMovilEqualTo(solIncorporacion.getMovil());
				
				List<CenSolicitudalter> solAlter = _cenSolicitudalterMapper.selectByExample(solAlterExample);
				if(solAlter.size() > 0){
					CenSolicitudalter solicitud = solAlter.get(0);
					requestBody.setIntIdSolicitud(Math.toIntExact(solicitud.getIdsolicitudalter()));
					requestBody.setBolDuplicado(estadosolicitudDTO.isDuplicado());
					request.setGetEstadoSolicitud(requestBody);
					
					WSRespuesta WSresponse = _clientAlterMutua.getEstadoSolicitud(request, uriService);
					
					if(WSresponse != null){
						responseDTO.setDocumento(WSresponse.getDocumento());
						responseDTO.setError(WSresponse.getError());
						responseDTO.setMensaje(WSresponse.getMensaje());
					}
				}else{
					responseDTO.setError(true);
					responseDTO.setMensaje("No existe solicitud de Alter mutua");
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
			example.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_url");
			
			List<GenParametros> config = _genParametrosMapper.selectByExample(example);
			
			if(config != null && config.size() > 0){
				
				String uriService = config.get(0).getValor();
				
				GetEstadoColegiadoDocument request = GetEstadoColegiadoDocument.Factory.newInstance();
				GetEstadoColegiado requestBody = GetEstadoColegiado.Factory.newInstance();
				
				requestBody.setIntTipoIdentificador(estadoColegiadoDTO.getTipoIdentificador());
				requestBody.setStrIdentificador(estadoColegiadoDTO.getIdentificador());
				request.setGetEstadoColegiado(requestBody);
				
				WSRespuesta responseWS = _clientAlterMutua.getEstadoColegiado(request, uriService);
				
				if(responseWS != null){
					responseDTO.setError(responseWS.getError());
					responseDTO.setMensaje(responseWS.getMensaje());
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
			example.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_url");
			
			List<GenParametros> config = _genParametrosMapper.selectByExample(example);
			
			if(config != null && config.size() > 0){
				
				String uriService = config.get(0).getValor();
				GetPropuestasDocument request = GetPropuestasDocument.Factory.newInstance();
				GetPropuestas requestBody = GetPropuestas.Factory.newInstance();
				
				if(PropuestasDTO.getTipoIdentificador().equals("NIF")){
					requestBody.setIntTipoIdentificador(1);
				}else if(PropuestasDTO.getTipoIdentificador().equals("Pasaporte")){
					requestBody.setIntTipoIdentificador(0);
				}
				requestBody.setStrIdentificador(PropuestasDTO.getIdentificador());
				Calendar cal = Calendar.getInstance();
				cal.setTime(PropuestasDTO.getFechaNacimiento());
				requestBody.setDtFechaNacimiento(cal);
				if(PropuestasDTO.getSexo().equals("H")){
					requestBody.setIntSexo(1);
				}else if (PropuestasDTO.getSexo().equals("M")){
					requestBody.setIntSexo(2);
				}
				
				requestBody.setIntTipoPropuesta(PropuestasDTO.getTipoPropuesta());
				request.setGetPropuestas(requestBody);
				
				responseWS = _clientAlterMutua.getPropuestas(request, uriService);
				
				if(responseWS != null){
					
					response.setIdentificador(responseWS.getIdentificador());
					response.setError(responseWS.getError());
					response.setMensaje(responseWS.getMensaje());
					
					if(responseWS.getPropuestas() != null){
						List<PropuestaDTO> propuestas = new ArrayList<PropuestaDTO>();
						for(int i = 0; i < responseWS.getPropuestas().sizeOfWSPropuestaArray();i++){
							
							PropuestaDTO propuestaItem = new PropuestaDTO();
							WSPropuesta propuestaWS = responseWS.getPropuestas().getWSPropuestaArray(i);
							
							
							propuestaItem.setBreve(propuestaWS.getBreve());
							propuestaItem.setDescripcion(propuestaWS.getDescripcion());
							propuestaItem.setFamiliares(propuestaWS.getFamiliares());
							propuestaItem.setHerederos(propuestaWS.getHerederos());
							propuestaItem.setIdPaquete(propuestaWS.getIdPaquete());
							propuestaItem.setNombre(propuestaWS.getNombre());
							propuestaItem.setTarifa(propuestaWS.getTarifa());
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
			example.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_url");
			
			List<GenParametros> config = _genParametrosMapper.selectByExample(example);
			
			if(config != null && config.size() > 0){
				if(config != null && config.size() > 0){
					
					String uriService = config.get(0).getValor();
					
					GetTarifaSolicitudDocument request = GetTarifaSolicitudDocument.Factory.newInstance();
					GetTarifaSolicitud requestBody = GetTarifaSolicitud.Factory.newInstance();
					WSSolicitud WSsolicitud = WSSolicitud.Factory.newInstance();
					WSsolicitud.setIdPaquete(solicitud.getIdPaquete());
					
					WSAsegurado asegurado = WSAsegurado.Factory.newInstance();
					asegurado.setApellidos(solicitud.getAsegurado().getApellidos());
					asegurado.setColegio(solicitud.getAsegurado().getColegio());
					WSCuentaBancaria datosBancarios = WSCuentaBancaria.Factory.newInstance();
					datosBancarios.setIBAN(solicitud.getAsegurado().getIban());
					datosBancarios.setDC(solicitud.getAsegurado().getIban().substring(12, 14));
					datosBancarios.setPais(solicitud.getAsegurado().getIban().substring(0, 2));
					datosBancarios.setCuenta(solicitud.getAsegurado().getIban().substring(14, 24));
					asegurado.setCuentaBancaria(datosBancarios);
					
					WSDireccion direccion = WSDireccion.Factory.newInstance();
					direccion.setCodigoPostal(solicitud.getAsegurado().getCp());
					direccion.setDireccionPostal(solicitud.getAsegurado().getDomicilio());
					direccion.setEmail(solicitud.getAsegurado().getMail());
					if(solicitud.getAsegurado().getFax()!= null){
						direccion.setFax(solicitud.getAsegurado().getFax());
					}
					direccion.setMovil(solicitud.getAsegurado().getFax());
					direccion.setPais(solicitud.getAsegurado().getPais());
					if(solicitud.getAsegurado().getProvincia()!= null){
						direccion.setProvincia(solicitud.getAsegurado().getProvincia());
					}
					if(solicitud.getAsegurado().getPoblacion()!= null){
						direccion.setPoblacion(solicitud.getAsegurado().getPoblacion());
					}
					if(solicitud.getAsegurado().getTelefono()!= null){
						direccion.setTelefono1(solicitud.getAsegurado().getTelefono());
					}
					if(solicitud.getAsegurado().getTelefono2()!= null){
						direccion.setTelefono2(solicitud.getAsegurado().getTelefono2());
					}
					direccion.setTipoDireccion(Integer.parseInt(solicitud.getAsegurado().getTipoDireccion()));				
					asegurado.setDireccion(direccion);
					asegurado.setEstadoCivil(Integer.parseInt(solicitud.getAsegurado().getEstadoCivil()));
					
					
					if(solicitud.getHerederos() != null){
						if(solicitud.getHerederos().size() > 0){
							WSPersona[] herederos = new WSPersona[solicitud.getHerederos().size()];
							int index = 0;
							for (PersonaDTO persona : solicitud.getHerederos()) {
								WSPersona heredero = WSPersona.Factory.newInstance();
								Calendar cal = Calendar.getInstance();
								
								heredero.setParentesco(getParentesco(persona.getParentesco()));
								heredero.setApellidos(persona.getApellido());
								heredero.setNombre(persona.getNombre());
								if(persona.getSexo().equals("H")==true){
									heredero.setSexo(1);
								}else{
									heredero.setSexo(2);
								}
								heredero.setIdentificador(persona.getIdentificacion());
								if(persona.getTipoIdentificacion().equals("NIF")){
									heredero.setTipoIdentificador(0);
								}else{
									heredero.setTipoIdentificador(1);
								}
								cal.setTime(persona.getFechaNacimiento());
								heredero.setFechaNacimiento(cal);
								herederos[index] = heredero;
								index++;
							}
							WSsolicitud.setHerederosArray(herederos);
						}
					}else{
						if(solicitud.getFamiliares().size() > 0){
							WSPersona[] familiares = new WSPersona[solicitud.getFamiliares().size()];
							int index = 0;
							for (PersonaDTO persona : solicitud.getFamiliares()) {
								WSPersona familiar = WSPersona.Factory.newInstance();
								Calendar cal = Calendar.getInstance();
								
								familiar.setParentesco(getParentesco(persona.getParentesco()));
								familiar.setApellidos(persona.getApellido());
								familiar.setNombre(persona.getNombre());
								if(persona.getSexo().equals("H")==true){
									asegurado.setSexo(1);
								}else{
									asegurado.setSexo(2);
								}
								familiar.setIdentificador(persona.getIdentificacion());
								if(persona.getTipoIdentificacion().equals("NIF")){
									familiar.setTipoIdentificador(0);
								}else{
									familiar.setTipoIdentificador(1);
								}
								cal.setTime(persona.getFechaNacimiento());
								familiar.setFechaNacimiento(cal);
								familiares[index] = familiar;
								index++;
							}
							asegurado.setFamiliaresArray(familiares);
						}
					}
					Calendar cal = Calendar.getInstance();
					cal.setTime(solicitud.getAsegurado().getFechaNacimiento());
					asegurado.setFechaNacimiento(cal);
					asegurado.setIdentificador(solicitud.getAsegurado().getIdentificador());
					asegurado.setIdioma(Integer.parseInt(solicitud.getAsegurado().getIdioma()));
					asegurado.setNombre(solicitud.getAsegurado().getNombre());
					asegurado.setPublicidad(solicitud.getAsegurado().isPublicidad());
					if(solicitud.getAsegurado().getSexo().equals("H")==true){
						asegurado.setSexo(1);
					}else{
						asegurado.setSexo(2);
					}
					asegurado.setTipoComunicacion(Integer.parseInt(solicitud.getAsegurado().getMedioComunicacion()));
					asegurado.setTipoEjercicio(Integer.parseInt(solicitud.getAsegurado().getTipoEjercicio()));
					
					if(solicitud.getAsegurado().getTipoIdentificador().equals("NIF")==true || solicitud.getAsegurado().getTipoIdentificador().equals("NIE")==true){
						asegurado.setTipoIdentificador(0);
					}else{
						asegurado.setTipoIdentificador(1);
					}
					
					
					WSsolicitud.setAsegurado(asegurado);
					requestBody.setWsSolicitud(WSsolicitud);
					request.setGetTarifaSolicitud(requestBody);
					
					WSRespuesta responseWS = _clientAlterMutua.getTarifaSolicitud(request, uriService);
					
					if(responseWS != null){
						responseDTO.setIdentificador(responseWS.getIdentificador());
						responseDTO.setDocumento(responseWS.getDocumento());
						responseDTO.setError(responseWS.getError());
						responseDTO.setMensaje(responseWS.getMensaje());
						
						insertarSolicitud(solicitud, responseWS);
						
						if(responseWS.getPropuestas() != null){
							List<PropuestaDTO> propuestas = new ArrayList<PropuestaDTO>();
							for(int i = 0; i < responseWS.getPropuestas().sizeOfWSPropuestaArray();i++){
								
								PropuestaDTO propuestaItem = new PropuestaDTO();
								WSPropuesta propuestaWS = responseWS.getPropuestas().getWSPropuestaArray(i);
								
								
								propuestaItem.setBreve(propuestaWS.getBreve());
								propuestaItem.setDescripcion(propuestaWS.getDescripcion());
								propuestaItem.setFamiliares(propuestaWS.getFamiliares());
								propuestaItem.setHerederos(propuestaWS.getHerederos());
								propuestaItem.setIdPaquete(propuestaWS.getIdPaquete());
								propuestaItem.setNombre(propuestaWS.getNombre());
								propuestaItem.setTarifa(propuestaWS.getTarifa());
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
			example.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_url");
			
			List<GenParametros> config = _genParametrosMapper.selectByExample(example);
			
			if(config != null && config.size() > 0){
				if(config != null && config.size() > 0){
					
					String uriService = config.get(0).getValor();
					
					SetSolicitudAlterDocument request = SetSolicitudAlterDocument.Factory.newInstance();
					SetSolicitudAlter requestBody = SetSolicitudAlter.Factory.newInstance();
					WSSolicitud WSsolicitud = WSSolicitud.Factory.newInstance();
					WSsolicitud.setIdPaquete(solicitud.getIdPaquete());
					
					WSAsegurado asegurado = WSAsegurado.Factory.newInstance();
					asegurado.setApellidos(solicitud.getAsegurado().getApellidos());
					asegurado.setColegio(solicitud.getAsegurado().getColegio());
					WSCuentaBancaria datosBancarios = WSCuentaBancaria.Factory.newInstance();
					datosBancarios.setIBAN(solicitud.getAsegurado().getIban());
					datosBancarios.setDC(solicitud.getAsegurado().getIban().substring(12, 14));
					datosBancarios.setPais(solicitud.getAsegurado().getIban().substring(0, 2));
					datosBancarios.setCuenta(solicitud.getAsegurado().getIban().substring(14, 24));
					asegurado.setCuentaBancaria(datosBancarios);
					
					WSDireccion direccion = WSDireccion.Factory.newInstance();
					direccion.setCodigoPostal(solicitud.getAsegurado().getCp());
					direccion.setDireccionPostal(solicitud.getAsegurado().getDomicilio());
					direccion.setEmail(solicitud.getAsegurado().getMail());
					if(solicitud.getAsegurado().getFax()!= null){
						direccion.setFax(solicitud.getAsegurado().getFax());
					}
					direccion.setMovil(solicitud.getAsegurado().getFax());
					direccion.setPais(solicitud.getAsegurado().getPais());
					if(solicitud.getAsegurado().getProvincia()!= null){
						direccion.setProvincia(solicitud.getAsegurado().getProvincia());
					}
					if(solicitud.getAsegurado().getPoblacion()!= null){
						direccion.setPoblacion(solicitud.getAsegurado().getPoblacion());
					}
					if(solicitud.getAsegurado().getTelefono()!= null){
						direccion.setTelefono1(solicitud.getAsegurado().getTelefono());
					}
					if(solicitud.getAsegurado().getTelefono2()!= null){
						direccion.setTelefono2(solicitud.getAsegurado().getTelefono2());
					}
					direccion.setTipoDireccion(Integer.parseInt(solicitud.getAsegurado().getTipoDireccion()));				
					asegurado.setDireccion(direccion);
					asegurado.setEstadoCivil(Integer.parseInt(solicitud.getAsegurado().getEstadoCivil()));
					
					
					if(solicitud.getHerederos() != null){
						if(solicitud.getHerederos().size() > 0){
							WSPersona[] herederos = new WSPersona[solicitud.getHerederos().size()];
							int index = 0;
							for (PersonaDTO persona : solicitud.getHerederos()) {
								WSPersona heredero = WSPersona.Factory.newInstance();
								Calendar cal = Calendar.getInstance();
								
								heredero.setParentesco(getParentesco(persona.getParentesco()));
								heredero.setApellidos(persona.getApellido());
								heredero.setNombre(persona.getNombre());
								if(persona.getSexo().equals("H")==true){
									heredero.setSexo(1);
								}else{
									heredero.setSexo(2);
								}
								heredero.setIdentificador(persona.getIdentificacion());
								if(persona.getTipoIdentificacion().equals("NIF")){
									heredero.setTipoIdentificador(0);
								}else{
									heredero.setTipoIdentificador(1);
								}
								cal.setTime(persona.getFechaNacimiento());
								heredero.setFechaNacimiento(cal);
								herederos[index] = heredero;
								index++;
							}
							WSsolicitud.setHerederosArray(herederos);
						}
					}else{
						if(solicitud.getFamiliares().size() > 0){
							WSPersona[] familiares = new WSPersona[solicitud.getFamiliares().size()];
							int index = 0;
							for (PersonaDTO persona : solicitud.getFamiliares()) {
								WSPersona familiar = WSPersona.Factory.newInstance();
								Calendar cal = Calendar.getInstance();
								
								familiar.setParentesco(getParentesco(persona.getParentesco()));
								familiar.setApellidos(persona.getApellido());
								familiar.setNombre(persona.getNombre());
								if(persona.getSexo().equals("H")==true){
									asegurado.setSexo(1);
								}else{
									asegurado.setSexo(2);
								}
								familiar.setIdentificador(persona.getIdentificacion());
								if(persona.getTipoIdentificacion().equals("NIF")){
									familiar.setTipoIdentificador(0);
								}else{
									familiar.setTipoIdentificador(1);
								}
								cal.setTime(persona.getFechaNacimiento());
								familiar.setFechaNacimiento(cal);
								familiares[index] = familiar;
								index++;
							}
							asegurado.setFamiliaresArray(familiares);
						}
					}
					Calendar cal = Calendar.getInstance();
					cal.setTime(solicitud.getAsegurado().getFechaNacimiento());
					asegurado.setFechaNacimiento(cal);
					asegurado.setIdentificador(solicitud.getAsegurado().getIdentificador());
					asegurado.setIdioma(Integer.parseInt(solicitud.getAsegurado().getIdioma()));
					asegurado.setNombre(solicitud.getAsegurado().getNombre());
					asegurado.setPublicidad(solicitud.getAsegurado().isPublicidad());
					if(solicitud.getAsegurado().getSexo().equals("H")==true){
						asegurado.setSexo(1);
					}else{
						asegurado.setSexo(2);
					}
					asegurado.setTipoComunicacion(Integer.parseInt(solicitud.getAsegurado().getMedioComunicacion()));
					asegurado.setTipoEjercicio(Integer.parseInt(solicitud.getAsegurado().getTipoEjercicio()));
					
					if(solicitud.getAsegurado().getTipoIdentificador().equals("NIF")==true || solicitud.getAsegurado().getTipoIdentificador().equals("NIE")==true){
						asegurado.setTipoIdentificador(0);
					}else{
						asegurado.setTipoIdentificador(1);
					}
					
					
					WSsolicitud.setAsegurado(asegurado);
					requestBody.setWsSolicitud(WSsolicitud);
					request.setSetSolicitudAlter(requestBody);
					
					WSRespuesta responseWS = _clientAlterMutua.setSolicitudAlter(request, uriService);
					
					if(responseWS != null){
						responseDTO.setIdentificador(responseWS.getIdentificador());
						if(responseDTO.getIdentificador() > 0){
							
							insertarSolicitud(solicitud, responseWS);
							
						}
						responseDTO.setDocumento(responseWS.getDocumento());
						responseDTO.setError(responseWS.getError());
						responseDTO.setMensaje(responseWS.getMensaje());
						
						if(responseWS.getPropuestas() != null){
							List<PropuestaDTO> propuestas = new ArrayList<PropuestaDTO>();
							for(int i = 0; i < responseWS.getPropuestas().sizeOfWSPropuestaArray();i++){
								
								PropuestaDTO propuestaItem = new PropuestaDTO();
								WSPropuesta propuestaWS = responseWS.getPropuestas().getWSPropuestaArray(i);
								
								
								propuestaItem.setBreve(propuestaWS.getBreve());
								propuestaItem.setDescripcion(propuestaWS.getDescripcion());
								propuestaItem.setFamiliares(propuestaWS.getFamiliares());
								propuestaItem.setHerederos(propuestaWS.getHerederos());
								propuestaItem.setIdPaquete(propuestaWS.getIdPaquete());
								propuestaItem.setNombre(propuestaWS.getNombre());
								propuestaItem.setTarifa(propuestaWS.getTarifa());
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
		solAlter.setIdprovincia(solicitud.getAsegurado().getProvincia());
		solAlter.setMovil(solicitud.getAsegurado().getMovil());
		if(solicitud.getAsegurado().getFax() != null){
			solAlter.setFax(solicitud.getAsegurado().getFax());
		}
		solAlter.setIdestadocivil(Short.parseShort(solicitud.getAsegurado().getEstadoCivil()));
		solAlter.setIdpais(solicitud.getAsegurado().getPais());
		solAlter.setIdsexo(solicitud.getAsegurado().getSexo());
		solAlter.setCodigosucursal(solicitud.getAsegurado().getIban().substring(4, 8));
		solAlter.setCboCodigo(solicitud.getAsegurado().getIban().substring(8, 12));
		solAlter.setDigitocontrol(solicitud.getAsegurado().getIban().substring(12, 14));
		solAlter.setNumerocuenta(solicitud.getAsegurado().getIban().substring(14, 24));
		solAlter.setIban(solicitud.getAsegurado().getIban());
		if(solicitud.getAsegurado().getProvincia()!= null){
			solAlter.setProvincia(solicitud.getAsegurado().getProvincia());	
		}
		if(solicitud.getAsegurado().getPoblacion() != null){
			solAlter.setPoblacion(solicitud.getAsegurado().getPoblacion());	
		}
		solAlter.setIdpaquete(solicitud.getIdPaquete()+"");
		solAlter.setIdtipoejercicio(solicitud.getAsegurado().getTipoEjercicio());
		if(solicitud.getFamiliares()!=null){
			solAlter.setIdpersona(Long.parseLong(solicitud.getFamiliares().size()+""));
		}
		if(solicitud.getHerederos()!=null){
			solAlter.setIdpersona(Long.parseLong(solicitud.getHerederos().size()+""));
		}
		
		solAlter.setBrevepaquete(responseWS.getPropuestas().getWSPropuestaArray(0).getBreve());
		solAlter.setDescripcionpaquete(responseWS.getPropuestas().getWSPropuestaArray(0).getDescripcion());
		solAlter.setTarifapaquete(responseWS.getPropuestas().getWSPropuestaArray(0).getTarifa().toString());
		solAlter.setNombrepaquete(responseWS.getPropuestas().getWSPropuestaArray(0).getNombre());
		
		
		
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
