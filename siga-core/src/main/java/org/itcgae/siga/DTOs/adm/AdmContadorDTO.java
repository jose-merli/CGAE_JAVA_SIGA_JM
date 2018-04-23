package org.itcgae.siga.DTOs.adm;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AdmContadorDTO  {


	private String idinstitucion;
	private String idcontador;
	private String nombre;
	private String descripcion;
	private String modificablecontador;
	private Short modo;
	private Long contador;
	private String prefijo;
	private String sufijo;
	private Integer longitudcontador;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechareconfiguracion;
	private String reconfiguracioncontador;
	private String reconfiguracionprefijo;
	private String reconfiguracionsufijo;
	private String idtabla;
	private String idcampocontador;
	private String idcampoprefijo;
	private String idcamposufijo;
	private Short idmodulo;
	private String general;
    @JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechamodificacion;
	private Integer usumodificacion;
	 @JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechacreacion;
	private Integer usucreacion;
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getModificablecontador() {
		return modificablecontador;
	}


	public void setModificablecontador(String modificablecontador) {
		this.modificablecontador = modificablecontador;
	}


	public Short getModo() {
		return modo;
	}


	public void setModo(Short modo) {
		this.modo = modo;
	}


	public Long getContador() {
		return contador;
	}


	public void setContador(Long contador) {
		this.contador = contador;
	}


	public String getPrefijo() {
		return prefijo;
	}


	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}


	public String getSufijo() {
		return sufijo;
	}


	public void setSufijo(String sufijo) {
		this.sufijo = sufijo;
	}


	public Integer getLongitudcontador() {
		return longitudcontador;
	}


	public void setLongitudcontador(Integer longitudcontador) {
		this.longitudcontador = longitudcontador;
	}


	public Date getFechareconfiguracion() {
		return fechareconfiguracion;
	}


	public void setFechareconfiguracion(Date fechareconfiguracion) {
		this.fechareconfiguracion = fechareconfiguracion;
	}


	public String getReconfiguracioncontador() {
		return reconfiguracioncontador;
	}


	public void setReconfiguracioncontador(String reconfiguracioncontador) {
		this.reconfiguracioncontador = reconfiguracioncontador;
	}


	public String getReconfiguracionprefijo() {
		return reconfiguracionprefijo;
	}


	public void setReconfiguracionprefijo(String reconfiguracionprefijo) {
		this.reconfiguracionprefijo = reconfiguracionprefijo;
	}


	public String getReconfiguracionsufijo() {
		return reconfiguracionsufijo;
	}


	public void setReconfiguracionsufijo(String reconfiguracionsufijo) {
		this.reconfiguracionsufijo = reconfiguracionsufijo;
	}


	public String getIdtabla() {
		return idtabla;
	}


	public void setIdtabla(String idtabla) {
		this.idtabla = idtabla;
	}


	public String getIdcampocontador() {
		return idcampocontador;
	}


	public void setIdcampocontador(String idcampocontador) {
		this.idcampocontador = idcampocontador;
	}


	public String getIdcampoprefijo() {
		return idcampoprefijo;
	}


	public void setIdcampoprefijo(String idcampoprefijo) {
		this.idcampoprefijo = idcampoprefijo;
	}


	public String getIdcamposufijo() {
		return idcamposufijo;
	}


	public void setIdcamposufijo(String idcamposufijo) {
		this.idcamposufijo = idcamposufijo;
	}


	public Short getIdmodulo() {
		return idmodulo;
	}


	public void setIdmodulo(Short idmodulo) {
		this.idmodulo = idmodulo;
	}


	public String getGeneral() {
		return general;
	}


	public void setGeneral(String general) {
		this.general = general;
	}


	public Date getFechamodificacion() {
		return fechamodificacion;
	}


	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}


	public Integer getUsumodificacion() {
		return usumodificacion;
	}


	public void setUsumodificacion(Integer usumodificacion) {
		this.usumodificacion = usumodificacion;
	}


	public Date getFechacreacion() {
		return fechacreacion;
	}


	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}


	public Integer getUsucreacion() {
		return usucreacion;
	}


	public void setUsucreacion(Integer usucreacion) {
		this.usucreacion = usucreacion;
	}
	


	public String getIdinstitucion() {
		return idinstitucion;
	}


	public void setIdinstitucion(String idinstitucion) {
		this.idinstitucion = idinstitucion;
	}


	public String getIdcontador() {
		return idcontador;
	}


	public void setIdcontador(String idcontador) {
		this.idcontador = idcontador;
	}
	
	
	

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		AdmContadorDTO admContadorDTO = (AdmContadorDTO) o;
		return  Objects.equals(this.idinstitucion, admContadorDTO.idinstitucion) &&
				Objects.equals(this.idcontador, admContadorDTO.idcontador) &&
				Objects.equals(this.nombre, admContadorDTO.nombre) &&
				Objects.equals(this.descripcion, admContadorDTO.descripcion) &&
				Objects.equals(this.modificablecontador, admContadorDTO.modificablecontador) &&
				Objects.equals(this.modo, admContadorDTO.modo) &&
				Objects.equals(this.contador, admContadorDTO.contador) &&
				Objects.equals(this.prefijo, admContadorDTO.prefijo) &&
				Objects.equals(this.sufijo, admContadorDTO.sufijo) &&
				Objects.equals(this.idcontador, admContadorDTO.idcontador) &&
				Objects.equals(this.fechareconfiguracion, admContadorDTO.fechareconfiguracion) &&
				Objects.equals(this.reconfiguracioncontador, admContadorDTO.reconfiguracioncontador) &&
				Objects.equals(this.reconfiguracionprefijo, admContadorDTO.reconfiguracionprefijo) &&
				Objects.equals(this.reconfiguracionsufijo, admContadorDTO.reconfiguracionsufijo) &&
				Objects.equals(this.idtabla, admContadorDTO.idtabla) &&
				Objects.equals(this.idcampocontador, admContadorDTO.idcampocontador) &&
				Objects.equals(this.idcampoprefijo, admContadorDTO.idcampoprefijo) &&
				Objects.equals(this.idcamposufijo, admContadorDTO.idcamposufijo) &&
				Objects.equals(this.idmodulo, admContadorDTO.idmodulo) &&
				Objects.equals(this.general, admContadorDTO.general) &&
				Objects.equals(this.fechamodificacion, admContadorDTO.fechamodificacion) &&
				Objects.equals(this.usumodificacion, admContadorDTO.usumodificacion) &&
				Objects.equals(this.fechacreacion, admContadorDTO.fechacreacion) &&
				Objects.equals(this.usucreacion, admContadorDTO.usucreacion) ;
				
	}

	@Override
	public int hashCode() {
		return Objects.hash(idinstitucion,idcontador,nombre,descripcion,modificablecontador,modo,contador,prefijo,sufijo,longitudcontador,fechareconfiguracion,reconfiguracioncontador,
				reconfiguracionprefijo,reconfiguracionsufijo,idtabla,idcampocontador,idcampoprefijo,idcamposufijo,idmodulo,general,fechamodificacion,usumodificacion,fechacreacion,usucreacion);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ParametroRequestDTO {\n");

		sb.append("    idinstitucion: ").append(toIndentedString(idinstitucion)).append("\n");
		sb.append("    idcontador: ").append(toIndentedString(idcontador)).append("\n");
		sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
		sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
		sb.append("    modificablecontador: ").append(toIndentedString(modificablecontador)).append("\n");
		sb.append("    modo: ").append(toIndentedString(modo)).append("\n");
		sb.append("    contador: ").append(toIndentedString(contador)).append("\n");
		sb.append("    prefijo: ").append(toIndentedString(prefijo)).append("\n");
		sb.append("    sufijo: ").append(toIndentedString(sufijo)).append("\n");
		sb.append("    longitudcontador: ").append(toIndentedString(longitudcontador)).append("\n");
		sb.append("    fechareconfiguracion: ").append(toIndentedString(fechareconfiguracion)).append("\n");
		sb.append("    reconfiguracioncontador: ").append(toIndentedString(reconfiguracioncontador)).append("\n");
		sb.append("    reconfiguracionprefijo: ").append(toIndentedString(reconfiguracionprefijo)).append("\n");
		sb.append("    reconfiguracionsufijo: ").append(toIndentedString(reconfiguracionsufijo)).append("\n");
		sb.append("    idtabla: ").append(toIndentedString(idtabla)).append("\n");
		sb.append("    idcampocontador: ").append(toIndentedString(idcampocontador)).append("\n");
		sb.append("    idcampoprefijo: ").append(toIndentedString(idcampoprefijo)).append("\n");
		sb.append("    idcamposufijo: ").append(toIndentedString(idcamposufijo)).append("\n");
		sb.append("    idmodulo: ").append(toIndentedString(idmodulo)).append("\n");
		sb.append("    general: ").append(toIndentedString(general)).append("\n");
		sb.append("    fechamodificacion: ").append(toIndentedString(fechamodificacion)).append("\n");
		sb.append("    usumodificacion: ").append(toIndentedString(usumodificacion)).append("\n");
		sb.append("    fechacreacion: ").append(toIndentedString(fechacreacion)).append("\n");
		sb.append("    usucreacion: ").append(toIndentedString(usucreacion)).append("\n");
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