package org.itcgae.siga.db.entities;

import java.util.Date;

public class CenDatoscolegialesestado extends CenDatoscolegialesestadoKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.IDESTADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private Short idestado;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.FECHAMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private Date fechamodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.USUMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private Integer usumodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.OBSERVACIONES
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String observaciones;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.IDESTADO
	 * @return  the value of USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.IDESTADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Short getIdestado() {
		return idestado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.IDESTADO
	 * @param idestado  the value for USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.IDESTADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setIdestado(Short idestado) {
		this.idestado = idestado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.FECHAMODIFICACION
	 * @return  the value of USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.FECHAMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.FECHAMODIFICACION
	 * @param fechamodificacion  the value for USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.FECHAMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.USUMODIFICACION
	 * @return  the value of USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.USUMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Integer getUsumodificacion() {
		return usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.USUMODIFICACION
	 * @param usumodificacion  the value for USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.USUMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setUsumodificacion(Integer usumodificacion) {
		this.usumodificacion = usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.OBSERVACIONES
	 * @return  the value of USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.OBSERVACIONES
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.OBSERVACIONES
	 * @param observaciones  the value for USCGAE_DESA.CEN_DATOSCOLEGIALESESTADO.OBSERVACIONES
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}