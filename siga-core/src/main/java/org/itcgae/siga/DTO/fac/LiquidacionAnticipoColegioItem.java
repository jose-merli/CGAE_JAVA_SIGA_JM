package org.itcgae.siga.DTO.fac;

import java.util.Date;
import java.util.Objects;

public class LiquidacionAnticipoColegioItem {
	
	private int idanticipo;
	private float importeanticipado;
	private int idpersona;
	private Date fechaefectiva;
	
	public int getIdanticipo() {
		return idanticipo;
	}
	public void setIdanticipo(int idanticipo) {
		this.idanticipo = idanticipo;
	}
	public float getImporteanticipado() {
		return importeanticipado;
	}
	public void setImporteanticipado(float importeanticipado) {
		this.importeanticipado = importeanticipado;
	}
	public int getIdpersona() {
		return idpersona;
	}
	public void setIdpersona(int idpersona) {
		this.idpersona = idpersona;
	}
	public Date getFechaefectiva() {
		return fechaefectiva;
	}
	public void setFechaefectiva(Date fechaefectiva) {
		this.fechaefectiva = fechaefectiva;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(fechaefectiva, idanticipo, idpersona, importeanticipado);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LiquidacionAnticipoColegioItem other = (LiquidacionAnticipoColegioItem) obj;
		return Objects.equals(fechaefectiva, other.fechaefectiva) && idanticipo == other.idanticipo
				&& idpersona == other.idpersona
				&& Float.floatToIntBits(importeanticipado) == Float.floatToIntBits(other.importeanticipado);
	}
	
	@Override
	public String toString() {
		return "LiquidacionAnticipoColegioItem [idanticipo=" + idanticipo + ", importeanticipado=" + importeanticipado
				+ ", idpersona=" + idpersona + ", fechaefectiva=" + fechaefectiva + "]";
	}
	
}
