package org.itcgae.siga.db.entities;

import java.util.Date;

public class EnvEnviosgrupocliente {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.ENV_ENVIOSGRUPOCLIENTE.IDENVIO
	 * @mbg.generated  Thu Dec 13 10:10:34 CET 2018
	 */
	private Long idenvio;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.ENV_ENVIOSGRUPOCLIENTE.IDGRUPO
	 * @mbg.generated  Thu Dec 13 10:10:34 CET 2018
	 */
	private Short idgrupo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.ENV_ENVIOSGRUPOCLIENTE.FECHAMODIFICACION
	 * @mbg.generated  Thu Dec 13 10:10:34 CET 2018
	 */
	private Date fechamodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.ENV_ENVIOSGRUPOCLIENTE.USUMODIFICACION
	 * @mbg.generated  Thu Dec 13 10:10:34 CET 2018
	 */
	private Integer usumodificacion;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.ENV_ENVIOSGRUPOCLIENTE.IDENVIO
	 * @return  the value of USCGAE.ENV_ENVIOSGRUPOCLIENTE.IDENVIO
	 * @mbg.generated  Thu Dec 13 10:10:34 CET 2018
	 */
	public Long getIdenvio() {
		return idenvio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.ENV_ENVIOSGRUPOCLIENTE.IDENVIO
	 * @param idenvio  the value for USCGAE.ENV_ENVIOSGRUPOCLIENTE.IDENVIO
	 * @mbg.generated  Thu Dec 13 10:10:34 CET 2018
	 */
	public void setIdenvio(Long idenvio) {
		this.idenvio = idenvio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.ENV_ENVIOSGRUPOCLIENTE.IDGRUPO
	 * @return  the value of USCGAE.ENV_ENVIOSGRUPOCLIENTE.IDGRUPO
	 * @mbg.generated  Thu Dec 13 10:10:34 CET 2018
	 */
	public Short getIdgrupo() {
		return idgrupo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.ENV_ENVIOSGRUPOCLIENTE.IDGRUPO
	 * @param idgrupo  the value for USCGAE.ENV_ENVIOSGRUPOCLIENTE.IDGRUPO
	 * @mbg.generated  Thu Dec 13 10:10:34 CET 2018
	 */
	public void setIdgrupo(Short idgrupo) {
		this.idgrupo = idgrupo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.ENV_ENVIOSGRUPOCLIENTE.FECHAMODIFICACION
	 * @return  the value of USCGAE.ENV_ENVIOSGRUPOCLIENTE.FECHAMODIFICACION
	 * @mbg.generated  Thu Dec 13 10:10:34 CET 2018
	 */
	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.ENV_ENVIOSGRUPOCLIENTE.FECHAMODIFICACION
	 * @param fechamodificacion  the value for USCGAE.ENV_ENVIOSGRUPOCLIENTE.FECHAMODIFICACION
	 * @mbg.generated  Thu Dec 13 10:10:34 CET 2018
	 */
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.ENV_ENVIOSGRUPOCLIENTE.USUMODIFICACION
	 * @return  the value of USCGAE.ENV_ENVIOSGRUPOCLIENTE.USUMODIFICACION
	 * @mbg.generated  Thu Dec 13 10:10:34 CET 2018
	 */
	public Integer getUsumodificacion() {
		return usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.ENV_ENVIOSGRUPOCLIENTE.USUMODIFICACION
	 * @param usumodificacion  the value for USCGAE.ENV_ENVIOSGRUPOCLIENTE.USUMODIFICACION
	 * @mbg.generated  Thu Dec 13 10:10:34 CET 2018
	 */
	public void setUsumodificacion(Integer usumodificacion) {
		this.usumodificacion = usumodificacion;
	}
}