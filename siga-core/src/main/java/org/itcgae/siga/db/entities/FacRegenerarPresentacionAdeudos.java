package org.itcgae.siga.db.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

import org.itcgae.siga.DTO.fac.FacRegenerarPresentacionAdeudosItem;

public class FacRegenerarPresentacionAdeudos extends FacFacturacionBase {
	private Long idDisqueteCargos;
	private String fechaPresentacion;
	private String fechaCargoFRST;
	private String fechaCargoRCUR;
	private String fechaCargoCOR1;
	private String fechaCargoB2B;
	private String pathFichero;
	
	private static final Function<Date, String> formatFechaCorta = date-> { 
																		  SimpleDateFormat df= new SimpleDateFormat("yyyyMMdd");
																		  return date!=null?df.format(date):null;};
	
	public FacRegenerarPresentacionAdeudos(FacRegenerarPresentacionAdeudosItem resgPresAdeuItem, AdmUsuarios usuario) {
		if (usuario != null) {
			this.setIdInstitucion(usuario.getIdinstitucion());
			this.setIdIdioma(usuario.getIdlenguaje());
		}
		this.setIdDisqueteCargos(resgPresAdeuItem.getIdDisqueteCargos());	
		this.setIdSerieFacturacion(resgPresAdeuItem.getIdSerieFacturacion());
		fechaPresentacion=formatFechaCorta.apply(resgPresAdeuItem.getFechaPresentacion());
		fechaCargoFRST=formatFechaCorta.apply(resgPresAdeuItem.getFechaCargoFRST());
		fechaCargoRCUR=formatFechaCorta.apply(resgPresAdeuItem.getFechaCargoRCUR());
		fechaCargoCOR1=formatFechaCorta.apply(resgPresAdeuItem.getFechaCargoCOR1());
		fechaCargoB2B=formatFechaCorta.apply(resgPresAdeuItem.getFechaCargoB2B());
		pathFichero = resgPresAdeuItem.getPathFichero();	
	}
	
	public Long getIdDisqueteCargos() {
		return idDisqueteCargos;
	}
	public void setIdDisqueteCargos(Long idDisqueteCargos) {
		this.idDisqueteCargos = idDisqueteCargos;
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
	
}