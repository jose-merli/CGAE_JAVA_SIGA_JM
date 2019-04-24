package org.itcgae.siga.DTOs.cen;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatosPersonaMutualidadDTO {

	private String apellido1;
	private String apellido2;
	private String asistenciaSanitaria;
	private int idAsistenciaSanitaria;
	private String[] edadesHijos;
	private String colegio;
	private int ejerciente;
	private String estadoCivil;
	private Date fechaNacimiento;
	private Date fechaNacConyuge;
	private String idMutualista;
	private String idSolicitud;
	@JsonProperty("NIF")
	private String NIF;
	private String nacionalidad;
	private String nombre;
	private String NumColegiado;
	private String idTratamiento;
	private String tipoSolicitud;
	private String profesion;
	private String tipoIdentificacion;
	private String sexo;
	private int numHijos;
	
	
	public String getIdMutualista() {
		return idMutualista;
	}
	public void setIdMutualista(String idMutualista) {
		this.idMutualista = idMutualista;
	}
	public String getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String[] getEdadesHijos() {
		return edadesHijos;
	}
	public void setEdadesHijos(String[] edadesHijos) {
		this.edadesHijos = edadesHijos;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getColegio() {
		return colegio;
	}
	public void setColegio(String colegio) {
		this.colegio = colegio;
	}

	public int getEjerciente() {
		return ejerciente;
	}
	public void setEjerciente(int ejerciente) {
		this.ejerciente = ejerciente;
	}
	public String getAsistenciaSanitaria() {
		return asistenciaSanitaria;
	}
	public void setAsistenciaSanitaria(String asistenciaSanitaria) {
		this.asistenciaSanitaria = asistenciaSanitaria;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public Date getFechaNacConyuge() {
		return fechaNacConyuge;
	}
	public void setFechaNacConyuge(Date fechaNacConyuge) {
		this.fechaNacConyuge = fechaNacConyuge;
	}
	public String getNIF() {
		return NIF;
	}
	public void setNIF(String nIF) {
		this.NIF = nIF;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNumColegiado() {
		return NumColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		NumColegiado = numColegiado;
	}
	public String getProfesion() {
		return profesion;
	}
	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public int getNumHijos() {
		return numHijos;
	}
	public void setNumHijos(int numHijos) {
		this.numHijos = numHijos;
	}
	public String getIdTratamiento() {
		return idTratamiento;
	}
	public void setIdTratamiento(String idTratamiento) {
		this.idTratamiento = idTratamiento;
	}
	public String getTipoSolicitud() {
		return tipoSolicitud;
	}
	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	public int getIdAsistenciaSanitaria() {
		return idAsistenciaSanitaria;
	}
	public void setIdAsistenciaSanitaria(int idAsistenciaSanitaria) {
		this.idAsistenciaSanitaria = idAsistenciaSanitaria;
	}
	
}
