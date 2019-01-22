package org.itcgae.siga.DTOs.cen;

import java.sql.Date;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BusquedaSancionesSearchDTO {

	// Búsqueda por Letrado
	private String nif;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	
	// Búsqueda por Colegio
	private String[] idColegios;
	private String tipoSancion;
	private String refConsejo;
	private String refColegio;
	private boolean chkRehabilitado;
	private Date fechaDesdeDate;
	private Date fechaHastaDate;
	private Boolean chkArchivadas;
	private Date fechaArchivadaDesdeDate;
	private Date fechaArchivadaHastaDate;
	
	private String tipoFecha;
	
	private boolean chkFirmeza;
	
	// Búsqueda por Sanciones
	private String tipo;
	private String origen;
	private String estado;
	
	@JsonProperty("tipoFecha")
	public String getTipoFecha() {
		return tipoFecha;
	}
	public void setTipoFecha(String tipoFecha) {
		this.tipoFecha = tipoFecha;
	}
	
	@JsonProperty("nif")
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@JsonProperty("primerApellido")
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	
	@JsonProperty("segundoApellido")
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	
	@JsonProperty("idColegios")
	public String[] getColegio() {
		return idColegios;
	}
	public void setColegio(String[] idColegios) {
		this.idColegios = idColegios;
	}
	
	@JsonProperty("tipoSancion")
	public String getTipoSancion() {
		return tipoSancion;
	}
	public void setTipoSancion(String tipoSancion) {
		this.tipoSancion = tipoSancion;
	}
	
	@JsonProperty("refColegio")
	public String getRefColegio() {
		return refColegio;
	}
	public void setRefColegio(String refColegio) {
		this.refColegio = refColegio;
	}
	
	@JsonProperty("refConsejo")
	public String getRefConsejo() {
		return refConsejo;
	}
	public void setRefConsejo(String refConsejo) {
		this.refConsejo = refConsejo;
	}
	
	@JsonProperty("chkRehabilitado")
	public boolean getChkRehabilitados() {
		return chkRehabilitado;
	}
	public void setChkRehabilitado(boolean chkRehabilitado) {
		this.chkRehabilitado = chkRehabilitado;
	}
	
	@JsonProperty("fechaDesdeDate")
	public Date getFechaDesde() {
		return fechaDesdeDate;
	}
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesdeDate = fechaDesde;
	}
	
	@JsonProperty("fechaHastaDate")
	public Date getFechaHasta() {
		return fechaHastaDate;
	}
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHastaDate = fechaHasta;
	}
	
	@JsonProperty("chkArchivadas")
	public Boolean getChkArchivadas() {
		return chkArchivadas;
	}
	public void setChkArchivadas(Boolean chkArchivadas) {
		this.chkArchivadas = chkArchivadas;
	}
	
	@JsonProperty("fechaArchivadaDesdeDate")
	public Date getFechaArchivadaDesde() {
		return fechaArchivadaDesdeDate;
	}
	public void setFechaArchivadaDesde(Date fechaArchivadaDesde) {
		this.fechaArchivadaDesdeDate = fechaArchivadaDesde;
	}
	
	@JsonProperty("fechaArchivadaHastaDate")
	public Date getFechaArchivadaHasta() {
		return fechaArchivadaHastaDate;
	}
	public void setFechaArchivadaHasta(Date fechaArchivadaHasta) {
		this.fechaArchivadaHastaDate = fechaArchivadaHasta;
	}
	
	@JsonProperty("tipo")
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@JsonProperty("origen")
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	
	@JsonProperty("estado")
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public boolean getChkFirmeza() {
		return chkFirmeza;
	}
	public void setChkFirmeza(boolean chkFirmeza) {
		this.chkFirmeza = chkFirmeza;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chkArchivadas == null) ? 0 : chkArchivadas.hashCode());
		result = prime * result + (chkFirmeza ? 1231 : 1237);
		result = prime * result + (chkRehabilitado ? 1231 : 1237);
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaArchivadaDesdeDate == null) ? 0 : fechaArchivadaDesdeDate.hashCode());
		result = prime * result + ((fechaArchivadaHastaDate == null) ? 0 : fechaArchivadaHastaDate.hashCode());
		result = prime * result + ((fechaDesdeDate == null) ? 0 : fechaDesdeDate.hashCode());
		result = prime * result + ((fechaHastaDate == null) ? 0 : fechaHastaDate.hashCode());
		result = prime * result + Arrays.hashCode(idColegios);
		result = prime * result + ((nif == null) ? 0 : nif.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((origen == null) ? 0 : origen.hashCode());
		result = prime * result + ((primerApellido == null) ? 0 : primerApellido.hashCode());
		result = prime * result + ((refColegio == null) ? 0 : refColegio.hashCode());
		result = prime * result + ((refConsejo == null) ? 0 : refConsejo.hashCode());
		result = prime * result + ((segundoApellido == null) ? 0 : segundoApellido.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((tipoFecha == null) ? 0 : tipoFecha.hashCode());
		result = prime * result + ((tipoSancion == null) ? 0 : tipoSancion.hashCode());
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
		BusquedaSancionesSearchDTO other = (BusquedaSancionesSearchDTO) obj;
		if (chkArchivadas == null) {
			if (other.chkArchivadas != null)
				return false;
		} else if (!chkArchivadas.equals(other.chkArchivadas))
			return false;
		if (chkFirmeza != other.chkFirmeza)
			return false;
		if (chkRehabilitado != other.chkRehabilitado)
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaArchivadaDesdeDate == null) {
			if (other.fechaArchivadaDesdeDate != null)
				return false;
		} else if (!fechaArchivadaDesdeDate.equals(other.fechaArchivadaDesdeDate))
			return false;
		if (fechaArchivadaHastaDate == null) {
			if (other.fechaArchivadaHastaDate != null)
				return false;
		} else if (!fechaArchivadaHastaDate.equals(other.fechaArchivadaHastaDate))
			return false;
		if (fechaDesdeDate == null) {
			if (other.fechaDesdeDate != null)
				return false;
		} else if (!fechaDesdeDate.equals(other.fechaDesdeDate))
			return false;
		if (fechaHastaDate == null) {
			if (other.fechaHastaDate != null)
				return false;
		} else if (!fechaHastaDate.equals(other.fechaHastaDate))
			return false;
		if (!Arrays.equals(idColegios, other.idColegios))
			return false;
		if (nif == null) {
			if (other.nif != null)
				return false;
		} else if (!nif.equals(other.nif))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (origen == null) {
			if (other.origen != null)
				return false;
		} else if (!origen.equals(other.origen))
			return false;
		if (primerApellido == null) {
			if (other.primerApellido != null)
				return false;
		} else if (!primerApellido.equals(other.primerApellido))
			return false;
		if (refColegio == null) {
			if (other.refColegio != null)
				return false;
		} else if (!refColegio.equals(other.refColegio))
			return false;
		if (refConsejo == null) {
			if (other.refConsejo != null)
				return false;
		} else if (!refConsejo.equals(other.refConsejo))
			return false;
		if (segundoApellido == null) {
			if (other.segundoApellido != null)
				return false;
		} else if (!segundoApellido.equals(other.segundoApellido))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (tipoFecha == null) {
			if (other.tipoFecha != null)
				return false;
		} else if (!tipoFecha.equals(other.tipoFecha))
			return false;
		if (tipoSancion == null) {
			if (other.tipoSancion != null)
				return false;
		} else if (!tipoSancion.equals(other.tipoSancion))
			return false;
		return true;
	}
	
	
	
	@Override
	public String toString() {
		return "BusquedaSancionesSearchDTO [nif=" + nif + ", nombre=" + nombre + ", primerApellido=" + primerApellido
				+ ", segundoApellido=" + segundoApellido + ", idColegios=" + Arrays.toString(idColegios)
				+ ", tipoSancion=" + tipoSancion + ", refConsejo=" + refConsejo + ", refColegio=" + refColegio
				+ ", chkRehabilitado=" + chkRehabilitado + ", fechaDesdeDate=" + fechaDesdeDate + ", fechaHastaDate="
				+ fechaHastaDate + ", chkArchivadas=" + chkArchivadas + ", fechaArchivadaDesdeDate="
				+ fechaArchivadaDesdeDate + ", fechaArchivadaHastaDate=" + fechaArchivadaHastaDate + ", tipoFecha="
				+ tipoFecha + ", chkFirmeza=" + chkFirmeza + ", tipo=" + tipo + ", origen=" + origen + ", estado="
				+ estado + "]";
	}
	/**
	* Convert the given object to string with each line indented by 4 spaces
	* (except the first line).
	*/
	private String toIndentedString(java.lang.Object o) {
	    if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	}
	
}
