package org.itcgae.siga.ws.client;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.datacontract.schemas._2004._07.IntegracionEnumsCombos;
import org.datacontract.schemas._2004._07.IntegracionSolicitudRespuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.xml.transform.StringSource;
import samples.servicemodel.microsoft.EstadoSolicitudDocument.EstadoSolicitud;
import samples.servicemodel.microsoft.GetEnumsDocument;
import samples.servicemodel.microsoft.impl.EstadoMutualistaResponseDocumentImpl;
import samples.servicemodel.microsoft.impl.EstadoSolicitudDocumentImpl;
import samples.servicemodel.microsoft.impl.GetEnumsResponseDocumentImpl;
import samples.servicemodel.microsoft.impl.MGASolicitudPolizaAccuGratuitosResponseDocumentImpl;
import samples.servicemodel.microsoft.impl.MGASolicitudPolizaProfesionalResponseDocumentImpl;

import com.altermutua.www.wssiga.GetEstadoColegiadoDocument.GetEstadoColegiado;

@Component
public class ClientMutualidad {
	
	/**
	 * Bean con la configuración básica del webServiceTemplate
	 */
	@Autowired
	protected WebServiceTemplate webServiceTemplate;
	
	/**
	 * Uri del servicio que se va a consumir
	 */
	protected String uriService;
	

	
	public IntegracionSolicitudRespuesta getEstadoMutualista (GetEstadoColegiado request , String uriService)throws Exception{
		
		
		
		webServiceTemplate.setDefaultUri(uriService);
		
		EstadoMutualistaResponseDocumentImpl responseWS = (EstadoMutualistaResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		IntegracionSolicitudRespuesta estadoMutualista = responseWS.getEstadoMutualistaResponse().getEstadoMutualistaResult();
		
		return estadoMutualista;
		
	}
	
	public EstadoSolicitud getEstadoSolicitud (GetEstadoColegiado request , String uriService)throws Exception{
		
	
		webServiceTemplate.setDefaultUri(uriService);
		
		EstadoSolicitudDocumentImpl responseWS = (EstadoSolicitudDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request);

		
		EstadoSolicitud estadoMutualista = responseWS.getEstadoSolicitud();
		
		return estadoMutualista;
		
	}
	
	
	public IntegracionEnumsCombos getEnums (GetEnumsDocument request , String uriService)throws Exception{
		
		
		webServiceTemplate.setDefaultUri(uriService);
		
		GetEnumsResponseDocumentImpl responseWS = (GetEnumsResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request, new WebServiceMessageCallback() {

	        public void doWithMessage(WebServiceMessage message) {
	            try {
	                SoapMessage soapMessage = (SoapMessage)message;
	                ((SoapMessage)message).setSoapAction("http://Microsoft.ServiceModel.Samples/IIntegracion_Metodos/GetEnums");
	                SoapHeader header = soapMessage.getSoapHeader();
	                StringSource headerSource = new StringSource("<wsa:MessageID xmlns:soapenv=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\" "
	                		+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
	                		+ "soapenv:mustUnderstand=\"false\">uuid:5c9c9160-d2ad-11e8-b137-887bf717ed6d</wsa:MessageID>\n"
	                		+ "<wsa:To soapenv:mustUnderstand=\"false\">https://www.mutualidadabogacia.net/IntegracionColegiosDesarrollo/Integracion_Metodos.svc</wsa:To>\n"
	                		+ "<wsa:Action soapenv:mustUnderstand=\"false\">http://Microsoft.ServiceModel.Samples/IIntegracion_Metodos/GetEnums</wsa:Action>\n"
	                		+ "<wsa:From xmlns=\"http://www.w3.org/2005/08/addressing\" soapenv:mustUnderstand=\"false\">\n"
	                		+ "<wsa:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa:Address>\n"
	                		+ "</wsa:From>");
	                Transformer transformer = TransformerFactory.newInstance().newTransformer();
	                transformer.transform(headerSource, header.getResult());
	            } catch (Exception e) {
	                // exception handling
	            }
	        }
	    });
		
		IntegracionEnumsCombos response =responseWS.getGetEnumsResponse().getGetEnumsResult();
		
		return response;
		
	}
	

	public IntegracionSolicitudRespuesta MGASolicitudPolizaAccuGratuitos (GetEstadoColegiado request , String uriService)throws Exception{
		
		
		
		webServiceTemplate.setDefaultUri(uriService);
		
		MGASolicitudPolizaAccuGratuitosResponseDocumentImpl responseWS = (MGASolicitudPolizaAccuGratuitosResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request);

		
		IntegracionSolicitudRespuesta response =responseWS.getMGASolicitudPolizaAccuGratuitosResponse().getMGASolicitudPolizaAccuGratuitosResult();
		
		return response;
		
	}

	public IntegracionSolicitudRespuesta MGASolicitudPolizaProfesional (GetEstadoColegiado request , String uriService)throws Exception{
		
		webServiceTemplate.setDefaultUri(uriService);
		
		MGASolicitudPolizaProfesionalResponseDocumentImpl responseWS = (MGASolicitudPolizaProfesionalResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request);

		
		IntegracionSolicitudRespuesta response = responseWS.getMGASolicitudPolizaProfesionalResponse().getMGASolicitudPolizaProfesionalResult();
		
		return response;
		
	}

}
