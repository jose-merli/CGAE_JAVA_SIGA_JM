package org.itcgae.siga.db.entities;

import java.util.Date;

public class CajgRemesaestados extends CajgRemesaestadosKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.CAJG_REMESAESTADOS.FECHAMODIFICACION
     *
     * @mbg.generated Thu Jul 29 11:33:16 CEST 2021
     */
    private Date fechamodificacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.CAJG_REMESAESTADOS.USUMODIFICACION
     *
     * @mbg.generated Thu Jul 29 11:33:16 CEST 2021
     */
    private Integer usumodificacion;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.CAJG_REMESAESTADOS.FECHAMODIFICACION
     *
     * @return the value of USCGAE.CAJG_REMESAESTADOS.FECHAMODIFICACION
     *
     * @mbg.generated Thu Jul 29 11:33:16 CEST 2021
     */
    public Date getFechamodificacion() {
        return fechamodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.CAJG_REMESAESTADOS.FECHAMODIFICACION
     *
     * @param fechamodificacion the value for USCGAE.CAJG_REMESAESTADOS.FECHAMODIFICACION
     *
     * @mbg.generated Thu Jul 29 11:33:16 CEST 2021
     */
    public void setFechamodificacion(Date fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.CAJG_REMESAESTADOS.USUMODIFICACION
     *
     * @return the value of USCGAE.CAJG_REMESAESTADOS.USUMODIFICACION
     *
     * @mbg.generated Thu Jul 29 11:33:16 CEST 2021
     */
    public Integer getUsumodificacion() {
        return usumodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.CAJG_REMESAESTADOS.USUMODIFICACION
     *
     * @param usumodificacion the value for USCGAE.CAJG_REMESAESTADOS.USUMODIFICACION
     *
     * @mbg.generated Thu Jul 29 11:33:16 CEST 2021
     */
    public void setUsumodificacion(Integer usumodificacion) {
        this.usumodificacion = usumodificacion;
    }
}