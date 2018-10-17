package org.itcgae.siga.ws.client;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import com.altermutua.www.wssiga.WSRespuesta;
import com.altermutua.www.wssiga.GetEstadoColegiadoDocument.GetEstadoColegiado;

@Component
public class ClientMutualidad {
	
	/**
	 * Bean con la configuración básica del webServiceTemplate
	 */
	@Autowired
	protected WebServiceTemplate webServiceTemplate;
	
	/**
	 * Uri del servicio que se va a consumir
	 */
	protected String uriService;
	
	
	private HttpComponentsMessageSender httpComponentsMessageSender;
	
	public WSRespuesta getEstadoMutualista (GetEstadoColegiado request , String uriService)throws Exception{
		
		//TODO cambiar por el objeto respuesta que sea de mutualidad
		
		webServiceTemplate.setDefaultUri(uriService);
		
		UsernamePasswordCredentials credenciales = new UsernamePasswordCredentials("user","pass");
		
		httpComponentsMessageSender.setCredentials(credenciales);
		webServiceTemplate.setMessageSender(httpComponentsMessageSender);
		
		
		WSRespuesta response = (WSRespuesta) webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		
		return response;
		
	}
	
	public WSRespuesta getEstadoSolicitud (GetEstadoColegiado request , String uriService)throws Exception{
		
		//TODO cambiar por el objeto respuesta que sea de mutualidad
		
		webServiceTemplate.setDefaultUri(uriService);
		
		UsernamePasswordCredentials credenciales = new UsernamePasswordCredentials("user","pass");
		
		httpComponentsMessageSender.setCredentials(credenciales);
		webServiceTemplate.setMessageSender(httpComponentsMessageSender);
		
		
		WSRespuesta response = (WSRespuesta) webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		
		return response;
		
	}
	
	
	public WSRespuesta getEnums (GetEstadoColegiado request , String uriService)throws Exception{
		
		//TODO cambiar por el objeto respuesta que sea de mutualidad
		
		webServiceTemplate.setDefaultUri(uriService);
		
		UsernamePasswordCredentials credenciales = new UsernamePasswordCredentials("user","pass");
		
		httpComponentsMessageSender.setCredentials(credenciales);
		webServiceTemplate.setMessageSender(httpComponentsMessageSender);
		
		
		WSRespuesta response = (WSRespuesta) webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		
		return response;
		
	}
	

	public WSRespuesta MGASolicitudPolizaAccuGratuitos (GetEstadoColegiado request , String uriService)throws Exception{
		
		//TODO cambiar por el objeto respuesta que sea de mutualidad
		
		webServiceTemplate.setDefaultUri(uriService);
		
		UsernamePasswordCredentials credenciales = new UsernamePasswordCredentials("user","pass");
		
		httpComponentsMessageSender.setCredentials(credenciales);
		webServiceTemplate.setMessageSender(httpComponentsMessageSender);
		
		
		WSRespuesta response = (WSRespuesta) webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		
		return response;
		
	}

	public WSRespuesta MGASolicitudPolizaProfesional (GetEstadoColegiado request , String uriService)throws Exception{
		
		//TODO cambiar por el objeto respuesta que sea de mutualidad
		
		webServiceTemplate.setDefaultUri(uriService);
		
		UsernamePasswordCredentials credenciales = new UsernamePasswordCredentials("user","pass");
		
		httpComponentsMessageSender.setCredentials(credenciales);
		webServiceTemplate.setMessageSender(httpComponentsMessageSender);
		
		
		WSRespuesta response = (WSRespuesta) webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		
		return response;
		
	}

}
