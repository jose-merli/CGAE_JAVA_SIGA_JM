package org.itcgae.siga.DTOs.scs;

public class ActuacionDesignaRequestDTO {

	private String anio;
	private String idTurno;
	private String numero;
	private String numeroAsunto;
	private String idPersonaColegiado;
	private boolean historico;
	private boolean esLetrado;

	/**
	 * @return the esLetrado
	 */
	public boolean isEsLetrado() {
		return esLetrado;
	}

	/**
	 * @param esLetrado the esLetrado to set
	 */
	public void setEsLetrado(boolean esLetrado) {
		this.esLetrado = esLetrado;
	}

	/**
	 * @return the anio
	 */
	public String getAnio() {
		return anio;
	}

	/**
	 * @param anio the anio to set
	 */
	public void setAnio(String anio) {
		this.anio = anio;
	}

	/**
	 * @return the idTurno
	 */
	public String getIdTurno() {
		return idTurno;
	}

	/**
	 * @param idTurno the idTurno to set
	 */
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return the numeroAsunto
	 */
	public String getNumeroAsunto() {
		return numeroAsunto;
	}

	/**
	 * @param numeroAsunto the numeroAsunto to set
	 */
	public void setNumeroAsunto(String numeroAsunto) {
		this.numeroAsunto = numeroAsunto;
	}

	/**
	 * @return the historico
	 */
	public boolean isHistorico() {
		return historico;
	}

	/**
	 * @param historico the historico to set
	 */
	public void setHistorico(boolean historico) {
		this.historico = historico;
	}

	/**
	 * @return the idPersonaColegiado
	 */
	public String getIdPersonaColegiado() {
		return idPersonaColegiado;
	}

	/**
	 * @param idPersonaColegiado the idPersonaColegiado to set
	 */
	public void setIdPersonaColegiado(String idPersonaColegiado) {
		this.idPersonaColegiado = idPersonaColegiado;
	}

	@Override
	public String toString() {
		return "ActuacionDesignaRequestDTO [anio=" + anio + ", idTurno=" + idTurno + ", numero=" + numero
				+ ", numeroAsunto=" + numeroAsunto + ", idPersonaColegiado=" + idPersonaColegiado + ", historico="
				+ historico + "]";
	}

}
