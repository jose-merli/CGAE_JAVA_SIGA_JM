package org.itcgae.siga.db.entities;

public class AdmAccessKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.ADM_ACCESS.NOMBRE_ACCION
     *
     * @mbg.generated Thu Jun 24 14:50:52 CEST 2021
     */
    private String nombreAccion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.ADM_ACCESS.IDPROCESO
     *
     * @mbg.generated Thu Jun 24 14:50:52 CEST 2021
     */
    private String idproceso;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column USCGAE.ADM_ACCESS.PERMISO
     *
     * @mbg.generated Thu Jun 24 14:50:52 CEST 2021
     */
    private Short permiso;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.ADM_ACCESS.NOMBRE_ACCION
     *
     * @return the value of USCGAE.ADM_ACCESS.NOMBRE_ACCION
     *
     * @mbg.generated Thu Jun 24 14:50:52 CEST 2021
     */
    public String getNombreAccion() {
        return nombreAccion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.ADM_ACCESS.NOMBRE_ACCION
     *
     * @param nombreAccion the value for USCGAE.ADM_ACCESS.NOMBRE_ACCION
     *
     * @mbg.generated Thu Jun 24 14:50:52 CEST 2021
     */
    public void setNombreAccion(String nombreAccion) {
        this.nombreAccion = nombreAccion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.ADM_ACCESS.IDPROCESO
     *
     * @return the value of USCGAE.ADM_ACCESS.IDPROCESO
     *
     * @mbg.generated Thu Jun 24 14:50:52 CEST 2021
     */
    public String getIdproceso() {
        return idproceso;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.ADM_ACCESS.IDPROCESO
     *
     * @param idproceso the value for USCGAE.ADM_ACCESS.IDPROCESO
     *
     * @mbg.generated Thu Jun 24 14:50:52 CEST 2021
     */
    public void setIdproceso(String idproceso) {
        this.idproceso = idproceso;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column USCGAE.ADM_ACCESS.PERMISO
     *
     * @return the value of USCGAE.ADM_ACCESS.PERMISO
     *
     * @mbg.generated Thu Jun 24 14:50:52 CEST 2021
     */
    public Short getPermiso() {
        return permiso;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column USCGAE.ADM_ACCESS.PERMISO
     *
     * @param permiso the value for USCGAE.ADM_ACCESS.PERMISO
     *
     * @mbg.generated Thu Jun 24 14:50:52 CEST 2021
     */
    public void setPermiso(Short permiso) {
        this.permiso = permiso;
    }
}