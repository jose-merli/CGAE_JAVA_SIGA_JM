package org.itcgae.siga.com.services.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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
import javax.mail.internet.MimePart;

import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.com.DestinatarioItem;
import org.itcgae.siga.DTOs.com.RemitenteDTO;
import org.itcgae.siga.com.services.IEnviosService;
import org.itcgae.siga.commons.constants.SigaConstants;

import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.CenDireccionesKey;
import org.itcgae.siga.db.entities.EnvEnvios;
import org.itcgae.siga.db.entities.EnvEnviosgrupocliente;
import org.itcgae.siga.db.entities.EnvPlantillasenviosKey;
import org.itcgae.siga.db.entities.EnvPlantillasenviosWithBLOBs;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.mappers.CenDireccionesMapper;
import org.itcgae.siga.db.mappers.EnvPlantillasenviosMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
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
	GenParametrosMapper _genParametrosMapper;
	
	
	@Autowired
	ClientECOS _clientECOS;
	
	
	@Override
	public void envioMail(RemitenteDTO remitente, List<DestinatarioItem> destinatarios, String asuntoFinal, String cuerpoFinal, List<DatosDocumentoItem> documentosEnvio, boolean envioMasivo) throws Exception {
		
        Transport tr = null;
		try {
			// OBTENCIÓN DE SERVIDOR DE CORREO
			
			LOGGER.debug("Configuramos el envio de correo");
			Context ctx = new InitialContext();
		    GenPropertiesKey keyProperties = new GenPropertiesKey();
		    keyProperties.setFichero("SIGA");
		    keyProperties.setParametro("mail.smtp.sesion");
		    GenProperties property = _genPropertiesMapper.selectByPrimaryKey(keyProperties);
		    String smtpSesion = property.getValor();
		    if(smtpSesion==null || smtpSesion.equals(""))
		    	smtpSesion = "CorreoSIGA";
		    
		    LOGGER.debug("Obtenemos la sesion del correo");		    
		    
		    Session sesion = (Session)javax.rmi.PortableRemoteObject.narrow(ctx.lookup(smtpSesion), Session.class);
		    ctx.close();
		    
		    if(sesion != null) {
		    	LOGGER.debug("Sesion de correo obtenida");	
		    }else {
		    	LOGGER.debug("Sesion de correo nula");	
		    }
		    
		    tr = sesion.getTransport("smtp");

		    // RGG autenticar SMTP
		    sesion.getProperties().put("mail.smtp.auth", "true");
		    keyProperties.setParametro("mail.smtp.port");
		    property = _genPropertiesMapper.selectByPrimaryKey(keyProperties);
		    sesion.getProperties().put("mail.smtp.port", property.getValor());
		    
		    LOGGER.debug("Obtenemos el remitente");
		    
		    //Remitente
		    String from = remitente.getCorreoElectronico();
		    String descFrom = remitente.getApellido1() + " " + remitente.getApellido2();

		    
		    if(destinatarios != null) {
		    	LOGGER.debug("Longitud de destinatarios: " + destinatarios.size());
		    }else {
		    	LOGGER.debug("No hay destinatarios");
		    }
		    
		    
		    for (DestinatarioItem destinatario : destinatarios) {
		    	
		    	String sTo = destinatario.getCorreoElectronico();

		    	LOGGER.debug("Enviamos el email a: " + sTo);
		    	LOGGER.debug("Enviamos desde: " + from);
		    	
			    //Se crea un nuevo Mensaje.
			    MimeMessage mensaje = new MimeMessage(sesion);
	    	    mensaje.setFrom(new InternetAddress(from,descFrom));
	    	    InternetAddress toInternetAddress = new InternetAddress(sTo);
	    	    mensaje.addRecipient(MimeMessage.RecipientType.TO,toInternetAddress);
	    	    
	    	    
	    	  //ASUNTO
                String sAsunto = asuntoFinal;
                mensaje.setSubject(sAsunto,"ISO-8859-1");
                mensaje.setHeader("Content-Type","text/html; charset=\"ISO-8859-1\"");
                
                //CUERPO
                String sCuerpo = cuerpoFinal == null ? "": cuerpoFinal;
                
                MimeMultipart mixedMultipart = new MimeMultipart("mixed");
                MimeBodyPart mixedBodyPart = new MimeBodyPart();
                
                MimeMultipart relatedMultipart = new MimeMultipart("related");
                //MimeBodyPart relatedBodyPart = new MimeBodyPart();

                //alternative message
                BodyPart messageBodyPart = new MimeBodyPart();
                      messageBodyPart.setContent(sCuerpo, "text/html");
                      relatedMultipart.addBodyPart(messageBodyPart);
                      
                mixedBodyPart.setContent(relatedMultipart);
                
                mixedMultipart.addBodyPart(mixedBodyPart);

	    	    
	    	    //Adjuntamos los informes adjuntos.
	    	    for (DatosDocumentoItem informe : documentosEnvio) {
	    	    	File file = new File(informe.getPathDocumento());
	    	    	DataSource ds = new FileDataSource(file);
	    	    	messageBodyPart.setDataHandler(new DataHandler(ds));
	    	    	messageBodyPart.setFileName(informe.getFileName());
	    	    	messageBodyPart.setDisposition(MimePart.ATTACHMENT);
	    	    	mixedMultipart.addBodyPart(messageBodyPart);
				}
	    	    
	    	    //Asociamos todo el contenido al mensaje
	    	    mensaje.setContent(mixedMultipart);
	    	    
	    	    LOGGER.debug("Enviando...");
	    	    
			    if(tr == null){			    	
	        		tr = sesion.getTransport("smtp");	        		
			    }
			    
			    keyProperties.setParametro("mail.smtp.host");
        		property = _genPropertiesMapper.selectByPrimaryKey(keyProperties);
        		String host =property.getValor();
        		keyProperties.setParametro("mail.smtp.user");
        		property = _genPropertiesMapper.selectByPrimaryKey(keyProperties);
        		String user = property.getValor();
        		keyProperties.setParametro("mail.smtp.pwd");
        		property = _genPropertiesMapper.selectByPrimaryKey(keyProperties);
        		String pwd = property.getValor();
        		tr.connect(host, user, pwd);
        		tr.sendMessage(mensaje, mensaje.getAllRecipients());
        		LOGGER.debug("Enviado");
        		
			}

		    
		    
		} catch(Exception e) {
			LOGGER.error("Error al enviar el email", e);
			throw e;
		}

	}

	@Override
	public String envioSMS(CenDirecciones remitente, String[] destinatarios, Short idInstitucion, String asunto, String texto, boolean esBuroSMS) {
		
		EnviarSMSResponse response = null;
		String respuesta = null;
		
		
		GenParametrosKey keyParam = new GenParametrosKey();
		
		keyParam.setIdinstitucion(Short.parseShort(SigaConstants.IDINSTITUCION_0));
		keyParam.setModulo(SigaConstants.MODULO_ENV);
		keyParam.setParametro(SigaConstants.SMS_URL_SERVICE);
		
		GenParametros property = _genParametrosMapper.selectByPrimaryKey(keyParam);
		String uriService = property.getValor();
		String idSolicidudEcos = "";
		try {
			
			LOGGER.debug("EnviosServiceImpl.envioSMS :: Se envia SMS a: " + destinatarios);
			
			//Instanciamos la peticion
			SolicitudEnvioSMS request = SolicitudEnvioSMS.Factory.newInstance();
			
			keyParam.setParametro(SigaConstants.SMS_CLIENTE_ECOS);
			property = _genParametrosMapper.selectByPrimaryKey(keyParam);
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
