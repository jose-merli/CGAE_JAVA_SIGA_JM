package org.itcgae.siga.DTO.fac;

import java.util.Date;
import java.util.Objects;

public class AltaAnticipoItem {
	private int idanticipo;
	private String descripcion;
	private float importeinicial;
	private int idpersona;
	private Date fecha;
	private String ctacontable;
	
	public int getIdanticipo() {
		return idanticipo;
	}
	public void setIdanticipo(int idanticipo) {
		this.idanticipo = idanticipo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public float getImporteinicial() {
		return importeinicial;
	}
	public void setImporteinicial(float importeinicial) {
		this.importeinicial = importeinicial;
	}
	public int getIdpersona() {
		return idpersona;
	}
	public void setIdpersona(int idpersona) {
		this.idpersona = idpersona;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getCtacontable() {
		return ctacontable;
	}
	public void setCtacontable(String ctacontable) {
		this.ctacontable = ctacontable;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(ctacontable, descripcion, fecha, idanticipo, idpersona, importeinicial);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AltaAnticipoItem other = (AltaAnticipoItem) obj;
		return Objects.equals(ctacontable, other.ctacontable) && Objects.equals(descripcion, other.descripcion)
				&& Objects.equals(fecha, other.fecha) && idanticipo == other.idanticipo && idpersona == other.idpersona
				&& Float.floatToIntBits(importeinicial) == Float.floatToIntBits(other.importeinicial);
	}
	
	@Override
	public String toString() {
		return "AltaAnticipoItem [idanticipo=" + idanticipo + ", descripcion=" + descripcion + ", importeinicial="
				+ importeinicial + ", idpersona=" + idpersona + ", fecha=" + fecha + ", ctacontable=" + ctacontable
				+ "]";
	}
	
}
