package org.itcgae.siga.ws.client;

import org.apache.log4j.Logger;
import org.itcgae.siga.ws.config.PFDClient;
import org.itcgae.siga.ws.config.WebServiceClientConfigPFD;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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
		AnnotationConfigApplicationContext context = null;
		try{
			LOGGER.info("La url de la PFD es: " + uriService);

			context = new AnnotationConfigApplicationContext(WebServiceClientConfigPFD.class);
			PFDClient client = context.getBean(PFDClient.class);
			LOGGER.debug(request);
			response = client.firmarPDF(uriService, request);
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
	
	public ObtenerDocumentoResponseDocument obtenerDocumento(String uriService, ObtenerDocumentoDocument request){
		
		LOGGER.debug("Llamada a obtener un documento firmado");
		ObtenerDocumentoResponseDocument response = null;
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
