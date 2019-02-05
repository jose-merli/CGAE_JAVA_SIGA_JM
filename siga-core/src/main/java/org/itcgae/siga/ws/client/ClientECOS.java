package org.itcgae.siga.ws.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import service.serviciosecos.EnviarSMSDocument;
import service.serviciosecos.EnviarSMSResponseDocument;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.xml.soap.SOAPException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

@Component
public class ClientECOS extends DefaultClientWs{
	
	/**
	 * Bean con la configuración básica del webServiceTemplate
	 */
	@Autowired
	protected WebServiceTemplate webServiceTemplate;
	
	public EnviarSMSResponseDocument enviarSMS (String uriService, EnviarSMSDocument request) throws NoSuchAlgorithmException, SOAPException, KeyManagementException{
		
		EnviarSMSResponseDocument response = null;
		
//		webServiceTemplate.setDefaultUri(uriService);
//		
//		SSLContext sslContext = SSLContexts.custom().useProtocol("TLSv1").build();
//		CloseableHttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).addInterceptorFirst(new 
//		HttpComponentsMessageSender.RemoveSoapHeadersInterceptor()).build();
//		HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender(httpClient);
//		webServiceTemplate.setMessageSender(messageSender);
		
		response = (EnviarSMSResponseDocument) webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		return response;
	}

}
