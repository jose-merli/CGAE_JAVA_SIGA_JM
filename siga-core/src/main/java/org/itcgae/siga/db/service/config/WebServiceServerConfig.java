package org.itcgae.siga.db.service.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchemaCollection;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;

@Configuration
public class WebServiceServerConfig {

	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/ws/*");
	}
	
	
	@Bean(name = "RegistroSociedadesService")
	public DefaultWsdl11Definition datosSociedadesWsdl11Definition(@Qualifier("registroSociedadesSchema") XsdSchemaCollection datosSociedades) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("RegistroSociedadesPort");
		wsdl11Definition.setLocationUri("/ws/RegistroSociedadesService");
		wsdl11Definition.setTargetNamespace("urn:registroSociedades.ws.sspp.itcgae.org");
		wsdl11Definition.setSchemaCollection(datosSociedades);
		
		return wsdl11Definition;
	}
	
	@Bean(name= "registroSociedadesSchema")
	public XsdSchemaCollection datosSociedadesSchema(){
		CommonsXsdSchemaCollection xsds = new CommonsXsdSchemaCollection(new ClassPathResource("xsd/registroSociedades/v1/RegistroSociedadesServer.xsd"));
		xsds.setInline(true);
		return xsds;
	}
}
