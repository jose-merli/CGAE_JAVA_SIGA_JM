package org.itcgae.siga.DTO.fac;

import java.util.Date;

public class FicherosDevolucionesItem {

	String idInstitucion;
	String idDisqueteDevoluciones;
	Date fechaCreacion;
	String nombreFichero;
	Date fechaUltimaModificacion;
	String bancosCodigo;

	String cuentaEntidad;
	Date fechaCreacionDesde;
	Date fechaCreacionHasta;
	String importeTotalDesde;
	String importeTotalHasta;
	String numRecibosDesde;
	String numRecibosHasta;

	String facturacion;
	String numRecibos;

	/**
	 * @return the idInstitucion
	 */
	public String getIdInstitucion() {
		return idInstitucion;
	}
	/**
	 * @param idInstitucion the idInstitucion to set
	 */
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	/**
	 * @return the idDisqueteCargos
	 */
	public String getIdDisqueteDevoluciones() {
		return idDisqueteDevoluciones;
	}
	/**
	 * @param idDisqueteDevoluciones the idDisqueteCargos to set
	 */
	public void setIdDisqueteDevoluciones(String idDisqueteDevoluciones) {
		this.idDisqueteDevoluciones = idDisqueteDevoluciones;
	}
	/**
	 * @return the nombreFichero
	 */
	public String getNombreFichero() {
		return nombreFichero;
	}
	/**
	 * @param nombreFichero the nombreFichero to set
	 */
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	/**
	 * @return the bancosCodigo
	 */
	public String getBancosCodigo() {
		return bancosCodigo;
	}
	/**
	 * @param bancosCodigo the bancosCodigo to set
	 */
	public void setBancosCodigo(String bancosCodigo) {
		this.bancosCodigo = bancosCodigo;
	}
	/**
	 * @return the cuentaEntidad
	 */
	public String getCuentaEntidad() {
		return cuentaEntidad;
	}
	/**
	 * @param cuentaEntidad the cuentaEntidad to set
	 */
	public void setCuentaEntidad(String cuentaEntidad) {
		this.cuentaEntidad = cuentaEntidad;
	}
	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	/**
	 * @return the fechaCreacionDesde
	 */
	public Date getFechaCreacionDesde() {
		return fechaCreacionDesde;
	}
	/**
	 * @param fechaCreacionDesde the fechaCreacionDesde to set
	 */
	public void setFechaCreacionDesde(Date fechaCreacionDesde) {
		this.fechaCreacionDesde = fechaCreacionDesde;
	}
	/**
	 * @return the fechaCreacionHasta
	 */
	public Date getFechaCreacionHasta() {
		return fechaCreacionHasta;
	}
	/**
	 * @param fechaCreacionHasta the fechaCreacionHasta to set
	 */
	public void setFechaCreacionHasta(Date fechaCreacionHasta) {
		this.fechaCreacionHasta = fechaCreacionHasta;
	}
	/**
	 * @return the importeTotalDesde
	 */
	public String getImporteTotalDesde() {
		return importeTotalDesde;
	}
	/**
	 * @param importeTotalDesde the importeTotalDesde to set
	 */
	public void setImporteTotalDesde(String importeTotalDesde) {
		this.importeTotalDesde = importeTotalDesde;
	}
	/**
	 * @return the importeTotalHasta
	 */
	public String getImporteTotalHasta() {
		return importeTotalHasta;
	}
	/**
	 * @param importeTotalHasta the importeTotalHasta to set
	 */
	public void setImporteTotalHasta(String importeTotalHasta) {
		this.importeTotalHasta = importeTotalHasta;
	}
	/**
	 * @return the numRecibos
	 */
	public String getNumRecibos() {
		return numRecibos;
	}
	/**
	 * @param numRecibos the numRecibos to set
	 */
	public void setNumRecibos(String numRecibos) {
		this.numRecibos = numRecibos;
	}
	/**
	 * @return the numRecibosDesde
	 */
	public String getNumRecibosDesde() {
		return numRecibosDesde;
	}
	/**
	 * @param numRecibosDesde the numRecibosDesde to set
	 */
	public void setNumRecibosDesde(String numRecibosDesde) {
		this.numRecibosDesde = numRecibosDesde;
	}
	/**
	 * @return the numRecibosHasta
	 */
	public String getNumRecibosHasta() {
		return numRecibosHasta;
	}
	/**
	 * @param numRecibosHasta the numRecibosHasta to set
	 */
	public void setNumRecibosHasta(String numRecibosHasta) {
		this.numRecibosHasta = numRecibosHasta;
	}
	/**
	 * @return the facturacion
	 */
	public String getFacturacion() {
		return facturacion;
	}
	
	/**
	 * @param facturacion the facturacion to set
	 */
	public void setFacturacion(String facturacion) {
		this.facturacion = facturacion;
	}
	/**
	 * @return the fechaUltimaModificacion
	 */
	public Date getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}
	/**
	 * @param fechaUltimaModificacion the fechaUltimaModificacion to set
	 */
	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bancosCodigo == null) ? 0 : bancosCodigo.hashCode());
		result = prime * result + ((cuentaEntidad == null) ? 0 : cuentaEntidad.hashCode());
		result = prime * result + ((facturacion == null) ? 0 : facturacion.hashCode());
		result = prime * result + ((fechaCreacion == null) ? 0 : fechaCreacion.hashCode());
		result = prime * result + ((fechaCreacionDesde == null) ? 0 : fechaCreacionDesde.hashCode());
		result = prime * result + ((fechaCreacionHasta == null) ? 0 : fechaCreacionHasta.hashCode());
		result = prime * result + ((fechaUltimaModificacion == null) ? 0 : fechaUltimaModificacion.hashCode());
		result = prime * result + ((idDisqueteDevoluciones == null) ? 0 : idDisqueteDevoluciones.hashCode());
		result = prime * result + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
		result = prime * result + ((importeTotalDesde == null) ? 0 : importeTotalDesde.hashCode());
		result = prime * result + ((importeTotalHasta == null) ? 0 : importeTotalHasta.hashCode());
		result = prime * result + ((nombreFichero == null) ? 0 : nombreFichero.hashCode());
		result = prime * result + ((numRecibos == null) ? 0 : numRecibos.hashCode());
		result = prime * result + ((numRecibosDesde == null) ? 0 : numRecibosDesde.hashCode());
		result = prime * result + ((numRecibosHasta == null) ? 0 : numRecibosHasta.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FicherosDevolucionesItem other = (FicherosDevolucionesItem) obj;
		if (bancosCodigo == null) {
			if (other.bancosCodigo != null)
				return false;
		} else if (!bancosCodigo.equals(other.bancosCodigo))
			return false;
		if (cuentaEntidad == null) {
			if (other.cuentaEntidad != null)
				return false;
		} else if (!cuentaEntidad.equals(other.cuentaEntidad))
			return false;
		if (facturacion == null) {
			if (other.facturacion != null)
				return false;
		} else if (!facturacion.equals(other.facturacion))
			return false;
		if (fechaCreacion == null) {
			if (other.fechaCreacion != null)
				return false;
		} else if (!fechaCreacion.equals(other.fechaCreacion))
			return false;
		if (fechaCreacionDesde == null) {
			if (other.fechaCreacionDesde != null)
				return false;
		} else if (!fechaCreacionDesde.equals(other.fechaCreacionDesde))
			return false;
		if (fechaCreacionHasta == null) {
			if (other.fechaCreacionHasta != null)
				return false;
		} else if (!fechaCreacionHasta.equals(other.fechaCreacionHasta))
			return false;
		if (fechaUltimaModificacion == null) {
			if (other.fechaUltimaModificacion != null)
				return false;
		} else if (!fechaUltimaModificacion.equals(other.fechaUltimaModificacion))
			return false;
		if (idDisqueteDevoluciones == null) {
			if (other.idDisqueteDevoluciones != null)
				return false;
		} else if (!idDisqueteDevoluciones.equals(other.idDisqueteDevoluciones))
			return false;
		if (idInstitucion == null) {
			if (other.idInstitucion != null)
				return false;
		} else if (!idInstitucion.equals(other.idInstitucion))
			return false;
		if (importeTotalDesde == null) {
			if (other.importeTotalDesde != null)
				return false;
		} else if (!importeTotalDesde.equals(other.importeTotalDesde))
			return false;
		if (importeTotalHasta == null) {
			if (other.importeTotalHasta != null)
				return false;
		} else if (!importeTotalHasta.equals(other.importeTotalHasta))
			return false;
		if (nombreFichero == null) {
			if (other.nombreFichero != null)
				return false;
		} else if (!nombreFichero.equals(other.nombreFichero))
			return false;
		if (numRecibos == null) {
			if (other.numRecibos != null)
				return false;
		} else if (!numRecibos.equals(other.numRecibos))
			return false;
		if (numRecibosDesde == null) {
			if (other.numRecibosDesde != null)
				return false;
		} else if (!numRecibosDesde.equals(other.numRecibosDesde))
			return false;
		if (numRecibosHasta == null) {
			if (other.numRecibosHasta != null)
				return false;
		} else if (!numRecibosHasta.equals(other.numRecibosHasta))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FicherosDevolucionesItem [idInstitucion=" + idInstitucion + ", idDisqueteDevoluciones=" + idDisqueteDevoluciones
				+ ", nombreFichero=" + nombreFichero + ", bancosCodigo=" + bancosCodigo + ", cuentaEntidad="
				+ cuentaEntidad + ", fechaCreacion=" + fechaCreacion + ", fechaCreacionDesde="
				+ fechaCreacionDesde + ", fechaCreacionHasta=" + fechaCreacionHasta + ", idseriefacturacion="
				+ ", importeTotalDesde=" + importeTotalDesde + ", importeTotalHasta=" + importeTotalHasta
				+ ", numRecibos=" + numRecibos + ", numRecibosDesde=" + numRecibosDesde + ", numRecibosHasta="
				+ numRecibosHasta + ", facturacion=" + facturacion + ", fechaUltimaModificacion=" + fechaUltimaModificacion + "]";
	}
}