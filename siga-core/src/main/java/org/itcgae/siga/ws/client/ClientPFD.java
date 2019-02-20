package org.itcgae.siga.ws.client;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.pfd.ws.service.FirmaCorporativaPDFDocument;
import com.pfd.ws.service.FirmaCorporativaPDFResponseDocument;
import com.pfd.ws.service.ObtenerDocumentoDocument;
import com.pfd.ws.service.ObtenerDocumentoResponseDocument;

@Component
public class ClientPFD extends DefaultClientWs{
	
	private static final Logger LOGGER = Logger.getLogger(ClientPFD.class);

	
	public FirmaCorporativaPDFResponseDocument firmarPDF(String uriService, FirmaCorporativaPDFDocument request) throws Exception{
		LOGGER.debug("Llamada a pfd firmarPDF");
		FirmaCorporativaPDFResponseDocument response = null;
		
		try{
			LOGGER.info("La url de la PFD es: " + uriService);
			configConnection(uriService);
			response = (FirmaCorporativaPDFResponseDocument) webServiceTemplate.marshalSendAndReceive(uriService, request);

		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | CertificateException
				| IOException e) {
			LOGGER.error("Error al enviar a firma un documento", e);
			
			throw e;
		} catch (Exception e){
			LOGGER.error("Error al enviar a firma un documento", e);
			throw e;
		}
		
		return response;
	}
	
	public ObtenerDocumentoResponseDocument obtenerDocumento(String uriService, ObtenerDocumentoDocument request){
		
		LOGGER.debug("Llamada a obtener un documento firmado");
		ObtenerDocumentoResponseDocument response = null;
		
		try{
			LOGGER.info("La url de la PFD es: " + uriService);
			response = (ObtenerDocumentoResponseDocument) webServiceTemplate.marshalSendAndReceive(uriService, request);
		}catch (Exception e){
			LOGGER.error("Error al obtener un documento firmado", e);
	
		}
		return response;
	}

}
