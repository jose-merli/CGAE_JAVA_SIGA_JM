package org.itcgae.siga.db.entities;

import java.math.BigDecimal;
import java.util.Date;

public class FcsHistoTipoactcostefijo extends FcsHistoTipoactcostefijoKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.IMPORTE
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	private BigDecimal importe;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.FECHACREACION
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	private Date fechacreacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.FECHAMODIFICACION
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	private Date fechamodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.USUMODIFICACION
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	private Integer usumodificacion;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.IMPORTE
	 * @return  the value of USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.IMPORTE
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.IMPORTE
	 * @param importe  the value for USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.IMPORTE
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.FECHACREACION
	 * @return  the value of USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.FECHACREACION
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public Date getFechacreacion() {
		return fechacreacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.FECHACREACION
	 * @param fechacreacion  the value for USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.FECHACREACION
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.FECHAMODIFICACION
	 * @return  the value of USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.FECHAMODIFICACION
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.FECHAMODIFICACION
	 * @param fechamodificacion  the value for USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.FECHAMODIFICACION
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.USUMODIFICACION
	 * @return  the value of USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.USUMODIFICACION
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public Integer getUsumodificacion() {
		return usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.USUMODIFICACION
	 * @param usumodificacion  the value for USCGAE.FCS_HISTO_TIPOACTCOSTEFIJO.USUMODIFICACION
	 * @mbg.generated  Thu Dec 19 19:42:15 CET 2019
	 */
	public void setUsumodificacion(Integer usumodificacion) {
		this.usumodificacion = usumodificacion;
	}
}