package org.itcgae.siga.com.services;

import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.EnvEnvios;

public interface IEnviosService {
	
	public void envioMail(EnvEnvios envio);
	public void envioFax();
	public String envioSMS(CenDirecciones remitente, String[] destinatarios, Short idInstitucion, String asunto, String texto, boolean esBuroSMS);
	public void envioCorreoOrdinario();

}
