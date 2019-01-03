package org.itcgae.siga.ws.client;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.engine.AxisConfiguration;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.datacontract.schemas._2004._07.ArrayOfIntegracionTextoValor;
import org.datacontract.schemas._2004._07.IntegracionEnumsCombos;
import org.datacontract.schemas._2004._07.IntegracionSolicitudRespuesta;
import org.datacontract.schemas._2004._07.IntegracionTextoValor;
import org.itcgae.siga.DTOs.ws.mutualidad.RespuestaMutualidad;
import org.itcgae.siga.DTOs.ws.mutualidad.ValueKeyVO;
import org.itcgae.siga.ws.config.MutualidadClient;
import org.itcgae.siga.ws.config.WebServiceClientConfig;
import org.siga.mutualidad.Integracion_MetodosStub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender.RemoveSoapHeadersInterceptor;

import com.altermutua.www.wssiga.GetEstadoColegiadoDocument;

import samples.servicemodel.microsoft.EstadoMutualistaDocument;
import samples.servicemodel.microsoft.EstadoSolicitudDocument;
import samples.servicemodel.microsoft.GetEnumsDocument;
import samples.servicemodel.microsoft.GetEnumsResponseDocument;
import samples.servicemodel.microsoft.MGASolicitudPolizaAccuGratuitosBloqueDocument;
import samples.servicemodel.microsoft.impl.EstadoMutualistaResponseDocumentImpl;
import samples.servicemodel.microsoft.impl.EstadoSolicitudResponseDocumentImpl;
import samples.servicemodel.microsoft.impl.MGASolicitudPolizaAccuGratuitosResponseDocumentImpl;
import samples.servicemodel.microsoft.impl.MGASolicitudPolizaProfesionalResponseDocumentImpl;


@Component
public class ClientMutualidad extends  WebServiceGatewaySupport  {
	
	private static Map<Integer, ConfigurationContext> mapa = new HashMap<Integer, ConfigurationContext>();
	private static final String URLMUTUALIDADABOGACIA = "URL_WS_MUTUALIDAD";
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
	

		
		IntegracionSolicitudRespuesta estadoMutualista = responseWS.getEstadoSolicitudResponse().getEstadoSolicitudResult();
		
		return estadoMutualista;
		
	}
	
	
	public RespuestaMutualidad getEnums (GetEnumsDocument request , String uriService)throws Exception{
		

		
		RespuestaMutualidad respuesta = new RespuestaMutualidad();
		try{
			AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebServiceClientConfig.class);
		    MutualidadClient client = context.getBean(MutualidadClient.class);
		    GetEnumsResponseDocument responsefinal = client.getMutualidad(request);
		        
			
			IntegracionEnumsCombos enumCombos = responsefinal.getGetEnumsResponse().getGetEnumsResult();

			
			respuesta.setBeneficiarios(transformaCombo(enumCombos.getDesignacionBeneficiarios()));
			respuesta.setCoberturas(transformaCombo(enumCombos.getOpcionesCoberturas()));
			respuesta.setEstadosCiviles(transformaCombo(enumCombos.getEstadosCiviles()));
			respuesta.setPeriodicidades(transformaCombo(enumCombos.getFormaPago()));
			respuesta.setEjerciente(transformaCombo(enumCombos.getEjerciente()));
			respuesta.setSexos(transformaCombo(enumCombos.getSexos()));
			respuesta.setTiposDireccion(transformaCombo(enumCombos.getTiposDireccion()));
			respuesta.setTiposDomicilio(transformaCombo(enumCombos.getTiposDomicilio()));
			respuesta.setTiposIdentificador(transformaCombo(enumCombos.getTiposIdentificador()));
			respuesta.setAsistencia(transformaCombo(enumCombos.getAsistenciaSanitaria()));
			respuesta.setCorrecto(true);
			return respuesta;
		}catch (Exception e) {
			throw new Exception("Imposible comunicar con la mutualidad en estos momentos. Inténtelo de nuevo en unos minutos");
		}
		
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
	
	
	
	private List<ValueKeyVO> transformaCombo(ArrayOfIntegracionTextoValor arrayOfIntegracionTextoValor){
		List<ValueKeyVO> map= new ArrayList<ValueKeyVO>();
			for (IntegracionTextoValor integracion : arrayOfIntegracionTextoValor.getIntegracionTextoValorArray()) {
				map.add(new ValueKeyVO(Integer.valueOf(integracion.getValor()).toString(), integracion.getOpcion()));
			}
			
		return map;
		
	}

}
