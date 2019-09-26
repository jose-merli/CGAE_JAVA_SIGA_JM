package org.itcgae.siga.db.entities;

import java.math.BigDecimal;
import java.util.Date;

public class ScsPartidapresupuestaria extends ScsPartidapresupuestariaKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.SCS_PARTIDAPRESUPUESTARIA.NOMBREPARTIDA
	 * @mbg.generated  Fri Sep 20 10:27:45 CEST 2019
	 */
	private String nombrepartida;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.SCS_PARTIDAPRESUPUESTARIA.FECHAMODIFICACION
	 * @mbg.generated  Fri Sep 20 10:27:45 CEST 2019
	 */
	private Date fechamodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.SCS_PARTIDAPRESUPUESTARIA.USUMODIFICACION
	 * @mbg.generated  Fri Sep 20 10:27:45 CEST 2019
	 */
	private Integer usumodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.SCS_PARTIDAPRESUPUESTARIA.DESCRIPCION
	 * @mbg.generated  Fri Sep 20 10:27:45 CEST 2019
	 */
	private String descripcion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.SCS_PARTIDAPRESUPUESTARIA.IMPORTEPARTIDA
	 * @mbg.generated  Fri Sep 20 10:27:45 CEST 2019
	 */
	private BigDecimal importepartida;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.SCS_PARTIDAPRESUPUESTARIA.FECHABAJA
	 * @mbg.generated  Fri Sep 20 10:27:45 CEST 2019
	 */
	private Date fechabaja;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.SCS_PARTIDAPRESUPUESTARIA.NOMBREPARTIDA
	 * @return  the value of USCGAE.SCS_PARTIDAPRESUPUESTARIA.NOMBREPARTIDA
	 * @mbg.generated  Fri Sep 20 10:27:45 CEST 2019
	 */
	public String getNombrepartida() {
		return nombrepartida;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.SCS_PARTIDAPRESUPUESTARIA.NOMBREPARTIDA
	 * @param nombrepartida  the value for USCGAE.SCS_PARTIDAPRESUPUESTARIA.NOMBREPARTIDA
	 * @mbg.generated  Fri Sep 20 10:27:45 CEST 2019
	 */
	public void setNombrepartida(String nombrepartida) {
		this.nombrepartida = nombrepartida;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.SCS_PARTIDAPRESUPUESTARIA.FECHAMODIFICACION
	 * @return  the value of USCGAE.SCS_PARTIDAPRESUPUESTARIA.FECHAMODIFICACION
	 * @mbg.generated  Fri Sep 20 10:27:45 CEST 2019
	 */
	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.SCS_PARTIDAPRESUPUESTARIA.FECHAMODIFICACION
	 * @param fechamodificacion  the value for USCGAE.SCS_PARTIDAPRESUPUESTARIA.FECHAMODIFICACION
	 * @mbg.generated  Fri Sep 20 10:27:45 CEST 2019
	 */
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.SCS_PARTIDAPRESUPUESTARIA.USUMODIFICACION
	 * @return  the value of USCGAE.SCS_PARTIDAPRESUPUESTARIA.USUMODIFICACION
	 * @mbg.generated  Fri Sep 20 10:27:45 CEST 2019
	 */
	public Integer getUsumodificacion() {
		return usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.SCS_PARTIDAPRESUPUESTARIA.USUMODIFICACION
	 * @param usumodificacion  the value for USCGAE.SCS_PARTIDAPRESUPUESTARIA.USUMODIFICACION
	 * @mbg.generated  Fri Sep 20 10:27:45 CEST 2019
	 */
	public void setUsumodificacion(Integer usumodificacion) {
		this.usumodificacion = usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.SCS_PARTIDAPRESUPUESTARIA.DESCRIPCION
	 * @return  the value of USCGAE.SCS_PARTIDAPRESUPUESTARIA.DESCRIPCION
	 * @mbg.generated  Fri Sep 20 10:27:45 CEST 2019
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.SCS_PARTIDAPRESUPUESTARIA.DESCRIPCION
	 * @param descripcion  the value for USCGAE.SCS_PARTIDAPRESUPUESTARIA.DESCRIPCION
	 * @mbg.generated  Fri Sep 20 10:27:45 CEST 2019
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.SCS_PARTIDAPRESUPUESTARIA.IMPORTEPARTIDA
	 * @return  the value of USCGAE.SCS_PARTIDAPRESUPUESTARIA.IMPORTEPARTIDA
	 * @mbg.generated  Fri Sep 20 10:27:45 CEST 2019
	 */
	public BigDecimal getImportepartida() {
		return importepartida;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.SCS_PARTIDAPRESUPUESTARIA.IMPORTEPARTIDA
	 * @param importepartida  the value for USCGAE.SCS_PARTIDAPRESUPUESTARIA.IMPORTEPARTIDA
	 * @mbg.generated  Fri Sep 20 10:27:45 CEST 2019
	 */
	public void setImportepartida(BigDecimal importepartida) {
		this.importepartida = importepartida;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.SCS_PARTIDAPRESUPUESTARIA.FECHABAJA
	 * @return  the value of USCGAE.SCS_PARTIDAPRESUPUESTARIA.FECHABAJA
	 * @mbg.generated  Fri Sep 20 10:27:45 CEST 2019
	 */
	public Date getFechabaja() {
		return fechabaja;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.SCS_PARTIDAPRESUPUESTARIA.FECHABAJA
	 * @param fechabaja  the value for USCGAE.SCS_PARTIDAPRESUPUESTARIA.FECHABAJA
	 * @mbg.generated  Fri Sep 20 10:27:45 CEST 2019
	 */
	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}
}