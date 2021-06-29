package org.itcgae.siga.DTOs.gen;

public class UserAccess {
	
	private String nombreAccion;
	
	private String idProceso;
	
	private int permiso;

	public String getNombreAccion() {
		return nombreAccion;
	}

	public void setNombreAccion(String nombreAccion) {
		this.nombreAccion = nombreAccion;
	}

	public String getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}

	public int getPermiso() {
		return permiso;
	}

	public void setPermiso(int permiso) {
		this.permiso = permiso;
	}

	@Override
	public String toString() {
		return "UserAccess [nombreAccion=" + nombreAccion + ", idProceso=" + idProceso + ", permiso=" + permiso + "]";
	}
	
	

}
