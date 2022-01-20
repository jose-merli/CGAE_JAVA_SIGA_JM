package org.itcgae.swagger.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter  {
	
	@Bean
	public Docket api() {
		
		
		return new Docket(DocumentationType.SWAGGER_2)
				.protocols(Collections.singleton("http"))
				.select()
				.apis(RequestHandlerSelectors.basePackage("org.itcgae.siga.scs.controllers.facturacionsjcs"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiInfo())
				.securityContexts(Arrays.asList(securityContext()))
			      .securitySchemes(Arrays.asList(apiKey()))
				;
	}
	
	
	private ApiInfo getApiInfo() {
		return new ApiInfo(
				"Order Service API",
				"Order Service API Description",
				"1.0",
				"http://codmind.com/terms",
				new Contact("Codmind", "https://codmind.com", "apis@codmind.com"),
				"LICENSE",
				"LICENSE URL",
				Collections.emptyList()
				);

	}
	
	private ApiKey apiKey() { 
	    return new ApiKey("JWT", "Authorization", "header"); 
	}
	
	private SecurityContext securityContext() { 
	    return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
	} 

	private List<SecurityReference> defaultAuth() { 
	    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
	    authorizationScopes[0] = authorizationScope; 
	    return Arrays.asList(new SecurityReference("JWT", authorizationScopes)); 
	}

	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		String context = "/siga-web-1.1.0-SNAPSHOT";
		
	    registry.addRedirectViewController(context+"/v2/api-docs", "/v2/api-docs");
	    registry.addRedirectViewController(context+"/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
	    registry.addRedirectViewController(context+"/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
	    registry.addRedirectViewController(context+"/swagger-resources", "/swagger-resources");
	    registry.addRedirectViewController(context+"/swagger-ui.html","/swagger-ui.html");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		String context = "";//"/siga-web-1.1.0-SNAPSHOT";
		
	    registry.addResourceHandler(context+"/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
	    registry.addResourceHandler(context+"/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
