package org.itcgae.siga.ws.client;

import org.itcgae.sspp.ws.publicadorSociedades.GetSociedadesPublicadorRequestDocument;
import org.itcgae.sspp.ws.publicadorSociedades.GetSociedadesPublicadorResponseDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;



@Service
@Primary
public class ClientRegistroSociedades {
	
	/**
	 * Bean con la configuración básica del webServiceTemplate
	 */
	@Autowired
	protected WebServiceTemplate webServiceTemplate;
	
	/**
	 * Uri del servicio que se va a consumir
	 */
	protected String uriService;
	
	public GetSociedadesPublicadorResponseDocument getListaSociedades(GetSociedadesPublicadorRequestDocument request) {


	
		webServiceTemplate.setDefaultUri("https://vmcgaeap003.cloud.es.deloitte.com/sspp-publicacion/ws/PublicadorSociedadesService");
		uriService = "https://vmcgaeap003.cloud.es.deloitte.com/sspp-publicacion/ws/PublicadorSociedadesService";

		GetSociedadesPublicadorResponseDocument response = 	(GetSociedadesPublicadorResponseDocument) 
			webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		return response;
	}

}
