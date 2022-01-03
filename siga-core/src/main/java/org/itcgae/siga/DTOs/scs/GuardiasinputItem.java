package org.itcgae.siga.DTOs.scs;

public class GuardiasinputItem {
	private String idLista;
	private String idInstitucion;
	private String idTurno;
	
	public String getIdLista() {
		return idLista;
	}
	public void setIdLista(String idLista) {
		this.idLista = idLista;
	}
	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	
	@Override
	public String toString() {
		return "GuardiasinputItem [idLista=" + idLista + ", idInstitucion=" + idInstitucion + ", idTurno=" + idTurno
				+ "]";
	}
}
