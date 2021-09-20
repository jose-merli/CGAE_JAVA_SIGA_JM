package org.itcgae.siga.DTOs.scs;

public class EJGRemesaItem {
	private String identificadorEJG;
	private int idTipoEJG;
	private int idInstitucion;
	private String turnoGuardiaEJG;
	private int anioEJG;
	private int numeroEJG;
	private String estadoEJG;
	private String solicitante;
	private int nuevaRemesa;
	private String estadoRemesa;
	
	public String getIdentificadorEJG() {
		return identificadorEJG;
	}
		
	public int getIdTipoEJG() {
		return idTipoEJG;
	}

	public void setIdTipoEJG(int idTipoEJG) {
		this.idTipoEJG = idTipoEJG;
	}

	public int getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(int idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public void setIdentificadorEJG(String identificadorEJG) {
		this.identificadorEJG = identificadorEJG;
	}
	
	public String getTurnoGuardiaEJG() {
		return turnoGuardiaEJG;
	}
	
	public void setTurnoGuardiaEJG(String turnoGuardiaEJG) {
		this.turnoGuardiaEJG = turnoGuardiaEJG;
	}
	
	public int getAnioEJG() {
		return anioEJG;
	}
	
	public void setAnioEJG(int anioEJG) {
		this.anioEJG = anioEJG;
	}
	
	public int getNumeroEJG() {
		return numeroEJG;
	}
	
	public void setNumeroEJG(int numeroEJG) {
		this.numeroEJG = numeroEJG;
	}
	
	public String getEstadoEJG() {
		return estadoEJG;
	}
	
	public void setEstadoEJG(String estadoEJG) {
		this.estadoEJG = estadoEJG;
	}
	
	public String getSolicitante() {
		return solicitante;
	}
	
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	
	public int getNuevaRemesa() {
		return nuevaRemesa;
	}
	
	public void setNuevaRemesa(int nuevaRemesa) {
		this.nuevaRemesa = nuevaRemesa;
	}
	
	public String getEstadoRemesa() {
		return estadoRemesa;
	}
	
	public void setEstadoRemesa(String estadoRemesa) {
		this.estadoRemesa = estadoRemesa;
	} 
}
