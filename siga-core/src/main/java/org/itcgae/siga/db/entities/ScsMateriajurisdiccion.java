package org.itcgae.siga.db.entities;

import java.util.Date;

public class ScsMateriajurisdiccion extends ScsMateriajurisdiccionKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.SCS_MATERIAJURISDICCION.FECHAMODIFICACION
     *
     * @mbg.generated Thu Sep 12 17:53:41 CEST 2019
     */
    private Date fechamodificacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.SCS_MATERIAJURISDICCION.USUMODIFICACION
     *
     * @mbg.generated Thu Sep 12 17:53:41 CEST 2019
     */
    private Integer usumodificacion;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.SCS_MATERIAJURISDICCION.FECHAMODIFICACION
     *
     * @return the value of USCGAE.SCS_MATERIAJURISDICCION.FECHAMODIFICACION
     *
     * @mbg.generated Thu Sep 12 17:53:41 CEST 2019
     */
    public Date getFechamodificacion() {
        return fechamodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.SCS_MATERIAJURISDICCION.FECHAMODIFICACION
     *
     * @param fechamodificacion the value for USCGAE.SCS_MATERIAJURISDICCION.FECHAMODIFICACION
     *
     * @mbg.generated Thu Sep 12 17:53:41 CEST 2019
     */
    public void setFechamodificacion(Date fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.SCS_MATERIAJURISDICCION.USUMODIFICACION
     *
     * @return the value of USCGAE.SCS_MATERIAJURISDICCION.USUMODIFICACION
     *
     * @mbg.generated Thu Sep 12 17:53:41 CEST 2019
     */
    public Integer getUsumodificacion() {
        return usumodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.SCS_MATERIAJURISDICCION.USUMODIFICACION
     *
     * @param usumodificacion the value for USCGAE.SCS_MATERIAJURISDICCION.USUMODIFICACION
     *
     * @mbg.generated Thu Sep 12 17:53:41 CEST 2019
     */
    public void setUsumodificacion(Integer usumodificacion) {
        this.usumodificacion = usumodificacion;
    }
}