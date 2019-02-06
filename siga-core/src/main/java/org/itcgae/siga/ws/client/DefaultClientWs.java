package org.itcgae.siga.ws.client;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender.RemoveSoapHeadersInterceptor;

@Component
public class DefaultClientWs {

	/**
	 * Uri del servicio que se va a consumir
	 */
	protected String uriService;
	
	/**
	 * Fichero que contiene el trustStore
	 */
	private File trustStore;

	/**
	 * Password del trustStore
	 */
	private String trustStorePassword;
	
	/**
	 * Bean con la configuración básica del webServiceTemplate
	 */
	@Autowired
	protected WebServiceTemplate webServiceTemplate;
	
	/**
	 * Bean que contiene la información del certificado para llamar a los ws
	 */
	@Autowired
	private HttpComponentsMessageSender httpComponentsMessageSender;
	
	/**
	 * Configura la conexión para llamar al webservice
	 * @throws IOException 
	 * @throws CertificateException 
	 * @throws KeyStoreException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 * @throws Exception 
	 */
	protected void configConnection(String uriService) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
		this.uriService = uriService;
		confWebserviceTemplate();
	}
	
	/**
	 * Configuracion del webserviceTemplate. Se incluye la url del servicio y la configuracion de certificado si lo necesita
	 * @throws IOException 
	 * @throws CertificateException 
	 * @throws KeyStoreException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 * @throws Exception
	 */
	private void confWebserviceTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
		webServiceTemplate.setDefaultUri(this.uriService);
		
		// Si la llamada al servicio necesita certificado hay que incluirlo en el webserviceTemplate
		if (trustStore != null && trustStorePassword != null){
			httpComponentsMessageSender.setHttpClient(httpClient());
		    webServiceTemplate.setMessageSender(httpComponentsMessageSender);			
		}
		
	}
	
	
	private HttpClient httpClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
		return HttpClientBuilder.create().setSSLSocketFactory(sslConnectionSocketFactory())
				.addInterceptorFirst(new RemoveSoapHeadersInterceptor()).build();
	}

	private SSLConnectionSocketFactory sslConnectionSocketFactory() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
		// NoopHostnameVerifier essentially turns hostname verification off as
		// otherwise following error
		// is thrown: java.security.cert.CertificateException: No name matching
		// localhost found
		return new SSLConnectionSocketFactory(sslContext(), NoopHostnameVerifier.INSTANCE);
	}

	private SSLContext sslContext() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
		return SSLContextBuilder.create().loadTrustMaterial(trustStore, trustStorePassword.toCharArray())
				.build();
	}
	
}
