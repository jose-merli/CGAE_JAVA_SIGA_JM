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
	private int numIncidencias;
	private int incidenciasAntesEnvio;
	private int incidenciasDespuesEnvio;
	private String incidencias;
	private int idEjgRemesa;
		
	public int getIdEjgRemesa() {
		return idEjgRemesa;
	}

	public void setIdEjgRemesa(int idEjgRemesa) {
		this.idEjgRemesa = idEjgRemesa;
	}

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

	public int getNumIncidencias() {
		return numIncidencias;
	}

	public void setNumIncidencias(int numIncidencias) {
		this.numIncidencias = numIncidencias;
	}

	public int getIncidenciasAntesEnvio() {
		return incidenciasAntesEnvio;
	}

	public void setIncidenciasAntesEnvio(int incidenciasAntesEnvio) {
		this.incidenciasAntesEnvio = incidenciasAntesEnvio;
	}

	public int getIncidenciasDespuesEnvio() {
		return incidenciasDespuesEnvio;
	}

	public void setIncidenciasDespuesEnvio(int incidenciasDespuesEnvio) {
		this.incidenciasDespuesEnvio = incidenciasDespuesEnvio;
	}

	public String getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(String incidencias) {
		this.incidencias = incidencias;
	} 
	
}
