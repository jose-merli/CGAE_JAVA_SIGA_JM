package org.itcgae.siga.ws.config;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.addressing.client.ActionCallback;

import service.serviciosecos.EnviarSMSDocument;
import service.serviciosecos.EnviarSMSResponseDocument;

public class ECOSClient extends WebServiceGatewaySupport {
	
	
    public EnviarSMSResponseDocument enviarSMS(String uriService, EnviarSMSDocument request) throws URISyntaxException {
   	 
//    	
//        ActionCallback callback = new ActionCallback(
//                new URI("https://demo.redabogacia.org/ecos/wsecos/services/ServiciosECOSService.service"));

        return (EnviarSMSResponseDocument) getWebServiceTemplate().marshalSendAndReceive(uriService, request);
    }
	
	

}
