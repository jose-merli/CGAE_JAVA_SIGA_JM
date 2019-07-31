package org.itcgae.siga.com.services;

import java.util.List;

import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.com.DestinatarioItem;
import org.itcgae.siga.DTOs.com.RemitenteDTO;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.EnvDestinatarios;
import org.itcgae.siga.db.entities.EnvEnvios;

public interface IEnviosService {
	
	public String envioSMS(CenDirecciones remitente, List<DestinatarioItem> listEnvDestinatarios, EnvEnvios envEnvio, String texto, boolean esBuroSMS);
	public Short envioMail(String idInstitucion, String idEnvio, RemitenteDTO remitente, List<DestinatarioItem> destinatarios, String asuntoFinal, String cuerpoFinal, List<DatosDocumentoItem> documentosEnvio, boolean envioMasivo) throws Exception;

}
