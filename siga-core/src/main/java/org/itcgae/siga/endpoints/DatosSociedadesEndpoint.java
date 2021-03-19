package org.itcgae.siga.endpoints;



import org.itcgae.siga.services.ISociedadesServerSevice;
import org.itcgae.sspp.ws.registrosociedades.GetListaSociedadesRequestDocument;
import org.itcgae.sspp.ws.registrosociedades.GetListaSociedadesResponseDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpServletConnection;


@Endpoint
public class DatosSociedadesEndpoint {
	private static final String NAMESPACE_URI = "urn:registroSociedades.ws.sspp.itcgae.org";

	Logger LOGGER = LoggerFactory.getLogger(DatosSociedadesEndpoint.class);
	
	static int numPeticion = 0;
	
	private String ipCliente = "";
	
	@Autowired
	ISociedadesServerSevice service;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getListaSociedadesRequest")
	@ResponsePayload
	public GetListaSociedadesResponseDocument getListaSociedades(@RequestPayload GetListaSociedadesRequestDocument peticion) {
		LOGGER.info("Entra en ws getDatosSociedades");
		
		TransportContext ctx = TransportContextHolder.getTransportContext();
       
		//WebServiceConnection  con=ctx.getConnection();
		try {
			//ipCliente = con.getUri().getHost();
			 HttpServletConnection connection = (HttpServletConnection) ctx.getConnection();
		        ipCliente = connection.getHttpServletRequest().getRemoteAddr(); 
		        LOGGER.info("IP desde la que se recibe la peticion: "+ ipCliente);

		}catch (Exception e){
			LOGGER.info("Imposible obtener la url de conexion de acceso a getDatosSociedades");
		}
		return service.peticionServicio(ipCliente, peticion);
	}
}
