package org.itcgae.siga.ws.config;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xmlbeans.XmlBeansMarshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointAdapter;
import org.springframework.ws.server.EndpointMapping;
import org.springframework.ws.server.endpoint.adapter.GenericMarshallingMethodEndpointAdapter;
import org.springframework.ws.server.endpoint.mapping.PayloadRootAnnotationMethodEndpointMapping;
import org.springframework.ws.soap.server.SoapMessageDispatcher;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchemaCollection;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;


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
	public SoapMessageDispatcher dispatcher(GenericMarshallingMethodEndpointAdapter endpointAdapter, ApplicationContext applicationContext, PayloadRootAnnotationMethodEndpointMapping endpointMapping){
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
	public PayloadRootAnnotationMethodEndpointMapping endpointMapping(ApplicationContext applicationContext){
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

}
