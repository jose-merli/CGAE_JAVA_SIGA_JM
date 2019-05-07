package org.itcgae.siga.ws.client;

import java.net.URISyntaxException;

import org.apache.log4j.Logger;
import org.itcgae.siga.ws.config.ECOSClient;
import org.itcgae.siga.ws.config.WebServiceClientConfigECOS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import service.serviciosecos.ConsultarEstadoMensajeDocument;
import service.serviciosecos.ConsultarEstadoMensajeResponseDocument;
import service.serviciosecos.EnviarSMSDocument;
import service.serviciosecos.EnviarSMSResponseDocument;

@Component
public class ClientECOS extends DefaultClientWs{
	
	Logger LOGGER = Logger.getLogger(ClientECOS.class);	
	
	/**
	 * Bean con la configuración básica del webServiceTemplate
	 */
	@Autowired
	protected WebServiceTemplate webServiceTemplate;
	
	public EnviarSMSResponseDocument enviarSMS (String uriService, EnviarSMSDocument request) throws URISyntaxException{
		AnnotationConfigApplicationContext context = null;
		EnviarSMSResponseDocument response = null;
		try {
			LOGGER.debug("Configuramos llamada a ECOS");
			context = new AnnotationConfigApplicationContext(WebServiceClientConfigECOS.class);
			ECOSClient client = context.getBean(ECOSClient.class);
			
			LOGGER.debug("Llamada a ECOS");
			response = client.enviarSMS(uriService, request);
			LOGGER.debug("Respuesta de ECOS recibida");
			
		}catch (Exception e) {
			LOGGER.error("Error al enviar SMS por ECOS", e);
		}finally {
			if(context != null) {
				context.close();
			}
		}
		
		
		return response;
	}
	
	public ConsultarEstadoMensajeResponseDocument consultaEstadoMensaje (String uriService, ConsultarEstadoMensajeDocument request) throws URISyntaxException{
		AnnotationConfigApplicationContext context = null;
		ConsultarEstadoMensajeResponseDocument response = null;
		try {
			LOGGER.debug("Configuramos llamada a ECOS");
			context = new AnnotationConfigApplicationContext(WebServiceClientConfigECOS.class);
			ECOSClient client = context.getBean(ECOSClient.class);
			
			LOGGER.debug("Llamada a ECOS");
			response = client.consultaEstadoMensaje(uriService, request);
			LOGGER.debug("Respuesta de ECOS recibida");
			
		}catch (Exception e) {
			LOGGER.error("Error al enviar SMS por ECOS", e);
		}finally {
			if(context != null) {
				context.close();
			}
		}
		
		
		return response;
	}

}
