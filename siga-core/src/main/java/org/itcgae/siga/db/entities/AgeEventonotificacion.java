package org.itcgae.siga.db.entities;

import java.util.Date;

public class AgeEventonotificacion {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.AGE_EVENTONOTIFICACION.IDEVENTO
	 * @mbg.generated  Mon Dec 03 11:16:57 CET 2018
	 */
	private Long idevento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.AGE_EVENTONOTIFICACION.IDNOTIFICACIONEVENTO
	 * @mbg.generated  Mon Dec 03 11:16:57 CET 2018
	 */
	private Long idnotificacionevento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.AGE_EVENTONOTIFICACION.FECHABAJA
	 * @mbg.generated  Mon Dec 03 11:16:57 CET 2018
	 */
	private Date fechabaja;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.AGE_EVENTONOTIFICACION.IDEVENTO
	 * @return  the value of USCGAE.AGE_EVENTONOTIFICACION.IDEVENTO
	 * @mbg.generated  Mon Dec 03 11:16:57 CET 2018
	 */
	public Long getIdevento() {
		return idevento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.AGE_EVENTONOTIFICACION.IDEVENTO
	 * @param idevento  the value for USCGAE.AGE_EVENTONOTIFICACION.IDEVENTO
	 * @mbg.generated  Mon Dec 03 11:16:57 CET 2018
	 */
	public void setIdevento(Long idevento) {
		this.idevento = idevento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.AGE_EVENTONOTIFICACION.IDNOTIFICACIONEVENTO
	 * @return  the value of USCGAE.AGE_EVENTONOTIFICACION.IDNOTIFICACIONEVENTO
	 * @mbg.generated  Mon Dec 03 11:16:57 CET 2018
	 */
	public Long getIdnotificacionevento() {
		return idnotificacionevento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.AGE_EVENTONOTIFICACION.IDNOTIFICACIONEVENTO
	 * @param idnotificacionevento  the value for USCGAE.AGE_EVENTONOTIFICACION.IDNOTIFICACIONEVENTO
	 * @mbg.generated  Mon Dec 03 11:16:57 CET 2018
	 */
	public void setIdnotificacionevento(Long idnotificacionevento) {
		this.idnotificacionevento = idnotificacionevento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.AGE_EVENTONOTIFICACION.FECHABAJA
	 * @return  the value of USCGAE.AGE_EVENTONOTIFICACION.FECHABAJA
	 * @mbg.generated  Mon Dec 03 11:16:57 CET 2018
	 */
	public Date getFechabaja() {
		return fechabaja;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.AGE_EVENTONOTIFICACION.FECHABAJA
	 * @param fechabaja  the value for USCGAE.AGE_EVENTONOTIFICACION.FECHABAJA
	 * @mbg.generated  Mon Dec 03 11:16:57 CET 2018
	 */
	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}
}