package org.itcgae.siga.ws.client;

import java.io.IOException;
import java.io.OutputStream;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.ws.handler.soap.SOAPHandler;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.log4j.Logger;
import org.datacontract.schemas._2004._07.IntegracionEnumsCombos;
import org.datacontract.schemas._2004._07.IntegracionSolicitudRespuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapEnvelope;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.xml.transform.StringSource;
import org.springframework.ws.transport.http.HttpComponentsMessageSender.RemoveSoapHeadersInterceptor;

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
		
		HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
		SSLContext ssl = SSLContext.getDefault();
		SSLConnectionSocketFactory sSLConnectionSocketFactory = new SSLConnectionSocketFactory(ssl, NoopHostnameVerifier.INSTANCE);
		HttpClient httpClient = HttpClientBuilder.create().setSSLSocketFactory(sSLConnectionSocketFactory).addInterceptorFirst(new RemoveSoapHeadersInterceptor()).build();
		MessageFactory msgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
		SaajSoapMessageFactory newSoapMessageFactory = new SaajSoapMessageFactory(msgFactory);
		messageSender.setHttpClient(httpClient);
		webServiceTemplate.setMessageSender(messageSender);
		webServiceTemplate.setMessageFactory(newSoapMessageFactory);
		EstadoMutualistaResponseDocumentImpl responseWS = (EstadoMutualistaResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		/*EstadoMutualistaResponseDocumentImpl responseWS = (EstadoMutualistaResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request, new WebServiceMessageCallback() {
			
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
		});*/
		
		IntegracionSolicitudRespuesta estadoMutualista = responseWS.getEstadoMutualistaResponse().getEstadoMutualistaResult();
		
		return estadoMutualista;
		
	}
	
	public IntegracionSolicitudRespuesta getEstadoSolicitud (EstadoSolicitudDocument request , String uriService)throws Exception{
		
	
		webServiceTemplate.setDefaultUri(uriService);
		HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
		SSLContext ssl = SSLContext.getDefault();
		SSLConnectionSocketFactory sSLConnectionSocketFactory = new SSLConnectionSocketFactory(ssl, NoopHostnameVerifier.INSTANCE);
		HttpClient httpClient = HttpClientBuilder.create().setSSLSocketFactory(sSLConnectionSocketFactory).addInterceptorFirst(new RemoveSoapHeadersInterceptor()).build();
		MessageFactory msgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
		SaajSoapMessageFactory newSoapMessageFactory = new SaajSoapMessageFactory(msgFactory);
		messageSender.setHttpClient(httpClient);
		webServiceTemplate.setMessageSender(messageSender);
		webServiceTemplate.setMessageFactory(newSoapMessageFactory);
		
		EstadoSolicitudResponseDocumentImpl responseWS = (EstadoSolicitudResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		/*EstadoSolicitudResponseDocumentImpl responseWS = (EstadoSolicitudResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request, new WebServiceMessageCallback() {
			
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
		});*/

		
		IntegracionSolicitudRespuesta estadoMutualista = responseWS.getEstadoSolicitudResponse().getEstadoSolicitudResult();
		
		return estadoMutualista;
		
	}
	
	
	public IntegracionEnumsCombos getEnums (GetEnumsDocument request , String uriService)throws Exception{
		
		
		webServiceTemplate.setDefaultUri(uriService);
		//configuracion para hacer llamada ssl con el webServiceTemplate
		HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
		SSLContext ssl = SSLContext.getDefault();
		SSLConnectionSocketFactory sSLConnectionSocketFactory = new SSLConnectionSocketFactory(ssl, NoopHostnameVerifier.INSTANCE);
		HttpClient httpClient = HttpClientBuilder.create().setSSLSocketFactory(sSLConnectionSocketFactory).addInterceptorFirst(new RemoveSoapHeadersInterceptor()).build();
		MessageFactory msgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
		SaajSoapMessageFactory newSoapMessageFactory = new SaajSoapMessageFactory(msgFactory);
		messageSender.setHttpClient(httpClient);
		webServiceTemplate.setMessageSender(messageSender);
		webServiceTemplate.setMessageFactory(newSoapMessageFactory);
		
//		GetEnumsResponseDocumentImpl responseWS = (GetEnumsResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request);
		
		
		
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		GetEnumsResponseDocumentImpl responseWS = (GetEnumsResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request, new WebServiceMessageCallback() {
			
//			xmlns:soapenv=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\" "
//            		+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
					
			@Override
	        public void doWithMessage(WebServiceMessage message) {
	            try {
	                SoapMessage soapMessage = (SoapMessage)message;
	                
	                SoapEnvelope soapEnv = soapMessage.getEnvelope();
	                soapEnv.addNamespaceDeclaration("soapenv", "http://www.w3.org/2003/05/soap-envelope");
	                soapEnv.addNamespaceDeclaration("wsa", "http://www.w3.org/2005/08/addressing");
//	                soapEnv.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
//	                soapEnv.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
	                
	                SaajSoapMessage soapMessager = (SaajSoapMessage) message;
	                SoapHeaderElement messageId =  soapMessager.getSoapHeader().addHeaderElement(new QName("http://www.w3.org/2005/08/addressing", "MessageID", "wsa"));
	                messageId.setText("uuid:ac2ff920-e0f3-11e8-99a7-a81d1686d13d");
	                
	                SoapHeaderElement to =  soapMessager.getSoapHeader().addHeaderElement(new QName("http://www.w3.org/2005/08/addressing", "To", "wsa"));
	                to.setText("https://www.mutualidadabogacia.net/IntegracionColegiosDesarrollo/Integracion_Metodos.svc");
	                
	                SoapHeaderElement action =  soapMessager.getSoapHeader().addHeaderElement(new QName("http://www.w3.org/2005/08/addressing", "Action", "wsa"));
	                action.setText("http://Microsoft.ServiceModel.Samples/IIntegracion_Metodos/GetEnums");
	                
	                SoapHeaderElement from =  soapMessager.getSoapHeader().addHeaderElement(new QName("http://www.w3.org/2005/08/addressing", "From", "wsa"));
	                from.setText("<wsa:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa:Address>\n");
	                
//					((SoapMessage)message).setSoapAction("http://Microsoft.ServiceModel.Samples/IIntegracion_Metodos/GetEnums");
//	                SoapHeader header = soapMessage.getSoapHeader();
//	                StringSource headerSource = new StringSource(
//	                		"<wsa:MessageID soapenv:mustUnderstand=\"0\">uuid:ac2ff920-e0f3-11e8-99a7-a81d1686d13d</wsa:MessageID>\n" +
//	                		"<wsa:To soapenv:mustUnderstand=\"false\">https://www.mutualidadabogacia.net/IntegracionColegiosDesarrollo/Integracion_Metodos.svc</wsa:To>\n" +
//	                		"<wsa:Action soapenv:mustUnderstand=\"false\">http://Microsoft.ServiceModel.Samples/IIntegracion_Metodos/GetEnums</wsa:Action>\n"
//	                		+ "<wsa:From xmlns=\"http://www.w3.org/2005/08/addressing\" soapenv:mustUnderstand=\"false\">\n"
//	                		+ "<wsa:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa:Address>\n"
//	                		+ "</wsa:From>");
//	                Transformer transformer = TransformerFactory.newInstance().newTransformer();
//	                transformer.transform(headerSource, header.getResult());
	                
	            } catch (Exception e) {
	            	LOGGER.warn("Error en la llamada al servicio getEnums: " + e.getMessage());
	            }
	        }
	    });
		
		IntegracionEnumsCombos response = responseWS.getGetEnumsResponse().getGetEnumsResult();
		
		return response;
		
	}
	

	public IntegracionSolicitudRespuesta MGASolicitudPolizaAccuGratuitos (MGASolicitudPolizaAccuGratuitosBloqueDocument request , String uriService)throws Exception{
		
		
		
		webServiceTemplate.setDefaultUri(uriService);
		//configuracion para hacer llamada ssl con el webServiceTemplate
		HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
		SSLContext ssl = SSLContext.getDefault();
		SSLConnectionSocketFactory sSLConnectionSocketFactory = new SSLConnectionSocketFactory(ssl, NoopHostnameVerifier.INSTANCE);
		HttpClient httpClient = HttpClientBuilder.create().setSSLSocketFactory(sSLConnectionSocketFactory).addInterceptorFirst(new RemoveSoapHeadersInterceptor()).build();
		MessageFactory msgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
		SaajSoapMessageFactory newSoapMessageFactory = new SaajSoapMessageFactory(msgFactory);
		messageSender.setHttpClient(httpClient);
		webServiceTemplate.setMessageSender(messageSender);
		webServiceTemplate.setMessageFactory(newSoapMessageFactory);
		
		MGASolicitudPolizaAccuGratuitosResponseDocumentImpl responseWS = (MGASolicitudPolizaAccuGratuitosResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request);

		
		IntegracionSolicitudRespuesta response = responseWS.getMGASolicitudPolizaAccuGratuitosResponse().getMGASolicitudPolizaAccuGratuitosResult();
		
		return response;
		
	}

	public IntegracionSolicitudRespuesta MGASolicitudPolizaProfesional (GetEstadoColegiadoDocument request , String uriService)throws Exception{
		
		webServiceTemplate.setDefaultUri(uriService);
		//configuracion para hacer llamada ssl con el webServiceTemplate
		HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
		SSLContext ssl = SSLContext.getDefault();
		SSLConnectionSocketFactory sSLConnectionSocketFactory = new SSLConnectionSocketFactory(ssl, NoopHostnameVerifier.INSTANCE);
		HttpClient httpClient = HttpClientBuilder.create().setSSLSocketFactory(sSLConnectionSocketFactory).addInterceptorFirst(new RemoveSoapHeadersInterceptor()).build();
		MessageFactory msgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
		SaajSoapMessageFactory newSoapMessageFactory = new SaajSoapMessageFactory(msgFactory);
		messageSender.setHttpClient(httpClient);
		webServiceTemplate.setMessageSender(messageSender);
		webServiceTemplate.setMessageFactory(newSoapMessageFactory);
		
		MGASolicitudPolizaProfesionalResponseDocumentImpl responseWS = (MGASolicitudPolizaProfesionalResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request);

		
		IntegracionSolicitudRespuesta response = responseWS.getMGASolicitudPolizaProfesionalResponse().getMGASolicitudPolizaProfesionalResult();
		
		return response;
		
	}

}
