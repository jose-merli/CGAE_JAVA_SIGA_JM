package org.itcgae.siga.db.entities;

import java.util.Date;

public class FcsPagosEstadospagos extends FcsPagosEstadospagosKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FCS_PAGOS_ESTADOSPAGOS.FECHAESTADO
     *
     * @mbg.generated Thu Jul 08 16:51:38 CEST 2021
     */
    private Date fechaestado;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FCS_PAGOS_ESTADOSPAGOS.FECHAMODIFICACION
     *
     * @mbg.generated Thu Jul 08 16:51:38 CEST 2021
     */
    private Date fechamodificacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FCS_PAGOS_ESTADOSPAGOS.USUMODIFICACION
     *
     * @mbg.generated Thu Jul 08 16:51:38 CEST 2021
     */
    private Integer usumodificacion;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FCS_PAGOS_ESTADOSPAGOS.FECHAESTADO
     *
     * @return the value of USCGAE.FCS_PAGOS_ESTADOSPAGOS.FECHAESTADO
     *
     * @mbg.generated Thu Jul 08 16:51:38 CEST 2021
     */
    public Date getFechaestado() {
        return fechaestado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FCS_PAGOS_ESTADOSPAGOS.FECHAESTADO
     *
     * @param fechaestado the value for USCGAE.FCS_PAGOS_ESTADOSPAGOS.FECHAESTADO
     *
     * @mbg.generated Thu Jul 08 16:51:38 CEST 2021
     */
    public void setFechaestado(Date fechaestado) {
        this.fechaestado = fechaestado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FCS_PAGOS_ESTADOSPAGOS.FECHAMODIFICACION
     *
     * @return the value of USCGAE.FCS_PAGOS_ESTADOSPAGOS.FECHAMODIFICACION
     *
     * @mbg.generated Thu Jul 08 16:51:38 CEST 2021
     */
    public Date getFechamodificacion() {
        return fechamodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FCS_PAGOS_ESTADOSPAGOS.FECHAMODIFICACION
     *
     * @param fechamodificacion the value for USCGAE.FCS_PAGOS_ESTADOSPAGOS.FECHAMODIFICACION
     *
     * @mbg.generated Thu Jul 08 16:51:38 CEST 2021
     */
    public void setFechamodificacion(Date fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FCS_PAGOS_ESTADOSPAGOS.USUMODIFICACION
     *
     * @return the value of USCGAE.FCS_PAGOS_ESTADOSPAGOS.USUMODIFICACION
     *
     * @mbg.generated Thu Jul 08 16:51:38 CEST 2021
     */
    public Integer getUsumodificacion() {
        return usumodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FCS_PAGOS_ESTADOSPAGOS.USUMODIFICACION
     *
     * @param usumodificacion the value for USCGAE.FCS_PAGOS_ESTADOSPAGOS.USUMODIFICACION
     *
     * @mbg.generated Thu Jul 08 16:51:38 CEST 2021
     */
    public void setUsumodificacion(Integer usumodificacion) {
        this.usumodificacion = usumodificacion;
    }
}