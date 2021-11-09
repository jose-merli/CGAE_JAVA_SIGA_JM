package org.itcgae.siga.db.entities;

import java.math.BigDecimal;
import java.util.Date;

public class FcsHistoricoProcedimientos extends FcsHistoricoProcedimientosKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.NOMBRE
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	private String nombre;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHACREACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	private Date fechacreacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHAMODIFICACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	private Date fechamodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.USUMODIFICACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	private Integer usumodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.PRECIO
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	private BigDecimal precio;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.IDJURISDICCION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	private Short idjurisdiccion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.CODIGO
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	private String codigo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.COMPLEMENTO
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	private String complemento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.VIGENTE
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	private String vigente;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.ORDEN
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	private Integer orden;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.CODIGOEXT
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	private String codigoext;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.PERMITIRANIADIRLETRADO
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	private String permitiraniadirletrado;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHADESDEVIGOR
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	private Date fechadesdevigor;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHAHASTAVIGOR
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	private Date fechahastavigor;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.NOMBRE
	 * @return  the value of USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.NOMBRE
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.NOMBRE
	 * @param nombre  the value for USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.NOMBRE
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHACREACION
	 * @return  the value of USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHACREACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public Date getFechacreacion() {
		return fechacreacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHACREACION
	 * @param fechacreacion  the value for USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHACREACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHAMODIFICACION
	 * @return  the value of USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHAMODIFICACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHAMODIFICACION
	 * @param fechamodificacion  the value for USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHAMODIFICACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.USUMODIFICACION
	 * @return  the value of USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.USUMODIFICACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public Integer getUsumodificacion() {
		return usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.USUMODIFICACION
	 * @param usumodificacion  the value for USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.USUMODIFICACION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public void setUsumodificacion(Integer usumodificacion) {
		this.usumodificacion = usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.PRECIO
	 * @return  the value of USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.PRECIO
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public BigDecimal getPrecio() {
		return precio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.PRECIO
	 * @param precio  the value for USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.PRECIO
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.IDJURISDICCION
	 * @return  the value of USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.IDJURISDICCION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public Short getIdjurisdiccion() {
		return idjurisdiccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.IDJURISDICCION
	 * @param idjurisdiccion  the value for USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.IDJURISDICCION
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public void setIdjurisdiccion(Short idjurisdiccion) {
		this.idjurisdiccion = idjurisdiccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.CODIGO
	 * @return  the value of USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.CODIGO
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.CODIGO
	 * @param codigo  the value for USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.CODIGO
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.COMPLEMENTO
	 * @return  the value of USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.COMPLEMENTO
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public String getComplemento() {
		return complemento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.COMPLEMENTO
	 * @param complemento  the value for USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.COMPLEMENTO
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.VIGENTE
	 * @return  the value of USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.VIGENTE
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public String getVigente() {
		return vigente;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.VIGENTE
	 * @param vigente  the value for USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.VIGENTE
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public void setVigente(String vigente) {
		this.vigente = vigente;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.ORDEN
	 * @return  the value of USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.ORDEN
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.ORDEN
	 * @param orden  the value for USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.ORDEN
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.CODIGOEXT
	 * @return  the value of USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.CODIGOEXT
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public String getCodigoext() {
		return codigoext;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.CODIGOEXT
	 * @param codigoext  the value for USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.CODIGOEXT
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public void setCodigoext(String codigoext) {
		this.codigoext = codigoext;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.PERMITIRANIADIRLETRADO
	 * @return  the value of USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.PERMITIRANIADIRLETRADO
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public String getPermitiraniadirletrado() {
		return permitiraniadirletrado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.PERMITIRANIADIRLETRADO
	 * @param permitiraniadirletrado  the value for USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.PERMITIRANIADIRLETRADO
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public void setPermitiraniadirletrado(String permitiraniadirletrado) {
		this.permitiraniadirletrado = permitiraniadirletrado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHADESDEVIGOR
	 * @return  the value of USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHADESDEVIGOR
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public Date getFechadesdevigor() {
		return fechadesdevigor;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHADESDEVIGOR
	 * @param fechadesdevigor  the value for USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHADESDEVIGOR
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public void setFechadesdevigor(Date fechadesdevigor) {
		this.fechadesdevigor = fechadesdevigor;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHAHASTAVIGOR
	 * @return  the value of USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHAHASTAVIGOR
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public Date getFechahastavigor() {
		return fechahastavigor;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHAHASTAVIGOR
	 * @param fechahastavigor  the value for USCGAE.FCS_HISTORICO_PROCEDIMIENTOS.FECHAHASTAVIGOR
	 * @mbg.generated  Thu Dec 19 19:42:16 CET 2019
	 */
	public void setFechahastavigor(Date fechahastavigor) {
		this.fechahastavigor = fechahastavigor;
	}
}