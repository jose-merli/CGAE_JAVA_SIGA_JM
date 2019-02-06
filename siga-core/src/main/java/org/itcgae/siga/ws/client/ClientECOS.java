package org.itcgae.siga.ws.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import service.serviciosecos.EnviarSMSDocument;
import service.serviciosecos.EnviarSMSResponseDocument;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.xml.soap.SOAPException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
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
