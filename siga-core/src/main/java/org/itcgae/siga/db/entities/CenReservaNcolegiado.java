package org.itcgae.siga.db.entities;

public class CenReservaNcolegiado extends CenReservaNcolegiadoKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CEN_RESERVA_NCOLEGIADO.ESTADO
     *
     * @mbg.generated Tue Dec 07 12:54:52 CET 2021
     */
    private String estado;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CEN_RESERVA_NCOLEGIADO.NEXPEXEA
     *
     * @mbg.generated Tue Dec 07 12:54:52 CET 2021
     */
    private String nexpexea;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CEN_RESERVA_NCOLEGIADO.ESTADO
     *
     * @return the value of CEN_RESERVA_NCOLEGIADO.ESTADO
     *
     * @mbg.generated Tue Dec 07 12:54:52 CET 2021
     */
    public String getEstado() {
        return estado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CEN_RESERVA_NCOLEGIADO.ESTADO
     *
     * @param estado the value for CEN_RESERVA_NCOLEGIADO.ESTADO
     *
     * @mbg.generated Tue Dec 07 12:54:52 CET 2021
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CEN_RESERVA_NCOLEGIADO.NEXPEXEA
     *
     * @return the value of CEN_RESERVA_NCOLEGIADO.NEXPEXEA
     *
     * @mbg.generated Tue Dec 07 12:54:52 CET 2021
     */
    public String getNexpexea() {
        return nexpexea;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CEN_RESERVA_NCOLEGIADO.NEXPEXEA
     *
     * @param nexpexea the value for CEN_RESERVA_NCOLEGIADO.NEXPEXEA
     *
     * @mbg.generated Tue Dec 07 12:54:52 CET 2021
     */
    public void setNexpexea(String nexpexea) {
        this.nexpexea = nexpexea;
    }
}