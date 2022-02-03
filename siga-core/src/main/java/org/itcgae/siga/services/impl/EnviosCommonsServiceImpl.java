package org.itcgae.siga.services.impl;

import org.apache.log4j.Logger;
import org.itcgae.siga.commons.constants.SigaConstants;
import org.itcgae.siga.commons.utils.UtilidadesString;
import org.itcgae.siga.db.entities.GenProperties;
import org.itcgae.siga.db.entities.GenPropertiesKey;
import org.itcgae.siga.db.mappers.GenPropertiesMapper;
import org.itcgae.siga.services.IEnviosCommonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import java.io.File;
import java.util.Arrays;
import java.util.Date;

@Service
public class EnviosCommonsServiceImpl implements IEnviosCommonsService {

    private final Logger LOGGER = Logger.getLogger(EnviosCommonsServiceImpl.class);

    @Autowired
    private GenPropertiesMapper genPropertiesMapper;

    @Override
    public void enviarCorreo(String from, String[] bcc, String asunto, String cuerpoMensaje, File file, String hostProperty, String portProperty, String userProperty, String pwdProperty) throws Exception {

        if (UtilidadesString.esCadenaVacia(from) || bcc == null || bcc.length == 0 || UtilidadesString.esCadenaVacia(asunto) || UtilidadesString.esCadenaVacia(cuerpoMensaje)) {
            String valores = "from = '" + from + "'";
            valores += ", bcc = '" + Arrays.toString(bcc) + "'";
            valores += ", asunto = '" + asunto + "'";
            valores += ", cuerpoMensaje = '" + cuerpoMensaje + "'";
            throw new IllegalArgumentException("Ninguno de los parametros puede ser nulo. Valores " + valores);
        }

        Context ctx = new InitialContext();

        Session sesion = (Session) PortableRemoteObject.narrow(ctx.lookup("CorreoSIGA"), Session.class);
        ctx.close();

        sesion.getProperties().put(SigaConstants.MAIL_SMTP_AUTH, "true");
        Transport tr = sesion.getTransport("smtp");

        String host = getPropertyFicheroSIGA(hostProperty);
        String user = getPropertyFicheroSIGA(userProperty);
        String pwd = getPropertyFicheroSIGA(pwdProperty);

        if (UtilidadesString.esCadenaVacia(portProperty)) {
            tr.connect(host, user, pwd);
        } else {
            String port = getPropertyFicheroSIGA(portProperty);
            tr.connect(host, Integer.parseInt(port), user, pwd);
        }

        MimeMessage mensaje = new MimeMessage(sesion);

        InternetAddress addressFrom = new InternetAddress(from);
        mensaje.setFrom(addressFrom);

        InternetAddress[] addressTo = new InternetAddress[bcc.length];
        for (int i = 0; i < bcc.length; i++) {
            addressTo[i] = new InternetAddress(bcc[i]);
        }
        mensaje.setRecipients(Message.RecipientType.TO, addressTo);

        mensaje.setSubject(asunto, "ISO-8859-1");
        mensaje.setHeader("Content-Type", "text/html; charset=\"ISO-8859-1\"");

        MimeMultipart mimeMultipart = new MimeMultipart("mixed");
        MimeBodyPart mimeBodyPart = new MimeBodyPart();

        MimeMultipart relatedMultipart = new MimeMultipart("related");

        if (file != null) {
            BodyPart messageBodyPart = new MimeBodyPart();
            DataSource ds = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(ds));
            messageBodyPart.setFileName(file.getName());
            messageBodyPart.setDisposition(MimePart.ATTACHMENT);

            mimeMultipart.addBodyPart(messageBodyPart);
        }

        BodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(cuerpoMensaje, "text/html");
        relatedMultipart.addBodyPart(bodyPart);

        mimeBodyPart.setContent(relatedMultipart);

        mimeMultipart.addBodyPart(mimeBodyPart);

        mensaje.setContent(mimeMultipart);
        mensaje.setSentDate(new Date());

        tr.sendMessage(mensaje, mensaje.getAllRecipients());
        LOGGER.info("El mensaje se ha enviado correctamente.");
    }

    private String getPropertyFicheroSIGA(String parametro) {
        return getProperty(SigaConstants.FICHERO_SIGA, parametro);
    }

    private String getProperty(String fichero, String parametro) {

        String respuesta = null;

        GenPropertiesKey genPropertiesKey = new GenPropertiesKey();
        genPropertiesKey.setFichero(fichero);
        genPropertiesKey.setParametro(parametro);

        GenProperties genProperties = genPropertiesMapper.selectByPrimaryKey(genPropertiesKey);

        if (genProperties != null && genProperties.getValor() != null) {
            respuesta = genProperties.getValor();
        }

        return respuesta;
    }

}
