package org.itcgae.siga.ws.client;

import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import org.apache.log4j.Logger;
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

import samples.servicemodel.microsoft.EstadoMutualistaDocument;
import samples.servicemodel.microsoft.EstadoSolicitudDocument;
import samples.servicemodel.microsoft.GetEnumsDocument;
import samples.servicemodel.microsoft.MGASolicitudPolizaAccuGratuitosBloqueDocument;
import samples.servicemodel.microsoft.impl.EstadoMutualistaResponseDocumentImpl;
import samples.servicemodel.microsoft.impl.EstadoSolicitudResponseDocumentImpl;
import samples.servicemodel.microsoft.impl.GetEnumsResponseDocumentImpl;
import samples.servicemodel.microsoft.impl.MGASolicitudPolizaAccuGratuitosResponseDocumentImpl;
import samples.servicemodel.microsoft.impl.MGASolicitudPolizaProfesionalResponseDocumentImpl;

import com.altermutua.www.wssiga.GetEstadoColegiadoDocument;

@Component
public class ClientMutualidad {
	
	private Logger LOGGER = Logger.getLogger(ClientMutualidad.class);
	
	/**
	 * Bean con la configuración básica del webServiceTemplate
	 */
	@Autowired
	protected WebServiceTemplate webServiceTemplate;
	
	/**
	 * Uri del servicio que se va a consumir
	 */
	protected String uriService;
	

	
	public IntegracionSolicitudRespuesta getEstadoMutualista (EstadoMutualistaDocument request , String uriService)throws Exception{
		
		
		
		webServiceTemplate.setDefaultUri(uriService);
		
		EstadoMutualistaResponseDocumentImpl responseWS = (EstadoMutualistaResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request, new WebServiceMessageCallback() {
			
			@Override
			public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
	            try {
	                SoapMessage soapMessage = (SoapMessage)message;
	                //((SoapMessage)message).setSoapAction("http://Microsoft.ServiceModel.Samples/IIntegracion_Metodos/GetEnums");
	                SoapHeader header = soapMessage.getSoapHeader();
	                StringSource headerSource = new StringSource("<wsa:MessageID xmlns:soapenv=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\" "
	                		+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
	                		+ "soapenv:mustUnderstand=\"false\">uuid:78490840-e0fd-11e8-b179-b09d25bf499d</wsa:MessageID>\n"
	                		+ "<wsa:To soapenv:mustUnderstand=\"false\">https://www.mutualidadabogacia.net/IntegracionColegiosDesarrollo/Integracion_Metodos.svc</wsa:To>\n"
	                		+ "<wsa:Action soapenv:mustUnderstand=\"false\">http://Microsoft.ServiceModel.Samples/IIntegracion_Metodos/EstadoMutualista</wsa:Action>\n"
	                		+ "<wsa:From xmlns=\"http://www.w3.org/2005/08/addressing\" soapenv:mustUnderstand=\"false\">\n"
	                		+ "<wsa:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa:Address>\n"
	                		+ "</wsa:From>");
	                Transformer transformer = TransformerFactory.newInstance().newTransformer();
	                transformer.transform(headerSource, header.getResult());
	            } catch (Exception e) {
	                LOGGER.warn("Error en la llamada al servicio getEstadoMutualista: " + e.getMessage());
	            }
				
			}
		});
		
		IntegracionSolicitudRespuesta estadoMutualista = responseWS.getEstadoMutualistaResponse().getEstadoMutualistaResult();
		
		return estadoMutualista;
		
	}
	
	public IntegracionSolicitudRespuesta getEstadoSolicitud (EstadoSolicitudDocument request , String uriService)throws Exception{
		
	
		webServiceTemplate.setDefaultUri(uriService);
		
		EstadoSolicitudResponseDocumentImpl responseWS = (EstadoSolicitudResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request, new WebServiceMessageCallback() {
			
			@Override
			public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
				 try {
		                SoapMessage soapMessage = (SoapMessage)message;
		                //((SoapMessage)message).setSoapAction("http://Microsoft.ServiceModel.Samples/IIntegracion_Metodos/GetEnums");
		                SoapHeader header = soapMessage.getSoapHeader();
		                StringSource headerSource = new StringSource("<wsa:MessageID xmlns:soapenv=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\" "
		                		+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
		                		+ "soapenv:mustUnderstand=\"false\">uuid:78490840-e0fd-11e8-b179-b09d25bf499d</wsa:MessageID>\n"
		                		+ "<wsa:To soapenv:mustUnderstand=\"false\">https://www.mutualidadabogacia.net/IntegracionColegiosDesarrollo/Integracion_Metodos.svc</wsa:To>\n"
		                		+ "<wsa:Action soapenv:mustUnderstand=\"false\">http://Microsoft.ServiceModel.Samples/IIntegracion_Metodos/EstadoSolicitud</wsa:Action>\n"
		                		+ "<wsa:From xmlns=\"http://www.w3.org/2005/08/addressing\" soapenv:mustUnderstand=\"false\">\n"
		                		+ "<wsa:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa:Address>\n"
		                		+ "</wsa:From>");
		                Transformer transformer = TransformerFactory.newInstance().newTransformer();
		                transformer.transform(headerSource, header.getResult());
		            } catch (Exception e) {
		            	LOGGER.warn("Error en la llamada al servicio getEstadoSolicitud: " + e.getMessage());
		            }
				
			}
		});

		
		IntegracionSolicitudRespuesta estadoMutualista = responseWS.getEstadoSolicitudResponse().getEstadoSolicitudResult();
		
		return estadoMutualista;
		
	}
	
	
	public IntegracionEnumsCombos getEnums (GetEnumsDocument request , String uriService)throws Exception{
		
		
		webServiceTemplate.setDefaultUri(uriService);
		
		GetEnumsResponseDocumentImpl responseWS = (GetEnumsResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		/*GetEnumsResponseDocumentImpl responseWS = (GetEnumsResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request, new WebServiceMessageCallback() {
			
			@Override
	        public void doWithMessage(WebServiceMessage message) {
	            try {
	                SoapMessage soapMessage = (SoapMessage)message;
	                //((SoapMessage)message).setSoapAction("http://Microsoft.ServiceModel.Samples/IIntegracion_Metodos/GetEnums");
	                SoapHeader header = soapMessage.getSoapHeader();
	                StringSource headerSource = new StringSource("<wsa:MessageID xmlns:soapenv=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\" "
	                		+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
	                		+ "soapenv:mustUnderstand=\"false\">uuid:ac2ff920-e0f3-11e8-99a7-a81d1686d13d</wsa:MessageID>\n"
	                		+ "<wsa:To soapenv:mustUnderstand=\"false\">https://www.mutualidadabogacia.net/IntegracionColegiosDesarrollo/Integracion_Metodos.svc</wsa:To>\n"
	                		+ "<wsa:Action soapenv:mustUnderstand=\"false\">http://Microsoft.ServiceModel.Samples/IIntegracion_Metodos/GetEnums</wsa:Action>\n"
	                		+ "<wsa:From xmlns=\"http://www.w3.org/2005/08/addressing\" soapenv:mustUnderstand=\"false\">\n"
	                		+ "<wsa:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa:Address>\n"
	                		+ "</wsa:From>");
	                Transformer transformer = TransformerFactory.newInstance().newTransformer();
	                transformer.transform(headerSource, header.getResult());
	            } catch (Exception e) {
	            	LOGGER.warn("Error en la llamada al servicio getEnums: " + e.getMessage());
	            }
	        }
	    });*/
		
		IntegracionEnumsCombos response =responseWS.getGetEnumsResponse().getGetEnumsResult();
		
		return response;
		
	}
	

	public IntegracionSolicitudRespuesta MGASolicitudPolizaAccuGratuitos (MGASolicitudPolizaAccuGratuitosBloqueDocument request , String uriService)throws Exception{
		
		
		
		webServiceTemplate.setDefaultUri(uriService);
		
		MGASolicitudPolizaAccuGratuitosResponseDocumentImpl responseWS = (MGASolicitudPolizaAccuGratuitosResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request, new WebServiceMessageCallback() {
			
			@Override
			public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
				
			}
		});

		
		IntegracionSolicitudRespuesta response = responseWS.getMGASolicitudPolizaAccuGratuitosResponse().getMGASolicitudPolizaAccuGratuitosResult();
		
		return response;
		
	}

	public IntegracionSolicitudRespuesta MGASolicitudPolizaProfesional (GetEstadoColegiadoDocument request , String uriService)throws Exception{
		
		webServiceTemplate.setDefaultUri(uriService);
		
		MGASolicitudPolizaProfesionalResponseDocumentImpl responseWS = (MGASolicitudPolizaProfesionalResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request);

		
		IntegracionSolicitudRespuesta response = responseWS.getMGASolicitudPolizaProfesionalResponse().getMGASolicitudPolizaProfesionalResult();
		
		return response;
		
	}

}
