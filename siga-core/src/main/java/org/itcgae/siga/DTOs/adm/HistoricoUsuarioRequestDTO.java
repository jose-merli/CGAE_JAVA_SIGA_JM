package org.itcgae.siga.DTOs.adm;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2018-03-15T12:50:09.033+01:00")
public class HistoricoUsuarioRequestDTO {

	private String usuario;
	private String usuarioAutomatico;
	private String idTipoAccion;
	private String idPersona; // campo front Persona
	private String idPersonaReal;
	//@JsonFormat(pattern = "dd/MM/yy")
	//@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaDesde;
	//@JsonFormat(pattern = "dd/MM/yy")
	//@JsonFormat(pattern = "dd-MM-yyyy")
	private Date fechaHasta;
	private String idInstitucion;
	private String idLenguaje;
	
	
	
	/**
	**/
	public HistoricoUsuarioRequestDTO usuario(String usuario) {
		this.usuario = usuario;
		return this;
	}
	
	
	@JsonProperty("usuario")
	public String getUsuario() {
		return usuario;
	}

	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	
	public String getIdPersonaReal() {
		return idPersonaReal;
	}


	public void setIdPersonaReal(String idPersonaReal) {
		this.idPersonaReal = idPersonaReal;
	}


	/**
	**/
	public HistoricoUsuarioRequestDTO usuarioAutomatico(String usuarioAutomatico) {
		this.usuarioAutomatico = usuarioAutomatico;
		return this;
	}
	
	
	@JsonProperty("usuarioAutomatico")
	public String getUsuarioAutomatico() {
		return usuarioAutomatico;
	}
	

	public void setUsuarioAutomatico(String usuarioAutomatico) {
		this.usuarioAutomatico = usuarioAutomatico;
	}

	
	/**
	**/
	public HistoricoUsuarioRequestDTO idTipoAccion(String idTipoAccion) {
		this.idTipoAccion = idTipoAccion;
		return this;
	}
	
	
	@JsonProperty("idTipoAccion")
	public String getIdTipoAccion() {
		return idTipoAccion;
	}

	
	public void setIdTipoAccion(String idTipoAccion) {
		this.idTipoAccion = idTipoAccion;
	}

	
	/**
	**/
	public HistoricoUsuarioRequestDTO idPersona(String idPersona) {
		this.idPersona = idPersona;
		return this;
	}
	
	
	
	@JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}

	
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}


	/**
	**/
	public HistoricoUsuarioRequestDTO fechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
		return this;
	}
	
	
	
	@JsonProperty("fechaDesde")
	public Date getFechaDesde() {
		return fechaDesde;
	}
	

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	
	/**
	**/
	public HistoricoUsuarioRequestDTO fechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
		return this;
	}
	
	
	@JsonProperty("fechaHasta")
	public Date getFechaHasta() {
		return fechaHasta;
	}

	
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	
	/**
	**/
	public HistoricoUsuarioRequestDTO idInstitucion(String idInstitucion) {
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
	**/
	public HistoricoUsuarioRequestDTO idLenguaje(String idLenguaje) {
		this.idLenguaje = idLenguaje;
		return this;
	}
	
	
	@JsonProperty("idLenguaje")
	public String getIdLenguaje() {
		return idLenguaje;
	}

	public void setIdLenguaje(String idLenguaje) {
		this.idLenguaje = idLenguaje;
	}

	

	
	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		HistoricoUsuarioRequestDTO historicoUsuarioRequestDTO = (HistoricoUsuarioRequestDTO) o;
		return Objects.equals(this.usuario, historicoUsuarioRequestDTO.usuario) && 
				Objects.equals(this.usuarioAutomatico, historicoUsuarioRequestDTO.usuarioAutomatico) &&
				Objects.equals(this.idTipoAccion, historicoUsuarioRequestDTO.idTipoAccion) &&
				Objects.equals(this.idPersona, historicoUsuarioRequestDTO.idPersona) &&
				Objects.equals(this.fechaDesde, historicoUsuarioRequestDTO.fechaDesde) &&
				Objects.equals(this.fechaHasta, historicoUsuarioRequestDTO.fechaHasta) &&
				Objects.equals(this.idInstitucion, historicoUsuarioRequestDTO.idInstitucion) &&
				Objects.equals(this.idLenguaje, historicoUsuarioRequestDTO.idLenguaje);
	}

	@Override
	public int hashCode() {
		return Objects.hash(usuario, usuarioAutomatico, idTipoAccion, idPersona, fechaDesde, fechaHasta,idInstitucion,idLenguaje);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class HistoricoUsuarioRequestDTO {\n");

		sb.append("    usuario: ").append(toIndentedString(usuario)).append("\n");
		sb.append("    usuarioAtutomatico: ").append(toIndentedString(usuarioAutomatico)).append("\n");
		sb.append("    idTipoAccion: ").append(toIndentedString(idTipoAccion)).append("\n");
		sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
		sb.append("    fechaDesde: ").append(toIndentedString(fechaDesde)).append("\n");
		sb.append("    fechaHasta: ").append(toIndentedString(fechaHasta)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    idLenguaje: ").append(toIndentedString(idLenguaje)).append("\n");
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
