package org.itcgae.siga.com.services;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.itcgae.siga.DTOs.com.DatosDocumentoItem;
import org.itcgae.siga.DTOs.com.DestinatarioItem;
import org.itcgae.siga.DTOs.com.RemitenteDTO;
import org.itcgae.siga.db.entities.CenDirecciones;
import org.itcgae.siga.db.entities.EnvEnvios;

public interface IEnviosService {
	
	public String envioSMS(CenDirecciones remitente, List<DestinatarioItem> listEnvDestinatarios, EnvEnvios envEnvio, String texto, boolean esBuroSMS);
	public Short envioMail(String idInstitucion, String idEnvio, RemitenteDTO remitente, List<DestinatarioItem> destinatarios, String asuntoFinal, String cuerpoFinal, List<DatosDocumentoItem> documentosEnvio, boolean envioMasivo) throws Exception;
	public Sheet creaLogGenericoExcel(EnvEnvios envio) throws IOException, InvalidFormatException;
	public void insertaExcelRowLogGenerico(EnvEnvios envio, Sheet sheet, String error);
	public void insertaExcelRow(EnvEnvios envEnvio, Sheet sheet, String from, String descFrom, String documentosAdjuntos, DestinatarioItem destinatarioItem, String mensaje);
	public void writeCloseLogFileGenerico(Short valueOf, Long idenvio, Sheet sheet);

}
