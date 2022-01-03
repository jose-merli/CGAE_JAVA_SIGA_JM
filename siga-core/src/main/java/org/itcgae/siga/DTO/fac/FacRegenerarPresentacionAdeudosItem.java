package org.itcgae.siga.DTO.fac;

import java.util.Date;

public class FacRegenerarPresentacionAdeudosItem extends IdentificadorFacturacionItem {
	private Long idDisqueteCargos;
	private Date fechaPresentacion;
	private Date fechaCargoFRST;
	private Date fechaCargoRCUR;
	private Date fechaCargoCOR1;
	private Date fechaCargoB2B;
	private String pathFichero;

	
	public Long getIdDisqueteCargos() {
		return idDisqueteCargos;
	}
	public void setIdDisqueteCargos(Long idDisqueteCargos) {
		this.idDisqueteCargos = idDisqueteCargos;
	}
	
	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}
	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
	public Date getFechaCargoFRST() {
		return fechaCargoFRST;
	}
	public void setFechaCargoFRST(Date fechaCargoFRST) {
		this.fechaCargoFRST = fechaCargoFRST;
	}
	public Date getFechaCargoRCUR() {
		return fechaCargoRCUR;
	}
	public void setFechaCargoRCUR(Date fechaCargoRCUR) {
		this.fechaCargoRCUR = fechaCargoRCUR;
	}
	public Date getFechaCargoCOR1() {
		return fechaCargoCOR1;
	}
	public void setFechaCargoCOR1(Date fechaCargoCOR1) {
		this.fechaCargoCOR1 = fechaCargoCOR1;
	}
	public Date getFechaCargoB2B() {
		return fechaCargoB2B;
	}
	public void setFechaCargoB2B(Date fechaCargoB2B) {
		this.fechaCargoB2B = fechaCargoB2B;
	}
	public String getPathFichero() {
		return pathFichero;
	}
	public void setPathFichero(String pathFichero) {
		this.pathFichero = pathFichero;
	}
	
}
