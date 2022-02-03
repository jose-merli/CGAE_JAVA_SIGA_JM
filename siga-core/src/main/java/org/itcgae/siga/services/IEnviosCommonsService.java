package org.itcgae.siga.services;

import java.io.File;

public interface IEnviosCommonsService {

    void enviarCorreo(String from, String[] bcc, String asunto, String cuerpoMensaje, File file, String host, String port, String user, String pwd) throws Exception;

}
