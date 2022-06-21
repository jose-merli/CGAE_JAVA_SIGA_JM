package org.itcgae.siga.ws.client;


import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.apache.log4j.Logger;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.xml.transform.StringSource;

import com.altermutua.www.wssiga.GetEstadoColegiadoDocument;
import com.altermutua.www.wssiga.GetEstadoSolicitudDocument;
import com.altermutua.www.wssiga.GetPropuestasDocument;
import com.altermutua.www.wssiga.GetTarifaSolicitudDocument;
import com.altermutua.www.wssiga.SetSolicitudAlterDocument;
import com.altermutua.www.wssiga.WSRespuesta;
import com.altermutua.www.wssiga.impl.GetEstadoColegiadoResponseDocumentImpl;
import com.altermutua.www.wssiga.impl.GetEstadoSolicitudResponseDocumentImpl;
import com.altermutua.www.wssiga.impl.GetPropuestasResponseDocumentImpl;
import com.altermutua.www.wssiga.impl.GetTarifaSolicitudResponseDocumentImpl;
import com.altermutua.www.wssiga.impl.SetSolicitudAlterResponseDocumentImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class ClientAlterMutua {
	
	private Logger LOGGER = Logger.getLogger(ClientAlterMutua.class);
	
	/**
	 * Bean con la configuración básica del webServiceTemplate
	 */
	@Autowired
	protected WebServiceTemplate webServiceTemplate;
	
	/**
	 * Uri del servicio que se va a consumir
	 */
	protected String uriService;
	
	@Autowired
	private GenParametrosMapper _genParametrosMapper;
	
	private static String user;
	private static String pass;
	
	public WSRespuesta getEstadoColegiado (GetEstadoColegiadoDocument request , String uriService)throws Exception{
		
		LOGGER.info("ClientAlterMutua => getEstadoColegiado()");
		webServiceTemplate.setDefaultUri(uriService);
		
		GenParametrosExample exampleUser = new GenParametrosExample();
		exampleUser.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_user");
		
		List<GenParametros> parametroUser = _genParametrosMapper.selectByExample(exampleUser);
		
		GenParametrosExample examplePass = new GenParametrosExample();
		examplePass.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_pass");
		
		List<GenParametros> parametroPass = _genParametrosMapper.selectByExample(examplePass);
		

		if(parametroUser.size() > 0){
			user = parametroUser.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getEstadoColegiado(), no se han encontrado user en gen_parametros");
		}
		
		if(parametroPass.size() > 0){
			pass = parametroPass.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getEstadoColegiado(), no se han encontrado pass en gen_parametros");
		}
		
		GetEstadoColegiadoResponseDocumentImpl responseWS = (GetEstadoColegiadoResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request, new WebServiceMessageCallback() {

	        public void doWithMessage(WebServiceMessage message) {
	            try {
	                SoapMessage soapMessage = (SoapMessage)message;
	                ((SoapMessage)message).setSoapAction("https://ws.altermutua.com/WSSIGA/GetEstadoColegiado");
	                SoapHeader header = soapMessage.getSoapHeader();
	                StringSource headerSource = new StringSource("<wss:Credenciales xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wss=\"https://ws.altermutua.com/WSSIGA\" "
	                		+ "soapenv:actor=\"http://schemas.xmlsoap.org/soap/actor/next\" soapenv:mustUnderstand=\"0\">\n"
	                		+ "<wss:Usuario>" + user + "</wss:Usuario>\n"
	                		+ "<wss:Clave>" + pass + "</wss:Clave>\n"
	                		+ "</wss:Credenciales>");
	                Transformer transformer = TransformerFactory.newInstance().newTransformer();
	                transformer.transform(headerSource, header.getResult());
	            } catch (Exception e) {
	                // exception handling
	            }
	        }
	    });
		
		WSRespuesta response = responseWS.getGetEstadoColegiadoResponse().getGetEstadoColegiadoResult();
		
		
		return response;
		
	}
	
	public JsonNode getEstadoColegiadoAPI(JSONObject jsonObject) throws Exception{
		
		// Inicializamos variables
		ObjectMapper mapper = new ObjectMapper();
		String tokenAPI = null;
		JsonNode resultado;
		
		LOGGER.info("ClientAlterMutua => getEstadoColegiadoAPI()");
		
		LOGGER.info("Obtenemos el token de autenticacion de alter mutua");
		
		// Obtenemos el token de autenticacion mediante una llamada a la API de alter mutua
		tokenAPI = getAuthenticationAPIToken();
		
		// Comprobamos que hemos recibido el token, de lo contrario terminaría aquí el proceso
		if (tokenAPI != null) {
			
			try {
				
				// Realizamos la llamada a la API
				URL url = new URL("https://api-preproduccion.altermutua.com/api/AlterApp/GetMemberStatusSIGA");
				HttpURLConnection http = (HttpURLConnection)url.openConnection();
				http.setRequestMethod("POST");
				http.setDoOutput(true);
				http.setRequestProperty("Content-Type", "application/json");
				http.setRequestProperty("Authorization", "Bearer " + tokenAPI);
				
				String jsonSend = jsonObject.toString();
				
				// Enviamos el JSON a la API
				byte[] out = jsonSend.getBytes(StandardCharsets.UTF_8);
				OutputStream stream = http.getOutputStream();
				stream.write(out);

				LOGGER.info(http.getResponseCode() + " " + http.getResponseMessage());

				LOGGER.info("Obtenemos el estado de colegiado de la API de alter mutua");
				// Obtenemos el JSON de la API
				resultado = mapper.readTree(http.getInputStream());
				
				// Cerramos la conexión con la API
				http.disconnect();
				
				LOGGER.info(resultado);
				
			} catch (Exception e) {
				
				// Se lanza la excepción para que se trate en el método padre
				throw e;
			}
			
			return resultado;
		} else {
			
			LOGGER.error("Usuario/contraseña no válido al conectar con la API de Alter Mutua");
			return null;
		}
	}
	
	public WSRespuesta getEstadoSolicitud (GetEstadoSolicitudDocument request , String uriService)throws Exception{
		
		LOGGER.info("ClientAlterMutua => getEstadoSolicitud()");
		webServiceTemplate.setDefaultUri(uriService);
		
		GenParametrosExample exampleUser = new GenParametrosExample();
		exampleUser.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_user");
		
		List<GenParametros> parametroUser = _genParametrosMapper.selectByExample(exampleUser);
		
		GenParametrosExample examplePass = new GenParametrosExample();
		examplePass.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_pass");
		
		List<GenParametros> parametroPass = _genParametrosMapper.selectByExample(examplePass);
		

		if(parametroUser.size() > 0){
			user = parametroUser.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getEstadoSolicitud(), no se han encontrado user en gen_parametros");
		}
		
		if(parametroPass.size() > 0){
			pass = parametroPass.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getEstadoSolicitud(), no se han encontrado pass en gen_parametros");
		}
		
		GetEstadoSolicitudResponseDocumentImpl responseWS = (GetEstadoSolicitudResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request, new WebServiceMessageCallback() {

	        public void doWithMessage(WebServiceMessage message) {
	            try {
	                SoapMessage soapMessage = (SoapMessage)message;
	                ((SoapMessage)message).setSoapAction("https://ws.altermutua.com/WSSIGA/GetEstadoSolicitud");
	                SoapHeader header = soapMessage.getSoapHeader();
	                StringSource headerSource = new StringSource("<wss:Credenciales xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wss=\"https://ws.altermutua.com/WSSIGA\" "
	                		+ "soapenv:actor=\"http://schemas.xmlsoap.org/soap/actor/next\" soapenv:mustUnderstand=\"0\">\n"
	                		+ "<wss:Usuario>" + user + "</wss:Usuario>\n"
	                		+ "<wss:Clave>" + pass + "</wss:Clave>\n"
	                		+ "</wss:Credenciales>");
	                Transformer transformer = TransformerFactory.newInstance().newTransformer();
	                transformer.transform(headerSource, header.getResult());
	            } catch (Exception e) {
	                // exception handling
	            }
	        }
	    });
		
		WSRespuesta response = responseWS.getGetEstadoSolicitudResponse().getGetEstadoSolicitudResult();
		
		
		return response;
		
	
	}
	
	public JsonNode getEstadoSolicitudAPI(JSONObject jsonObject) throws Exception{
		
		// Inicializamos variables
		ObjectMapper mapper = new ObjectMapper();
		String tokenAPI = null;
		JsonNode resultado;
		
		LOGGER.info("ClientAlterMutua => getEstadoSolicitudAPI()");
		
		LOGGER.info("Obtenemos el token de autenticacion de alter mutua");
		
		// Obtenemos el token de autenticacion mediante una llamada a la API de alter mutua
		tokenAPI = getAuthenticationAPIToken();
		
		// Comprobamos que hemos recibido el token, de lo contrario terminaría aquí el proceso
		if (tokenAPI != null) {
			
			try {
				
				// Realizamos la llamada a la API
				URL url = new URL("https://api-preproduccion.altermutua.com/api/AlterApp/GetRequestStatusSIGA");
				HttpURLConnection http = (HttpURLConnection)url.openConnection();
				http.setRequestMethod("POST");
				http.setDoOutput(true);
				http.setRequestProperty("Content-Type", "application/json");
				http.setRequestProperty("Authorization", "Bearer " + tokenAPI);
				
				String jsonSend = jsonObject.toString();
				
				// Enviamos el JSON a la API
				byte[] out = jsonSend.getBytes(StandardCharsets.UTF_8);
				OutputStream stream = http.getOutputStream();
				stream.write(out);

				LOGGER.info(http.getResponseCode() + " " + http.getResponseMessage());

				LOGGER.info("Obtenemos el estado de solicitud de la API de alter mutua");
				// Obtenemos el JSON de la API
				resultado = mapper.readTree(http.getInputStream());
				
				// Cerramos la conexión con la API
				http.disconnect();
				
				LOGGER.info(resultado);
				
			} catch (Exception e) {
				
				// Se lanza la excepción para que se trate en el método padre
				throw e;
			}
			
			return resultado;
		} else {
			
			LOGGER.error("Usuario/contraseña no válido al conectar con la API de Alter Mutua");
			return null;
		}
	}
	
	public WSRespuesta getPropuestas (GetPropuestasDocument request, String uriService) throws Exception{
		
		LOGGER.info("ClientAlterMutua => getPropuestas()");
		webServiceTemplate.setDefaultUri(uriService);
		
		GenParametrosExample exampleUser = new GenParametrosExample();
		exampleUser.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_user");
		
		List<GenParametros> parametroUser = _genParametrosMapper.selectByExample(exampleUser);
		
		
		
		GenParametrosExample examplePass = new GenParametrosExample();
		examplePass.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_pass");
		
		List<GenParametros> parametroPass = _genParametrosMapper.selectByExample(examplePass);

		if(parametroUser.size() > 0){
			user = parametroUser.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getPropuestas(),no se han encontrado user en gen_parametros");
		}
		
		if(parametroPass.size() > 0){
			pass = parametroPass.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getPropuestas() ,no se ha encontrado pass en gen_parametros");
		}

		webServiceTemplate.afterPropertiesSet();
		
		GetPropuestasResponseDocumentImpl responseWS = (GetPropuestasResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request, new WebServiceMessageCallback() {

	        public void doWithMessage(WebServiceMessage message) {
	            try {
	                SoapMessage soapMessage = (SoapMessage)message;
	                ((SoapMessage)message).setSoapAction("https://ws.altermutua.com/WSSIGA/GetPropuestas");
	                SoapHeader header = soapMessage.getSoapHeader();
	                StringSource headerSource = new StringSource("<wss:Credenciales xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wss=\"https://ws.altermutua.com/WSSIGA\" "
	                		+ "soapenv:actor=\"http://schemas.xmlsoap.org/soap/actor/next\" soapenv:mustUnderstand=\"0\">\n"
	                		+ "<wss:Usuario>" + user + "</wss:Usuario>\n"
	                		+ "<wss:Clave>" + pass + "</wss:Clave>\n"
	                		+ "</wss:Credenciales>");
	                Transformer transformer = TransformerFactory.newInstance().newTransformer();
	                transformer.transform(headerSource, header.getResult());
	            } catch (Exception e) {
	                // exception handling
	            }
	        }
	    });
		
		WSRespuesta response = responseWS.getGetPropuestasResponse().getGetPropuestasResult();
		
		
		return response;
	}
	
	public JsonNode getPropuestasAPI(JSONObject jsonObject) throws Exception{
		
		// Inicializamos variables
		ObjectMapper mapper = new ObjectMapper();
		String tokenAPI = null;
		JsonNode resultado;
		
		LOGGER.info("ClientAlterMutua => getPropuestasAPI()");
		
		LOGGER.info("Obtenemos el token de autenticacion de alter mutua");
		
		// Obtenemos el token de autenticacion mediante una llamada a la API de alter mutua
		tokenAPI = getAuthenticationAPIToken();
		
		// Comprobamos que hemos recibido el token, de lo contrario terminaría aquí el proceso
		if (tokenAPI != null) {
			
			try {
				
				// Realizamos la llamada a la API
				URL url = new URL("https://api-preproduccion.altermutua.com/api/AlterApp/GetProposalsSIGA");
				HttpURLConnection http = (HttpURLConnection)url.openConnection();
				http.setRequestMethod("POST");
				http.setDoOutput(true);
				http.setRequestProperty("Content-Type", "application/json");
				http.setRequestProperty("Authorization", "Bearer " + tokenAPI);
				
				String jsonSend = jsonObject.toString();
				
				// Enviamos el JSON a la API
				byte[] out = jsonSend.getBytes(StandardCharsets.UTF_8);
				OutputStream stream = http.getOutputStream();
				stream.write(out);

				LOGGER.info(http.getResponseCode() + " " + http.getResponseMessage());

				LOGGER.info("Obtenemos las propuestas de la API de alter mutua");
				// Obtenemos el JSON de la API
				resultado = mapper.readTree(http.getInputStream());
				
				// Cerramos la conexión con la API
				http.disconnect();
				
				LOGGER.info(resultado);
				
			} catch (Exception e) {
				
				// Se lanza la excepción para que se trate en el método padre
				throw e;
			}
			
			return resultado;
		} else {
			
			LOGGER.error("Usuario/contraseña no válido al conectar con la API de Alter Mutua");
			return null;
		}
	}
	
	public WSRespuesta getTarifaSolicitud (GetTarifaSolicitudDocument request, String uriService) throws Exception{
		
		LOGGER.info("ClientAlterMutua => getEstadoColegiado()");
		webServiceTemplate.setDefaultUri(uriService);
		
		GenParametrosExample exampleUser = new GenParametrosExample();
		exampleUser.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_user");
		
		List<GenParametros> parametroUser = _genParametrosMapper.selectByExample(exampleUser);
		
		GenParametrosExample examplePass = new GenParametrosExample();
		examplePass.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_pass");
		
		List<GenParametros> parametroPass = _genParametrosMapper.selectByExample(examplePass);
		

		if(parametroUser.size() > 0){
			user = parametroUser.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getEstadoColegiado() no se han encontrado user en gen_parametros");
		}
		
		if(parametroPass.size() > 0){
			pass = parametroPass.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getEstadoColegiado() no se han encontrado pass en gen_parametros");
		}
		
		GetTarifaSolicitudResponseDocumentImpl responseWS = (GetTarifaSolicitudResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request, new WebServiceMessageCallback() {

	        public void doWithMessage(WebServiceMessage message) {
	            try {
	                SoapMessage soapMessage = (SoapMessage)message;
	                ((SoapMessage)message).setSoapAction("https://ws.altermutua.com/WSSIGA/GetTarifaSolicitud");
	                SoapHeader header = soapMessage.getSoapHeader();
	                StringSource headerSource = new StringSource("<wss:Credenciales xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wss=\"https://ws.altermutua.com/WSSIGA\" "
	                		+ "soapenv:actor=\"http://schemas.xmlsoap.org/soap/actor/next\" soapenv:mustUnderstand=\"0\">\n"
	                		+ "<wss:Usuario>" + user + "</wss:Usuario>\n"
	                		+ "<wss:Clave>" + pass + "</wss:Clave>\n"
	                		+ "</wss:Credenciales>");
	                Transformer transformer = TransformerFactory.newInstance().newTransformer();
	                transformer.transform(headerSource, header.getResult());
	            } catch (Exception e) {
	                // exception handling
	            }
	        }
	    });
		
		WSRespuesta response = responseWS.getGetTarifaSolicitudResponse().getGetTarifaSolicitudResult();
		
		
		return response;
	}
	
	public JsonNode getTarifaSolicitudAPI(JSONObject jsonObject) throws Exception {
		
		// Inicializamos variables
		ObjectMapper mapper = new ObjectMapper();
		String tokenAPI = null;
		JsonNode resultado;
		
		LOGGER.info("ClientAlterMutua => getTarifaSolicitudAPI()");
		
		LOGGER.info("Obtenemos el token de autenticacion de alter mutua");
		
		// Obtenemos el token de autenticacion mediante una llamada a la API de alter mutua
		tokenAPI = getAuthenticationAPIToken();
		
		// Comprobamos que hemos recibido el token, de lo contrario terminaría aquí el proceso
		if (tokenAPI != null) {
			
			try {
				
				// Realizamos la llamada a la API
				URL url = new URL("https://api-preproduccion.altermutua.com/api/AlterApp/GetRequestTariffSIGA");
				HttpURLConnection http = (HttpURLConnection)url.openConnection();
				http.setRequestMethod("POST");
				http.setDoOutput(true);
				http.setRequestProperty("Content-Type", "application/json");
				http.setRequestProperty("Authorization", "Bearer " + tokenAPI);
				
				String jsonSend = jsonObject.toString();
				
				// Enviamos el JSON a la API
				byte[] out = jsonSend.getBytes(StandardCharsets.UTF_8);
				OutputStream stream = http.getOutputStream();
				stream.write(out);

				LOGGER.info(http.getResponseCode() + " " + http.getResponseMessage());

				LOGGER.info("Obtenemos la solicitud de la tarifa de la API de alter mutua");
				// Obtenemos el JSON de la API
				resultado = mapper.readTree(http.getInputStream());
				
				// Cerramos la conexión con la API
				http.disconnect();
				
				LOGGER.info(resultado);
				
			} catch (Exception e) {
				
				// Se lanza la excepción para que se trate en el método padre
				throw e;
			}
			
			return resultado;
		} else {
			
			LOGGER.error("Usuario/contraseña no válido al conectar con la API de Alter Mutua");
			return null;
		}
	}
	
	public WSRespuesta setSolicitudAlter(SetSolicitudAlterDocument request, String uriService) throws Exception{
		
		LOGGER.info("ClientAlterMutua => getEstadoColegiado()");
		webServiceTemplate.setDefaultUri(uriService);
		
		GenParametrosExample exampleUser = new GenParametrosExample();
		exampleUser.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_user");
		
		List<GenParametros> parametroUser = _genParametrosMapper.selectByExample(exampleUser);
		
		GenParametrosExample examplePass = new GenParametrosExample();
		examplePass.createCriteria().andIdrecursoEqualTo("administracion.parametro.alterm_pass");
		
		List<GenParametros> parametroPass = _genParametrosMapper.selectByExample(examplePass);

		if(parametroUser.size() > 0){
			user = parametroUser.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getEstadoColegiado(), no se han encontrado user en gen_parametros");
		}
		
		if(parametroPass.size() > 0){
			pass = parametroPass.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getEstadoColegiado(), no se han encontrado pass en gen_parametros");
		}
		
		SetSolicitudAlterResponseDocumentImpl responseWS = (SetSolicitudAlterResponseDocumentImpl)webServiceTemplate.marshalSendAndReceive(uriService, request, new WebServiceMessageCallback() {

	        public void doWithMessage(WebServiceMessage message) {
	            try {
	                SoapMessage soapMessage = (SoapMessage)message;
	                ((SoapMessage)message).setSoapAction("https://ws.altermutua.com/WSSIGA/SetSolicitudAlter");
	                SoapHeader header = soapMessage.getSoapHeader();
	                StringSource headerSource = new StringSource("<wss:Credenciales xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wss=\"https://ws.altermutua.com/WSSIGA\" "
	                		+ "soapenv:actor=\"http://schemas.xmlsoap.org/soap/actor/next\" soapenv:mustUnderstand=\"0\">\n"
	                		+ "<wss:Usuario>" + user + "</wss:Usuario>\n"
	                		+ "<wss:Clave>" + pass + "</wss:Clave>\n"
	                		+ "</wss:Credenciales>");
	                Transformer transformer = TransformerFactory.newInstance().newTransformer();
	                transformer.transform(headerSource, header.getResult());
	            } catch (Exception e) {
	                // exception handling
	            	e.printStackTrace();
	            }
	        }
	    });
		
		WSRespuesta response = responseWS.getSetSolicitudAlterResponse().getSetSolicitudAlterResult();
		
		
		return response;
		
	}
	
	public JsonNode setSolicitudAlterAPI(JSONObject jsonObject) throws Exception {
		
		// Inicializamos variables
		ObjectMapper mapper = new ObjectMapper();
		String tokenAPI = null;
		JsonNode resultado;
		
		LOGGER.info("ClientAlterMutua => setSolicitudAlterAPI()");
		
		LOGGER.info("Obtenemos el token de autenticacion de alter mutua");
		
		// Obtenemos el token de autenticacion mediante una llamada a la API de alter mutua
		tokenAPI = getAuthenticationAPIToken();
		
		// Comprobamos que hemos recibido el token, de lo contrario terminaría aquí el proceso
		if (tokenAPI != null) {
			
			try {
				
				// Realizamos la llamada a la API
				URL url = new URL("https://api-preproduccion.altermutua.com/api/AlterApp/GenerateRequestSIGA");
				HttpURLConnection http = (HttpURLConnection)url.openConnection();
				http.setRequestMethod("POST");
				http.setDoOutput(true);
				http.setRequestProperty("Content-Type", "application/json");
				http.setRequestProperty("Authorization", "Bearer " + tokenAPI);
				
				String jsonSend = jsonObject.toString();
				
				// Enviamos el JSON a la API
				byte[] out = jsonSend.getBytes(StandardCharsets.UTF_8);
				OutputStream stream = http.getOutputStream();
				stream.write(out);

				LOGGER.info(http.getResponseCode() + " " + http.getResponseMessage());

				LOGGER.info("Generamos la solicitud a la API de alter mutua");
				// Obtenemos el JSON de la API
				resultado = mapper.readTree(http.getInputStream());
				
				// Cerramos la conexión con la API
				http.disconnect();
				
				LOGGER.info(resultado);
				
			} catch (Exception e) {
				
				// Se lanza la excepción para que se trate en el método padre
				throw e;
			}
			
			return resultado;
		} else {
			
			LOGGER.error("Usuario/contraseña no válido al conectar con la API de Alter Mutua");
			return null;
		}
	}
	
	/**
	 * Obtiene el token de autenticación de alter mutua mediante una llamada a la API.
	 * El token es necesario para realizar llamadas a la API.
	 * @throws Exception
	 */
	private String getAuthenticationAPIToken() throws Exception {
		
		// Inicializamos variables
		ObjectMapper mapper = new ObjectMapper();
		String token = null;
		
		GenParametrosExample example = new GenParametrosExample();
		example.createCriteria().andIdrecursoEqualTo("administracion.parametro.api_alterm_url");
		List<GenParametros> config = _genParametrosMapper.selectByExample(example);
		
		String uriService = config.get(0).getValor();
		
		// Obtenemos el usuario para la autenticación de la API
		GenParametrosExample exampleUser = new GenParametrosExample();
		exampleUser.createCriteria().andIdrecursoEqualTo("administracion.parametro.api_alterm_user");
		List<GenParametros> parametroUser = _genParametrosMapper.selectByExample(exampleUser);
		
		// Obtenemos la contraseña del usuario
		GenParametrosExample examplePass = new GenParametrosExample();
		examplePass.createCriteria().andIdrecursoEqualTo("administracion.parametro.api_alterm_pass");
		List<GenParametros> parametroPass = _genParametrosMapper.selectByExample(examplePass);
		
		// Comprobamos que existe el usuario en la base de datos
		if(parametroUser.size() > 0){
			user = parametroUser.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getAuthenticationAPIToken(), no se han encontrado user de API alter mutua en gen_parametros");
		}
		
		// Comprobamos que existe la contraseña en la base de datos
		if(parametroPass.size() > 0){
			pass = parametroPass.get(0).getValor();
			
		}else{
			LOGGER.warn("ClientAlterMutua => getAuthenticationAPIToken(), no se han encontrado pass de API alter mutua en gen_parametros");
		}
		
		try {
			
			// Inicializamos la llamada a la API
			URL url = new URL(uriService + "/Authentication/Authenticate");
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("Content-Type", "application/json");

			// Preparamos el JSON que enviaremos a la API
			String jsonSend = new JSONObject()
	                  .put("Username", user)
	                  .put("Password", pass)
	                  .toString();

			// Enviamos el JSON a la API
			byte[] out = jsonSend.getBytes(StandardCharsets.UTF_8);
			OutputStream stream = http.getOutputStream();
			stream.write(out);

			LOGGER.info(http.getResponseCode() + " " + http.getResponseMessage());

			// Obtenemos el token de autenticacion
			JsonNode response = mapper.readTree(http.getInputStream());
			
			// Cerramos la conexión con la API
			http.disconnect();
			
			LOGGER.info(response);
			
			if (response.get("token") != null) {
				token = response.get("token").asText();
			}
			
		} catch (Exception e) {
			
			// Se lanza la excepción para que se trate en el método padre
			throw e;
		}
		
		return token;
	}
	
	public JsonNode APICall(JSONObject jsonObject, String urlAPI) throws Exception{
		
		// Inicializamos variables
		ObjectMapper mapper = new ObjectMapper();
		String tokenAPI = null;
		JsonNode resultado;
		
		LOGGER.info("ClientAlterMutua => APICall()");
		
		LOGGER.info("Obtenemos el token de autenticacion de alter mutua");
		
		// Obtenemos el token de autenticacion mediante una llamada a la API de alter mutua
		tokenAPI = getAuthenticationAPIToken();
		
		// Comprobamos que hemos recibido el token, de lo contrario terminaría aquí el proceso
		if (tokenAPI != null) {
			
			try {
				
				// Realizamos la llamada a la API
				URL url = new URL(urlAPI);
				HttpURLConnection http = (HttpURLConnection)url.openConnection();
				http.setRequestMethod("POST");
				http.setDoOutput(true);
				http.setRequestProperty("Content-Type", "application/json");
				http.setRequestProperty("Authorization", "Bearer " + tokenAPI);
				
				String jsonSend = jsonObject.toString();
				
				// Enviamos el JSON a la API
				byte[] out = jsonSend.getBytes(StandardCharsets.UTF_8);
				OutputStream stream = http.getOutputStream();
				stream.write(out);

				LOGGER.info(http.getResponseCode() + " " + http.getResponseMessage());

				// Obtenemos el JSON de la API
				resultado = mapper.readTree(http.getInputStream());
				
				// Cerramos la conexión con la API
				http.disconnect();
				
				LOGGER.info(resultado);
				
			} catch (Exception e) {
				
				// Se lanza la excepción para que se trate en el método padre
				throw e;
			}
			
			return resultado;
		} else {
			
			LOGGER.error("Usuario/contraseña no válido al conectar con la API de Alter Mutua");
			return null;
		}
	}

}
