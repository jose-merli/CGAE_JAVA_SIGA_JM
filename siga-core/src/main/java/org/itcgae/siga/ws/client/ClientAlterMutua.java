package org.itcgae.siga.ws.client;


import org.apache.http.auth.UsernamePasswordCredentials;
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
	
	
	public WSRespuesta getEstadoColegiado (GetEstadoColegiadoDocument request , String uriService)throws Exception{
		
		
		webServiceTemplate.setDefaultUri(uriService);
		
		UsernamePasswordCredentials credenciales = new UsernamePasswordCredentials("user","pass");
		
		httpComponentsMessageSender.setCredentials(credenciales);
		webServiceTemplate.setMessageSender(httpComponentsMessageSender);
		
		
		WSRespuesta response = (WSRespuesta) webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		
		return response;
		
	}
	
	
	public WSRespuesta getEstadoSolicitud (GetEstadoSolicitudDocument request , String uriService)throws Exception{
		
		webServiceTemplate.setDefaultUri(uriService);
		
		UsernamePasswordCredentials credenciales = new UsernamePasswordCredentials("WS_SIGA_TEST","Hutfnw54oi62ywQDhr");
		
		httpComponentsMessageSender.setCredentials(credenciales);
		webServiceTemplate.setMessageSender(httpComponentsMessageSender);
		
		WSRespuesta response = (WSRespuesta) webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		
		return response;
	}
	
	public WSRespuesta getPropuestas (GetPropuestasDocument request, String uriService) throws Exception{
		
		
		webServiceTemplate.setDefaultUri(uriService);
		
		UsernamePasswordCredentials credenciales = new UsernamePasswordCredentials("user","pass");
		
		httpComponentsMessageSender.setCredentials(credenciales);
		webServiceTemplate.setMessageSender(httpComponentsMessageSender);
		
		WSRespuesta response = (WSRespuesta) webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		return response;
	}
	
	public WSRespuesta getTarifaSolicitud (GetTarifaSolicitudDocument request, String uriService) throws Exception{
		
		webServiceTemplate.setDefaultUri(uriService);
		
		UsernamePasswordCredentials credenciales = new UsernamePasswordCredentials("user","pass");
		
		httpComponentsMessageSender.setCredentials(credenciales);
		webServiceTemplate.setMessageSender(httpComponentsMessageSender);
		
		WSRespuesta response = (WSRespuesta) webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		return response;
	}
	
	public WSRespuesta setSolicitudAlter(SetSolicitudAlterDocument request, String uriService) throws Exception{
		
		webServiceTemplate.setDefaultUri(uriService);
		
		UsernamePasswordCredentials credenciales = new UsernamePasswordCredentials("user","pass");
		
		httpComponentsMessageSender.setCredentials(credenciales);
		webServiceTemplate.setMessageSender(httpComponentsMessageSender);
		
		WSRespuesta response = (WSRespuesta) webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		return response;
		
	}

}
