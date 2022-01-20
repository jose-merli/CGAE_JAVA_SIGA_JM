package org.itcgae.siga.db.entities;

import java.math.BigDecimal;
import java.util.Date;

public class FacAbono extends FacAbonoKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.MOTIVOS
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private String motivos;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.FECHA
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private Date fecha;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.CONTABILIZADA
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private String contabilizada;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.IDPERSONA
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private Long idpersona;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.USUMODIFICACION
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private Integer usumodificacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.FECHAMODIFICACION
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private Date fechamodificacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.IDCUENTA
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private Short idcuenta;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.IDFACTURA
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private String idfactura;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.IDPAGOSJG
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private Integer idpagosjg;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.NUMEROABONO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private String numeroabono;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.OBSERVACIONES
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private String observaciones;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.ESTADO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private Short estado;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.IMPTOTALNETO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private BigDecimal imptotalneto;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.IMPTOTALIVA
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private BigDecimal imptotaliva;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.IMPTOTAL
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private BigDecimal imptotal;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.IMPTOTALABONADOEFECTIVO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private BigDecimal imptotalabonadoefectivo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.IMPTOTALABONADOPORBANCO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private BigDecimal imptotalabonadoporbanco;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.IMPTOTALABONADO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private BigDecimal imptotalabonado;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.IMPPENDIENTEPORABONAR
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private BigDecimal imppendienteporabonar;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.IDPERSONADEUDOR
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private Long idpersonadeudor;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.IDCUENTADEUDOR
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private Short idcuentadeudor;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_ABONO.IDPERORIGEN
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    private Long idperorigen;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.MOTIVOS
     *
     * @return the value of USCGAE.FAC_ABONO.MOTIVOS
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public String getMotivos() {
        return motivos;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.MOTIVOS
     *
     * @param motivos the value for USCGAE.FAC_ABONO.MOTIVOS
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setMotivos(String motivos) {
        this.motivos = motivos;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.FECHA
     *
     * @return the value of USCGAE.FAC_ABONO.FECHA
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.FECHA
     *
     * @param fecha the value for USCGAE.FAC_ABONO.FECHA
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.CONTABILIZADA
     *
     * @return the value of USCGAE.FAC_ABONO.CONTABILIZADA
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public String getContabilizada() {
        return contabilizada;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.CONTABILIZADA
     *
     * @param contabilizada the value for USCGAE.FAC_ABONO.CONTABILIZADA
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setContabilizada(String contabilizada) {
        this.contabilizada = contabilizada;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.IDPERSONA
     *
     * @return the value of USCGAE.FAC_ABONO.IDPERSONA
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public Long getIdpersona() {
        return idpersona;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.IDPERSONA
     *
     * @param idpersona the value for USCGAE.FAC_ABONO.IDPERSONA
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setIdpersona(Long idpersona) {
        this.idpersona = idpersona;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.USUMODIFICACION
     *
     * @return the value of USCGAE.FAC_ABONO.USUMODIFICACION
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public Integer getUsumodificacion() {
        return usumodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.USUMODIFICACION
     *
     * @param usumodificacion the value for USCGAE.FAC_ABONO.USUMODIFICACION
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setUsumodificacion(Integer usumodificacion) {
        this.usumodificacion = usumodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.FECHAMODIFICACION
     *
     * @return the value of USCGAE.FAC_ABONO.FECHAMODIFICACION
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public Date getFechamodificacion() {
        return fechamodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.FECHAMODIFICACION
     *
     * @param fechamodificacion the value for USCGAE.FAC_ABONO.FECHAMODIFICACION
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setFechamodificacion(Date fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.IDCUENTA
     *
     * @return the value of USCGAE.FAC_ABONO.IDCUENTA
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public Short getIdcuenta() {
        return idcuenta;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.IDCUENTA
     *
     * @param idcuenta the value for USCGAE.FAC_ABONO.IDCUENTA
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setIdcuenta(Short idcuenta) {
        this.idcuenta = idcuenta;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.IDFACTURA
     *
     * @return the value of USCGAE.FAC_ABONO.IDFACTURA
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public String getIdfactura() {
        return idfactura;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.IDFACTURA
     *
     * @param idfactura the value for USCGAE.FAC_ABONO.IDFACTURA
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setIdfactura(String idfactura) {
        this.idfactura = idfactura;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.IDPAGOSJG
     *
     * @return the value of USCGAE.FAC_ABONO.IDPAGOSJG
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public Integer getIdpagosjg() {
        return idpagosjg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.IDPAGOSJG
     *
     * @param idpagosjg the value for USCGAE.FAC_ABONO.IDPAGOSJG
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setIdpagosjg(Integer idpagosjg) {
        this.idpagosjg = idpagosjg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.NUMEROABONO
     *
     * @return the value of USCGAE.FAC_ABONO.NUMEROABONO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public String getNumeroabono() {
        return numeroabono;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.NUMEROABONO
     *
     * @param numeroabono the value for USCGAE.FAC_ABONO.NUMEROABONO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setNumeroabono(String numeroabono) {
        this.numeroabono = numeroabono;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.OBSERVACIONES
     *
     * @return the value of USCGAE.FAC_ABONO.OBSERVACIONES
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.OBSERVACIONES
     *
     * @param observaciones the value for USCGAE.FAC_ABONO.OBSERVACIONES
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.ESTADO
     *
     * @return the value of USCGAE.FAC_ABONO.ESTADO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public Short getEstado() {
        return estado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.ESTADO
     *
     * @param estado the value for USCGAE.FAC_ABONO.ESTADO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setEstado(Short estado) {
        this.estado = estado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.IMPTOTALNETO
     *
     * @return the value of USCGAE.FAC_ABONO.IMPTOTALNETO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public BigDecimal getImptotalneto() {
        return imptotalneto;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.IMPTOTALNETO
     *
     * @param imptotalneto the value for USCGAE.FAC_ABONO.IMPTOTALNETO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setImptotalneto(BigDecimal imptotalneto) {
        this.imptotalneto = imptotalneto;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.IMPTOTALIVA
     *
     * @return the value of USCGAE.FAC_ABONO.IMPTOTALIVA
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public BigDecimal getImptotaliva() {
        return imptotaliva;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.IMPTOTALIVA
     *
     * @param imptotaliva the value for USCGAE.FAC_ABONO.IMPTOTALIVA
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setImptotaliva(BigDecimal imptotaliva) {
        this.imptotaliva = imptotaliva;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.IMPTOTAL
     *
     * @return the value of USCGAE.FAC_ABONO.IMPTOTAL
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public BigDecimal getImptotal() {
        return imptotal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.IMPTOTAL
     *
     * @param imptotal the value for USCGAE.FAC_ABONO.IMPTOTAL
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setImptotal(BigDecimal imptotal) {
        this.imptotal = imptotal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.IMPTOTALABONADOEFECTIVO
     *
     * @return the value of USCGAE.FAC_ABONO.IMPTOTALABONADOEFECTIVO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public BigDecimal getImptotalabonadoefectivo() {
        return imptotalabonadoefectivo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.IMPTOTALABONADOEFECTIVO
     *
     * @param imptotalabonadoefectivo the value for USCGAE.FAC_ABONO.IMPTOTALABONADOEFECTIVO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setImptotalabonadoefectivo(BigDecimal imptotalabonadoefectivo) {
        this.imptotalabonadoefectivo = imptotalabonadoefectivo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.IMPTOTALABONADOPORBANCO
     *
     * @return the value of USCGAE.FAC_ABONO.IMPTOTALABONADOPORBANCO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public BigDecimal getImptotalabonadoporbanco() {
        return imptotalabonadoporbanco;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.IMPTOTALABONADOPORBANCO
     *
     * @param imptotalabonadoporbanco the value for USCGAE.FAC_ABONO.IMPTOTALABONADOPORBANCO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setImptotalabonadoporbanco(BigDecimal imptotalabonadoporbanco) {
        this.imptotalabonadoporbanco = imptotalabonadoporbanco;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.IMPTOTALABONADO
     *
     * @return the value of USCGAE.FAC_ABONO.IMPTOTALABONADO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public BigDecimal getImptotalabonado() {
        return imptotalabonado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.IMPTOTALABONADO
     *
     * @param imptotalabonado the value for USCGAE.FAC_ABONO.IMPTOTALABONADO
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setImptotalabonado(BigDecimal imptotalabonado) {
        this.imptotalabonado = imptotalabonado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.IMPPENDIENTEPORABONAR
     *
     * @return the value of USCGAE.FAC_ABONO.IMPPENDIENTEPORABONAR
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public BigDecimal getImppendienteporabonar() {
        return imppendienteporabonar;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.IMPPENDIENTEPORABONAR
     *
     * @param imppendienteporabonar the value for USCGAE.FAC_ABONO.IMPPENDIENTEPORABONAR
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setImppendienteporabonar(BigDecimal imppendienteporabonar) {
        this.imppendienteporabonar = imppendienteporabonar;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.IDPERSONADEUDOR
     *
     * @return the value of USCGAE.FAC_ABONO.IDPERSONADEUDOR
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public Long getIdpersonadeudor() {
        return idpersonadeudor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.IDPERSONADEUDOR
     *
     * @param idpersonadeudor the value for USCGAE.FAC_ABONO.IDPERSONADEUDOR
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setIdpersonadeudor(Long idpersonadeudor) {
        this.idpersonadeudor = idpersonadeudor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.IDCUENTADEUDOR
     *
     * @return the value of USCGAE.FAC_ABONO.IDCUENTADEUDOR
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public Short getIdcuentadeudor() {
        return idcuentadeudor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.IDCUENTADEUDOR
     *
     * @param idcuentadeudor the value for USCGAE.FAC_ABONO.IDCUENTADEUDOR
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setIdcuentadeudor(Short idcuentadeudor) {
        this.idcuentadeudor = idcuentadeudor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_ABONO.IDPERORIGEN
     *
     * @return the value of USCGAE.FAC_ABONO.IDPERORIGEN
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public Long getIdperorigen() {
        return idperorigen;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_ABONO.IDPERORIGEN
     *
     * @param idperorigen the value for USCGAE.FAC_ABONO.IDPERORIGEN
     *
     * @mbg.generated Tue Sep 14 13:04:04 CEST 2021
     */
    public void setIdperorigen(Long idperorigen) {
        this.idperorigen = idperorigen;
    }
}