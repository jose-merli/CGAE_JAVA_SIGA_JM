package org.itcgae.siga.ws.client;


import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;


import org.apache.log4j.Logger;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosExample;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
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
	                ((SoapMessage)message).setSoapAction("https://preproduccion.altermutua.com/WSSIGATEST/GetEstadoColegiado");
	                SoapHeader header = soapMessage.getSoapHeader();
	                StringSource headerSource = new StringSource("<wss:Credenciales xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wss=\"https://preproduccion.altermutua.com/WSSIGATEST\" "
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
	                ((SoapMessage)message).setSoapAction("https://www.altermutua.com/WSSIGA/GetEstadoSolicitud");
	                SoapHeader header = soapMessage.getSoapHeader();
	                StringSource headerSource = new StringSource("<wss:Credenciales xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wss=\"https://www.altermutua.com/WSSIGA\" "
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
	                ((SoapMessage)message).setSoapAction("https://www.altermutua.com/WSSIGA/GetPropuestas");
	                SoapHeader header = soapMessage.getSoapHeader();
	                StringSource headerSource = new StringSource("<wss:Credenciales xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wss=\"https://www.altermutua.com/WSSIGA\" "
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
	                ((SoapMessage)message).setSoapAction("https://www.altermutua.com/WSSIGA/GetTarifaSolicitud");
	                SoapHeader header = soapMessage.getSoapHeader();
	                StringSource headerSource = new StringSource("<wss:Credenciales xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wss=\"https://preproduccion.altermutua.com/WSSIGATEST\" "
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
	                ((SoapMessage)message).setSoapAction("https://www.altermutua.com/WSSIGA/SetSolicitudAlter");
	                SoapHeader header = soapMessage.getSoapHeader();
	                StringSource headerSource = new StringSource("<wss:Credenciales xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wss=\"https://www.altermutua.com/WSSIGA\" "
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

}
