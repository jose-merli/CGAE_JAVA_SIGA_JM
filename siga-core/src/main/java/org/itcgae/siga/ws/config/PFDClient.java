package org.itcgae.siga.ws.config;

import java.net.URISyntaxException;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.pfd.ws.validacionFirma.ResultSolicitudDocumentoTO;
import com.pfd.ws.validacionFirma.SolicitudDocumentoTO;
import com.sis.firma.core.TO.DatosSelloFirmaPDFTO;
import com.sis.firma.core.TO.FirmaResultadoTO;
import com.sis.firma.core.excepciones.ProblemasComunicacionWSException;
import com.sis.firma.coreServer.accesoWS;


public class PFDClient extends WebServiceGatewaySupport {
	
	
    public FirmaResultadoTO firmarPDF(String idCliente,String b64Pdf,DatosSelloFirmaPDFTO request, String uriService) throws URISyntaxException {
    	
    	FirmaResultadoTO firma = null;
    	try {
    		firma=accesoWS.solicitarFirmaCorporativaPDF(idCliente, b64Pdf, request, "0", uriService);

    	}catch (ProblemasComunicacionWSException e){
    		e.printStackTrace();
    	}
    	
    	return firma;
    }
    
    public ResultSolicitudDocumentoTO obtenerDocumento(String uriService, SolicitudDocumentoTO request) throws URISyntaxException {
    	ResultSolicitudDocumentoTO obtDoc = null;;
		try {
			obtDoc = accesoWS.obtenerDocumento(request, uriService);
		} catch (ProblemasComunicacionWSException e) {
			e.printStackTrace();
		}
    	return obtDoc;
    }	
	

}
