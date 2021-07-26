package org.itcgae.siga.db.entities;

import java.util.Date;

public class FacSufijo extends FacSufijoKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_SUFIJO.SUFIJO
     *
     * @mbg.generated Mon Jul 26 10:00:16 CEST 2021
     */
    private String sufijo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_SUFIJO.CONCEPTO
     *
     * @mbg.generated Mon Jul 26 10:00:16 CEST 2021
     */
    private String concepto;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_SUFIJO.FECHAMODIFICACION
     *
     * @mbg.generated Mon Jul 26 10:00:16 CEST 2021
     */
    private Date fechamodificacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.FAC_SUFIJO.USUMODIFICACION
     *
     * @mbg.generated Mon Jul 26 10:00:16 CEST 2021
     */
    private Integer usumodificacion;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_SUFIJO.SUFIJO
     *
     * @return the value of USCGAE.FAC_SUFIJO.SUFIJO
     *
     * @mbg.generated Mon Jul 26 10:00:16 CEST 2021
     */
    public String getSufijo() {
        return sufijo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_SUFIJO.SUFIJO
     *
     * @param sufijo the value for USCGAE.FAC_SUFIJO.SUFIJO
     *
     * @mbg.generated Mon Jul 26 10:00:16 CEST 2021
     */
    public void setSufijo(String sufijo) {
        this.sufijo = sufijo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_SUFIJO.CONCEPTO
     *
     * @return the value of USCGAE.FAC_SUFIJO.CONCEPTO
     *
     * @mbg.generated Mon Jul 26 10:00:16 CEST 2021
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_SUFIJO.CONCEPTO
     *
     * @param concepto the value for USCGAE.FAC_SUFIJO.CONCEPTO
     *
     * @mbg.generated Mon Jul 26 10:00:16 CEST 2021
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_SUFIJO.FECHAMODIFICACION
     *
     * @return the value of USCGAE.FAC_SUFIJO.FECHAMODIFICACION
     *
     * @mbg.generated Mon Jul 26 10:00:16 CEST 2021
     */
    public Date getFechamodificacion() {
        return fechamodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_SUFIJO.FECHAMODIFICACION
     *
     * @param fechamodificacion the value for USCGAE.FAC_SUFIJO.FECHAMODIFICACION
     *
     * @mbg.generated Mon Jul 26 10:00:16 CEST 2021
     */
    public void setFechamodificacion(Date fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.FAC_SUFIJO.USUMODIFICACION
     *
     * @return the value of USCGAE.FAC_SUFIJO.USUMODIFICACION
     *
     * @mbg.generated Mon Jul 26 10:00:16 CEST 2021
     */
    public Integer getUsumodificacion() {
        return usumodificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.FAC_SUFIJO.USUMODIFICACION
     *
     * @param usumodificacion the value for USCGAE.FAC_SUFIJO.USUMODIFICACION
     *
     * @mbg.generated Mon Jul 26 10:00:16 CEST 2021
     */
    public void setUsumodificacion(Integer usumodificacion) {
        this.usumodificacion = usumodificacion;
    }
}