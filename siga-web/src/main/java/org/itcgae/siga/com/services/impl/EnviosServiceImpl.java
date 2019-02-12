package org.itcgae.siga.com.services.impl;

import java.io.UnsupportedEncodingException;


import javax.mail.BodyPart;
import javax.mail.MessagingException;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.itcgae.siga.com.services.IEnviosService;
import org.itcgae.siga.commons.constants.SigaConstants;

import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenDireccionesKey;
import org.itcgae.siga.db.entities.EnvEnvios;
import org.itcgae.siga.db.entities.EnvPlantillasenviosKey;
import org.itcgae.siga.db.entities.EnvPlantillasenviosWithBLOBs;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.mappers.CenDireccionesMapper;
import org.itcgae.siga.db.mappers.EnvPlantillasenviosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecos.ws.solicitarenvio.SolicitudEnvioSMS;

import org.apache.log4j.Logger;

import service.serviciosecos.EnviarSMSDocument.EnviarSMS;
import service.serviciosecos.EnviarSMSDocument;
import service.serviciosecos.EnviarSMSResponseDocument;
import service.serviciosecos.EnviarSMSResponseDocument.EnviarSMSResponse;
import org.itcgae.siga.ws.client.ClientECOS;

@Component
public class EnviosServiceImpl implements IEnviosService{

	Logger LOGGER = Logger.getLogger(EnviosServiceImpl.class);	
	
	@Autowired	
	EnvPlantillasenviosMapper _envPlantillasenviosMapper;
	
	@Autowired	
	CenDireccionesMapper _cenDireccionesMapper;
	
	@Autowired	
	GenPropertiesMapper _genPropertiesMapper;
	
	@Autowired
	ClientECOS _clientECOS;
	
	
	@Override
	public void envioMail(EnvEnvios envio) {
		
        Transport tr = null;
		try {
			// OBTENCIÓN DE SERVIDOR DE CORREO
			Context ctx = new InitialContext();
		    GenPropertiesKey keyProperties = new GenPropertiesKey();
		    keyProperties.setFichero("SIGA");
		    keyProperties.setParametro("mail.smtp.sesion");
		    GenProperties property = _genPropertiesMapper.selectByPrimaryKey(keyProperties);
		    String smtpSesion = property.getParametro();
		    if(smtpSesion==null || smtpSesion.equals(""))
		    	smtpSesion = "CorreoSIGA";
		    Session sesion = (Session)javax.rmi.PortableRemoteObject.narrow(ctx.lookup(smtpSesion), Session.class);
		    ctx.close();
		    
		    tr = sesion.getTransport("smtp");

		    // RGG autenticar SMTP
		    sesion.getProperties().put("mail.smtp.auth", "true");
		    keyProperties.setParametro("mail.smtp.port");
		    property = _genPropertiesMapper.selectByPrimaryKey(keyProperties);
		    sesion.getProperties().put("mail.smtp.port", property.getParametro());
		    
		    
		    //obtenemos remitente
		    EnvPlantillasenviosKey keyPlantilla = new EnvPlantillasenviosKey();
		    keyPlantilla.setIdinstitucion(envio.getIdinstitucion());
		    keyPlantilla.setIdplantillaenvios(envio.getIdplantillaenvios());
		    keyPlantilla.setIdtipoenvios(envio.getIdtipoenvios());
		    EnvPlantillasenviosWithBLOBs plantilla = _envPlantillasenviosMapper.selectByPrimaryKey(keyPlantilla);
		    CenDirecciones remitente = null;
		    if(plantilla.getIdpersona() != null){
		    	CenDireccionesKey keyDir = new CenDireccionesKey();
		    	keyDir.setIddireccion(plantilla.getIddireccion());
		    	keyDir.setIdinstitucion(envio.getIdinstitucion());
		    	keyDir.setIdpersona(plantilla.getIdpersona());
		    	remitente = _cenDireccionesMapper.selectByPrimaryKey(keyDir);
		    	
		    }else{
		    	LOGGER.error("La plantilla no tiene un remitente");
		    	throw new BusinessException("La plantilla no tiene un remitente");
		    }
		    String from = remitente.getCorreoelectronico();
		    String descFrom = "Nombre y apellidos";
		    
		    //TODO: destinatarios
		    //Obtenemos destinatarios
		    String sTo = "bherrero@deloitte.es";
		    
		    //si viene desde envios masivos buscamos por las etiquetas de envio
		    //CenGruposclienteCliente etiquetas = 
		    
		    
		    //Se crea un nuevo Mensaje.
		    MimeMessage mensaje = new MimeMessage(sesion);
    	    mensaje.setFrom(new InternetAddress(from,descFrom));
    	    InternetAddress toInternetAddress = new InternetAddress(sTo);
    	    mensaje.addRecipient(MimeMessage.RecipientType.TO,toInternetAddress);
    	    
    	    
    	    //ASUNTO
    	    String sAsunto = plantilla.getAsunto();
    	    mensaje.setSubject(sAsunto,"ISO-8859-1");
    	    mensaje.setHeader("Content-Type","text/html; charset=\"ISO-8859-1\"");
    	    
    	    //CUERPO
    	    String sCuerpo = plantilla.getCuerpo() == null ? "": plantilla.getCuerpo();
    	    

    	    MimeMultipart mixedMultipart = new MimeMultipart("mixed");
    	    MimeBodyPart mixedBodyPart = new MimeBodyPart();
    	    MimeMultipart relatedMultipart = new MimeMultipart("related");

    		// first part (the html)
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(sCuerpo, "text/html");
			relatedMultipart.addBodyPart(messageBodyPart);


    	    //Hierarchy
    	    mixedBodyPart.setContent(relatedMultipart);
    	    mixedMultipart.addBodyPart(mixedBodyPart);
    	    
    	    
    	    //TODO: adjuntar ficheros al envio
		    
    	    
		    if(tr == null){
        		tr = sesion.getTransport("smtp");
        		keyProperties.setParametro("mail.smtp.host");
        		property = _genPropertiesMapper.selectByPrimaryKey(keyProperties);
        		String host =property.getParametro();
        		keyProperties.setParametro("mail.smtp.user");
        		property = _genPropertiesMapper.selectByPrimaryKey(keyProperties);
        		String user = property.getParametro();
        		keyProperties.setParametro("mail.smtp.pwd");
        		property = _genPropertiesMapper.selectByPrimaryKey(keyProperties);
        		String pwd = property.getParametro();
        		tr.connect(host, user, pwd);
        		tr.sendMessage(mensaje, mensaje.getAllRecipients());
		    }
		    
		    
		} catch (NamingException | UnsupportedEncodingException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public String envioSMS(CenDirecciones remitente, String[] destinatarios, Short idInstitucion, String asunto, String texto, boolean esBuroSMS) {
		
		EnviarSMSResponse response = null;
		String respuesta = null;
		
		
		GenPropertiesKey keyProp = new GenPropertiesKey();
		keyProp.setFichero("SIGA");
		keyProp.setParametro("ecos.url.conexion");
		GenProperties property = _genPropertiesMapper.selectByPrimaryKey(keyProp);
		String uriService = property.getValor();
		String idSolicidudEcos = "";
		try {
			
			LOGGER.debug("EnviosServiceImpl.envioSMS :: Se envia SMS a: " + destinatarios);
			
			//Instanciamos la peticion
			SolicitudEnvioSMS request = SolicitudEnvioSMS.Factory.newInstance();
			
			keyProp.setParametro("ecos.url.conexion");
			property = _genPropertiesMapper.selectByPrimaryKey(keyProp);
			String idECOS = property.getValor();
			request.setIdClienteECOS(idECOS);
			
			if (idInstitucion == null) {
				String error = "Para enviar un correo se debe informar del colegio";
				LOGGER.error(error);
				throw new BusinessException(error);
			}
			
			request.setIdColegio(idInstitucion.toString());	
			
			if(destinatarios == null || destinatarios.length == 0){
				LOGGER.error("El destinatario no puede ser nulo para enviar un sms");
				throw new BusinessException("El destinatario no puede ser nulo para enviar un sms");
			}
			
			//Si no viene el prefijo se lo añadimos
			for(int i = 0; i< destinatarios.length; i++){
				if(destinatarios[i].length() == 9){
					destinatarios[i] =  SigaConstants.ECOS_PREFIJO_ESPANA + destinatarios[i];
				}
			}	
			
			//Fijamos los destinatarios
			request.setListaTOsArray(destinatarios);
			
			request.setTexto(texto);
			request.setIsProgramado(false);
			
			//Si es BuroSMS
			request.setIsSMSCertificado(esBuroSMS);
			
			if(esBuroSMS){
				//TODO:ver que correos poner
				request.setEmail(remitente.getCorreoelectronico());
			}
			
			EnviarSMSResponseDocument responseDoc = EnviarSMSResponseDocument.Factory.newInstance();			
			EnviarSMS sms = EnviarSMS.Factory.newInstance();
			sms.setEnviarSMSRequest(request);
			
			EnviarSMSDocument requestDoc = EnviarSMSDocument.Factory.newInstance();
			requestDoc.setEnviarSMS(sms);
			
			try {
				responseDoc = _clientECOS.enviarSMS(uriService, requestDoc);	
				response = responseDoc.getEnviarSMSResponse();
				idSolicidudEcos = response.getEnviarSMSResponse().getIdSolicitud();
				LOGGER.error("El SMS se ha enviado con idSolicitud: "+idSolicidudEcos+"");
			} catch (Exception e) {
				LOGGER.error("Error en la comunicacion con ECOS", e);
				throw new BusinessException("Error en la comunicacion con ECOS", e);
			}
			
			if (response.getEnviarSMSResponse() !=null){
				respuesta = response.getEnviarSMSResponse().getResultado();
				LOGGER.info("La respuesta de ECOS al enviar ha sido: "+ respuesta);
			}
			
			
		}catch (Exception e) {
			LOGGER.error("No se ha enviado el sms", e);
			throw new BusinessException("No se ha enviado el sms", e);			
		}
		
		return idSolicidudEcos;
		
	}


	@Override
	public void envioCorreoOrdinario() {
		// TODO Auto-generated method stub
		
	}

}
