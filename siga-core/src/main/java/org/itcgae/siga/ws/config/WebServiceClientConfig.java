package org.itcgae.siga.ws.config;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.xmlbeans.XmlBeansMarshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointAdapter;
import org.springframework.ws.server.EndpointMapping;
import org.springframework.ws.server.endpoint.adapter.GenericMarshallingMethodEndpointAdapter;
import org.springframework.ws.server.endpoint.mapping.PayloadRootAnnotationMethodEndpointMapping;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.server.SoapMessageDispatcher;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

@EnableWs
@Configuration
@SuppressWarnings("deprecation")
public class WebServiceClientConfig extends WsConfigurerAdapter {

	@Bean
	public GenericMarshallingMethodEndpointAdapter endpointAdapter() {
		GenericMarshallingMethodEndpointAdapter endpointAdapter = new GenericMarshallingMethodEndpointAdapter();
		endpointAdapter.setMarshaller(new XmlBeansMarshaller());
		endpointAdapter.setUnmarshaller(new XmlBeansMarshaller());
		return endpointAdapter;
	}

	@Bean
	public SoapMessageDispatcher dispatcher(GenericMarshallingMethodEndpointAdapter endpointAdapter,
			ApplicationContext applicationContext, PayloadRootAnnotationMethodEndpointMapping endpointMapping) {
		SoapMessageDispatcher dispatcher = new SoapMessageDispatcher();
		List<EndpointAdapter> endpointAdapterList = new ArrayList<EndpointAdapter>();
		endpointAdapterList.add(endpointAdapter);
		dispatcher.setEndpointAdapters(endpointAdapterList);
		dispatcher.setApplicationContext(applicationContext);

		List<EndpointMapping> endpointMappings = new ArrayList<EndpointMapping>();
		endpointMappings.add(endpointMapping);
		dispatcher.setEndpointMappings(endpointMappings);

		return dispatcher;
	}

	@Bean
	public PayloadRootAnnotationMethodEndpointMapping endpointMapping(ApplicationContext applicationContext) {
		PayloadRootAnnotationMethodEndpointMapping endpointMapping = new PayloadRootAnnotationMethodEndpointMapping();
		endpointMapping.setApplicationContext(applicationContext);
		return endpointMapping;
	}

	@Bean
	public WebServiceTemplate webServiceTemplate() throws Exception {
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setMarshaller(new XmlBeansMarshaller());
		webServiceTemplate.setUnmarshaller(new XmlBeansMarshaller());
		return webServiceTemplate;
	}

	@Bean
	public HttpComponentsMessageSender httpComponentsMessageSender() throws Exception {
		HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();

		return httpComponentsMessageSender;
	}

	@Bean
	public MutualidadClient webServiceTemplateMutualidad()
			throws SOAPException, KeyManagementException, NoSuchAlgorithmException {
		MutualidadClient client = new MutualidadClient();

		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		
		SSLContext sslContext = SSLContexts.custom().useProtocol("TLSv1.1").build();
		
		CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLContext(sslContext)
				.addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor())
				.build();
		
		HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender(httpClient);
		
		MessageFactory msgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
		SaajSoapMessageFactory newSoapMessageFactory = new SaajSoapMessageFactory(msgFactory);
		
		webServiceTemplate.setMessageFactory(newSoapMessageFactory);
		webServiceTemplate.setDefaultUri("https://www.mutualidadabogacia.net/IntegracionColegiosDesarrollo/Integracion_Metodos.svc");
		webServiceTemplate.setMarshaller(new XmlBeansMarshaller());
		webServiceTemplate.setUnmarshaller(new XmlBeansMarshaller());
		webServiceTemplate.setMessageSender(messageSender);
		client.setWebServiceTemplate(webServiceTemplate);
		
		return client;
	}

}
