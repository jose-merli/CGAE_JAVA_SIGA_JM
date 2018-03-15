package org.itcgae.siga.db.entities;

import java.math.BigDecimal;

public class AdmConfig {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.ADM_CONFIG.ID
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private Short id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.ADM_CONFIG.CLAVE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String clave;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.ADM_CONFIG.VALOR
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String valor;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.ADM_CONFIG.DESCRIPCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String descripcion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.ADM_CONFIG.VALOR_POR_DEFECTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String valorPorDefecto;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.ADM_CONFIG.NECESITA_REINICIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private Short necesitaReinicio;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.ADM_CONFIG.ID
	 * @return  the value of USCGAE_DESA.ADM_CONFIG.ID
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Short getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.ADM_CONFIG.ID
	 * @param id  the value for USCGAE_DESA.ADM_CONFIG.ID
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setId(Short id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.ADM_CONFIG.CLAVE
	 * @return  the value of USCGAE_DESA.ADM_CONFIG.CLAVE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.ADM_CONFIG.CLAVE
	 * @param clave  the value for USCGAE_DESA.ADM_CONFIG.CLAVE
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.ADM_CONFIG.VALOR
	 * @return  the value of USCGAE_DESA.ADM_CONFIG.VALOR
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.ADM_CONFIG.VALOR
	 * @param valor  the value for USCGAE_DESA.ADM_CONFIG.VALOR
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.ADM_CONFIG.DESCRIPCION
	 * @return  the value of USCGAE_DESA.ADM_CONFIG.DESCRIPCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.ADM_CONFIG.DESCRIPCION
	 * @param descripcion  the value for USCGAE_DESA.ADM_CONFIG.DESCRIPCION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.ADM_CONFIG.VALOR_POR_DEFECTO
	 * @return  the value of USCGAE_DESA.ADM_CONFIG.VALOR_POR_DEFECTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getValorPorDefecto() {
		return valorPorDefecto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.ADM_CONFIG.VALOR_POR_DEFECTO
	 * @param valorPorDefecto  the value for USCGAE_DESA.ADM_CONFIG.VALOR_POR_DEFECTO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setValorPorDefecto(String valorPorDefecto) {
		this.valorPorDefecto = valorPorDefecto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.ADM_CONFIG.NECESITA_REINICIO
	 * @return  the value of USCGAE_DESA.ADM_CONFIG.NECESITA_REINICIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Short getNecesitaReinicio() {
		return necesitaReinicio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.ADM_CONFIG.NECESITA_REINICIO
	 * @param necesitaReinicio  the value for USCGAE_DESA.ADM_CONFIG.NECESITA_REINICIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setNecesitaReinicio(Short necesitaReinicio) {
		this.necesitaReinicio = necesitaReinicio;
	}
}