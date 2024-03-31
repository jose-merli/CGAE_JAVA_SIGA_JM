package org.itcgae.siga.com.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import javax.mail.internet.PreencodedMimeBodyPart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.cen.StringDTO;
import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.com.DestinatarioItem;
import org.itcgae.siga.DTOs.com.RemitenteDTO;
import org.itcgae.siga.com.services.IEnviosMasivosService;
import org.itcgae.siga.com.services.IEnviosService;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.SIGAServicesHelper;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.EnvEnvios;
import org.itcgae.siga.db.entities.EnvEnviosKey;
import org.itcgae.siga.db.entities.GenParametros;
import org.itcgae.siga.db.entities.GenParametrosKey;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.mappers.CenDireccionesMapper;
import org.itcgae.siga.db.mappers.EnvEnviosMapper;
import org.itcgae.siga.db.mappers.EnvImagenplantillaMapper;
import org.itcgae.siga.db.mappers.GenParametrosMapper;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.db.services.cen.mappers.CenClienteExtendsMapper;
import org.itcgae.siga.db.services.com.mappers.EnvPlantillaEnviosExtendsMapper;
import org.itcgae.siga.exception.BusinessException;
import org.itcgae.siga.ws.client.ClientECOS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecos.ws.solicitarenvio.SolicitudEnvioSMS;

import org.springframework.stereotype.Service;
import service.serviciosecos.EnviarSMSDocument;
import service.serviciosecos.EnviarSMSDocument.EnviarSMS;
import service.serviciosecos.EnviarSMSResponseDocument;
import service.serviciosecos.EnviarSMSResponseDocument.EnviarSMSResponse;

@Service
public class EnviosServiceImpl implements IEnviosService{

    Logger LOGGER = Logger.getLogger(EnviosServiceImpl.class);    
    
    @Autowired    
    EnvPlantillaEnviosExtendsMapper _envPlantillasenviosMapper;
    
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
    IEnviosMasivosService _enviosMasivosService;
    
    @Autowired
    EnvEnviosMapper envEnviosMapper;
    
    
    @Autowired
    ClientECOS _clientECOS;
    
    
    
    
    @Override
    public Short envioMail(String idInstitucion, String idEnvio, RemitenteDTO remitente, List<DestinatarioItem> destinatarios, String asuntoFinal, String cuerpoFinal, List<DatosDocumentoItem> documentosEnvio, boolean envioMasivo) throws Exception {
        
    	Short idEstadoEnvio = SigaConstants.ENVIO_PROCESADO;
    	
    	GenParametrosKey keyParam = new GenParametrosKey();
    	GenParametros parametro = new GenParametros();
    	String remitenteFromPorDefecto = null;
    	
        try {
            
            EnvEnviosKey envEnviosKey = new EnvEnviosKey();
            envEnviosKey.setIdinstitucion(Short.valueOf(idInstitucion));
            envEnviosKey.setIdenvio(Long.valueOf(idEnvio));;
            EnvEnvios envEnvio = envEnviosMapper.selectByPrimaryKey(envEnviosKey);
            
            if (envEnvio == null) {
                LOGGER.error("No se ha encontrado el envío");
                throw new BusinessException("No se ha encontrado el envío");
            }
            
            
            // OBTENCIÓN DE PARÁMETRO REMITENTE POR DEFECTO DE LA INSTITUCIÓN ACTUAL
            
	        keyParam.setIdinstitucion(Short.valueOf(idInstitucion));
	        keyParam.setModulo(SigaConstants.MODULO_CENSO);
	        keyParam.setParametro(SigaConstants.DEFAULT_EMAIL_FROM);
	        
	        parametro = _genParametrosMapper.selectByPrimaryKey(keyParam);
	        
	        // COMPROBACIÓN PARÁMETRO OBTENIDO
	        
	        if (parametro != null && parametro.getValor() != null) {
	        	remitenteFromPorDefecto = parametro.getValor();
	        } else {
	        	
	        	// OBTENCIÓN DE PARÁMETRO REMITENTE POR DEFECTO DE LA INSTITUCIÓN POR DEFECTO
	        	
	        	keyParam.setIdinstitucion(Short.parseShort(SigaConstants.IDINSTITUCION_0));
		        keyParam.setModulo(SigaConstants.MODULO_CENSO);
		        keyParam.setParametro(SigaConstants.DEFAULT_EMAIL_FROM);
		        
		        parametro = _genParametrosMapper.selectByPrimaryKey(keyParam);
		        
		        if (parametro != null) {
		        	remitenteFromPorDefecto = parametro.getValor();
		        }
	        }
	        
	        
            // OBTENCIÓN DE SERVIDOR DE CORREO
            
            LOGGER.debug("Configuramos el envio de correo");
            GenPropertiesKey keyProperties = new GenPropertiesKey();
            keyProperties.setFichero("SIGA");
            keyProperties.setParametro("mail.smtp.sesion");
            GenProperties property = _genPropertiesMapper.selectByPrimaryKey(keyProperties);
            String smtpSesion = property.getValor();
            if(smtpSesion==null || smtpSesion.equals(""))
                smtpSesion = "CorreoSIGA";
            
            LOGGER.debug("Obtenemos la sesion del correo");     
            
//            sesion.getProperties().put("mail.mime.allowencodedmessages", "true");  
            
            LOGGER.debug("Obtenemos el remitente");
            
            //Remitente
            String from = remitente.getCorreoElectronico();
            String defaultFrom = remitenteFromPorDefecto;
            
            String descFrom = "";

            if(remitente.getDescripcion()!=null) {
                descFrom = remitente.getDescripcion();
            }else {
                descFrom = remitente.getNombre() + " " + remitente.getApellido1();
                if(remitente.getApellido2() != null && !"".equalsIgnoreCase(remitente.getApellido2())) {
                    descFrom = descFrom + " " + remitente.getApellido2();
                }
            }
            
            if(destinatarios == null) {                
                LOGGER.debug("No hay destinatarios");
            } else {
                LOGGER.debug("Longitud de destinatarios: " + destinatarios.size());
            
                Sheet sheet = creaLogExcel(envEnvio);
            
                destinatarios = limpiaCorreosDuplicados(destinatarios);
                
                keyProperties.setParametro("mail.smtp.host");
                property = _genPropertiesMapper.selectByPrimaryKey(keyProperties);
                String host =property.getValor();
                keyProperties.setParametro("mail.smtp.user");
                property = _genPropertiesMapper.selectByPrimaryKey(keyProperties);
                String user = property.getValor();
                keyProperties.setParametro("mail.smtp.pwd");
                property = _genPropertiesMapper.selectByPrimaryKey(keyProperties);
                String pwd = property.getValor();
                
             // RGG autenticar SMTP
	            keyProperties.setParametro("mail.smtp.port");
	            property = _genPropertiesMapper.selectByPrimaryKey(keyProperties);
	            String portSt = property.getValor();
	            
	            
           	 	Properties prop = new Properties();
	            prop.put("mail.smtp.host", host);
	            if (portSt != null && !portSt.trim().equals("")) {
	            	prop.put("mail.smtp.port", portSt);
	            }
	            
	            prop.put("mail.smtp.user", user);
	            prop.put("mail.smtp.pwd", pwd);
	            prop.put("mail.smtp.auth", "true");
	            
	            Session sesion = Session.getInstance(prop,
	                    new javax.mail.Authenticator() {
	                       protected PasswordAuthentication getPasswordAuthentication() {
	                          return new PasswordAuthentication(
	                             user, pwd);
	                       }
	                    });
	            
	            String documentosAdjuntos = null;
                
                
	            for (DestinatarioItem destinatario : destinatarios) {
                    try {
                    
                        String sTo = destinatario.getCorreoElectronico();
                        
                        if (from == null || from.trim().equals("")) {
                            throw new BusinessException("ERROR: El remitente no tiene dirección de correo electrónico");
                        }
                        
                        if (sTo == null || sTo.trim().equals("")) {
                        	throw new BusinessException("ERROR: El destinatario no tiene dirección de correo electrónico");
                        }
                       
                        //public static final 
                       Pattern EXPRESION_REGULAR_PATTERN_MAIL = Pattern.compile(SigaConstants.EXPRESION_REGULAR_MAIL2, Pattern.CASE_INSENSITIVE);
                       if (!EXPRESION_REGULAR_PATTERN_MAIL.matcher(sTo).matches()) {
                    	   throw new BusinessException("ERROR: El destinatario no tiene dirección de correo electrónico válida");
                       }
        
                        LOGGER.debug("Enviamos el email a: " + sTo);
                        LOGGER.debug("Enviamos desde: " + from);
                        
                        //Se crea un nuevo Mensaje.
                        MimeMessage mensaje = new MimeMessage(sesion);
                        
                        //Si se ha asignado un correo por defecto y es válido
                        if (defaultFrom != null && SIGAServicesHelper.isValidEmailAddress(defaultFrom)) {
                        	
                        	mensaje.setFrom(new InternetAddress(defaultFrom,descFrom));
    						mensaje.setReplyTo(new javax.mail.Address[]
    								{
    									    new javax.mail.internet.InternetAddress(from, descFrom)
    									});
                        } else {
                        	
	                        mensaje.setFrom(new InternetAddress(from,descFrom));
							mensaje.setReplyTo(new javax.mail.Address[]
									{
										    new javax.mail.internet.InternetAddress(from)
										});
                        }
                        
						mensaje.setSender(new InternetAddress(from,descFrom));
						
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
                        
                        sCuerpo = adjuntaImagenBase64(mixedMultipart, sCuerpo);
                        
                        documentosAdjuntos = adjuntaDocumentos(mixedMultipart, documentosEnvio, idEnvio, idInstitucion);
                        
                        //alternative message
                        BodyPart messageBodyPart = new MimeBodyPart();
                        messageBodyPart.setContent(sCuerpo, "text/html");
                        relatedMultipart.addBodyPart(messageBodyPart);    
                              
                        mixedBodyPart.setContent(relatedMultipart);
                        mixedMultipart.addBodyPart(mixedBodyPart);
                        
                        
                        //Asociamos todo el contenido al mensaje
                        mensaje.setContent(mixedMultipart);
                        mensaje.setSentDate(new Date());
                        
                        LOGGER.debug("Enviando...");
                          

                        Transport.send(mensaje, mensaje.getAllRecipients());
                        LOGGER.debug("Enviado");
                        
                        insertaExcelRow(envEnvio, sheet, from, descFrom, documentosAdjuntos, destinatario, "OK");
                    } catch (BusinessException e) {
                        LOGGER.error(e);
                        idEstadoEnvio = SigaConstants.ENVIO_PROCESADO_CON_ERRORES;
                        insertaExcelRow(envEnvio, sheet, from, descFrom, documentosAdjuntos, destinatario, e.getMessage());
                    } catch(Exception e) {
                        LOGGER.error("Error al enviar el email", e);
                        idEstadoEnvio = SigaConstants.ENVIO_PROCESADO_CON_ERRORES;
                        insertaExcelRow(envEnvio, sheet, from, descFrom, documentosAdjuntos, destinatario, "ERROR");
                    }
                }
                
                writeCloseLogFile(envEnvio.getIdinstitucion(), envEnvio.getIdenvio(), sheet);
                
            }

            return idEstadoEnvio;
            
        } catch(Exception e) {
            LOGGER.error("Error al enviar el email", e);
            throw e;
        }

    }

    private void writeCloseLogFile(Short idinstitucion, Long idenvio, Sheet sheet) {
    	if (sheet != null) {
    		
    		try {
    		
	    		/*for(int i = 0; i < SigaConstants.columnsExcelLogEnvios.length; i++) {
	                sheet.autoSizeColumn(i);
	            }*/
	    		
	            File file = _enviosMasivosService.getPathFicheroLOGEnvioMasivo(idinstitucion, idenvio);
	            
	            FileOutputStream fileOut = new FileOutputStream(file);
	            
	            LOGGER.debug("Cerrando fichero... " + file.getAbsoluteFile());
				
	            sheet.getWorkbook().write(fileOut);
	            fileOut.flush();
	            fileOut.close();
	            sheet.getWorkbook().close();
	            
	            LOGGER.debug("Fichero creado... " + file.getAbsoluteFile());
            
			} catch (Exception e) {
				LOGGER.error(e);
			}
    	}
	}

	private void insertaExcelRow(EnvEnvios envEnvio, Sheet sheet, String from, String descFrom, String documentosAdjuntos, DestinatarioItem destinatarioItem, String mensaje) {
		
        if (sheet != null) {
            Row row = sheet.createRow(sheet.getLastRowNum()+1);
            
            
            CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
    	    CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
    	    short dateFormat = createHelper.createDataFormat().getFormat(SigaConstants.DATEST_FORMAT_MIN_SEC);
    	    cellStyle.setDataFormat(dateFormat);
    	    
            for(int i = 0; i < SigaConstants.columnsExcelLogEnvios.length; i++) {
                Cell cell = row.createCell(i);
                switch (i) {
                case 0://ENVIO
                    cell.setCellValue(envEnvio.getIdenvio());
                    break;
                case 1://DESCRIPCION
                    cell.setCellValue(envEnvio.getDescripcion());
                    break;
                case 2://FECHA ENVÍO
                    cell.setCellValue(Calendar.getInstance());
            	    cell.setCellStyle(cellStyle);
                    break;
                case 3://REMITENTE
                    cell.setCellValue(descFrom);
                    break;
                case 4://CORREO REMITENTE
                	cell.setCellValue(from);
                	break;
                case 5://NIF/CIF
                    cell.setCellValue(destinatarioItem.getNIFCIF());
                    break;
                case 6://NOMBRE
                    cell.setCellValue(destinatarioItem.getNombre());
                    break;
                case 7://APELLIDO 1
                    cell.setCellValue(destinatarioItem.getApellidos1());
                    break;
                case 8://APELLIDO 2
                    cell.setCellValue(destinatarioItem.getApellidos2());
                    break;
                case 9://MOVIL
                    cell.setCellValue(destinatarioItem.getMovil());
                    break;
                case 10://CORREO ELECTRONICO
                    cell.setCellValue(destinatarioItem.getCorreoElectronico());
                    break;
                case 11://MENSAJE    
                    cell.setCellValue(mensaje);
                    break;
                case 12://DOCUMENTOS
                	if (documentosAdjuntos != null) {
                		cell.setCellValue(documentosAdjuntos);	
                	}
                    break;
                default:
                    break;
                }
                
            }
            
        }
        
        
    }


    private Sheet creaLogExcel(EnvEnvios envEnvio) throws IOException, InvalidFormatException {
        Sheet sheet = null;
        if (envEnvio != null) {
        	Workbook workbook = null;
        	File file = _enviosMasivosService.getPathFicheroLOGEnvioMasivo(envEnvio.getIdinstitucion(), envEnvio.getIdenvio());
        	
        	if (file != null && file.exists()) {
        		LOGGER.info("El fichero de log ya existe por tanto cargamos el excel: " + file.getAbsolutePath());
        		FileInputStream fip = new FileInputStream(file);
        		workbook = WorkbookFactory.create(fip);
        		sheet = workbook.getSheetAt(0);
        	} else {
        		LOGGER.info("Creamos un nuevo excel para el log");
        		workbook = new XSSFWorkbook();
        		sheet = workbook.createSheet();
                Row headerRow = sheet.createRow(0);
                
                int index = 0;
                
                for(String value : SigaConstants.columnsExcelLogEnvios) {
                    Cell cell = headerRow.createCell(index++);
                    cell.setCellValue(value);
//                    cell.setCellStyle(headerCellStyle);
                }
        	}
            
        }
        return sheet;
        
        
    }
    
    public Sheet creaLogGenericoExcel(EnvEnvios envEnvio) throws IOException, InvalidFormatException {
        Sheet sheet = null;
        if (envEnvio != null) {
        	Workbook workbook = null;
        	File file = _enviosMasivosService.getPathFicheroLOGEnvioMasivo(envEnvio.getIdinstitucion(), envEnvio.getIdenvio());
        	
        	if (file != null && file.exists()) {
        		LOGGER.info("El fichero de log ya existe por tanto cargamos el excel: " + file.getAbsolutePath());
        		FileInputStream fip = new FileInputStream(file);
        		workbook = WorkbookFactory.create(fip);
        		sheet = workbook.getSheetAt(0);
        	} else {
        		LOGGER.info("Creamos un nuevo excel para el log");
        		workbook = new XSSFWorkbook();
        		sheet = workbook.createSheet();
                Row headerRow = sheet.createRow(0);
                
                int index = 0;
                
                for(String value : SigaConstants.columnsExcelLogEnvios) {
                    Cell cell = headerRow.createCell(index++);
                    cell.setCellValue(value);
                }
        	}
            
        }
        return sheet;
    }
    
    public void insertaExcelRowLogGenerico(EnvEnvios envEnvio, Sheet sheet, String mensaje) {
		
        if (sheet != null) {
            Row row = sheet.createRow(sheet.getLastRowNum()+1);
            
            
            CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
    	    CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
    	    short dateFormat = createHelper.createDataFormat().getFormat(SigaConstants.DATEST_FORMAT_MIN_SEC);
    	    cellStyle.setDataFormat(dateFormat);
    	    
            for(int i = 0; i < SigaConstants.columnsExcelLogEnvios.length; i++) {
                Cell cell = row.createCell(i);
                switch (i) {
                case 0://ENVIO
                    cell.setCellValue(envEnvio.getIdenvio());
                    break;
                case 1://DESCRIPCION
                    cell.setCellValue(envEnvio.getDescripcion());
                    break;
                case 2://FECHA ENVÍO
                    cell.setCellValue(Calendar.getInstance());
            	    cell.setCellStyle(cellStyle);
                    break;
                case 3://REMITENTE
                    cell.setCellValue("");
                    break;
                case 4://CORREO REMITENTE
                	cell.setCellValue("");
                	break;
                case 5://NIF/CIF
                    cell.setCellValue("");
                    break;
                case 6://NOMBRE
                    cell.setCellValue("");
                    break;
                case 7://APELLIDO 1
                    cell.setCellValue("");
                    break;
                case 8://APELLIDO 2
                    cell.setCellValue("");
                    break;
                case 9://MOVIL
                    cell.setCellValue("");
                    break;
                case 10://CORREO ELECTRONICO
                    cell.setCellValue("");
                    break;
                case 11://MENSAJE    
                    cell.setCellValue(mensaje);
                    break;
                case 12://DOCUMENTOS
                	cell.setCellValue("");	
                    break;
                default:
                    break;
                }
                
            }
            
        }
        
        
    }
    
    public void writeCloseLogFileGenerico(Short idinstitucion, Long idenvio, Sheet sheet) {
    	if (sheet != null) {
    		
    		try {
    		
	    		/*for(int i = 0; i < SigaConstants.columnsExcelLogEnvios.length; i++) {
	                sheet.autoSizeColumn(i);
	            }*/
	    		
	            File file = _enviosMasivosService.getPathFicheroLOGEnvioMasivo(idinstitucion, idenvio);
	            
	            FileOutputStream fileOut = new FileOutputStream(file);
	            
	            LOGGER.debug("Cerrando fichero... " + file.getAbsoluteFile());
				
	            sheet.getWorkbook().write(fileOut);
	            fileOut.flush();
	            fileOut.close();
	            sheet.getWorkbook().close();
	            
	            LOGGER.debug("Fichero creado... " + file.getAbsoluteFile());
            
			} catch (Exception e) {
				LOGGER.error(e);
			}
    	}
	}

    private List<DestinatarioItem> limpiaCorreosDuplicados(List<DestinatarioItem> destinatarios) {
        List<DestinatarioItem> destinatariosCopia = new ArrayList<DestinatarioItem>();
        Map<Map<String, String>, Boolean> mapaCorreos = new HashMap<Map<String, String>, Boolean>();
        if (destinatarios != null) {
            for (DestinatarioItem destinatarioItem : destinatarios) {
                if (destinatarioItem != null && destinatarioItem.getCorreoElectronico() != null && !destinatarioItem.getCorreoElectronico().trim().equals("")) {
                	Map<String, String> correo = new HashMap<String, String>();
                	correo.put(destinatarioItem.getIdPersona(), destinatarioItem.getCorreoElectronico());        
                    if (!mapaCorreos.containsKey(correo)) {
                        destinatariosCopia.add(destinatarioItem);
                        mapaCorreos.put(correo, true);
                    }
                } else if (destinatarioItem != null) {//el correo es nulo o vacío pues lo añado también para que se marque como correo vacío
                	destinatariosCopia.add(destinatarioItem);
                }
            }
        }
        
        return destinatariosCopia;
    }

    private String adjuntaDocumentos(MimeMultipart mixedMultipart, List<DatosDocumentoItem> documentosEnvio, String idEnvio, String idInstitucion) throws MessagingException, IOException, BusinessException {
    	String listaDocumentos = null;
        if (documentosEnvio != null) {
        	listaDocumentos = "";
            //Adjuntamos los informes adjuntos.
            for (DatosDocumentoItem informe : documentosEnvio) {
                File file = informe.getDocumentoFile();
                if (file == null) {
                    String error = "El fichero del envío " + idEnvio + " para el colegio " + idInstitucion + " es nulo";
                    LOGGER.error(error);
                    throw new BusinessException(error);
                } else if (!file.exists()) {
                    String error = "El fichero del envío " + idEnvio + " para el colegio " + idInstitucion + " es no existe " + file.getAbsolutePath();
                    LOGGER.error(error);
                    throw new BusinessException(error);
                }
                DataSource ds = new FileDataSource(file);
                BodyPart messageBodyPart = new MimeBodyPart();
                
                messageBodyPart.setDataHandler(new DataHandler(ds));
                String fileName = truncarFileName((informe.getFileName()));
                messageBodyPart.setFileName(fileName);
                messageBodyPart.setDisposition(MimePart.ATTACHMENT);
//                mimeBodyPart.attachFile(file);
                mixedMultipart.addBodyPart(messageBodyPart);
                
                if (listaDocumentos.length() == 0) {
                	listaDocumentos = informe.getFileName();
                } else {
                	listaDocumentos += ", " + informe.getFileName();
                }
            }
            
            
        }
        return listaDocumentos;
    }

    private static String truncarFileName(String fileName) {
        int MAX_LENGTH_FILE_NAME = 60;
        if (fileName != null && fileName.length() > MAX_LENGTH_FILE_NAME) {
            int puntoExtension = fileName.lastIndexOf(".");
            if (puntoExtension < 0) {
                puntoExtension = fileName.length();
            }
            String extension = fileName.substring(puntoExtension);
            extension = "..." + extension;
            String name = fileName.substring(0, puntoExtension);
            int recorta = MAX_LENGTH_FILE_NAME - extension.length();
            fileName = name.substring(0, recorta) + extension;
        }
        return fileName;
    }
    
    public static void main(String[] args) {
        String name = "GUARDIAS CALAHORRA AGOSTO 2019 VIOLENCIA29_05_2019_rrqr63.pdf";
        System.out.println(name);
        System.out.println(name.length());
        name = truncarFileName(name);
        System.out.println(name);
        System.out.println(name.length());
    }

    private String adjuntaImagenBase64(MimeMultipart mixedMultipart, String sCuerpo) throws MessagingException {
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
//              cid = cid + getExtension(srcText.split( "," )[0]);
              String newSrc = src.replace( srcText, "cid:" + cid );
              if(srcText.contains("data:image")) {
                  inlineImage.put( cid, srcText.split( "," )[1] );
                  sCuerpo = sCuerpo.replace( src, newSrc );
                  i++;
              
//                  LOGGER.debug(" CID " + cid + " Image data :: " + srcText.split( "," )[1]);
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
//           pmp.setText( pairs.getValue());
           pmp.setContent( pairs.getValue(), "image/png" );
           mixedMultipart.addBodyPart( pmp );
        }
        
        return sCuerpo;
        
    }
    
  
    @Override
    public String envioSMS(CenDirecciones remitente, List<DestinatarioItem> listEnvDestinatarios, EnvEnvios envEnvio, String texto, boolean esBuroSMS) {
        
        EnviarSMSResponse response = null;
        String respuesta = null;
                
        GenParametrosKey keyParam = new GenParametrosKey();
        
        keyParam.setIdinstitucion(Short.parseShort(SigaConstants.IDINSTITUCION_0));
        keyParam.setModulo(SigaConstants.MODULO_ENV);
        keyParam.setParametro(SigaConstants.SMS_URL_SERVICE);
        
        GenParametros property = _genParametrosMapper.selectByPrimaryKey(keyParam);
        String uriService = property.getValor();
        String idSolicidudEcos = "";
        List<DestinatarioItem> listCorrectos = null;
        
        Sheet sheet = null;
        
        String from = null;
        String descFrom = null;
        
        
        try {
            
//            LOGGER.debug("EnviosServiceImpl.envioSMS :: Se envia SMS a: " + destinatarios);
        	
        	sheet = creaLogExcel(envEnvio);
            
            //Instanciamos la peticion
            SolicitudEnvioSMS request = SolicitudEnvioSMS.Factory.newInstance();
            
            keyParam.setParametro(SigaConstants.SMS_CLIENTE_ECOS);
            property = _genParametrosMapper.selectByPrimaryKey(keyParam);
            String idECOS = property.getValor();
            request.setIdClienteECOS(idECOS);
            
            if (envEnvio == null || envEnvio.getIdinstitucion() == null || envEnvio.getIdenvio() == null) {
                String error = "Para enviar un correo se debe informar del envEnvio";
                LOGGER.error(error);
                throw new BusinessException(error);
            }
            
            request.setIdColegio(envEnvio.getIdinstitucion().toString());    
            
            if(listEnvDestinatarios == null || listEnvDestinatarios.size() == 0){
                LOGGER.error("El destinatario no puede ser nulo para enviar un sms");
                throw new BusinessException("El destinatario no puede ser nulo para enviar un sms");
            }
            
            if(esBuroSMS){
                if (remitente == null || remitente.getCorreoelectronico() == null) {
                    LOGGER.error("El remitente o su correo electrónico es nulo");
                    throw new BusinessException("El remitente o su correo electrónico es nulo");
                }
                request.setEmail(remitente.getCorreoelectronico());
            }
            
            from = request.getEmail();
            descFrom = request.getEmail();
            
         
            listCorrectos = new ArrayList<DestinatarioItem>();
            //Si no viene el prefijo se lo añadimos
            for(int i = listEnvDestinatarios.size()-1; i >= 0; i--) {
            	if (listEnvDestinatarios.get(i).getMovil() == null) {
            		insertaExcelRow(envEnvio, sheet, from, descFrom, null, listEnvDestinatarios.get(i), "No tiene móvil informado");
            		listEnvDestinatarios.remove(i);
            		break;
            	}     	
                if(listEnvDestinatarios.get(i).getMovil().length() == 9){
                    listEnvDestinatarios.get(i).setMovil(SigaConstants.ECOS_PREFIJO_ESPANA + listEnvDestinatarios.get(i).getMovil());
                }
                                
                //public static final 
               if (!listEnvDestinatarios.get(i).getMovil().matches(SigaConstants.EXPRESION_REGULAR_MOVIL)) {
            	   insertaExcelRow(envEnvio, sheet, from, descFrom, null, listEnvDestinatarios.get(i), "Número de móvil no es válido");
            	   listEnvDestinatarios.remove(i);
               } else {
            	   listCorrectos.add(listEnvDestinatarios.get(i));
               }
            }    
            
            request.setTexto(texto);
            request.setIsProgramado(false);
            
            //Si es BuroSMS
            request.setIsSMSCertificado(esBuroSMS);
                    
            if (listCorrectos != null && listCorrectos.size() > 0) {
            	
            	String[] listaTOs = new String[listCorrectos.size()];
            	for(int i = 0; i < listCorrectos.size(); i++){
            		listaTOs[i] = listEnvDestinatarios.get(i).getMovil();
            	}
            	request.setListaTOsArray(listaTOs);
                EnviarSMSResponseDocument responseDoc = EnviarSMSResponseDocument.Factory.newInstance();            
                EnviarSMS sms = EnviarSMS.Factory.newInstance();
                sms.setEnviarSMSRequest(request);
                
                EnviarSMSDocument requestDoc = EnviarSMSDocument.Factory.newInstance();
                requestDoc.setEnviarSMS(sms);
	            try {
	                responseDoc = _clientECOS.enviarSMS(uriService, requestDoc);    
	                response = responseDoc.getEnviarSMSResponse();
	                idSolicidudEcos = response.getEnviarSMSResponse().getIdSolicitud();
	                LOGGER.error("El SMS se ha enviado con idSolicitud: "+idSolicidudEcos);
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
	            
	            for(int i = 0; i < listCorrectos.size(); i++){
	            	insertaExcelRow(envEnvio, sheet, from, descFrom, null, listCorrectos.get(i), "OK");//TODO REVISAR LA DESCRIPCIÓN DEL REMITENTE
	            }
            }
            
        } catch (Exception e) {
        	String mensaje = "ERROR";
        	if (e instanceof BusinessException) {
        		mensaje = e.getMessage();
        	}
        	if (listCorrectos != null && listCorrectos.size() > 0) {
        		for(int i = 0; i < listCorrectos.size(); i++){
                	insertaExcelRow(envEnvio, sheet, from, descFrom, null, listCorrectos.get(i), mensaje);//TODO REVISAR LA DESCRIPCIÓN DEL REMITENTE
                }	
        	} else if (listEnvDestinatarios != null) {
        		for(int i = 0; i < listEnvDestinatarios.size(); i++){
                	insertaExcelRow(envEnvio, sheet, from, descFrom, null, listEnvDestinatarios.get(i), mensaje);//TODO REVISAR LA DESCRIPCIÓN DEL REMITENTE
                }
        	}
            LOGGER.error("No se ha enviado el sms a causa de: "+ e.getMessage(), e);
        } finally {
        	writeCloseLogFile(envEnvio.getIdinstitucion(), envEnvio.getIdenvio(), sheet);
        }
        
        return idSolicidudEcos;
        
    }
    
		  
		   

  
 
    private String sustituirEtiquetas(String idInstitucion, String sArchivo, DestinatarioItem destinatario, String marcaInicioFin, String idEnvio){
        String etiqueta = SigaConstants.ETIQUETA_DEST_NOMBRE;

        Pattern pattern = Pattern.compile(marcaInicioFin + etiqueta + marcaInicioFin);
        Matcher matcher = pattern.matcher(sArchivo);
        if (destinatario.getNombre() != null) {
        	sArchivo = matcher.replaceAll(destinatario.getNombre());
        }
        
        if (destinatario.getApellidos1() != null) {
	        etiqueta = SigaConstants.ETIQUETA_DEST_APELLIDO1;
	        pattern = Pattern.compile(marcaInicioFin + etiqueta + marcaInicioFin);
	        matcher = pattern.matcher(sArchivo);
	        sArchivo = matcher.replaceAll(destinatario.getApellidos1());
        }
        
        if (destinatario.getApellidos2() != null) {
	        etiqueta = SigaConstants.ETIQUETA_DEST_APELLIDO2;
	        pattern = Pattern.compile(marcaInicioFin + etiqueta + marcaInicioFin);
	        matcher = pattern.matcher(sArchivo);
	        sArchivo = matcher.replaceAll(destinatario.getApellidos2());
        }
        
        if (destinatario.getNIFCIF() != null) {
	        etiqueta = SigaConstants.ETIQUETA_DEST_CIFNIF;
	        pattern = Pattern.compile(marcaInicioFin + etiqueta + marcaInicioFin);
	        matcher = pattern.matcher(sArchivo);
	        sArchivo = matcher.replaceAll(destinatario.getNIFCIF());
        }
        
        
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
        
        if (idEnvio != null) {
	        etiqueta = SigaConstants.ETIQUETA_IDENVIO;
	        pattern = Pattern.compile(marcaInicioFin + etiqueta + marcaInicioFin);
	        matcher = pattern.matcher(sArchivo);
	        sArchivo = matcher.replaceAll(idEnvio);
        }
        
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
//        StringBuffer sDirectorioImagen = new StringBuffer( );
//           sDirectorioImagen.append(File.separator);
//           sDirectorioImagen.append(imagenPlantilla.getIdInstitucion());
//           sDirectorioImagen.append(File.separator);
//           sDirectorioImagen.append(imagenPlantilla.getIdTipoEnvios());
//           sDirectorioImagen.append(File.separator);
//           sDirectorioImagen.append(imagenPlantilla.getIdPlantillaEnvios());
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
