package org.itcgae.siga.db.entities;

import java.util.Date;

public class FcsCertificacionesHistoricoEstado extends FcsCertificacionesHistoricoEstadoKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.PROCESO
	 * @mbg.generated  Tue Dec 21 13:07:06 CET 2021
	 */
	private String proceso;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.FECHAESTADO
	 * @mbg.generated  Tue Dec 21 13:07:06 CET 2021
	 */
	private Date fechaestado;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.IDESTADO
	 * @mbg.generated  Tue Dec 21 13:07:06 CET 2021
	 */
	private Short idestado;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.FECHAMODIFICACION
	 * @mbg.generated  Tue Dec 21 13:07:06 CET 2021
	 */
	private Date fechamodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.USUMODIFICACION
	 * @mbg.generated  Tue Dec 21 13:07:06 CET 2021
	 */
	private Integer usumodificacion;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.PROCESO
	 * @return  the value of USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.PROCESO
	 * @mbg.generated  Tue Dec 21 13:07:06 CET 2021
	 */
	public String getProceso() {
		return proceso;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.PROCESO
	 * @param proceso  the value for USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.PROCESO
	 * @mbg.generated  Tue Dec 21 13:07:06 CET 2021
	 */
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.FECHAESTADO
	 * @return  the value of USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.FECHAESTADO
	 * @mbg.generated  Tue Dec 21 13:07:06 CET 2021
	 */
	public Date getFechaestado() {
		return fechaestado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.FECHAESTADO
	 * @param fechaestado  the value for USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.FECHAESTADO
	 * @mbg.generated  Tue Dec 21 13:07:06 CET 2021
	 */
	public void setFechaestado(Date fechaestado) {
		this.fechaestado = fechaestado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.IDESTADO
	 * @return  the value of USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.IDESTADO
	 * @mbg.generated  Tue Dec 21 13:07:06 CET 2021
	 */
	public Short getIdestado() {
		return idestado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.IDESTADO
	 * @param idestado  the value for USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.IDESTADO
	 * @mbg.generated  Tue Dec 21 13:07:06 CET 2021
	 */
	public void setIdestado(Short idestado) {
		this.idestado = idestado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.FECHAMODIFICACION
	 * @return  the value of USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.FECHAMODIFICACION
	 * @mbg.generated  Tue Dec 21 13:07:06 CET 2021
	 */
	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.FECHAMODIFICACION
	 * @param fechamodificacion  the value for USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.FECHAMODIFICACION
	 * @mbg.generated  Tue Dec 21 13:07:06 CET 2021
	 */
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.USUMODIFICACION
	 * @return  the value of USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.USUMODIFICACION
	 * @mbg.generated  Tue Dec 21 13:07:06 CET 2021
	 */
	public Integer getUsumodificacion() {
		return usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.USUMODIFICACION
	 * @param usumodificacion  the value for USCGAE_INT.FCS_CERTIFICACIONES_HISTORICO_ESTADO.USUMODIFICACION
	 * @mbg.generated  Tue Dec 21 13:07:06 CET 2021
	 */
	public void setUsumodificacion(Integer usumodificacion) {
		this.usumodificacion = usumodificacion;
	}
}