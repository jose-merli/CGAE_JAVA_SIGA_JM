package org.itcgae.siga.db.entities;

import java.math.BigDecimal;
import java.util.Date;

public class FacPagoabonoefectivo extends FacPagoabonoefectivoKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_INT.FAC_PAGOABONOEFECTIVO.FECHA
	 * @mbg.generated  Mon Feb 14 08:33:59 CET 2022
	 */
	private Date fecha;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_INT.FAC_PAGOABONOEFECTIVO.FECHAMODIFICACION
	 * @mbg.generated  Mon Feb 14 08:33:59 CET 2022
	 */
	private Date fechamodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_INT.FAC_PAGOABONOEFECTIVO.USUMODIFICACION
	 * @mbg.generated  Mon Feb 14 08:33:59 CET 2022
	 */
	private Integer usumodificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_INT.FAC_PAGOABONOEFECTIVO.CONTABILIZADO
	 * @mbg.generated  Mon Feb 14 08:33:59 CET 2022
	 */
	private String contabilizado;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_INT.FAC_PAGOABONOEFECTIVO.IMPORTE
	 * @mbg.generated  Mon Feb 14 08:33:59 CET 2022
	 */
	private BigDecimal importe;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USCGAE_INT.FAC_PAGOABONOEFECTIVO.OBSERVACIONES
	 * @mbg.generated  Mon Feb 14 08:33:59 CET 2022
	 */
	private String observaciones;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_INT.FAC_PAGOABONOEFECTIVO.FECHA
	 * @return  the value of USCGAE_INT.FAC_PAGOABONOEFECTIVO.FECHA
	 * @mbg.generated  Mon Feb 14 08:33:59 CET 2022
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_INT.FAC_PAGOABONOEFECTIVO.FECHA
	 * @param fecha  the value for USCGAE_INT.FAC_PAGOABONOEFECTIVO.FECHA
	 * @mbg.generated  Mon Feb 14 08:33:59 CET 2022
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_INT.FAC_PAGOABONOEFECTIVO.FECHAMODIFICACION
	 * @return  the value of USCGAE_INT.FAC_PAGOABONOEFECTIVO.FECHAMODIFICACION
	 * @mbg.generated  Mon Feb 14 08:33:59 CET 2022
	 */
	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_INT.FAC_PAGOABONOEFECTIVO.FECHAMODIFICACION
	 * @param fechamodificacion  the value for USCGAE_INT.FAC_PAGOABONOEFECTIVO.FECHAMODIFICACION
	 * @mbg.generated  Mon Feb 14 08:33:59 CET 2022
	 */
	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_INT.FAC_PAGOABONOEFECTIVO.USUMODIFICACION
	 * @return  the value of USCGAE_INT.FAC_PAGOABONOEFECTIVO.USUMODIFICACION
	 * @mbg.generated  Mon Feb 14 08:33:59 CET 2022
	 */
	public Integer getUsumodificacion() {
		return usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_INT.FAC_PAGOABONOEFECTIVO.USUMODIFICACION
	 * @param usumodificacion  the value for USCGAE_INT.FAC_PAGOABONOEFECTIVO.USUMODIFICACION
	 * @mbg.generated  Mon Feb 14 08:33:59 CET 2022
	 */
	public void setUsumodificacion(Integer usumodificacion) {
		this.usumodificacion = usumodificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_INT.FAC_PAGOABONOEFECTIVO.CONTABILIZADO
	 * @return  the value of USCGAE_INT.FAC_PAGOABONOEFECTIVO.CONTABILIZADO
	 * @mbg.generated  Mon Feb 14 08:33:59 CET 2022
	 */
	public String getContabilizado() {
		return contabilizado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_INT.FAC_PAGOABONOEFECTIVO.CONTABILIZADO
	 * @param contabilizado  the value for USCGAE_INT.FAC_PAGOABONOEFECTIVO.CONTABILIZADO
	 * @mbg.generated  Mon Feb 14 08:33:59 CET 2022
	 */
	public void setContabilizado(String contabilizado) {
		this.contabilizado = contabilizado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_INT.FAC_PAGOABONOEFECTIVO.IMPORTE
	 * @return  the value of USCGAE_INT.FAC_PAGOABONOEFECTIVO.IMPORTE
	 * @mbg.generated  Mon Feb 14 08:33:59 CET 2022
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_INT.FAC_PAGOABONOEFECTIVO.IMPORTE
	 * @param importe  the value for USCGAE_INT.FAC_PAGOABONOEFECTIVO.IMPORTE
	 * @mbg.generated  Mon Feb 14 08:33:59 CET 2022
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USCGAE_INT.FAC_PAGOABONOEFECTIVO.OBSERVACIONES
	 * @return  the value of USCGAE_INT.FAC_PAGOABONOEFECTIVO.OBSERVACIONES
	 * @mbg.generated  Mon Feb 14 08:33:59 CET 2022
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USCGAE_INT.FAC_PAGOABONOEFECTIVO.OBSERVACIONES
	 * @param observaciones  the value for USCGAE_INT.FAC_PAGOABONOEFECTIVO.OBSERVACIONES
	 * @mbg.generated  Mon Feb 14 08:33:59 CET 2022
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}