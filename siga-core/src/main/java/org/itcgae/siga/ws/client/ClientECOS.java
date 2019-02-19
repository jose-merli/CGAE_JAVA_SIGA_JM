package org.itcgae.siga.ws.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import service.serviciosecos.EnviarSMSDocument;
import service.serviciosecos.EnviarSMSResponseDocument;

import java.net.URISyntaxException;
import org.itcgae.siga.ws.config.ECOSClient;
import org.itcgae.siga.ws.config.WebServiceClientConfigECOS;

@Component
public class ClientECOS extends DefaultClientWs{
	
	/**
	 * Bean con la configuración básica del webServiceTemplate
	 */
	@Autowired
	protected WebServiceTemplate webServiceTemplate;
	
	public EnviarSMSResponseDocument enviarSMS (String uriService, EnviarSMSDocument request) throws URISyntaxException{
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebServiceClientConfigECOS.class);
		ECOSClient client = context.getBean(ECOSClient.class);
		
		EnviarSMSResponseDocument response = client.enviarSMS(request);
		
		
		
		return response;
	}

}
