package org.itcgae.siga.DTOs.cen;

import java.sql.Date;
import java.util.Objects;

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
	
	
	@Override
	public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    BusquedaSancionesSearchDTO busquedaSancionesSearchDTO = (BusquedaSancionesSearchDTO) o;
	    return  Objects.equals(this.nif, busquedaSancionesSearchDTO.nif) &&
	    		Objects.equals(this.nombre, busquedaSancionesSearchDTO.nombre) &&
	    		Objects.equals(this.primerApellido, busquedaSancionesSearchDTO.primerApellido) &&
	    		Objects.equals(this.segundoApellido, busquedaSancionesSearchDTO.segundoApellido) &&
	    		Objects.equals(this.idColegios, busquedaSancionesSearchDTO.idColegios) &&
	    		Objects.equals(this.tipoSancion, busquedaSancionesSearchDTO.tipoSancion) &&
	    		Objects.equals(this.refColegio, busquedaSancionesSearchDTO.refColegio) && 
	    		Objects.equals(this.refConsejo, busquedaSancionesSearchDTO.refConsejo) && 
	    		Objects.equals(this.chkRehabilitado, busquedaSancionesSearchDTO.chkRehabilitado) && 
	    Objects.equals(this.fechaDesdeDate, busquedaSancionesSearchDTO.fechaDesdeDate) && 
	    Objects.equals(this.fechaHastaDate, busquedaSancionesSearchDTO.fechaHastaDate) && 
	    Objects.equals(this.chkArchivadas, busquedaSancionesSearchDTO.chkArchivadas) && 
	    Objects.equals(this.fechaArchivadaDesdeDate, busquedaSancionesSearchDTO.fechaArchivadaDesdeDate) && 
	    Objects.equals(this.fechaArchivadaHastaDate, busquedaSancionesSearchDTO.fechaArchivadaHastaDate) && 
	    Objects.equals(this.tipo, busquedaSancionesSearchDTO.tipo) && 
	    Objects.equals(this.origen, busquedaSancionesSearchDTO.origen) && 
	    Objects.equals(this.estado, busquedaSancionesSearchDTO.estado);
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(tipo, nif, nombre, primerApellido, segundoApellido, idColegios, tipoSancion, refColegio, chkRehabilitado, fechaDesdeDate, fechaHastaDate, chkArchivadas, fechaArchivadaDesdeDate, 
	    		fechaArchivadaHastaDate, tipo, origen, estado);
	}

	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class BusquedaSancionesSearchDTO {\n");
	    
	    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
	    sb.append("    nif: ").append(toIndentedString(nif)).append("\n");
	    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
	    sb.append("    primerApellido: ").append(toIndentedString(primerApellido)).append("\n");
	    sb.append("    segundoApellido: ").append(toIndentedString(segundoApellido)).append("\n");
	    sb.append("    idColegios: ").append(toIndentedString(idColegios)).append("\n");
	    sb.append("    tipoSancion: ").append(toIndentedString(tipoSancion)).append("\n");
	    sb.append("    refColegio: ").append(toIndentedString(refColegio)).append("\n");
	    sb.append("    refConsejo: ").append(toIndentedString(refConsejo)).append("\n");
	    sb.append("    chkRehabilitado: ").append(toIndentedString(chkRehabilitado)).append("\n");
	    sb.append("    fechaDesde: ").append(toIndentedString(fechaDesdeDate)).append("\n");
	    sb.append("    fechaHasta: ").append(toIndentedString(fechaHastaDate)).append("\n");
	    sb.append("    chkArchivadas: ").append(toIndentedString(chkArchivadas)).append("\n");
	    sb.append("    fechaArchivadaDesde: ").append(toIndentedString(fechaArchivadaDesdeDate)).append("\n");
	    sb.append("    fechaArchivadaHasta: ").append(toIndentedString(fechaArchivadaHastaDate)).append("\n");
	    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
	    sb.append("    origen: ").append(toIndentedString(origen)).append("\n");
	    sb.append("    estado: ").append(toIndentedString(estado)).append("\n");
	    sb.append("}");
	    return sb.toString();
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
