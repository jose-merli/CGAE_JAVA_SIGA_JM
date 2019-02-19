package org.itcgae.siga.com.services;

import java.util.List;

import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.com.DestinatarioItem;
import org.itcgae.siga.DTOs.com.RemitenteDTO;
import org.itcgae.siga.db.entities.CenDirecciones;

public interface IEnviosService {
	
	//public void envioMail(EnvEnvios envio);
	public String envioSMS(CenDirecciones remitente, String[] destinatarios, Short idInstitucion, String asunto, String texto, boolean esBuroSMS);
	public void envioCorreoOrdinario();
	public void envioMail(RemitenteDTO remitente, List<DestinatarioItem> destinatarios, String asuntoFinal, String cuerpoFinal, List<DatosDocumentoItem> documentosEnvio, boolean envioMasivo);

}