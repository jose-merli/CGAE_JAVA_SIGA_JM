package org.itcgae.siga.db.entities;

import java.util.Date;

public class CenDocumentacionmodalidad extends CenDocumentacionmodalidadKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.DESCRIPCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String descripcion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.CODIGOEXT
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String codigoext;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.FECHAMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private Date fechamodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.USUMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private Integer usumodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.BLOQUEADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String bloqueado;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.DESCRIPCION
	 * @return  the value of USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.DESCRIPCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.DESCRIPCION
	 * @param descripcion  the value for USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.DESCRIPCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.CODIGOEXT
	 * @return  the value of USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.CODIGOEXT
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getCodigoext() {
		return codigoext;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.CODIGOEXT
	 * @param codigoext  the value for USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.CODIGOEXT
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setCodigoext(String codigoext) {
		this.codigoext = codigoext;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.FECHAMODIFICACION
	 * @return  the value of USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.FECHAMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.FECHAMODIFICACION
	 * @param fechamodificacion  the value for USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.FECHAMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.USUMODIFICACION
	 * @return  the value of USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.USUMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Integer getUsumodificacion() {
		return usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.USUMODIFICACION
	 * @param usumodificacion  the value for USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.USUMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setUsumodificacion(Integer usumodificacion) {
		this.usumodificacion = usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.BLOQUEADO
	 * @return  the value of USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.BLOQUEADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getBloqueado() {
		return bloqueado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.BLOQUEADO
	 * @param bloqueado  the value for USCGAE_DESA.CEN_DOCUMENTACIONMODALIDAD.BLOQUEADO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setBloqueado(String bloqueado) {
		this.bloqueado = bloqueado;
	}
}