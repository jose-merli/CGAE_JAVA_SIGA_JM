package org.itcgae.siga.DTOs.scs;


public class IncompatibilidadesDatosEntradaItem {
	
	private String idTurno;
	private String nombreGuardia;
	private String idArea;
	private String idMateria;
	private String idZona;
	private String idSubZona;
	private String idJurisdiccion;
	private String idGrupoFacturacion;
	private String idPartidaPresupuestaria;
	private String idTipoTurno;
	private String idTipoGuardia;
	private String idGuardia;

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
	public String getNombreGuardia() {
		return nombreGuardia;
	}
	public void setNombreGuardia(String nombreGuardia) {
		this.nombreGuardia = nombreGuardia;
	}
	public String getIdArea() {
		return idArea;
	}
	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}
	public String getIdMateria() {
		return idMateria;
	}
	public void setIdMateria(String idMateria) {
		this.idMateria = idMateria;
	}
	public String getIdZona() {
		return idZona;
	}
	public void setIdZona(String idZona) {
		this.idZona = idZona;
	}
	public String getIdSubZona() {
		return idSubZona;
	}
	public void setIdSubZona(String idSubZona) {
		this.idSubZona = idSubZona;
	}
	public String getIdJurisdiccion() {
		return idJurisdiccion;
	}
	public void setIdJurisdiccion(String idJurisdiccion) {
		this.idJurisdiccion = idJurisdiccion;
	}
	public String getIdGrupoFacturacion() {
		return idGrupoFacturacion;
	}
	public void setIdGrupoFacturacion(String idGrupoFacturacion) {
		this.idGrupoFacturacion = idGrupoFacturacion;
	}
	public String getIdPartidaPresupuestaria() {
		return idPartidaPresupuestaria;
	}
	public void setIdPartidaPresupuestaria(String idPartidaPresupuestaria) {
		this.idPartidaPresupuestaria = idPartidaPresupuestaria;
	}
	public String getIdTipoTurno() {
		return idTipoTurno;
	}
	public void setIdTipoTurno(String idTipoTurno) {
		this.idTipoTurno = idTipoTurno;
	}
	public String getIdTipoGuardia() {
		return idTipoGuardia;
	}
	public void setIdTipoGuardia(String idTipoGuardia) {
		this.idTipoGuardia = idTipoGuardia;
	}

	public IncompatibilidadesDatosEntradaItem(String idTurno, String nombreGuardia, String idArea, String idMateria,
			String idZona, String idSubZona, String idJurisdiccion, String idGrupoFacturacion,
			String idPartidaPresupuestaria, String idTipoTurno, String idTipoGuardia, String idGuardia) {
		super();
		this.idTurno = idTurno;
		this.nombreGuardia = nombreGuardia;
		this.idArea = idArea;
		this.idMateria = idMateria;
		this.idZona = idZona;
		this.idSubZona = idSubZona;
		this.idJurisdiccion = idJurisdiccion;
		this.idGrupoFacturacion = idGrupoFacturacion;
		this.idPartidaPresupuestaria = idPartidaPresupuestaria;
		this.idTipoTurno = idTipoTurno;
		this.idTipoGuardia = idTipoGuardia;
		this.idGuardia = idGuardia;
	}

	public IncompatibilidadesDatosEntradaItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
}
