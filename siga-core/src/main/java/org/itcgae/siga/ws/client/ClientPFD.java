package org.itcgae.siga.ws.client;

import org.apache.log4j.Logger;
import org.itcgae.siga.ws.config.PFDClient;
import org.itcgae.siga.ws.config.WebServiceClientConfigPFD;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.pfd.ws.validacionFirma.ResultSolicitudDocumentoTO;
import com.pfd.ws.validacionFirma.SolicitudDocumentoTO;
import com.sis.firma.core.TO.DatosSelloFirmaPDFTO;
import com.sis.firma.core.TO.FirmaResultadoTO;

@Component
public class ClientPFD extends DefaultClientWs{
	
	private static final Logger LOGGER = Logger.getLogger(ClientPFD.class);

	
	public FirmaResultadoTO firmarPDF(String idClientePFD, String uriService, DatosSelloFirmaPDFTO request, String base64File) throws Exception{
		LOGGER.debug("Llamada a pfd firmarPDF");
		FirmaResultadoTO response = null;
		AnnotationConfigApplicationContext context = null;
		try{
			LOGGER.info("La url de la PFD es: " + uriService);

			context = new AnnotationConfigApplicationContext(WebServiceClientConfigPFD.class);
			PFDClient client = context.getBean(PFDClient.class);
			LOGGER.debug(request);
			response = client.firmarPDF(idClientePFD, uriService, request, base64File);
			LOGGER.debug(response);
		} catch (Exception e){
			LOGGER.error("Error al enviar a firma un documento", e);
			throw e;
		}finally {
			if(context != null) {
				context.close();
			}
		}
		
		return response;
	}
	
	public ResultSolicitudDocumentoTO obtenerDocumento(String uriService, SolicitudDocumentoTO request){
		
		LOGGER.debug("Llamada a obtener un documento firmado");
		ResultSolicitudDocumentoTO response = null;
		AnnotationConfigApplicationContext context = null;
		
		try{
			LOGGER.info("La url de la PFD es: " + uriService);
			context = new AnnotationConfigApplicationContext(WebServiceClientConfigPFD.class);
			PFDClient client = context.getBean(PFDClient.class);
			LOGGER.debug(request);
			response = client.obtenerDocumento(uriService, request);
			LOGGER.debug(response);
		}catch (Exception e){
			LOGGER.error("Error al obtener un documento firmado", e);
	
		}finally {
			if(context != null) {
				context.close();
			}
		}
		
		return response;
	}

}
