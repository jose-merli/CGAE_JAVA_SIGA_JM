package org.itcgae.siga.ws.config;

import java.net.URISyntaxException;

import org.itcgae.siga.commons.constants.SigaConstants;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.ecos.ws.solicitarenvio.ResultadoSolicitudEnvio;

import service.serviciosecos.ConsultarEstadoMensajeDocument;
import service.serviciosecos.ConsultarEstadoMensajeResponseDocument;
import service.serviciosecos.EnviarSMSDocument;
import service.serviciosecos.EnviarSMSResponseDocument;

public class ECOSClient extends WebServiceGatewaySupport {
	
	
    public EnviarSMSResponseDocument enviarSMS(String uriService, EnviarSMSDocument request) throws URISyntaxException {
        return (EnviarSMSResponseDocument) getWebServiceTemplate().marshalSendAndReceive(uriService, request);
    }
    
    public ConsultarEstadoMensajeResponseDocument consultaEstadoMensaje(String uriService, ConsultarEstadoMensajeDocument request) throws URISyntaxException {
        return (ConsultarEstadoMensajeResponseDocument) getWebServiceTemplate().marshalSendAndReceive(uriService, request);
    }
	
	

}
