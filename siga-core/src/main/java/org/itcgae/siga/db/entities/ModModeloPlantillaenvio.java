package org.itcgae.siga.db.entities;

import java.util.Date;

public class ModModeloPlantillaenvio extends ModModeloPlantillaenvioKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.MOD_MODELO_PLANTILLAENVIO.FECHAMODIFICACION
	 * @mbg.generated  Sun Jan 20 19:23:02 CET 2019
	 */
	private Date fechamodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.MOD_MODELO_PLANTILLAENVIO.USUMODIFICACION
	 * @mbg.generated  Sun Jan 20 19:23:02 CET 2019
	 */
	private Integer usumodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.MOD_MODELO_PLANTILLAENVIO.PORDEFECTO
	 * @mbg.generated  Sun Jan 20 19:23:02 CET 2019
	 */
	private String pordefecto;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.MOD_MODELO_PLANTILLAENVIO.FECHABAJA
	 * @mbg.generated  Sun Jan 20 19:23:02 CET 2019
	 */
	private Date fechabaja;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.MOD_MODELO_PLANTILLAENVIO.FECHAMODIFICACION
	 * @return  the value of USCGAE.MOD_MODELO_PLANTILLAENVIO.FECHAMODIFICACION
	 * @mbg.generated  Sun Jan 20 19:23:02 CET 2019
	 */
	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.MOD_MODELO_PLANTILLAENVIO.FECHAMODIFICACION
	 * @param fechamodificacion  the value for USCGAE.MOD_MODELO_PLANTILLAENVIO.FECHAMODIFICACION
	 * @mbg.generated  Sun Jan 20 19:23:02 CET 2019
	 */
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.MOD_MODELO_PLANTILLAENVIO.USUMODIFICACION
	 * @return  the value of USCGAE.MOD_MODELO_PLANTILLAENVIO.USUMODIFICACION
	 * @mbg.generated  Sun Jan 20 19:23:02 CET 2019
	 */
	public Integer getUsumodificacion() {
		return usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.MOD_MODELO_PLANTILLAENVIO.USUMODIFICACION
	 * @param usumodificacion  the value for USCGAE.MOD_MODELO_PLANTILLAENVIO.USUMODIFICACION
	 * @mbg.generated  Sun Jan 20 19:23:02 CET 2019
	 */
	public void setUsumodificacion(Integer usumodificacion) {
		this.usumodificacion = usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.MOD_MODELO_PLANTILLAENVIO.PORDEFECTO
	 * @return  the value of USCGAE.MOD_MODELO_PLANTILLAENVIO.PORDEFECTO
	 * @mbg.generated  Sun Jan 20 19:23:02 CET 2019
	 */
	public String getPordefecto() {
		return pordefecto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.MOD_MODELO_PLANTILLAENVIO.PORDEFECTO
	 * @param pordefecto  the value for USCGAE.MOD_MODELO_PLANTILLAENVIO.PORDEFECTO
	 * @mbg.generated  Sun Jan 20 19:23:02 CET 2019
	 */
	public void setPordefecto(String pordefecto) {
		this.pordefecto = pordefecto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.MOD_MODELO_PLANTILLAENVIO.FECHABAJA
	 * @return  the value of USCGAE.MOD_MODELO_PLANTILLAENVIO.FECHABAJA
	 * @mbg.generated  Sun Jan 20 19:23:02 CET 2019
	 */
	public Date getFechabaja() {
		return fechabaja;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.MOD_MODELO_PLANTILLAENVIO.FECHABAJA
	 * @param fechabaja  the value for USCGAE.MOD_MODELO_PLANTILLAENVIO.FECHABAJA
	 * @mbg.generated  Sun Jan 20 19:23:02 CET 2019
	 */
	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}
}