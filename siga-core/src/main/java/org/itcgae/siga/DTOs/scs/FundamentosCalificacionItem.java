package org.itcgae.siga.DTOs.scs;

public class FundamentosCalificacionItem {

	private String idFundamento;
	private String codigo;
	private String codigoEjis;
	private String descripcionFundamento;
	private String fechabaja;
	private String idTipoDictamenEjg;
	private String idInstitucion;
	private String descripcionDictamen;
	private String textoEnPlantilla;
	private Boolean historico;
	private String idDescripcion;
	
	
	
	public FundamentosCalificacionItem() {
		super();
	}
	
	
	
	public String getIdDescripcion() {
		return idDescripcion;
	}



	public void setIdDescripcion(String idDescripcion) {
		this.idDescripcion = idDescripcion;
	}



	public String getFechabaja() {
		return fechabaja;
	}



	public void setFechabaja(String fechabaja) {
		this.fechabaja = fechabaja;
	}



	public Boolean getHistorico() {
		return historico;
	}



	public void setHistorico(Boolean historico) {
		this.historico = historico;
	}



	public String getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getIdFundamento() {
		return idFundamento;
	}
	public void setIdFundamento(String idFundamento) {
		this.idFundamento = idFundamento;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCodigoEjis() {
		return codigoEjis;
	}
	public void setCodigoEjis(String codigoEjis) {
		this.codigoEjis = codigoEjis;
	}
	public String getDescripcionFundamento() {
		return descripcionFundamento;
	}
	public void setDescripcionFundamento(String descripcionFundamento) {
		this.descripcionFundamento = descripcionFundamento;
	}
	public String getFechaBaja() {
		return fechabaja;
	}
	public void setFechaBaja(String fechaBaja) {
		this.fechabaja = fechaBaja;
	}
	public String getIdTipoDictamenEjg() {
		return idTipoDictamenEjg;
	}
	public void setIdTipoDictamenEjg(String idTipoDictamenEjg) {
		this.idTipoDictamenEjg = idTipoDictamenEjg;
	}
	public String getDescripcionDictamen() {
		return descripcionDictamen;
	}
	public void setDescripcionDictamen(String descripcionDictamen) {
		this.descripcionDictamen = descripcionDictamen;
	}
	public String getTextoEnPlantilla() {
		return textoEnPlantilla;
	}
	public void setTextoEnPlantilla(String textoEnPlantilla) {
		this.textoEnPlantilla = textoEnPlantilla;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idFundamento == null) ? 0 : idFundamento.hashCode());
		result = prime * result + ((idTipoDictamenEjg == null) ? 0 : idTipoDictamenEjg.hashCode());
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
		FundamentosCalificacionItem other = (FundamentosCalificacionItem) obj;
		if (idFundamento == null) {
			if (other.idFundamento != null)
				return false;
		} else if (!idFundamento.equals(other.idFundamento))
			return false;
		if (idTipoDictamenEjg == null) {
			if (other.idTipoDictamenEjg != null)
				return false;
		} else if (!idTipoDictamenEjg.equals(other.idTipoDictamenEjg))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FundamentosCalificacionItem [idFundamento=" + idFundamento + ", codigo=" + codigo + ", codigoEjis="
				+ codigoEjis + ", descripcionFundamento=" + descripcionFundamento + ", fechaBaja=" + fechabaja
				+ ", idTipoDictamenEjg=" + idTipoDictamenEjg + ", descripcionDictamen=" + descripcionDictamen
				+ ", textoEnPlantilla=" + textoEnPlantilla + "]";
	}

	
}
