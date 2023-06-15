package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProcedimientoItem {

	private String idProcedimiento;
	private String nombre;
	private String importe;
	private String idInstitucion;
	private Long usuModificacion;
	private Date fechaModificacion;
	private String jurisdiccion;
	private String codigo;
	private String idJuzgado;
	private Date fechadesdevigor;
	private Date fechahastavigor;
	

	/**
	 **/
	public ProcedimientoItem idProcedimiento(String idProcedimiento) {
		this.idProcedimiento = idProcedimiento;
		return this;
	}

	public Date getFechadesdevigor() {
		return fechadesdevigor;
	}

	public void setFechadesdevigor(Date fechadesdevigor) {
		this.fechadesdevigor = fechadesdevigor;
	}

	public Date getFechahastavigor() {
		return fechahastavigor;
	}

	public void setFechahastavigor(Date fechahastavigor) {
		this.fechahastavigor = fechahastavigor;
	}

	@JsonProperty("idProcedimiento")
	public String getIdProcedimiento() {
		return idProcedimiento;
	}

	public void setIdProcedimiento(String idProcedimiento) {
		this.idProcedimiento = idProcedimiento;
	}

	/**
	 * 
	 **/
	public ProcedimientoItem nombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	@JsonProperty("nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * 
	 **/
	public ProcedimientoItem importe(String importe) {
		this.importe = importe;
		return this;
	}

	@JsonProperty("importe")
	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}
	
	/**
	 * 
	 **/
	public ProcedimientoItem idInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
		return this;
	}

	@JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	
	

	/**
	 * 
	 **/
	public ProcedimientoItem usuModificacion(Long usuModificacion) {
		this.usuModificacion = usuModificacion;
		return this;
	}

	@JsonProperty("usuModificacion")
	public Long getUsuModificacion() {
		return usuModificacion;
	}

	public void setUsuModificacion(Long usuModificacion) {
		this.usuModificacion = usuModificacion;
	}

	/**
	 * 
	 **/
	public ProcedimientoItem fechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
		return this;
	}

	@JsonProperty("fechaModificacion")
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	/**
	 * 
	 **/
	public ProcedimientoItem jurisdiccion(String jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
		return this;
	}

	@JsonProperty("jurisdiccion")
	public String getJurisdiccion() {
		return jurisdiccion;
	}

	public void setJurisdiccion(String jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}

	/**
	 **/
	public ProcedimientoItem codigo(String codigo) {
		this.codigo = codigo;
		return this;
	}
	
	@JsonProperty("codigo")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 **/
	public ProcedimientoItem idJuzgado(String idJuzgado) {
		this.idJuzgado = idJuzgado;
		return this;
	}	
	
	public String getIdJuzgado() {
		return idJuzgado;
	}

	public void setIdJuzgado(String idJuzgado) {
		this.idJuzgado = idJuzgado;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ProcedimientoItem juzgadoItem = (ProcedimientoItem) o;
		return Objects.equals(this.idProcedimiento, juzgadoItem.idProcedimiento)
				&& Objects.equals(this.nombre, juzgadoItem.nombre)
				&& Objects.equals(this.importe, juzgadoItem.importe)
				&& Objects.equals(this.usuModificacion, juzgadoItem.usuModificacion)
				&& Objects.equals(this.fechaModificacion, juzgadoItem.fechaModificacion)
				&& Objects.equals(this.jurisdiccion, juzgadoItem.jurisdiccion)
				&& Objects.equals(this.idInstitucion, juzgadoItem.idInstitucion)
				&& Objects.equals(this.codigo, juzgadoItem.codigo)
				&& Objects.equals(this.idJuzgado, juzgadoItem.idJuzgado);
	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(idProcedimiento, nombre, importe, usuModificacion, fechaModificacion, jurisdiccion,
				idInstitucion, codigo, idJuzgado);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ProcedimientoItem {\n");

		sb.append("    idProcedimiento: ").append(toIndentedString(idProcedimiento)).append("\n");
		sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
		sb.append("    importe: ").append(toIndentedString(importe)).append("\n");
		sb.append("    usuModificacion: ").append(toIndentedString(usuModificacion)).append("\n");
		sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
		sb.append("    jurisdiccion: ").append(toIndentedString(jurisdiccion)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    codigo: ").append(toIndentedString(codigo)).append("\n");
		sb.append("    idJuzgado: ").append(toIndentedString(idJuzgado)).append("\n");

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
