package org.itcgae.siga.db.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

import org.itcgae.siga.DTO.fac.FacPresentacionAdeudosItem;

public class FacPresentacionAdeudos extends FacFacturacionBase {
	private String fechaPresentacion;
	private String fechaCargoFRST;
	private String fechaCargoRCUR;
	private String fechaCargoCOR1;
	private String fechaCargoB2B;
	private String pathFichero;
	private String nFicheros;
	
	private static final Function<Date, String> formatFechaCorta = date-> { 
																		  SimpleDateFormat df= new SimpleDateFormat("yyyyMMdd");
																		  return date!=null?df.format(date):null;};
	
	public FacPresentacionAdeudos(FacPresentacionAdeudosItem presAdeuItem, AdmUsuarios usuario) {
		if (usuario != null) {
			this.setIdInstitucion(usuario.getIdinstitucion());
			this.setIdUsuarioModificacion(usuario.getIdusuario());
			this.setIdIdioma(usuario.getIdlenguaje());
		}
		this.setIdProgramacion(presAdeuItem.getIdProgramacion());
		this.setIdSerieFacturacion(presAdeuItem.getIdSerieFacturacion());
		fechaPresentacion=formatFechaCorta.apply(presAdeuItem.getFechaPresentacion());
		fechaCargoFRST=formatFechaCorta.apply(presAdeuItem.getFechaCargoFRST());
		fechaCargoRCUR=formatFechaCorta.apply(presAdeuItem.getFechaCargoRCUR());
		fechaCargoCOR1=formatFechaCorta.apply(presAdeuItem.getFechaCargoCOR1());
		fechaCargoB2B=formatFechaCorta.apply(presAdeuItem.getFechaCargoB2B());
		pathFichero = presAdeuItem.getPathFichero();	
	}
	
	public String getFechaPresentacion() {
		return fechaPresentacion;
	}
	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
	public String getFechaCargoFRST() {
		return fechaCargoFRST;
	}
	public void setFechaCargoFRST(String fechaCargoFRST) {
		this.fechaCargoFRST = fechaCargoFRST;
	}
	public String getFechaCargoRCUR() {
		return fechaCargoRCUR;
	}
	public void setFechaCargoRCUR(String fechaCargoRCUR) {
		this.fechaCargoRCUR = fechaCargoRCUR;
	}
	public String getFechaCargoCOR1() {
		return fechaCargoCOR1;
	}
	public void setFechaCargoCOR1(String fechaCargoCOR1) {
		this.fechaCargoCOR1 = fechaCargoCOR1;
	}
	public String getFechaCargoB2B() {
		return fechaCargoB2B;
	}
	public void setFechaCargoB2B(String fechaCargoB2B) {
		this.fechaCargoB2B = fechaCargoB2B;
	}
	public String getPathFichero() {
		return pathFichero;
	}
	public void setPathFichero(String pathFichero) {
		this.pathFichero = pathFichero;
	}
	public String getnFicheros() {
		return nFicheros;
	}
	public void setnFicheros(String nFicheros) {
		this.nFicheros = nFicheros;
	}
	
	
		
	
}