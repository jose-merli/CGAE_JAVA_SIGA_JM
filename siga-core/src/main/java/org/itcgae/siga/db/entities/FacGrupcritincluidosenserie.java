package org.itcgae.siga.db.entities;

import java.util.Date;

public class FacGrupcritincluidosenserie extends FacGrupcritincluidosenserieKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE_INT.FAC_GRUPCRITINCLUIDOSENSERIE.FECHAMODIFICACION
     *
     * @mbg.generated Tue Dec 21 14:46:21 CET 2021
     */
    private Date fechamodificacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE_INT.FAC_GRUPCRITINCLUIDOSENSERIE.USUMODIFICACION
     *
     * @mbg.generated Tue Dec 21 14:46:21 CET 2021
     */
    private Integer usumodificacion;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE_INT.FAC_GRUPCRITINCLUIDOSENSERIE.FECHAMODIFICACION
     *
     * @return the value of USCGAE_INT.FAC_GRUPCRITINCLUIDOSENSERIE.FECHAMODIFICACION
     *
     * @mbg.generated Tue Dec 21 14:46:21 CET 2021
     */
    public Date getFechamodificacion() {
        return fechamodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE_INT.FAC_GRUPCRITINCLUIDOSENSERIE.FECHAMODIFICACION
     *
     * @param fechamodificacion the value for USCGAE_INT.FAC_GRUPCRITINCLUIDOSENSERIE.FECHAMODIFICACION
     *
     * @mbg.generated Tue Dec 21 14:46:21 CET 2021
     */
    public void setFechamodificacion(Date fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE_INT.FAC_GRUPCRITINCLUIDOSENSERIE.USUMODIFICACION
     *
     * @return the value of USCGAE_INT.FAC_GRUPCRITINCLUIDOSENSERIE.USUMODIFICACION
     *
     * @mbg.generated Tue Dec 21 14:46:21 CET 2021
     */
    public Integer getUsumodificacion() {
        return usumodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE_INT.FAC_GRUPCRITINCLUIDOSENSERIE.USUMODIFICACION
     *
     * @param usumodificacion the value for USCGAE_INT.FAC_GRUPCRITINCLUIDOSENSERIE.USUMODIFICACION
     *
     * @mbg.generated Tue Dec 21 14:46:21 CET 2021
     */
    public void setUsumodificacion(Integer usumodificacion) {
        this.usumodificacion = usumodificacion;
    }
}