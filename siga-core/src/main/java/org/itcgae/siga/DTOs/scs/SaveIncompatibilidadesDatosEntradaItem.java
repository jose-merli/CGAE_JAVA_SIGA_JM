package org.itcgae.siga.DTOs.scs;

import java.util.List;

public class SaveIncompatibilidadesDatosEntradaItem {
	
	private String idTurno;
	private String idGuardia;
	private String idTurnoIncompatible;
	private String idGuardiaIncompatible;
	private String idInstitucion;
	private String motivos;
	private String diasSeparacionGuardias;
	private String usuario;
	private String nombreTurno;
	private String nombreGuardia;
	private String nombreTurnoIncompatible;
	private List<String> nombreGuardiaIncompatible;
	
	public String getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	public String getIdGuardia() {
		return idGuardia;
	}
	public void setIdGuardia(String idGuardia) {
		this.idGuardia = idGuardia;
	}
	public String getIdTurnoIncompatible() {
		return idTurnoIncompatible;
	}
	public void setIdTurnoIncompatible(String idTurnoIncompatible) {
		this.idTurnoIncompatible = idTurnoIncompatible;
	}
	public String getIdGuardiaIncompatible() {
		return idGuardiaIncompatible;
	}
	public void setIdGuardiaIncompatible(String idGuardiaIncompatible) {
		this.idGuardiaIncompatible = idGuardiaIncompatible;
	}
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getMotivos() {
		return motivos;
	}
	public void setMotivos(String motivos) {
		this.motivos = motivos;
	}
	public String getDiasSeparacionGuardias() {
		return diasSeparacionGuardias;
	}
	public void setDiasSeparacionGuardias(String diasSeparacionGuardias) {
		this.diasSeparacionGuardias = diasSeparacionGuardias;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getNombreTurno() {
		return nombreTurno;
	}
	public void setNombreTurno(String nombreTurno) {
		this.nombreTurno = nombreTurno;
	}
	public String getNombreGuardia() {
		return nombreGuardia;
	}
	public void setNombreGuardia(String nombreGuardia) {
		this.nombreGuardia = nombreGuardia;
	}
	public String getNombreTurnoIncompatible() {
		return nombreTurnoIncompatible;
	}
	public void setNombreTurnoIncompatible(String nombreTurnoIncompatible) {
		this.nombreTurnoIncompatible = nombreTurnoIncompatible;
	}
	public List<String> getNombreGuardiaIncompatible() {
		return nombreGuardiaIncompatible;
	}
	public void setNombreGuardiaIncompatible(List<String> nombreGuardiaIncompatible) {
		this.nombreGuardiaIncompatible = nombreGuardiaIncompatible;
	}

	
}
