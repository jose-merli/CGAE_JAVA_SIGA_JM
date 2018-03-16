package org.itcgae.siga.db.entities;

import java.util.Date;

public class CenGruposFicheros extends CenGruposFicherosKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.IDGRUPO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private Short idgrupo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.IDINSTITUCION_GRUPO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private Short idinstitucionGrupo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.NOMBREFICHERO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String nombrefichero;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.NOMBREFICHEROLOG
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String nombreficherolog;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.DIRECTORIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private String directorio;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.FECHAMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private Date fechamodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.USUMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	private Integer usumodificacion;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.IDGRUPO
	 * @return  the value of USCGAE_DESA.CEN_GRUPOS_FICHEROS.IDGRUPO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Short getIdgrupo() {
		return idgrupo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.IDGRUPO
	 * @param idgrupo  the value for USCGAE_DESA.CEN_GRUPOS_FICHEROS.IDGRUPO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setIdgrupo(Short idgrupo) {
		this.idgrupo = idgrupo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.IDINSTITUCION_GRUPO
	 * @return  the value of USCGAE_DESA.CEN_GRUPOS_FICHEROS.IDINSTITUCION_GRUPO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Short getIdinstitucionGrupo() {
		return idinstitucionGrupo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.IDINSTITUCION_GRUPO
	 * @param idinstitucionGrupo  the value for USCGAE_DESA.CEN_GRUPOS_FICHEROS.IDINSTITUCION_GRUPO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setIdinstitucionGrupo(Short idinstitucionGrupo) {
		this.idinstitucionGrupo = idinstitucionGrupo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.NOMBREFICHERO
	 * @return  the value of USCGAE_DESA.CEN_GRUPOS_FICHEROS.NOMBREFICHERO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getNombrefichero() {
		return nombrefichero;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.NOMBREFICHERO
	 * @param nombrefichero  the value for USCGAE_DESA.CEN_GRUPOS_FICHEROS.NOMBREFICHERO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setNombrefichero(String nombrefichero) {
		this.nombrefichero = nombrefichero;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.NOMBREFICHEROLOG
	 * @return  the value of USCGAE_DESA.CEN_GRUPOS_FICHEROS.NOMBREFICHEROLOG
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getNombreficherolog() {
		return nombreficherolog;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.NOMBREFICHEROLOG
	 * @param nombreficherolog  the value for USCGAE_DESA.CEN_GRUPOS_FICHEROS.NOMBREFICHEROLOG
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setNombreficherolog(String nombreficherolog) {
		this.nombreficherolog = nombreficherolog;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.DIRECTORIO
	 * @return  the value of USCGAE_DESA.CEN_GRUPOS_FICHEROS.DIRECTORIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public String getDirectorio() {
		return directorio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.DIRECTORIO
	 * @param directorio  the value for USCGAE_DESA.CEN_GRUPOS_FICHEROS.DIRECTORIO
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setDirectorio(String directorio) {
		this.directorio = directorio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.FECHAMODIFICACION
	 * @return  the value of USCGAE_DESA.CEN_GRUPOS_FICHEROS.FECHAMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.FECHAMODIFICACION
	 * @param fechamodificacion  the value for USCGAE_DESA.CEN_GRUPOS_FICHEROS.FECHAMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.USUMODIFICACION
	 * @return  the value of USCGAE_DESA.CEN_GRUPOS_FICHEROS.USUMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public Integer getUsumodificacion() {
		return usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_DESA.CEN_GRUPOS_FICHEROS.USUMODIFICACION
	 * @param usumodificacion  the value for USCGAE_DESA.CEN_GRUPOS_FICHEROS.USUMODIFICACION
	 * @mbg.generated  Wed Mar 14 18:23:45 CET 2018
	 */
	public void setUsumodificacion(Integer usumodificacion) {
		this.usumodificacion = usumodificacion;
	}
}