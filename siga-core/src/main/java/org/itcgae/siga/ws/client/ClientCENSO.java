package org.itcgae.siga.ws.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.colegiados.info.redabogacia.BusquedaColegiadoRequestDocument;
import com.colegiados.info.redabogacia.BusquedaColegiadoResponseDocument;
import com.colegiados.info.redabogacia.ColegiadoRequestDocument;
import com.colegiados.info.redabogacia.ColegiadoResponseDocument;

@Component
public class ClientCENSO {
	
	/**
	 * Bean con la configuración básica del webServiceTemplate
	 */
	@Autowired
	protected WebServiceTemplate webServiceTemplate;
	
	/**
	 * Uri del servicio que se va a consumir
	 */
	protected String uriService;
	
	
	
	public ColegiadoResponseDocument busquedaColegiadoConIdentificacion(ColegiadoRequestDocument request) throws Exception {

		webServiceTemplate.setDefaultUri("https://ecomdes.redabogacia.org/eCOM/services/InfoColegiadosService");
		uriService = "https://ecomdes.redabogacia.org/eCOM/services/InfoColegiadosService";
		

		ColegiadoResponseDocument response = (ColegiadoResponseDocument) webServiceTemplate
				.marshalSendAndReceive(uriService, request);
	
		return response;
	}
	
	public BusquedaColegiadoResponseDocument busquedaColegiadoSinIdentificacion(BusquedaColegiadoRequestDocument request) throws Exception {

		webServiceTemplate.setDefaultUri("https://ecomdes.redabogacia.org/eCOM/services/InfoColegiadosService");
		uriService = "https://ecomdes.redabogacia.org/eCOM/services/InfoColegiadosService";
		

		BusquedaColegiadoResponseDocument response = (BusquedaColegiadoResponseDocument) webServiceTemplate
				.marshalSendAndReceive(uriService, request);
	
		return response;
	}	
	
	
}
