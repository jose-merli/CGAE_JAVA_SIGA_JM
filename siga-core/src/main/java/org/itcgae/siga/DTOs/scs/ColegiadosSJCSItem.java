package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.Objects;

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
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ColegiadosSJCSItem prisionItem = (ColegiadosSJCSItem) o;
		return Objects.equals(this.idProcurador, prisionItem.idProcurador)
				&& Objects.equals(this.nombre, prisionItem.nombre)
				&& Objects.equals(this.usuModificacion, prisionItem.usuModificacion)
				&& Objects.equals(this.fechaModificacion, prisionItem.fechaModificacion)
				&& Objects.equals(this.codigoPostal, prisionItem.codigoPostal)
				&& Objects.equals(this.idProvincia, prisionItem.idProvincia)
				&& Objects.equals(this.idInstitucion, prisionItem.idInstitucion)
				&& Objects.equals(this.historico, prisionItem.historico)
				&& Objects.equals(this.codigoExt, prisionItem.codigoExt)
				&& Objects.equals(this.email, prisionItem.email)
				&& Objects.equals(this.nombrePoblacion, prisionItem.nombrePoblacion)
				&& Objects.equals(this.idPersona, prisionItem.idPersona)
				&& Objects.equals(this.nComunitario, prisionItem.nComunitario)
				&& Objects.equals(this.residente, prisionItem.residente)
				&& Objects.equals(this.tieneGuardia, prisionItem.tieneGuardia)
				&& Objects.equals(this.tieneTurno, prisionItem.tieneTurno);

	}

	@Override
	public int hashCode() {
		return Objects.hash(idProcurador, nombre, usuModificacion, fechaModificacion, codigoPostal, idPoblacion,
				idProvincia, idInstitucion, historico, codigoExt, email, nombrePoblacion, idPersona, nComunitario,
				residente, tieneGuardia, tieneTurno);
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

}
