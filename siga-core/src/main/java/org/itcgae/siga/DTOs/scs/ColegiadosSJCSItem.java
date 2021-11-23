package org.itcgae.siga.DTOs.scs;

import java.util.Arrays;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ColegiadosSJCSItem {

	private String idProcurador;
	private String nombre;
	private String apellidos;
	private String nColegiado;
	private String idEstado;
	private String descripcion;
	private String inscritoturno;
	private String inscritoguardia;
	private String guardiasPendientes;
	private String[] idTurno;
	private String idInstitucion;
	private Long usuModificacion;
	private Date fechaModificacion;
	private String codigoPostal;
	private String idPoblacion;
	private String idProvincia;
	private boolean historico;
	private Date fechaestado;
	private String nif;
	private String codigoExt;
	private String email;
	private String nombrePoblacion;
	private String[] idGuardia;
	private String telefono;
	private String abreviatura;
	private String idPersona;
	private String nComunitario;
	private boolean residente;
	private String tieneGuardia;
	private String tieneTurno;

	@JsonProperty("abreviatura")
	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	@JsonProperty("telefono")
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@JsonProperty("descripcion")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@JsonProperty("inscritoturno")
	public String getInscritoturno() {
		return inscritoturno;
	}

	public void setInscritoturno(String inscritoturno) {
		this.inscritoturno = inscritoturno;
	}

	@JsonProperty("inscritoguardia")
	public String getInscritoguardia() {
		return inscritoguardia;
	}

	public void setInscritoguardia(String inscritoguardia) {
		this.inscritoguardia = inscritoguardia;
	}

	@JsonProperty("guardiasPendientes")
	public String getGuardiasPendientes() {
		return guardiasPendientes;
	}

	public void setGuardiasPendientes(String guardiasPendientes) {
		this.guardiasPendientes = guardiasPendientes;
	}

	@JsonProperty("fechaestado")
	public Date getFechaestado() {
		return fechaestado;
	}

	public void setFechaestado(Date fechaestado) {
		this.fechaestado = fechaestado;
	}

	@JsonProperty("nif")
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	@JsonProperty("idGuardia")
	public String[] getIdGuardia() {
		return idGuardia;
	}

	public void setIdGuardia(String[] idGuardia) {
		this.idGuardia = idGuardia;
	}

	@JsonProperty("idTurno")
	public String[] getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(String[] idTurno) {
		this.idTurno = idTurno;
	}

	@JsonProperty("idProcurador")
	public String getIdProcurador() {
		return idProcurador;
	}

	public void setIdProcurador(String idProcurador) {
		this.idProcurador = idProcurador;
	}

	@JsonProperty("apellidos")
	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	@JsonProperty("nColegiado")
	public String getnColegiado() {
		return nColegiado;
	}

	public void setnColegiado(String nColegiado) {
		this.nColegiado = nColegiado;
	}

	@JsonProperty("idEstado")
	public String getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}

	public ColegiadosSJCSItem nombre(String nombre) {
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

	public ColegiadosSJCSItem idInstitucion(String idInstitucion) {
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
	public ColegiadosSJCSItem usuModificacion(Long usuModificacion) {
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
	public ColegiadosSJCSItem fechaModificacion(Date fechaModificacion) {
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
	public ColegiadosSJCSItem descripcionsubzona(String codigoPostal) {
		this.codigoPostal = codigoPostal;
		return this;
	}

	@JsonProperty("codigoPostal")
	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/**
	 * 
	 **/
	public ColegiadosSJCSItem idPoblacion(String idPoblacion) {
		this.idPoblacion = idPoblacion;
		return this;
	}

	@JsonProperty("idPoblacion")
	public String getIdPoblacion() {
		return idPoblacion;
	}

	public void setIdPoblacion(String idPoblacion) {
		this.idPoblacion = idPoblacion;
	}

	/**
	 * 
	 **/
	public ColegiadosSJCSItem idProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
		return this;
	}

	@JsonProperty("idProvincia")
	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	/**
	 * 
	 **/
	public ColegiadosSJCSItem historico(boolean historico) {
		this.historico = historico;
		return this;
	}

	@JsonProperty("historico")
	public boolean getHistorico() {
		return historico;
	}

	public void setHistorico(boolean historico) {
		this.historico = historico;
	}

	/**
	 **/
	public ColegiadosSJCSItem codigoExt(String codigoExt) {
		this.codigoExt = codigoExt;
		return this;
	}

	@JsonProperty("codigoExt")
	public String getCodigoExt() {
		return codigoExt;
	}

	public void setCodigoExt(String codigoExt) {
		this.codigoExt = codigoExt;
	}

	/**
	 **/

	/**
	 **/
	public ColegiadosSJCSItem email(String email) {
		this.email = email;
		return this;
	}

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 **/
	public ColegiadosSJCSItem nombrePoblacion(String nombrePoblacion) {
		this.nombrePoblacion = nombrePoblacion;
		return this;
	}

	@JsonProperty("nombrePoblacion")
	public String getNombrePoblacion() {
		return nombrePoblacion;
	}

	public void setNombrePoblacion(String nombrePoblacion) {
		this.nombrePoblacion = nombrePoblacion;
	}

	@JsonProperty("idPersona")
	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	@JsonProperty("residente")
	public boolean getResidente() {
		return residente;
	}

	public void setResidente(boolean residente) {
		this.residente = residente;
	}

	/**
	 * @return the nComunitario
	 */
	public String getnComunitario() {
		return nComunitario;
	}

	/**
	 * @param nComunitario the nComunitario to set
	 */
	public void setnComunitario(String nComunitario) {
		this.nComunitario = nComunitario;
	}
	


	/**
	 * @return the tieneGuardia
	 */
	public String getTieneGuardia() {
		return tieneGuardia;
	}

	/**
	 * @param tieneGuardia the tieneGuardia to set
	 */
	public void setTieneGuardia(String tieneGuardia) {
		this.tieneGuardia = tieneGuardia;
	}

	/**
	 * @return the tieneTurno
	 */
	public String getTieneTurno() {
		return tieneTurno;
	}

	/**
	 * @param tieneTurno the tieneTurno to set
	 */
	public void setTieneTurno(String tieneTurno) {
		this.tieneTurno = tieneTurno;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class PrisionItem {\n");

		sb.append("    idProcurador: ").append(toIndentedString(idProcurador)).append("\n");
		sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
		sb.append("    usuModificacion: ").append(toIndentedString(usuModificacion)).append("\n");
		sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
		sb.append("    codigoPostal: ").append(toIndentedString(codigoPostal)).append("\n");
		sb.append("    idPoblacion: ").append(toIndentedString(idPoblacion)).append("\n");
		sb.append("    idProvincia: ").append(toIndentedString(idProvincia)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    historico: ").append(toIndentedString(historico)).append("\n");
		sb.append("    codigoExt: ").append(toIndentedString(codigoExt)).append("\n");
		sb.append("    email: ").append(toIndentedString(email)).append("\n");
		sb.append("    nombrePoblacion: ").append(toIndentedString(nombrePoblacion)).append("\n");
		sb.append("    idPersona: ").append(toIndentedString(idPersona)).append("\n");
		sb.append("    nComunitario: ").append(toIndentedString(nComunitario)).append("\n");
		sb.append("    residente: ").append(toIndentedString(residente)).append("\n");
		sb.append("    tieneGuardia: ").append(toIndentedString(tieneGuardia)).append("\n");
		sb.append("    tieneTurno: ").append(toIndentedString(tieneTurno)).append("\n");

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abreviatura == null) ? 0 : abreviatura.hashCode());
		result = prime * result + ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result + ((codigoExt == null) ? 0 : codigoExt.hashCode());
		result = prime * result + ((codigoPostal == null) ? 0 : codigoPostal.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fechaModificacion == null) ? 0 : fechaModificacion.hashCode());
		result = prime * result + ((fechaestado == null) ? 0 : fechaestado.hashCode());
		result = prime * result + ((guardiasPendientes == null) ? 0 : guardiasPendientes.hashCode());
		result = prime * result + (historico ? 1231 : 1237);
		result = prime * result + ((idEstado == null) ? 0 : idEstado.hashCode());
		result = prime * result + Arrays.hashCode(idGuardia);
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((idPersona == null) ? 0 : idPersona.hashCode());
		result = prime * result + ((idPoblacion == null) ? 0 : idPoblacion.hashCode());
		result = prime * result + ((idProcurador == null) ? 0 : idProcurador.hashCode());
		result = prime * result + ((idProvincia == null) ? 0 : idProvincia.hashCode());
		result = prime * result + Arrays.hashCode(idTurno);
		result = prime * result + ((inscritoguardia == null) ? 0 : inscritoguardia.hashCode());
		result = prime * result + ((inscritoturno == null) ? 0 : inscritoturno.hashCode());
		result = prime * result + ((nColegiado == null) ? 0 : nColegiado.hashCode());
		result = prime * result + ((nComunitario == null) ? 0 : nComunitario.hashCode());
		result = prime * result + ((nif == null) ? 0 : nif.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((nombrePoblacion == null) ? 0 : nombrePoblacion.hashCode());
		result = prime * result + (residente ? 1231 : 1237);
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		result = prime * result + ((tieneGuardia == null) ? 0 : tieneGuardia.hashCode());
		result = prime * result + ((tieneTurno == null) ? 0 : tieneTurno.hashCode());
		result = prime * result + ((usuModificacion == null) ? 0 : usuModificacion.hashCode());
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
		ColegiadosSJCSItem other = (ColegiadosSJCSItem) obj;
		if (abreviatura == null) {
			if (other.abreviatura != null)
				return false;
		} else if (!abreviatura.equals(other.abreviatura))
			return false;
		if (apellidos == null) {
			if (other.apellidos != null)
				return false;
		} else if (!apellidos.equals(other.apellidos))
			return false;
		if (codigoExt == null) {
			if (other.codigoExt != null)
				return false;
		} else if (!codigoExt.equals(other.codigoExt))
			return false;
		if (codigoPostal == null) {
			if (other.codigoPostal != null)
				return false;
		} else if (!codigoPostal.equals(other.codigoPostal))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fechaModificacion == null) {
			if (other.fechaModificacion != null)
				return false;
		} else if (!fechaModificacion.equals(other.fechaModificacion))
			return false;
		if (fechaestado == null) {
			if (other.fechaestado != null)
				return false;
		} else if (!fechaestado.equals(other.fechaestado))
			return false;
		if (guardiasPendientes == null) {
			if (other.guardiasPendientes != null)
				return false;
		} else if (!guardiasPendientes.equals(other.guardiasPendientes))
			return false;
		if (historico != other.historico)
			return false;
		if (idEstado == null) {
			if (other.idEstado != null)
				return false;
		} else if (!idEstado.equals(other.idEstado))
			return false;
		if (!Arrays.equals(idGuardia, other.idGuardia))
			return false;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		if (idPersona == null) {
			if (other.idPersona != null)
				return false;
		} else if (!idPersona.equals(other.idPersona))
			return false;
		if (idPoblacion == null) {
			if (other.idPoblacion != null)
				return false;
		} else if (!idPoblacion.equals(other.idPoblacion))
			return false;
		if (idProcurador == null) {
			if (other.idProcurador != null)
				return false;
		} else if (!idProcurador.equals(other.idProcurador))
			return false;
		if (idProvincia == null) {
			if (other.idProvincia != null)
				return false;
		} else if (!idProvincia.equals(other.idProvincia))
			return false;
		if (!Arrays.equals(idTurno, other.idTurno))
			return false;
		if (inscritoguardia == null) {
			if (other.inscritoguardia != null)
				return false;
		} else if (!inscritoguardia.equals(other.inscritoguardia))
			return false;
		if (inscritoturno == null) {
			if (other.inscritoturno != null)
				return false;
		} else if (!inscritoturno.equals(other.inscritoturno))
			return false;
		if (nColegiado == null) {
			if (other.nColegiado != null)
				return false;
		} else if (!nColegiado.equals(other.nColegiado))
			return false;
		if (nComunitario == null) {
			if (other.nComunitario != null)
				return false;
		} else if (!nComunitario.equals(other.nComunitario))
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
		if (nombrePoblacion == null) {
			if (other.nombrePoblacion != null)
				return false;
		} else if (!nombrePoblacion.equals(other.nombrePoblacion))
			return false;
		if (residente != other.residente)
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		if (tieneGuardia == null) {
			if (other.tieneGuardia != null)
				return false;
		} else if (!tieneGuardia.equals(other.tieneGuardia))
			return false;
		if (tieneTurno == null) {
			if (other.tieneTurno != null)
				return false;
		} else if (!tieneTurno.equals(other.tieneTurno))
			return false;
		if (usuModificacion == null) {
			if (other.usuModificacion != null)
				return false;
		} else if (!usuModificacion.equals(other.usuModificacion))
			return false;
		return true;
	}
}