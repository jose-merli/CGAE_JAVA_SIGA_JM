package org.itcgae.siga.ws.client;


import java.util.List;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.log4j.Logger;
import org.itcgae.siga.cen.services.impl.AlterMutuaServiceImpl;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import com.altermutua.www.wssiga.GetEstadoColegiadoDocument;
import com.altermutua.www.wssiga.GetEstadoSolicitudDocument;
import com.altermutua.www.wssiga.GetPropuestasDocument;
import com.altermutua.www.wssiga.GetTarifaSolicitudDocument;
import com.altermutua.www.wssiga.SetSolicitudAlterDocument;
import com.altermutua.www.wssiga.WSRespuesta;


@Component
public class ClientAlterMutua {
	
	private Logger LOGGER = Logger.getLogger(ClientAlterMutua.class);
	
	/**
	 * Bean con la configuración básica del webServiceTemplate
	 */
	@Autowired
	protected WebServiceTemplate webServiceTemplate;
	
	/**
	 * Uri del servicio que se va a consumir
	 */
	protected String uriService;
	
	@Autowired
	private GenParametrosMapper _genParametrosMapper;
	
	
	private HttpComponentsMessageSender httpComponentsMessageSender;
	
	
	public WSRespuesta getEstadoColegiado (GetEstadoColegiadoDocument request , String uriService)throws Exception{
		
		LOGGER.info("ClientAlterMutua => getEstadoColegiado()");
		webServiceTemplate.setDefaultUri(uriService);
		
		GenParametrosExample exampleUser = new GenParametrosExample();
		exampleUser.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_user");
		
		List<GenParametros> parametroUser = _genParametrosMapper.selectByExample(exampleUser);
		
		GenParametrosExample examplePass = new GenParametrosExample();
		examplePass.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_pass");
		
		List<GenParametros> parametroPass = _genParametrosMapper.selectByExample(examplePass);
		
		String user  = null;
		String pass = null;
		if(parametroUser.size() > 0){
			user = parametroUser.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getEstadoColegiado(), no se han encontrado user en gen_parametros");
		}
		
		if(parametroPass.size() > 0){
			pass = parametroPass.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getEstadoColegiado(), no se han encontrado pass en gen_parametros");
		}
		
		UsernamePasswordCredentials credenciales = new UsernamePasswordCredentials(user,pass);
		httpComponentsMessageSender.setCredentials(credenciales);
		webServiceTemplate.setMessageSender(httpComponentsMessageSender);
		
		
		WSRespuesta response = (WSRespuesta) webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		
		return response;
		
	}
	
	
	public WSRespuesta getEstadoSolicitud (GetEstadoSolicitudDocument request , String uriService)throws Exception{
		
		LOGGER.info("ClientAlterMutua => getEstadoSolicitud()");
		webServiceTemplate.setDefaultUri(uriService);
		
		GenParametrosExample exampleUser = new GenParametrosExample();
		exampleUser.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_user");
		
		List<GenParametros> parametroUser = _genParametrosMapper.selectByExample(exampleUser);
		
		GenParametrosExample examplePass = new GenParametrosExample();
		examplePass.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_pass");
		
		List<GenParametros> parametroPass = _genParametrosMapper.selectByExample(examplePass);
		
		String user  = null;
		String pass = null;
		if(parametroUser.size() > 0){
			user = parametroUser.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getEstadoSolicitud(), no se han encontrado user en gen_parametros");
		}
		
		if(parametroPass.size() > 0){
			pass = parametroPass.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getEstadoSolicitud(), no se han encontrado pass en gen_parametros");
		}
		
		UsernamePasswordCredentials credenciales = new UsernamePasswordCredentials(user,pass);
		httpComponentsMessageSender.setCredentials(credenciales);
		webServiceTemplate.setMessageSender(httpComponentsMessageSender);
		
		
		WSRespuesta response = (WSRespuesta) webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		
		return response;
		
	
	}
	
	public WSRespuesta getPropuestas (GetPropuestasDocument request, String uriService) throws Exception{
		
		LOGGER.info("ClientAlterMutua => getPropuestas()");
		webServiceTemplate.setDefaultUri(uriService);
		
		GenParametrosExample exampleUser = new GenParametrosExample();
		exampleUser.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_user");
		
		List<GenParametros> parametroUser = _genParametrosMapper.selectByExample(exampleUser);
		
		GenParametrosExample examplePass = new GenParametrosExample();
		examplePass.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_pass");
		
		List<GenParametros> parametroPass = _genParametrosMapper.selectByExample(examplePass);
		
		String user  = null;
		String pass = null;
		if(parametroUser.size() > 0){
			user = parametroUser.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getPropuestas(),no se han encontrado user en gen_parametros");
		}
		
		if(parametroPass.size() > 0){
			pass = parametroPass.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getPropuestas() ,no se han encontrado pass en gen_parametros");
		}
		
		UsernamePasswordCredentials credenciales = new UsernamePasswordCredentials(user,pass);
		httpComponentsMessageSender.setCredentials(credenciales);
		webServiceTemplate.setMessageSender(httpComponentsMessageSender);
		
		
		WSRespuesta response = (WSRespuesta) webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		
		return response;
	}
	
	public WSRespuesta getTarifaSolicitud (GetTarifaSolicitudDocument request, String uriService) throws Exception{
		
		LOGGER.info("ClientAlterMutua => getEstadoColegiado()");
		webServiceTemplate.setDefaultUri(uriService);
		
		GenParametrosExample exampleUser = new GenParametrosExample();
		exampleUser.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_user");
		
		List<GenParametros> parametroUser = _genParametrosMapper.selectByExample(exampleUser);
		
		GenParametrosExample examplePass = new GenParametrosExample();
		examplePass.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_pass");
		
		List<GenParametros> parametroPass = _genParametrosMapper.selectByExample(examplePass);
		
		String user  = null;
		String pass = null;
		if(parametroUser.size() > 0){
			user = parametroUser.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getEstadoColegiado() no se han encontrado user en gen_parametros");
		}
		
		if(parametroPass.size() > 0){
			pass = parametroPass.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getEstadoColegiado() no se han encontrado pass en gen_parametros");
		}
		
		UsernamePasswordCredentials credenciales = new UsernamePasswordCredentials(user,pass);
		httpComponentsMessageSender.setCredentials(credenciales);
		webServiceTemplate.setMessageSender(httpComponentsMessageSender);
		
		
		WSRespuesta response = (WSRespuesta) webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		
		return response;
	}
	
	public WSRespuesta setSolicitudAlter(SetSolicitudAlterDocument request, String uriService) throws Exception{
		
		LOGGER.info("ClientAlterMutua => getEstadoColegiado()");
		webServiceTemplate.setDefaultUri(uriService);
		
		GenParametrosExample exampleUser = new GenParametrosExample();
		exampleUser.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_user");
		
		List<GenParametros> parametroUser = _genParametrosMapper.selectByExample(exampleUser);
		
		GenParametrosExample examplePass = new GenParametrosExample();
		examplePass.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_pass");
		
		List<GenParametros> parametroPass = _genParametrosMapper.selectByExample(examplePass);
		
		String user  = null;
		String pass = null;
		if(parametroUser.size() > 0){
			user = parametroUser.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getEstadoColegiado(), no se han encontrado user en gen_parametros");
		}
		
		if(parametroPass.size() > 0){
			pass = parametroPass.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getEstadoColegiado(), no se han encontrado pass en gen_parametros");
		}
		
		UsernamePasswordCredentials credenciales = new UsernamePasswordCredentials(user,pass);
		httpComponentsMessageSender.setCredentials(credenciales);
		webServiceTemplate.setMessageSender(httpComponentsMessageSender);
		
		
		WSRespuesta response = (WSRespuesta) webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		
		return response;
		
	}

}
