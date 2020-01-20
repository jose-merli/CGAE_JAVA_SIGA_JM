package org.itcgae.siga.DTOs.scs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PagosjgItem {
	private String idInstitucion;
	private String idPagosjg;
	private String idFacturacion;
	private String nombre;
	private String importeEJG;
	private String importeGuardia;
	private String importeOficio;
	private String importesOJ;
	private String importeRepartir;
	private String importePagado;
	private Date fechaEstado;
	private String desEstado;
	private String desConcepto;
	private String porcentaje;
	
	@JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}
	
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	
	@JsonProperty("idPagosjg")
	public String getIdPagosjg() {
		return idPagosjg;
	}
	
	public void setIdPagosjg(String idPagosjg) {
		this.idPagosjg = idPagosjg;
	}
	
	@JsonProperty("idFacturacion")
	public String getIdFacturacion() {
		return idFacturacion;
	}
	
	public void setIdFacturacion(String idFacturacion) {
		this.idFacturacion = idFacturacion;
	}
	
	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@JsonProperty("importeEJG")
	public String getImporteEJG() {
		return importeEJG;
	}
	
	public void setImporteEJG(String importeEJG) {
		this.importeEJG = importeEJG;
	}
	
	@JsonProperty("importeGuardia")
	public String getImporteGuardia() {
		return importeGuardia;
	}
	
	public void setImporteGuardia(String importeGuardia) {
		this.importeGuardia = importeGuardia;
	}
	
	@JsonProperty("importeOficio")
	public String getImporteOficio() {
		return importeOficio;
	}
	
	public void setImporteOficio(String importeOficio) {
		this.importeOficio = importeOficio;
	}
	
	@JsonProperty("importesOJ")
	public String getImportesOJ() {
		return importesOJ;
	}
	
	public void setImportesOJ(String importesOJ) {
		this.importesOJ = importesOJ;
	}
	
	@JsonProperty("importeRepartir")
	public String getImporteRepartir() {
		return importeRepartir;
	}
	
	public void setImporteRepartir(String importeRepartir) {
		this.importeRepartir = importeRepartir;
	}
	
	@JsonProperty("importePagado")
	public String getImportePagado() {
		return importePagado;
	}
	
	public void setImportePagado(String importePagado) {
		this.importePagado = importePagado;
	}
	
	@JsonProperty("fechaEstado")
	public Date getFechaEstado() {
		return fechaEstado;
	}
	
	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}
	
	@JsonProperty("desEstado")
	public String getDesEstado() {
		return desEstado;
	}
	
	public void setDesEstado(String desEstado) {
		this.desEstado = desEstado;
	}

	@JsonProperty("desConcepto")
	public String getDesConcepto() {
		return desConcepto;
	}

	public void setDesConcepto(String desConcepto) {
		this.desConcepto = desConcepto;
	}

	@JsonProperty("porcentaje")
	public String getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((desConcepto == null) ? 0 : desConcepto.hashCode());
		result = prime * result + ((desEstado == null) ? 0 : desEstado.hashCode());
		result = prime * result + ((fechaEstado == null) ? 0 : fechaEstado.hashCode());
		result = prime * result + ((idFacturacion == null) ? 0 : idFacturacion.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idPagosjg == null) ? 0 : idPagosjg.hashCode());
		result = prime * result + ((importeEJG == null) ? 0 : importeEJG.hashCode());
		result = prime * result + ((importeGuardia == null) ? 0 : importeGuardia.hashCode());
		result = prime * result + ((importeOficio == null) ? 0 : importeOficio.hashCode());
		result = prime * result + ((importePagado == null) ? 0 : importePagado.hashCode());
		result = prime * result + ((importeRepartir == null) ? 0 : importeRepartir.hashCode());
		result = prime * result + ((importesOJ == null) ? 0 : importesOJ.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((porcentaje == null) ? 0 : porcentaje.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PagosjgItem other = (PagosjgItem) obj;
		if (desConcepto == null) {
			if (other.desConcepto != null)
				return false;
		} else if (!desConcepto.equals(other.desConcepto))
			return false;
		if (desEstado == null) {
			if (other.desEstado != null)
				return false;
		} else if (!desEstado.equals(other.desEstado))
			return false;
		if (fechaEstado == null) {
			if (other.fechaEstado != null)
				return false;
		} else if (!fechaEstado.equals(other.fechaEstado))
			return false;
		if (idFacturacion == null) {
			if (other.idFacturacion != null)
				return false;
		} else if (!idFacturacion.equals(other.idFacturacion))
			return false;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		if (idPagosjg == null) {
			if (other.idPagosjg != null)
				return false;
		} else if (!idPagosjg.equals(other.idPagosjg))
			return false;
		if (importeEJG == null) {
			if (other.importeEJG != null)
				return false;
		} else if (!importeEJG.equals(other.importeEJG))
			return false;
		if (importeGuardia == null) {
			if (other.importeGuardia != null)
				return false;
		} else if (!importeGuardia.equals(other.importeGuardia))
			return false;
		if (importeOficio == null) {
			if (other.importeOficio != null)
				return false;
		} else if (!importeOficio.equals(other.importeOficio))
			return false;
		if (importePagado == null) {
			if (other.importePagado != null)
				return false;
		} else if (!importePagado.equals(other.importePagado))
			return false;
		if (importeRepartir == null) {
			if (other.importeRepartir != null)
				return false;
		} else if (!importeRepartir.equals(other.importeRepartir))
			return false;
		if (importesOJ == null) {
			if (other.importesOJ != null)
				return false;
		} else if (!importesOJ.equals(other.importesOJ))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (porcentaje == null) {
			if (other.porcentaje != null)
				return false;
		} else if (!porcentaje.equals(other.porcentaje))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PagosjgItem [idInstitucion=" + idInstitucion + ", idPagosJG=" + idPagosjg + ", idFacturacion="
				+ idFacturacion + ", nombre=" + nombre + ", importeEJG=" + importeEJG + ", importeGuardia="
				+ importeGuardia + ", importeOficio=" + importeOficio + ", importesOJ=" + importesOJ
				+ ", importeRepartir=" + importeRepartir + ", importePagado=" + importePagado + ", fechaEstado="
				+ fechaEstado + ", desEstado=" + desEstado + ", desConcepto=" + desConcepto + ", porcentaje="
				+ porcentaje + "]";
	}
}
