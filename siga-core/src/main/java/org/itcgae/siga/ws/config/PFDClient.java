package org.itcgae.siga.ws.config;

import java.net.URISyntaxException;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.pfd.ws.service.FirmaCorporativaPDFDocument;
import com.pfd.ws.service.FirmaCorporativaPDFResponseDocument;
import com.pfd.ws.service.ObtenerDocumentoDocument;
import com.pfd.ws.service.ObtenerDocumentoResponseDocument;

public class PFDClient extends WebServiceGatewaySupport {
	
	
    public FirmaCorporativaPDFResponseDocument firmarPDF(String uriService, FirmaCorporativaPDFDocument request) throws URISyntaxException {
        return (FirmaCorporativaPDFResponseDocument) getWebServiceTemplate().marshalSendAndReceive(uriService, request);
    }
    
    public ObtenerDocumentoResponseDocument obtenerDocumento(String uriService, ObtenerDocumentoDocument request) throws URISyntaxException {
        return (ObtenerDocumentoResponseDocument) getWebServiceTemplate().marshalSendAndReceive(uriService, request);
    }	
	

}
