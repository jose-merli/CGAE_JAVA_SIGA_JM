package org.itcgae.siga.db.entities;

import java.math.BigDecimal;
import java.util.Date;

public class FcsFactGuardiascolegiado extends FcsFactGuardiascolegiadoKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDTURNO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	private Integer idturno;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDGUARDIA
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	private Integer idguardia;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.FECHAINICIO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	private Date fechainicio;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDPERSONA
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	private Long idpersona;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.PRECIOAPLICADO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	private BigDecimal precioaplicado;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.FECHAMODIFICACION
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	private Date fechamodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.USUMODIFICACION
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	private Integer usumodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.PRECIOCOSTESFIJOS
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	private BigDecimal preciocostesfijos;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDHITO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	private Long idhito;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.MOTIVO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	private String motivo;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDTURNO
	 * @return  the value of USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDTURNO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public Integer getIdturno() {
		return idturno;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDTURNO
	 * @param idturno  the value for USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDTURNO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public void setIdturno(Integer idturno) {
		this.idturno = idturno;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDGUARDIA
	 * @return  the value of USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDGUARDIA
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public Integer getIdguardia() {
		return idguardia;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDGUARDIA
	 * @param idguardia  the value for USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDGUARDIA
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public void setIdguardia(Integer idguardia) {
		this.idguardia = idguardia;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.FECHAINICIO
	 * @return  the value of USCGAE.FCS_FACT_GUARDIASCOLEGIADO.FECHAINICIO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public Date getFechainicio() {
		return fechainicio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.FECHAINICIO
	 * @param fechainicio  the value for USCGAE.FCS_FACT_GUARDIASCOLEGIADO.FECHAINICIO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public void setFechainicio(Date fechainicio) {
		this.fechainicio = fechainicio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDPERSONA
	 * @return  the value of USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDPERSONA
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public Long getIdpersona() {
		return idpersona;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDPERSONA
	 * @param idpersona  the value for USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDPERSONA
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public void setIdpersona(Long idpersona) {
		this.idpersona = idpersona;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.PRECIOAPLICADO
	 * @return  the value of USCGAE.FCS_FACT_GUARDIASCOLEGIADO.PRECIOAPLICADO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public BigDecimal getPrecioaplicado() {
		return precioaplicado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.PRECIOAPLICADO
	 * @param precioaplicado  the value for USCGAE.FCS_FACT_GUARDIASCOLEGIADO.PRECIOAPLICADO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public void setPrecioaplicado(BigDecimal precioaplicado) {
		this.precioaplicado = precioaplicado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.FECHAMODIFICACION
	 * @return  the value of USCGAE.FCS_FACT_GUARDIASCOLEGIADO.FECHAMODIFICACION
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.FECHAMODIFICACION
	 * @param fechamodificacion  the value for USCGAE.FCS_FACT_GUARDIASCOLEGIADO.FECHAMODIFICACION
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.USUMODIFICACION
	 * @return  the value of USCGAE.FCS_FACT_GUARDIASCOLEGIADO.USUMODIFICACION
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public Integer getUsumodificacion() {
		return usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.USUMODIFICACION
	 * @param usumodificacion  the value for USCGAE.FCS_FACT_GUARDIASCOLEGIADO.USUMODIFICACION
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public void setUsumodificacion(Integer usumodificacion) {
		this.usumodificacion = usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.PRECIOCOSTESFIJOS
	 * @return  the value of USCGAE.FCS_FACT_GUARDIASCOLEGIADO.PRECIOCOSTESFIJOS
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public BigDecimal getPreciocostesfijos() {
		return preciocostesfijos;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.PRECIOCOSTESFIJOS
	 * @param preciocostesfijos  the value for USCGAE.FCS_FACT_GUARDIASCOLEGIADO.PRECIOCOSTESFIJOS
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public void setPreciocostesfijos(BigDecimal preciocostesfijos) {
		this.preciocostesfijos = preciocostesfijos;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDHITO
	 * @return  the value of USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDHITO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public Long getIdhito() {
		return idhito;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDHITO
	 * @param idhito  the value for USCGAE.FCS_FACT_GUARDIASCOLEGIADO.IDHITO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public void setIdhito(Long idhito) {
		this.idhito = idhito;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.MOTIVO
	 * @return  the value of USCGAE.FCS_FACT_GUARDIASCOLEGIADO.MOTIVO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public String getMotivo() {
		return motivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_FACT_GUARDIASCOLEGIADO.MOTIVO
	 * @param motivo  the value for USCGAE.FCS_FACT_GUARDIASCOLEGIADO.MOTIVO
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
}