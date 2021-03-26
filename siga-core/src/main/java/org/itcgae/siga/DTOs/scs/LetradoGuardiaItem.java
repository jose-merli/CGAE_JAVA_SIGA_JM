package org.itcgae.siga.DTOs.scs;

public class LetradoGuardiaItem {

	private String activo;
	private String idPersona;
	private String nombre;
	private String apellidos1;
	private String apellidos2;
	private String alfabeticoApellidos;
	private String numeroColegiado;
	private String idGrupoGuardiaColegiado;
	private String grupo;
	private String numeroGrupo;
	private String ordenGrupo;

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos1() {
		return apellidos1;
	}

	public void setApellidos1(String apellidos1) {
		this.apellidos1 = apellidos1;
	}

	public String getApellidos2() {
		return apellidos2;
	}

	public void setApellidos2(String apellidos2) {
		this.apellidos2 = apellidos2;
	}

	public String getAlfabeticoApellidos() {
		return alfabeticoApellidos;
	}

	public void setAlfabeticoApellidos(String alfabeticoApellidos) {
		this.alfabeticoApellidos = alfabeticoApellidos;
	}

	public String getNumeroColegiado() {
		return numeroColegiado;
	}

	public void setNumeroColegiado(String numeroColegiado) {
		this.numeroColegiado = numeroColegiado;
	}

	public String getIdGrupoGuardiaColegiado() {
		return idGrupoGuardiaColegiado;
	}

	public void setIdGrupoGuardiaColegiado(String idGrupoGuardiaColegiado) {
		this.idGrupoGuardiaColegiado = idGrupoGuardiaColegiado;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getNumeroGrupo() {
		return numeroGrupo;
	}

	public void setNumeroGrupo(String numeroGrupo) {
		this.numeroGrupo = numeroGrupo;
	}

	public String getOrdenGrupo() {
		return ordenGrupo;
	}

	public void setOrdenGrupo(String ordenGrupo) {
		this.ordenGrupo = ordenGrupo;
	}

	@Override
	public String toString() {
		return "LetradosGuardiaItem [activo=" + activo + ", idPersona=" + idPersona + ", nombre=" + nombre
				+ ", apellidos1=" + apellidos1 + ", apellidos2=" + apellidos2 + ", alfabeticoApellidos="
				+ alfabeticoApellidos + ", numeroColegiado=" + numeroColegiado + ", idGrupoGuardiaColegiado="
				+ idGrupoGuardiaColegiado + ", grupo=" + grupo + ", numeroGrupo=" + numeroGrupo + ", ordenGrupo="
				+ ordenGrupo + "]";
	}

}
