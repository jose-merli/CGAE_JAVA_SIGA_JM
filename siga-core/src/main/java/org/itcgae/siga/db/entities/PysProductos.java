package org.itcgae.siga.db.entities;

import java.util.Date;

public class PysProductos extends PysProductosKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.PYS_PRODUCTOS.DESCRIPCION
	 * @mbg.generated  Wed Jun 16 14:57:12 CEST 2021
	 */
	private String descripcion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.PYS_PRODUCTOS.FECHAMODIFICACION
	 * @mbg.generated  Wed Jun 16 14:57:12 CEST 2021
	 */
	private Date fechamodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.PYS_PRODUCTOS.USUMODIFICACION
	 * @mbg.generated  Wed Jun 16 14:57:12 CEST 2021
	 */
	private Integer usumodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.PYS_PRODUCTOS.FECHABAJA
	 * @mbg.generated  Wed Jun 16 14:57:12 CEST 2021
	 */
	private Date fechabaja;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.PYS_PRODUCTOS.DESCRIPCION
	 * @return  the value of USCGAE.PYS_PRODUCTOS.DESCRIPCION
	 * @mbg.generated  Wed Jun 16 14:57:12 CEST 2021
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.PYS_PRODUCTOS.DESCRIPCION
	 * @param descripcion  the value for USCGAE.PYS_PRODUCTOS.DESCRIPCION
	 * @mbg.generated  Wed Jun 16 14:57:12 CEST 2021
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.PYS_PRODUCTOS.FECHAMODIFICACION
	 * @return  the value of USCGAE.PYS_PRODUCTOS.FECHAMODIFICACION
	 * @mbg.generated  Wed Jun 16 14:57:12 CEST 2021
	 */
	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.PYS_PRODUCTOS.FECHAMODIFICACION
	 * @param fechamodificacion  the value for USCGAE.PYS_PRODUCTOS.FECHAMODIFICACION
	 * @mbg.generated  Wed Jun 16 14:57:12 CEST 2021
	 */
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.PYS_PRODUCTOS.USUMODIFICACION
	 * @return  the value of USCGAE.PYS_PRODUCTOS.USUMODIFICACION
	 * @mbg.generated  Wed Jun 16 14:57:12 CEST 2021
	 */
	public Integer getUsumodificacion() {
		return usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.PYS_PRODUCTOS.USUMODIFICACION
	 * @param usumodificacion  the value for USCGAE.PYS_PRODUCTOS.USUMODIFICACION
	 * @mbg.generated  Wed Jun 16 14:57:12 CEST 2021
	 */
	public void setUsumodificacion(Integer usumodificacion) {
		this.usumodificacion = usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.PYS_PRODUCTOS.FECHABAJA
	 * @return  the value of USCGAE.PYS_PRODUCTOS.FECHABAJA
	 * @mbg.generated  Wed Jun 16 14:57:12 CEST 2021
	 */
	public Date getFechabaja() {
		return fechabaja;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.PYS_PRODUCTOS.FECHABAJA
	 * @param fechabaja  the value for USCGAE.PYS_PRODUCTOS.FECHABAJA
	 * @mbg.generated  Wed Jun 16 14:57:12 CEST 2021
	 */
	public void setFechabaja(Date fechabaja) {
		this.fechabaja = fechabaja;
	}
}