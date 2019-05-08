package org.itcgae.siga.com.services.impl;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javax.mail.internet.MimePart;
import javax.mail.internet.PreencodedMimeBodyPart;
import javax.mail.util.ByteArrayDataSource;

import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.com.DestinatarioItem;
import org.itcgae.siga.DTOs.com.RemitenteDTO;
import org.itcgae.siga.com.services.IEnviosService;
import org.itcgae.siga.commons.constants.SigaConstants;

import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.mappers.CenDireccionesMapper;
import org.itcgae.siga.db.mappers.EnvImagenplantillaMapper;
import org.itcgae.siga.db.mappers.EnvPlantillasenviosMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
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
	CenClienteExtendsMapper _cenClienteExtendsMapper;
	
	@Autowired	
	GenPropertiesMapper _genPropertiesMapper;
	
	@Autowired	
	GenParametrosMapper _genParametrosMapper;
	
	@Autowired
	EnvImagenplantillaMapper _envImagenplantillaMapper;
	
	
	@Autowired
	ClientECOS _clientECOS;
	
	
	@Override
	public void envioMail(String idInstitucion, String idEnvio, RemitenteDTO remitente, List<DestinatarioItem> destinatarios, String asuntoFinal, String cuerpoFinal, List<DatosDocumentoItem> documentosEnvio, boolean envioMasivo) throws Exception {
		
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
		    String portSt = property.getValor();
		    if (portSt != null && !portSt.trim().equals("")) {
		    	sesion.getProperties().put("mail.smtp.port", portSt);
		    }
		    
		    LOGGER.debug("Obtenemos el remitente");
		    
		    //Remitente
		    String from = remitente.getCorreoElectronico();
		    String descFrom = "";

		    if(remitente.getDescripcion()!=null) {
		    	descFrom = remitente.getDescripcion();
		    }else {
		    	descFrom = remitente.getNombre() + " " + remitente.getApellido1();
		    	if(remitente.getApellido2() != null && !"".equalsIgnoreCase(remitente.getApellido2())) {
			    	descFrom = descFrom + " " + remitente.getApellido2();
			    }
		    }

		    
		    
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
                String sAsunto = sustituirEtiquetas(idInstitucion, asuntoFinal, destinatario, SigaConstants.MARCAS_ETIQUETAS_REEMPLAZO_TEXTO_ANTIGUO, idEnvio);
	    	    sAsunto = sustituirEtiquetas(idInstitucion, asuntoFinal, destinatario, SigaConstants.MARCAS_ETIQUETAS_REEMPLAZO_TEXTO, idEnvio);
	    	    
                mensaje.setSubject(sAsunto,"ISO-8859-1");
                mensaje.setHeader("Content-Type","text/html; charset=\"ISO-8859-1\"");
                
                //CUERPO
                String sCuerpo = cuerpoFinal == null ? "": cuerpoFinal;
                sCuerpo = sustituirEtiquetas(idInstitucion, sCuerpo, destinatario, SigaConstants.MARCAS_ETIQUETAS_REEMPLAZO_TEXTO_ANTIGUO, idEnvio);
                sCuerpo = sustituirEtiquetas(idInstitucion, sCuerpo, destinatario, SigaConstants.MARCAS_ETIQUETAS_REEMPLAZO_TEXTO, idEnvio);
                
                
                MimeMultipart mixedMultipart = new MimeMultipart("mixed");
                MimeBodyPart mixedBodyPart = new MimeBodyPart();
                
                MimeMultipart relatedMultipart = new MimeMultipart("related");
                //MimeBodyPart relatedBodyPart = new MimeBodyPart();

                //Buscamos las imagenes antiguas
                /*EnvImagenplantillaExample imagenExample = new EnvImagenplantillaExample();
                imagenExample.createCriteria().andIdinstitucionEqualTo(envio.getIdinstitucion()).andIdplantillaenviosEqualTo(envio.getIdplantillaenvios()).andIdtipoenviosEqualTo(envio.getIdtipoenvios());
                
                List<EnvImagenplantilla> lImagenes = _envImagenplantillaMapper.selectByExample(imagenExample);
                
                if(lImagenes != null && lImagenes.size() > 0) {
                	for(EnvImagenplantilla imagenPlantilla:lImagenes){    	
                		imagenPlantilla.get
    	    	    	EnvImagenPlantillaBean imagenPlantillaBean = imagenPlantilla.getImagenPlantillaBean();
    	    	    	if(imagenPlantillaBean.isEmbebed()){
    	    	    		if(sCuerpo.indexOf(imagenPlantillaBean.getImagenSrcEmbebida("/"))!=-1){
    			    	    	addCIDToMultipart(relatedMultipart,imagenPlantillaBean.getPathImagen(null,File.separator),imagenPlantilla.getNombre());
    		    	    	}
    	    	    	}
    	    	    }
                }*/
                
                
                //Buscamos todas las imagenes para adjuntarlas
	    	    Pattern imgRegExp  = Pattern.compile( "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>" );
	    	    Map<String, String> inlineImage = new HashMap<String, String>();
	    	    final Matcher matcher = imgRegExp.matcher( sCuerpo );
	    	    int i = 0;
	    	    while ( matcher.find() ) {
	    	       String src = matcher.group();
	    	       if ( sCuerpo.indexOf( src ) != -1 ) {
	    	          String srcToken = "src=\"";
	    	          int x = src.indexOf( srcToken );
	    	          int y = src.indexOf( "\"", x + srcToken.length() );
	    	          String srcText = src.substring( x + srcToken.length(), y );
	    	          String cid = "image" + i;
	    	          String newSrc = src.replace( srcText, "cid:" + cid );
	    	          if(srcText.contains("data:image")) {
		    	          inlineImage.put( cid, srcText.split( "," )[1] );
		    	          sCuerpo = sCuerpo.replace( src, newSrc );
		    	          i++;
	    	          
		    	          LOGGER.debug(" CID " + cid + " Image data :: " + srcText.split( "," )[1]);
		    	          MimeBodyPart imagen = addAttachment(cid,srcText.split( "," )[1]);
		    	          imagen.setDisposition(MimePart.INLINE);
		    	          mixedMultipart.addBodyPart(imagen);
	    	          }
	    			
	    	       }
	    	    }
	    	    
	    	    Iterator<Entry<String, String>> it = inlineImage.entrySet().iterator();
	    	    while ( it.hasNext() ) {
	    	       Entry<String, String> pairs = it.next();
	    	       PreencodedMimeBodyPart pmp = new PreencodedMimeBodyPart( "base64" );
	    	       pmp.setHeader( "Content-ID", "<" + pairs.getKey() + ">" );
	    	       pmp.setDisposition( MimeBodyPart.INLINE );
	    	       pmp.setText( pairs.getValue() );
	    	       mixedMultipart.addBodyPart( pmp );
	    	    }
	    	    
	    	    
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
	    	    	messageBodyPart = new MimeBodyPart();
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
        		
        		if(!tr.isConnected()) {
        			if (portSt != null && !portSt.trim().equals("")) {
            			tr.connect(host, Integer.parseInt(portSt), user, pwd);	
            		} else {
            			tr.connect(host, user, pwd);
            		}
        		}        		
        		
        		tr.sendMessage(mensaje, mensaje.getAllRecipients());
        		LOGGER.debug("Enviado");
        		
			}

		    
		    
		} catch(Exception e) {
			LOGGER.error("Error al enviar el email", e);
			throw e;
		}

	}

	@Override
	public String envioSMS(CenDirecciones remitente, String[] destinatarios, Short idInstitucion, String texto, boolean esBuroSMS) {
		
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
				if (remitente == null || remitente.getCorreoelectronico() == null) {
					LOGGER.error("El remitente o su correo electrónico es nulo");
					throw new BusinessException("El remitente o su correo electrónico es nulo");
				}
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
				if(respuesta.indexOf(SigaConstants.KO) > -1) {
					LOGGER.error("No se ha enviado el sms: " + respuesta);
					throw new BusinessException("No se ha enviado el sms", respuesta);	
				}else {
					LOGGER.info("La respuesta de ECOS al enviar ha sido: "+ respuesta);
				}				
			}
			
			
		}catch (Exception e) {
			LOGGER.error("No se ha enviado el sms", e);
			throw new BusinessException("No se ha enviado el sms", e);			
		}
		
		return idSolicidudEcos;
		
	}
	
	@Override
	public void envioMailLetrado(String idInstitucion, String idEnvio, RemitenteDTO remitente,
			List<DestinatarioItem> destinatarios, String asuntoFinal, String cuerpoFinal, boolean envioMasivo) throws Exception {

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
			if (smtpSesion == null || smtpSesion.equals(""))
				smtpSesion = "CorreoSIGA";

			LOGGER.debug("Obtenemos la sesion del correo");

			Session sesion = (Session) javax.rmi.PortableRemoteObject.narrow(ctx.lookup(smtpSesion), Session.class);
			ctx.close();

			if (sesion != null) {
				LOGGER.debug("Sesion de correo obtenida");
			} else {
				LOGGER.debug("Sesion de correo nula");
			}

			tr = sesion.getTransport("smtp");

			// RGG autenticar SMTP
			sesion.getProperties().put("mail.smtp.auth", "true");
			keyProperties.setParametro("mail.smtp.port");
			property = _genPropertiesMapper.selectByPrimaryKey(keyProperties);
			String portSt = property.getValor();
			if (portSt != null && !portSt.trim().equals("")) {
				sesion.getProperties().put("mail.smtp.port", portSt);
			}

			LOGGER.debug("Obtenemos el remitente");

			// Remitente
			String from = remitente.getCorreoElectronico();
			String descFrom = "";

			if (remitente.getDescripcion() != null) {
				descFrom = remitente.getDescripcion();
			} else {
				descFrom = remitente.getNombre() + " " + remitente.getApellido1();
				if (remitente.getApellido2() != null && !"".equalsIgnoreCase(remitente.getApellido2())) {
					descFrom = descFrom + " " + remitente.getApellido2();
				}
			}

			if (destinatarios != null) {
				LOGGER.debug("Longitud de destinatarios: " + destinatarios.size());
			} else {
				LOGGER.debug("No hay destinatarios");
			}

			for (DestinatarioItem destinatario : destinatarios) {

				String sTo = destinatario.getCorreoElectronico();

				LOGGER.debug("Enviamos el email a: " + sTo);
				LOGGER.debug("Enviamos desde: " + from);

				// Se crea un nuevo Mensaje.
				MimeMessage mensaje = new MimeMessage(sesion);
				mensaje.setFrom(new InternetAddress(from, descFrom));
				InternetAddress toInternetAddress = new InternetAddress(sTo);
				mensaje.addRecipient(MimeMessage.RecipientType.TO, toInternetAddress);

	    	    //ASUNTO   
                String sAsunto = sustituirEtiquetas(idInstitucion, asuntoFinal, destinatario, SigaConstants.MARCAS_ETIQUETAS_REEMPLAZO_TEXTO_ANTIGUO, idEnvio);
	    	    sAsunto = sustituirEtiquetas(idInstitucion, asuntoFinal, destinatario, SigaConstants.MARCAS_ETIQUETAS_REEMPLAZO_TEXTO, idEnvio);
	    	    
                mensaje.setSubject(sAsunto,"ISO-8859-1");
                mensaje.setHeader("Content-Type","text/html; charset=\"ISO-8859-1\"");
                
                //CUERPO
                String sCuerpo = cuerpoFinal == null ? "": cuerpoFinal;
                sCuerpo = sustituirEtiquetas(idInstitucion, sCuerpo, destinatario, SigaConstants.MARCAS_ETIQUETAS_REEMPLAZO_TEXTO_ANTIGUO, idEnvio);
                sCuerpo = sustituirEtiquetas(idInstitucion, sCuerpo, destinatario, SigaConstants.MARCAS_ETIQUETAS_REEMPLAZO_TEXTO, idEnvio);

				MimeMultipart mixedMultipart = new MimeMultipart("mixed");
				MimeBodyPart mixedBodyPart = new MimeBodyPart();

				MimeMultipart relatedMultipart = new MimeMultipart("related");
				// MimeBodyPart relatedBodyPart = new MimeBodyPart();

				// alternative message
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(sCuerpo, "text/html");
				relatedMultipart.addBodyPart(messageBodyPart);

				mixedBodyPart.setContent(relatedMultipart);

				mixedMultipart.addBodyPart(mixedBodyPart);

				// Asociamos todo el contenido al mensaje
				mensaje.setContent(mixedMultipart);

				LOGGER.debug("Enviando...");

				if (tr == null) {
					tr = sesion.getTransport("smtp");
				}

				keyProperties.setParametro("mail.smtp.host");
				property = _genPropertiesMapper.selectByPrimaryKey(keyProperties);
				String host = property.getValor();
				keyProperties.setParametro("mail.smtp.user");
				property = _genPropertiesMapper.selectByPrimaryKey(keyProperties);
				String user = property.getValor();
				keyProperties.setParametro("mail.smtp.pwd");
				property = _genPropertiesMapper.selectByPrimaryKey(keyProperties);
				String pwd = property.getValor();

				if (!tr.isConnected()) {
					if (portSt != null && !portSt.trim().equals("")) {
						tr.connect(host, Integer.parseInt(portSt), user, pwd);
					} else {
						tr.connect(host, user, pwd);
					}
				}

				tr.sendMessage(mensaje, mensaje.getAllRecipients());
				LOGGER.debug("Enviado");

			}

		} catch (Exception e) {
			LOGGER.error("Error al enviar el email", e);
			throw e;
		}

	}
	
	private String sustituirEtiquetas(String idInstitucion, String sArchivo, DestinatarioItem destinatario, String marcaInicioFin, String idEnvio){
		String etiqueta = SigaConstants.ETIQUETA_DEST_NOMBRE;

		Pattern pattern = Pattern.compile(marcaInicioFin + etiqueta + marcaInicioFin);
		Matcher matcher = pattern.matcher(sArchivo);
		sArchivo = matcher.replaceAll(destinatario.getNombre());
		
		etiqueta = SigaConstants.ETIQUETA_DEST_APELLIDO1;
		pattern = Pattern.compile(marcaInicioFin + etiqueta + marcaInicioFin);
		matcher = pattern.matcher(sArchivo);
		sArchivo = matcher.replaceAll(destinatario.getApellidos1());
		
		etiqueta = SigaConstants.ETIQUETA_DEST_APELLIDO2;
		pattern = Pattern.compile(marcaInicioFin + etiqueta + marcaInicioFin);
		matcher = pattern.matcher(sArchivo);
		sArchivo = matcher.replaceAll(destinatario.getApellidos2());
		
		etiqueta = SigaConstants.ETIQUETA_DEST_CIFNIF;
		pattern = Pattern.compile(marcaInicioFin + etiqueta + marcaInicioFin);
		matcher = pattern.matcher(sArchivo);
		sArchivo = matcher.replaceAll(destinatario.getNIFCIF());
		
		
		//Obtenemos el tratamiento del destinatario
		etiqueta = SigaConstants.ETIQUETA_DEST_TRATAMIENTO;
		pattern = Pattern.compile(marcaInicioFin + etiqueta + marcaInicioFin);
		matcher = pattern.matcher(sArchivo);
		sArchivo = matcher.replaceAll(getTratamientoDestinatario(idInstitucion, destinatario));
		
		
		etiqueta = SigaConstants.ETIQUETA_FECHAACTUAL;
		pattern = Pattern.compile(marcaInicioFin + etiqueta + marcaInicioFin);
		Date date = Calendar.getInstance().getTime();  
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		String strDate = dateFormat.format(date); 
		matcher = pattern.matcher(sArchivo);		
		sArchivo = matcher.replaceAll(strDate);
		
		etiqueta = SigaConstants.ETIQUETA_IDENVIO;
		pattern = Pattern.compile(marcaInicioFin + etiqueta + marcaInicioFin);
		matcher = pattern.matcher(sArchivo);
		sArchivo = matcher.replaceAll(idEnvio);
		
		return sArchivo;
	}
	
	private String getTratamientoDestinatario(String idInstitucion, DestinatarioItem destinatario) {
		String tratamiento = "";
		int idIdioma = 1;
		
		if(destinatario.getTratamiento() == null){
			List<StringDTO> result = _cenClienteExtendsMapper.getTratamiento(idInstitucion, destinatario.getIdPersona(), idIdioma);			
			if(result != null && result.size() > 0 && result.get(0) != null) {
				tratamiento = result.get(0).getValor();
				destinatario.setTratamiento(tratamiento);
			}
		}		
		
		return destinatario.getTratamiento();
	}
	
	private MimeBodyPart addAttachment(final String fileName, final String fileContent) throws MessagingException {

	    if (fileName == null || fileContent == null) {
	        return null;
	    }

	    LOGGER.debug("addAttachment()");

	    MimeBodyPart filePart = new MimeBodyPart();

	    String data = fileContent;
	    byte[] imgBytes = Base64.getDecoder().decode(data);
	    DataSource ds;
	    ds = new ByteArrayDataSource(imgBytes, "image/*");

	    // "image/*"
	    filePart.setDataHandler(new DataHandler(ds));
	    filePart.setFileName(fileName);

	    LOGGER.debug("addAttachment success !");

	    return filePart;

	}
	
	
	/**
	* Añade al mensaje un cid:name utilizado para guardar las imagenes referenciadas en el HTML de la forma <img src=cid:name />
	* @param cidname identificador que se le da a la imagen. suele ser un string generado aleatoriamente.
	* @param pathname ruta del fichero que almacena la imagen
	* @throws Exception excepcion levantada en caso de error
	*/
	/*
	public void addCIDToMultipart(MimeMultipart multipart,String pathname,String cidname) throws Exception
	{
		DataSource fds = new FileDataSource(pathname);
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setDataHandler(new DataHandler(fds));
		messageBodyPart.setHeader("Content-ID","<"+cidname+">");
		multipart.addBodyPart(messageBodyPart);
	}
	
	public String getPathImagen(EnvImagenplantilla imagen, String directorioImagen,String separador){
   		StringBuffer sPathImagen = null;
   		if(directorioImagen==null)
   			sPathImagen = new StringBuffer(getDirectorioImagen(separador));
   		else
   			sPathImagen = new StringBuffer(directorioImagen);

   		sPathImagen.append(separador);
   		sPathImagen.append(imagen.getNombre());
   		sPathImagen.append(".");
   		sPathImagen.append(imagen.getTipoarchivo());
   		
   		return  sPathImagen.toString();  	
	}
	
	public String getDirectorioImagen(EnvImagenplantilla imagen, String separador){
   		StringBuffer sDirectorioImagen = new StringBuffer(getPathImagenes());
   		sDirectorioImagen.append(getCarpetaImagen(imagen, separador));
   		return  sDirectorioImagen.toString();  		
   		
   	}
	public String getCarpetaImagen(EnvImagenplantilla imagen, String separador){
//		StringBuffer sDirectorioImagen = new StringBuffer( );
//   		sDirectorioImagen.append(File.separator);
//   		sDirectorioImagen.append(imagenPlantilla.getIdInstitucion());
//   		sDirectorioImagen.append(File.separator);
//   		sDirectorioImagen.append(imagenPlantilla.getIdTipoEnvios());
//   		sDirectorioImagen.append(File.separator);
//   		sDirectorioImagen.append(imagenPlantilla.getIdPlantillaEnvios());
		StringBuffer sDirectorioImagen = new StringBuffer( );
		
		
		ReadProperties rp= new ReadProperties(SIGAReferences.RESOURCE_FILES.SIGA);
		String carpetaEnvios = rp.returnProperty("general.path.imagenes.envios");
		sDirectorioImagen.append(carpetaEnvios);
		sDirectorioImagen.append(separador);
		sDirectorioImagen.append(imagen.getIdinstitucion());
		sDirectorioImagen.append(separador);
		sDirectorioImagen.append(imagen.getIdtipoenvios());
		sDirectorioImagen.append(separador);
		sDirectorioImagen.append(imagen.getIdplantillaenvios());
   		return  sDirectorioImagen.toString();		
		
	}
	
	public String getPathImagenes()
	{
		ReadProperties rp= new ReadProperties(SIGAReferences.RESOURCE_FILES.SIGA);
		String pathImagenes = rp.returnProperty("general.path.imagenes");
		
		return pathImagenes;
	}
	*/
   	

}
