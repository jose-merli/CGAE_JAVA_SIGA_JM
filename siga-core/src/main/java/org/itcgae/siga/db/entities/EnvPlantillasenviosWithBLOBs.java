package org.itcgae.siga.db.entities;

public class EnvPlantillasenviosWithBLOBs extends EnvPlantillasenvios {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.ENV_PLANTILLASENVIOS.ASUNTO
	 * @mbg.generated  Mon Jan 20 14:13:09 CET 2020
	 */
	private String asunto;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.ENV_PLANTILLASENVIOS.CUERPO
	 * @mbg.generated  Mon Jan 20 14:13:09 CET 2020
	 */
	private String cuerpo;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.ENV_PLANTILLASENVIOS.ASUNTO
	 * @return  the value of USCGAE.ENV_PLANTILLASENVIOS.ASUNTO
	 * @mbg.generated  Mon Jan 20 14:13:09 CET 2020
	 */
	public String getAsunto() {
		return asunto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.ENV_PLANTILLASENVIOS.ASUNTO
	 * @param asunto  the value for USCGAE.ENV_PLANTILLASENVIOS.ASUNTO
	 * @mbg.generated  Mon Jan 20 14:13:09 CET 2020
	 */
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.ENV_PLANTILLASENVIOS.CUERPO
	 * @return  the value of USCGAE.ENV_PLANTILLASENVIOS.CUERPO
	 * @mbg.generated  Mon Jan 20 14:13:09 CET 2020
	 */
	public String getCuerpo() {
		return cuerpo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.ENV_PLANTILLASENVIOS.CUERPO
	 * @param cuerpo  the value for USCGAE.ENV_PLANTILLASENVIOS.CUERPO
	 * @mbg.generated  Mon Jan 20 14:13:09 CET 2020
	 */
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
}