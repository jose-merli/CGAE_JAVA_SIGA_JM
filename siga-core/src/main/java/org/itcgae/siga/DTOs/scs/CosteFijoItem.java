package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.ComboItem;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CosteFijoItem {

	private String idCosteFijo;
	private String descripcion;
	private String importe;
	private String idInstitucion;
	private Long usuModificacion;
	private Date fechaModificacion;
	private String fechaBaja;
	private String idTipoAsistencia;
	private String idTipoActuacion;
	private String tipoAsistencia;
	private String tipoActuacion;
	private ComboItem [] opcionTipoActuacion;
	private String idCosteFijoOld;
	private String idTipoAsistenciaOld;
	private String idTipoActuacionOld;
	
	/**
	 **/
	public CosteFijoItem idCosteFijo(String idCosteFijo) {
		this.idCosteFijo = idCosteFijo;
		return this;
	}

	@JsonProperty("idCosteFijo")
	public String getIdCosteFijo() {
		return idCosteFijo;
	}

	public void setIdCosteFijo(String idCosteFijo) {
		this.idCosteFijo = idCosteFijo;
	}

	/**
	 * 
	 **/
	public CosteFijoItem descripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	@JsonProperty("descripcion")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * 
	 **/
	public CosteFijoItem importe(String importe) {
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
	public CosteFijoItem idInstitucion(String idInstitucion) {
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
	public CosteFijoItem usuModificacion(Long usuModificacion) {
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
	public CosteFijoItem fechaModificacion(Date fechaModificacion) {
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
	public CosteFijoItem fechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
		return this;
	}

	@JsonProperty("fechaBaja")
	public String getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	/**
	 * 
	 **/
	public CosteFijoItem idTipoAsistencia(String idTipoAsistencia) {
		this.idTipoAsistencia = idTipoAsistencia;
		return this;
	}

	@JsonProperty("idTipoAsistencia")
	public String getIdTipoAsistencia() {
		return idTipoAsistencia;
	}

	public void setIdTipoAsistencia(String idTipoAsistencia) {
		this.idTipoAsistencia = idTipoAsistencia;
	}

	/**
	 **/
	public CosteFijoItem idTipoActuacion(String idTipoActuacion) {
		this.idTipoActuacion = idTipoActuacion;
		return this;
	}
	
	@JsonProperty("idTipoActuacion")
	public String getIdTipoActuacion() {
		return idTipoActuacion;
	}

	public void setIdTipoActuacion(String idTipoActuacion) {
		this.idTipoActuacion = idTipoActuacion;
	}

	/**
	 **/
	public CosteFijoItem tipoAsistencia(String tipoAsistencia) {
		this.tipoAsistencia = tipoAsistencia;
		return this;
	}	
	
	@JsonProperty("tipoAsistencia")
	public String getTipoAsistencia() {
		return tipoAsistencia;
	}

	public void setTipoAsistencia(String tipoAsistencia) {
		this.tipoAsistencia = tipoAsistencia;
	}
	
	/**
	 **/
	public CosteFijoItem tipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
		return this;
	}
	
	@JsonProperty("tipoActuacion")
	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	/**
	 **/
	public CosteFijoItem opcionTipoActuacion(ComboItem [] opcionTipoActuacion) {
		this.opcionTipoActuacion = opcionTipoActuacion;
		return this;
	}
	
	@JsonProperty("opcionTipoActuacion")
	public ComboItem [] getOpcionTipoActuacion() {
		return opcionTipoActuacion;
	}

	public void setOpcionTipoActuacion(ComboItem [] opcionTipoActuacion) {
		this.opcionTipoActuacion = opcionTipoActuacion;
	}

	/**
	 **/
	public CosteFijoItem idCosteFijoOld(String idCosteFijoOld) {
		this.idCosteFijoOld = idCosteFijoOld;
		return this;
	}
	
	@JsonProperty("idCosteFijoOld")	
	public String getIdCosteFijoOld() {
		return idCosteFijoOld;
	}

	public void setIdCosteFijoOld(String idCosteFijoOld) {
		this.idCosteFijoOld = idCosteFijoOld;
	}

	/**
	 **/
	public CosteFijoItem idTipoAsistenciaOld(String idTipoAsistenciaOld) {
		this.idTipoAsistenciaOld = idTipoAsistenciaOld;
		return this;
	}
	
	@JsonProperty("idTipoAsistenciaOld")	
	public String getIdTipoAsistenciaOld() {
		return idTipoAsistenciaOld;
	}

	public void setIdTipoAsistenciaOld(String idTipoAsistenciaOld) {
		this.idTipoAsistenciaOld = idTipoAsistenciaOld;
	}

	/**
	 **/
	public CosteFijoItem idTipoActuacionOld(String idTipoActuacionOld) {
		this.idTipoActuacionOld = idTipoActuacionOld;
		return this;
	}
	
	@JsonProperty("idTipoActuacionOld")	
	public String getIdTipoActuacionOld() {
		return idTipoActuacionOld;
	}

	public void setIdTipoActuacionOld(String idTipoActuacionOld) {
		this.idTipoActuacionOld = idTipoActuacionOld;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CosteFijoItem juzgadoItem = (CosteFijoItem) o;
		return Objects.equals(this.idCosteFijo, juzgadoItem.idCosteFijo)
				&& Objects.equals(this.descripcion, juzgadoItem.descripcion)
				&& Objects.equals(this.importe, juzgadoItem.importe)
				&& Objects.equals(this.usuModificacion, juzgadoItem.usuModificacion)
				&& Objects.equals(this.fechaModificacion, juzgadoItem.fechaModificacion)
				&& Objects.equals(this.fechaBaja, juzgadoItem.fechaBaja)
				&& Objects.equals(this.idTipoAsistencia, juzgadoItem.idTipoAsistencia)
				&& Objects.equals(this.idInstitucion, juzgadoItem.idInstitucion)
				&& Objects.equals(this.idTipoActuacion, juzgadoItem.idTipoActuacion)
				&& Objects.equals(this.tipoAsistencia, juzgadoItem.tipoAsistencia)
				&& Objects.equals(this.tipoActuacion, juzgadoItem.tipoActuacion)
				&& Objects.equals(this.opcionTipoActuacion, juzgadoItem.opcionTipoActuacion)
				&& Objects.equals(this.idTipoAsistenciaOld, juzgadoItem.idTipoAsistenciaOld)
				&& Objects.equals(this.idCosteFijoOld, juzgadoItem.idCosteFijoOld)
				&& Objects.equals(this.idTipoActuacionOld, juzgadoItem.idTipoActuacionOld);
	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(idCosteFijo, descripcion, importe, usuModificacion, fechaModificacion, fechaBaja, idTipoAsistencia,
				idInstitucion, idTipoActuacion, tipoAsistencia, tipoActuacion, opcionTipoActuacion, idTipoAsistenciaOld, idCosteFijoOld, idTipoActuacionOld );
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ProcedimientoItem {\n");

		sb.append("    idProcedimiento: ").append(toIndentedString(idCosteFijo)).append("\n");
		sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
		sb.append("    importe: ").append(toIndentedString(importe)).append("\n");
		sb.append("    usuModificacion: ").append(toIndentedString(usuModificacion)).append("\n");
		sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
		sb.append("    fechaBaja: ").append(toIndentedString(fechaBaja)).append("\n");
		sb.append("    idTipoAsistencia: ").append(toIndentedString(idTipoAsistencia)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    idTipoActuacion: ").append(toIndentedString(idTipoActuacion)).append("\n");
		sb.append("    tipoAsistencia: ").append(toIndentedString(tipoAsistencia)).append("\n");
		sb.append("    tipoActuacion: ").append(toIndentedString(tipoActuacion)).append("\n");
		sb.append("    opcionTipoActuacion: ").append(toIndentedString(opcionTipoActuacion)).append("\n");
		sb.append("    idTipoAsistenciaOld: ").append(toIndentedString(idTipoAsistenciaOld)).append("\n");
		sb.append("    idCosteFijoOld: ").append(toIndentedString(idCosteFijoOld)).append("\n");
		sb.append("    idTipoActuacionOld: ").append(toIndentedString(idTipoActuacionOld)).append("\n");

		
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
