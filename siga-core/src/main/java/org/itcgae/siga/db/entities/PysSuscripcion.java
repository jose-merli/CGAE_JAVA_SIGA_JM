package org.itcgae.siga.db.entities;

import java.math.BigDecimal;
import java.util.Date;

public class PysSuscripcion extends PysSuscripcionKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.PYS_SUSCRIPCION.IDPERSONA
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    private Long idpersona;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.PYS_SUSCRIPCION.IDPETICION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    private Long idpeticion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.PYS_SUSCRIPCION.FECHASUSCRIPCION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    private Date fechasuscripcion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.PYS_SUSCRIPCION.CANTIDAD
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    private Integer cantidad;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.PYS_SUSCRIPCION.IDFORMAPAGO
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    private Short idformapago;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.PYS_SUSCRIPCION.FECHAMODIFICACION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    private Date fechamodificacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.PYS_SUSCRIPCION.USUMODIFICACION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    private Integer usumodificacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.PYS_SUSCRIPCION.DESCRIPCION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    private String descripcion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.PYS_SUSCRIPCION.IMPORTEUNITARIO
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    private BigDecimal importeunitario;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.PYS_SUSCRIPCION.IMPORTEANTICIPADO
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    private BigDecimal importeanticipado;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.PYS_SUSCRIPCION.FECHABAJA
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    private Date fechabaja;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.PYS_SUSCRIPCION.IDCUENTA
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    private Short idcuenta;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.PYS_SUSCRIPCION.FECHABAJAFACTURACION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    private Date fechabajafacturacion;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.PYS_SUSCRIPCION.IDPERSONA
     *
     * @return the value of USCGAE.PYS_SUSCRIPCION.IDPERSONA
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public Long getIdpersona() {
        return idpersona;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.PYS_SUSCRIPCION.IDPERSONA
     *
     * @param idpersona the value for USCGAE.PYS_SUSCRIPCION.IDPERSONA
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public void setIdpersona(Long idpersona) {
        this.idpersona = idpersona;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.PYS_SUSCRIPCION.IDPETICION
     *
     * @return the value of USCGAE.PYS_SUSCRIPCION.IDPETICION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public Long getIdpeticion() {
        return idpeticion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.PYS_SUSCRIPCION.IDPETICION
     *
     * @param idpeticion the value for USCGAE.PYS_SUSCRIPCION.IDPETICION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public void setIdpeticion(Long idpeticion) {
        this.idpeticion = idpeticion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.PYS_SUSCRIPCION.FECHASUSCRIPCION
     *
     * @return the value of USCGAE.PYS_SUSCRIPCION.FECHASUSCRIPCION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public Date getFechasuscripcion() {
        return fechasuscripcion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.PYS_SUSCRIPCION.FECHASUSCRIPCION
     *
     * @param fechasuscripcion the value for USCGAE.PYS_SUSCRIPCION.FECHASUSCRIPCION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public void setFechasuscripcion(Date fechasuscripcion) {
        this.fechasuscripcion = fechasuscripcion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.PYS_SUSCRIPCION.CANTIDAD
     *
     * @return the value of USCGAE.PYS_SUSCRIPCION.CANTIDAD
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.PYS_SUSCRIPCION.CANTIDAD
     *
     * @param cantidad the value for USCGAE.PYS_SUSCRIPCION.CANTIDAD
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.PYS_SUSCRIPCION.IDFORMAPAGO
     *
     * @return the value of USCGAE.PYS_SUSCRIPCION.IDFORMAPAGO
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public Short getIdformapago() {
        return idformapago;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.PYS_SUSCRIPCION.IDFORMAPAGO
     *
     * @param idformapago the value for USCGAE.PYS_SUSCRIPCION.IDFORMAPAGO
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public void setIdformapago(Short idformapago) {
        this.idformapago = idformapago;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.PYS_SUSCRIPCION.FECHAMODIFICACION
     *
     * @return the value of USCGAE.PYS_SUSCRIPCION.FECHAMODIFICACION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public Date getFechamodificacion() {
        return fechamodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.PYS_SUSCRIPCION.FECHAMODIFICACION
     *
     * @param fechamodificacion the value for USCGAE.PYS_SUSCRIPCION.FECHAMODIFICACION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public void setFechamodificacion(Date fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.PYS_SUSCRIPCION.USUMODIFICACION
     *
     * @return the value of USCGAE.PYS_SUSCRIPCION.USUMODIFICACION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public Integer getUsumodificacion() {
        return usumodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.PYS_SUSCRIPCION.USUMODIFICACION
     *
     * @param usumodificacion the value for USCGAE.PYS_SUSCRIPCION.USUMODIFICACION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public void setUsumodificacion(Integer usumodificacion) {
        this.usumodificacion = usumodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.PYS_SUSCRIPCION.DESCRIPCION
     *
     * @return the value of USCGAE.PYS_SUSCRIPCION.DESCRIPCION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.PYS_SUSCRIPCION.DESCRIPCION
     *
     * @param descripcion the value for USCGAE.PYS_SUSCRIPCION.DESCRIPCION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.PYS_SUSCRIPCION.IMPORTEUNITARIO
     *
     * @return the value of USCGAE.PYS_SUSCRIPCION.IMPORTEUNITARIO
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public BigDecimal getImporteunitario() {
        return importeunitario;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.PYS_SUSCRIPCION.IMPORTEUNITARIO
     *
     * @param importeunitario the value for USCGAE.PYS_SUSCRIPCION.IMPORTEUNITARIO
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public void setImporteunitario(BigDecimal importeunitario) {
        this.importeunitario = importeunitario;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.PYS_SUSCRIPCION.IMPORTEANTICIPADO
     *
     * @return the value of USCGAE.PYS_SUSCRIPCION.IMPORTEANTICIPADO
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public BigDecimal getImporteanticipado() {
        return importeanticipado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.PYS_SUSCRIPCION.IMPORTEANTICIPADO
     *
     * @param importeanticipado the value for USCGAE.PYS_SUSCRIPCION.IMPORTEANTICIPADO
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public void setImporteanticipado(BigDecimal importeanticipado) {
        this.importeanticipado = importeanticipado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.PYS_SUSCRIPCION.FECHABAJA
     *
     * @return the value of USCGAE.PYS_SUSCRIPCION.FECHABAJA
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public Date getFechabaja() {
        return fechabaja;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.PYS_SUSCRIPCION.FECHABAJA
     *
     * @param fechabaja the value for USCGAE.PYS_SUSCRIPCION.FECHABAJA
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public void setFechabaja(Date fechabaja) {
        this.fechabaja = fechabaja;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.PYS_SUSCRIPCION.IDCUENTA
     *
     * @return the value of USCGAE.PYS_SUSCRIPCION.IDCUENTA
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public Short getIdcuenta() {
        return idcuenta;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.PYS_SUSCRIPCION.IDCUENTA
     *
     * @param idcuenta the value for USCGAE.PYS_SUSCRIPCION.IDCUENTA
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public void setIdcuenta(Short idcuenta) {
        this.idcuenta = idcuenta;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.PYS_SUSCRIPCION.FECHABAJAFACTURACION
     *
     * @return the value of USCGAE.PYS_SUSCRIPCION.FECHABAJAFACTURACION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public Date getFechabajafacturacion() {
        return fechabajafacturacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.PYS_SUSCRIPCION.FECHABAJAFACTURACION
     *
     * @param fechabajafacturacion the value for USCGAE.PYS_SUSCRIPCION.FECHABAJAFACTURACION
     *
     * @mbg.generated Wed Jan 02 16:57:30 CET 2019
     */
    public void setFechabajafacturacion(Date fechabajafacturacion) {
        this.fechabajafacturacion = fechabajafacturacion;
    }
}