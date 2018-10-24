package org.itcgae.siga.cen.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.itcgae.siga.DTOs.cen.AlterMutuaResponseDTO;
import org.itcgae.siga.DTOs.cen.EstadoColegiadoDTO;
import org.itcgae.siga.DTOs.cen.EstadoSolicitudDTO;
import org.itcgae.siga.DTOs.cen.PropuestaDTO;
import org.itcgae.siga.DTOs.cen.PropuestasDTO;
import org.itcgae.siga.DTOs.cen.SolicitudDTO;
import org.itcgae.siga.cen.services.IAlterMutuaService;
import org.itcgae.siga.db.entities.AdmConfig;
import org.itcgae.siga.db.entities.AdmConfigExample;
import org.itcgae.siga.db.mappers.AdmConfigMapper;
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
	AdmConfigMapper _admConfigMapper;
	

	@Override
	public AlterMutuaResponseDTO getEstadoSolicitud(EstadoSolicitudDTO estadosolicitudDTO) {
		
		LOGGER.info("getEstadoSolicitud() --> Entrada al servicio para obtener el estado de la solicitud");
		AlterMutuaResponseDTO responseDTO = new AlterMutuaResponseDTO();
		try{
			
			AdmConfigExample example = new AdmConfigExample();
			example.createCriteria().andClaveEqualTo("url.censo.alterMutua.estadoSolicitud");
			List<AdmConfig> config = _admConfigMapper.selectByExample(example);
			
			if(config != null && config.size() > 0){
				
				String uriService = config.get(0).getValor();
				
				GetEstadoSolicitudDocument request = GetEstadoSolicitudDocument.Factory.newInstance();
				GetEstadoSolicitud requestBody = GetEstadoSolicitud.Factory.newInstance();
				
				
				requestBody.setIntIdSolicitud(estadosolicitudDTO.getIdSolicitud());
				requestBody.setBolDuplicado(estadosolicitudDTO.isDuplicado());
				request.setGetEstadoSolicitud(requestBody);
				
				WSRespuesta WSresponse = _clientAlterMutua.getEstadoSolicitud(request, uriService);
				
				if(WSresponse != null){
					responseDTO.setDocumento(WSresponse.getDocumento());
					responseDTO.setError(WSresponse.getError());
					responseDTO.setMensaje(WSresponse.getMensaje());
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
			AdmConfigExample example = new AdmConfigExample();
			example.createCriteria().andClaveEqualTo("url.censo.alterMutua.estadoColegiado");
			List<AdmConfig> config = _admConfigMapper.selectByExample(example);
			
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
			AdmConfigExample example = new AdmConfigExample();
			example.createCriteria().andClaveEqualTo("url.censo.alterMutua.propuestas");
			List<AdmConfig> config = _admConfigMapper.selectByExample(example);
			
			if(config != null && config.size() > 0){
				
				String uriService = config.get(0).getValor();
				GetPropuestasDocument request = GetPropuestasDocument.Factory.newInstance();
				GetPropuestas requestBody = GetPropuestas.Factory.newInstance();
				
				requestBody.setIntTipoIdentificador(PropuestasDTO.getTipoIdentificador());
				requestBody.setStrIdentificador(PropuestasDTO.getIdentificador());
				requestBody.setDtFechaNacimiento(PropuestasDTO.getFechaNacimiento());
				requestBody.setIntSexo(PropuestasDTO.getSexo());
				requestBody.setIntTipoPropuesta(PropuestasDTO.getTipoPropuesta());
				request.setGetPropuestas(requestBody);
				
				responseWS = _clientAlterMutua.getPropuestas(request, uriService);
				
				if(responseWS != null){

					response.setIdentificador(responseWS.getIdentificador());
					response.setError(responseWS.getError());
					response.setMensaje(responseWS.getMensaje());
					
					int index = 0;
					List<PropuestaDTO> propuestas = new ArrayList<PropuestaDTO>();
					while(responseWS.getPropuestas().getWSPropuestaArray(index)!= null){
						
						PropuestaDTO propuestaItem = new PropuestaDTO();
						WSPropuesta propuestaWS = responseWS.getPropuestas().getWSPropuestaArray(index);
						
						
						propuestaItem.setBreve(propuestaWS.getBreve());
						propuestaItem.setDescripcion(propuestaWS.getDescripcion());
						propuestaItem.setFamiliares(propuestaWS.getFamiliares());
						propuestaItem.setHerederos(propuestaWS.getHerederos());
						propuestaItem.setIdPaquete(propuestaWS.getIdPaquete());
						propuestaItem.setNombre(propuestaWS.getNombre());
						propuestaItem.setTarifa(propuestaWS.getTarifa());
						propuestas.add(propuestaItem);
						
						index++;
					}
					response.setPropuestas(propuestas);
					
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
			AdmConfigExample example = new AdmConfigExample();
			example.createCriteria().andClaveEqualTo("url.censo.alterMutua.tarifaSolicitud");
			List<AdmConfig> config = _admConfigMapper.selectByExample(example);
			
			if(config != null && config.size() > 0){
				
				String uriService = config.get(0).getValor();
				
				GetTarifaSolicitudDocument request = GetTarifaSolicitudDocument.Factory.newInstance();
				GetTarifaSolicitud requestBody = GetTarifaSolicitud.Factory.newInstance();
				WSSolicitud WSsolicitud = WSSolicitud.Factory.newInstance();
				WSsolicitud.setIdPaquete(solicitud.getIdPaquete());
				requestBody.setWsSolicitud(WSsolicitud);
				request.setGetTarifaSolicitud(requestBody);
				
				WSRespuesta response = _clientAlterMutua.getTarifaSolicitud(request, uriService);
				
				if(response != null){
					
					responseDTO.setMensaje(response.getMensaje());
					responseDTO.setError(response.getError());
					
					WSPropuesta propuestaWS = response.getPropuestas().getWSPropuestaArray(0);
					List<PropuestaDTO> propuestasDTO = new ArrayList<PropuestaDTO>();
					
					PropuestaDTO propuestaDTO = new PropuestaDTO();
					propuestaDTO.setBreve(propuestaWS.getBreve());
					propuestaDTO.setDescripcion(propuestaWS.getDescripcion());
					propuestaDTO.setFamiliares(propuestaWS.getFamiliares());
					propuestaDTO.setHerederos(propuestaWS.getHerederos());
					propuestaDTO.setIdPaquete(propuestaWS.getIdPaquete());
					propuestaDTO.setNombre(propuestaWS.getNombre());
					propuestaDTO.setTarifa(propuestaWS.getTarifa());
					propuestasDTO.add(propuestaDTO);
					
					responseDTO.setPropuestas(propuestasDTO);
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

		LOGGER.info("setSolicitudAlter() --> Entrada al servicio para solicitar");
		
		AlterMutuaResponseDTO response = new AlterMutuaResponseDTO();
		try{
			AdmConfigExample example = new AdmConfigExample();
			example.createCriteria().andClaveEqualTo("url.censo.alterMutua.solicitudAlter");
			List<AdmConfig> config = _admConfigMapper.selectByExample(example);
			
			if(config != null && config.size() > 0){
				
				String uriService = config.get(0).getValor();
				
				SetSolicitudAlterDocument request = SetSolicitudAlterDocument.Factory.newInstance();
				SetSolicitudAlter requestBody = SetSolicitudAlter.Factory.newInstance();
				WSSolicitud WSsolicitud = WSSolicitud.Factory.newInstance();
				WSsolicitud.setIdPaquete(solicitud.getIdPaquete());
				requestBody.setWsSolicitud(WSsolicitud);
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
				
				//TODO: familiares
				//asegurado.setFamiliaresArray(arg0);
				//TODO: beneficiarios
				Calendar cal = Calendar.getInstance();
				  cal.setTime(solicitud.getAsegurado().getFechaNacimiento());
				asegurado.setFechaNacimiento(cal);
				asegurado.setIdentificador(solicitud.getAsegurado().getIdentificador());
				asegurado.setIdioma(Integer.parseInt(solicitud.getAsegurado().getIdioma()));
				asegurado.setNombre(solicitud.getAsegurado().getNombre());
				asegurado.setPublicidad(solicitud.getAsegurado().isPublicidad());
				asegurado.setSexo(Integer.parseInt(solicitud.getAsegurado().getSexo()));
				asegurado.setTipoComunicacion(Integer.parseInt(solicitud.getAsegurado().getMedioComunicacion()));
				asegurado.setTipoEjercicio(Integer.parseInt(solicitud.getAsegurado().getSitEjercicio()));
				asegurado.setTipoIdentificador(Integer.parseInt(solicitud.getAsegurado().getTipoIdentificador()));
				
				
				WSsolicitud.setAsegurado(asegurado);
				request.setSetSolicitudAlter(requestBody);
				
				WSRespuesta responseWS = _clientAlterMutua.setSolicitudAlter(request, uriService);
				
				if(responseWS != null){
					response.setIdentificador(responseWS.getIdentificador());
					response.setDocumento(responseWS.getDocumento());
					response.setError(responseWS.getError());
					response.setMensaje(responseWS.getMensaje());
				}
				
				
				
			}
		}catch(Exception e){
			LOGGER.error("setSolicitudAlter() --> error en el servicio: " + e.getMessage());
			response.setError(true);
			response.setMensaje(e.getMessage());
		}
		
		LOGGER.info("setSolicitudAlter() --> Salida del servicio para para solicitar");
		return response;
	}

}
