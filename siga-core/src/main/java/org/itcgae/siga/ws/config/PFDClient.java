package org.itcgae.siga.ws.config;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.addressing.client.ActionCallback;

import com.pfd.ws.service.FirmaCorporativaPDFDocument;
import com.pfd.ws.service.FirmaCorporativaPDFResponseDocument;
import com.pfd.ws.service.ObtenerDocumentoDocument;
import com.pfd.ws.service.ObtenerDocumentoResponseDocument;

import service.serviciosecos.EnviarSMSDocument;
import service.serviciosecos.EnviarSMSResponseDocument;

public class PFDClient extends WebServiceGatewaySupport {
	
	
    public FirmaCorporativaPDFResponseDocument firmarPDF(String uriService, FirmaCorporativaPDFDocument request) throws URISyntaxException {
   	 
//    	
//        ActionCallback callback = new ActionCallback(
//                new URI("https://demo.redabogacia.org/ecos/wsecos/services/ServiciosECOSService.service"));

        return (FirmaCorporativaPDFResponseDocument) getWebServiceTemplate().marshalSendAndReceive(uriService, request);
    }
    
    public ObtenerDocumentoResponseDocument obtenerDocumento(String uriService, ObtenerDocumentoDocument request) throws URISyntaxException {
      	 
//    	
//        ActionCallback callback = new ActionCallback(
//                new URI("https://demo.redabogacia.org/ecos/wsecos/services/ServiciosECOSService.service"));

        return (ObtenerDocumentoResponseDocument) getWebServiceTemplate().marshalSendAndReceive(uriService, request);
    }	
	

}
