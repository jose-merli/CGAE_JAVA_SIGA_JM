package org.itcgae.siga.DTOs.scs;

import java.util.Date;

public class GestionInscripcion {

	private String idGuardia;
	private String idTurno;
	private String numeroGrupo;
	private String colegiado;
	private String colegiadoGrupo;
	private String letrado;
	private String nombreTurno;
	private String idZona;
	private String nombreZona;
	private String idSubzona;
	private String nombreSubzona;
	private String idArea;
	private String nombreArea;
	private String idMateria;
	private String nombreMateria;
	private String nombreGuardia;
	private String descripcionNombreGuardia;
	private String obligatoriedadInscripcion;
	private String descripcionObligatoriedad;
	private Date fechasolicitud;
	

	public String getIdGuardia() {
		return idGuardia;
	}

	public void setIdGuardia(String idGuardia) {
		this.idGuardia = idGuardia;
	}

	public String getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}

	public String getNumeroGrupo() {
		return numeroGrupo;
	}

	public void setNumeroGrupo(String numeroGrupo) {
		this.numeroGrupo = numeroGrupo;
	}

	public String getColegiado() {
		return colegiado;
	}

	public void setColegiado(String colegiado) {
		this.colegiado = colegiado;
	}

	public String getColegiadoGrupo() {
		return colegiadoGrupo;
	}

	public void setColegiadoGrupo(String colegiadoGrupo) {
		this.colegiadoGrupo = colegiadoGrupo;
	}

	public String getLetrado() {
		return letrado;
	}

	public void setLetrado(String letrado) {
		this.letrado = letrado;
	}

	public String getNombreTurno() {
		return nombreTurno;
	}

	public void setNombreTurno(String nombreTurno) {
		this.nombreTurno = nombreTurno;
	}

	public String getIdZona() {
		return idZona;
	}

	public void setIdZona(String idZona) {
		this.idZona = idZona;
	}

	public String getNombreZona() {
		return nombreZona;
	}

	public void setNombreZona(String nombreZona) {
		this.nombreZona = nombreZona;
	}

	public String getIdSubzona() {
		return idSubzona;
	}

	public void setIdSubzona(String idSubzona) {
		this.idSubzona = idSubzona;
	}

	public String getNombreSubzona() {
		return nombreSubzona;
	}

	public void setNombreSubzona(String nombreSubzona) {
		this.nombreSubzona = nombreSubzona;
	}

	public String getIdArea() {
		return idArea;
	}

	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}

	public String getNombreArea() {
		return nombreArea;
	}

	public void setNombreArea(String nombreArea) {
		this.nombreArea = nombreArea;
	}

	public String getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(String idMateria) {
		this.idMateria = idMateria;
	}

	public String getNombreMateria() {
		return nombreMateria;
	}

	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}

	public String getNombreGuardia() {
		return nombreGuardia;
	}

	public void setNombreGuardia(String nombreGuardia) {
		this.nombreGuardia = nombreGuardia;
	}

	public String getDescripcionNombreGuardia() {
		return descripcionNombreGuardia;
	}

	public void setDescripcionNombreGuardia(String descripcionNombreGuardia) {
		this.descripcionNombreGuardia = descripcionNombreGuardia;
	}

	public String getObligatoriedadInscripcion() {
		return obligatoriedadInscripcion;
	}

	public void setObligatoriedadInscripcion(String obligatoriedadInscripcion) {
		this.obligatoriedadInscripcion = obligatoriedadInscripcion;
	}

	public String getDescripcionObligatoriedad() {
		return descripcionObligatoriedad;
	}

	public void setDescripcionObligatoriedad(String descripcionObligatoriedad) {
		this.descripcionObligatoriedad = descripcionObligatoriedad;
	}
	public Date getFechasolicitud() {
		return fechasolicitud;
	}

	public void setFechasolicitud(Date fechasolicitud) {
		this.fechasolicitud = fechasolicitud;
	}

	@Override
	public String toString() {
		return "InscripcionSearchDTO [idGuardia=" + idGuardia + ", nombreGuardia=" + nombreGuardia + ", idTurno="
				+ idTurno + ", nombreTurno=" + nombreTurno + ", numeroGrupo=" + numeroGrupo + ", colegiado=" + colegiado
				+ ", colegiadoGrupo=" + colegiadoGrupo + ", letrado=" + letrado + ", idZona=" + idZona + ", nombreZOna="
				+ nombreZona + ", idSubzona=" + idSubzona + ", nombreSubzona=" + nombreSubzona + ", idArea=" + idArea
				+ ", nombreArea=" + nombreArea + ", idMateria=" + idMateria + ", nombreMateria=" + nombreMateria
				+ ", descripcionNombreGuardia=" + descripcionNombreGuardia + ", obligatoriedadInscripcion="
				+ obligatoriedadInscripcion + ", descripcionObligatoriedad=" + descripcionObligatoriedad + ", fechasolicitud=" + fechasolicitud +"]";
	}


}