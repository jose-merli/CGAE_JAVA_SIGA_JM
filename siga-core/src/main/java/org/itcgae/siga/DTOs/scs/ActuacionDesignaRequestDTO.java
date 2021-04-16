package org.itcgae.siga.DTOs.scs;

public class ActuacionDesignaRequestDTO {

	private String anio;
	private String idTurno;
	private String numero;
	private boolean historico;

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

	@Override
	public String toString() {
		return "ActuacionDesignaRequestDTO [anio=" + anio + ", idTurno=" + idTurno + ", numero=" + numero
				+ ", historico=" + historico + "]";
	}

}
