package org.itcgae.siga.db.entities;

import java.math.BigDecimal;
import java.util.Date;

public class FcsAplicaMovimientosvarios extends FcsAplicaMovimientosvariosKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IDPERSONA
     *
     * @mbg.generated Mon Aug 09 12:07:54 CEST 2021
     */
    private Long idpersona;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IDMOVIMIENTO
     *
     * @mbg.generated Mon Aug 09 12:07:54 CEST 2021
     */
    private Long idmovimiento;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IDPAGOSJG
     *
     * @mbg.generated Mon Aug 09 12:07:54 CEST 2021
     */
    private Integer idpagosjg;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IMPORTEAPLICADO
     *
     * @mbg.generated Mon Aug 09 12:07:54 CEST 2021
     */
    private BigDecimal importeaplicado;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.FECHAMODIFICACION
     *
     * @mbg.generated Mon Aug 09 12:07:54 CEST 2021
     */
    private Date fechamodificacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.USUMODIFICACION
     *
     * @mbg.generated Mon Aug 09 12:07:54 CEST 2021
     */
    private Long usumodificacion;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IDPERSONA
     *
     * @return the value of USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IDPERSONA
     *
     * @mbg.generated Mon Aug 09 12:07:54 CEST 2021
     */
    public Long getIdpersona() {
        return idpersona;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IDPERSONA
     *
     * @param idpersona the value for USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IDPERSONA
     *
     * @mbg.generated Mon Aug 09 12:07:54 CEST 2021
     */
    public void setIdpersona(Long idpersona) {
        this.idpersona = idpersona;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IDMOVIMIENTO
     *
     * @return the value of USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IDMOVIMIENTO
     *
     * @mbg.generated Mon Aug 09 12:07:54 CEST 2021
     */
    public Long getIdmovimiento() {
        return idmovimiento;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IDMOVIMIENTO
     *
     * @param idmovimiento the value for USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IDMOVIMIENTO
     *
     * @mbg.generated Mon Aug 09 12:07:54 CEST 2021
     */
    public void setIdmovimiento(Long idmovimiento) {
        this.idmovimiento = idmovimiento;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IDPAGOSJG
     *
     * @return the value of USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IDPAGOSJG
     *
     * @mbg.generated Mon Aug 09 12:07:54 CEST 2021
     */
    public Integer getIdpagosjg() {
        return idpagosjg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IDPAGOSJG
     *
     * @param idpagosjg the value for USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IDPAGOSJG
     *
     * @mbg.generated Mon Aug 09 12:07:54 CEST 2021
     */
    public void setIdpagosjg(Integer idpagosjg) {
        this.idpagosjg = idpagosjg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IMPORTEAPLICADO
     *
     * @return the value of USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IMPORTEAPLICADO
     *
     * @mbg.generated Mon Aug 09 12:07:54 CEST 2021
     */
    public BigDecimal getImporteaplicado() {
        return importeaplicado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IMPORTEAPLICADO
     *
     * @param importeaplicado the value for USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.IMPORTEAPLICADO
     *
     * @mbg.generated Mon Aug 09 12:07:54 CEST 2021
     */
    public void setImporteaplicado(BigDecimal importeaplicado) {
        this.importeaplicado = importeaplicado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.FECHAMODIFICACION
     *
     * @return the value of USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.FECHAMODIFICACION
     *
     * @mbg.generated Mon Aug 09 12:07:54 CEST 2021
     */
    public Date getFechamodificacion() {
        return fechamodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.FECHAMODIFICACION
     *
     * @param fechamodificacion the value for USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.FECHAMODIFICACION
     *
     * @mbg.generated Mon Aug 09 12:07:54 CEST 2021
     */
    public void setFechamodificacion(Date fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.USUMODIFICACION
     *
     * @return the value of USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.USUMODIFICACION
     *
     * @mbg.generated Mon Aug 09 12:07:54 CEST 2021
     */
    public Long getUsumodificacion() {
        return usumodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.USUMODIFICACION
     *
     * @param usumodificacion the value for USCGAE.FCS_APLICA_MOVIMIENTOSVARIOS.USUMODIFICACION
     *
     * @mbg.generated Mon Aug 09 12:07:54 CEST 2021
     */
    public void setUsumodificacion(Long usumodificacion) {
        this.usumodificacion = usumodificacion;
    }
}