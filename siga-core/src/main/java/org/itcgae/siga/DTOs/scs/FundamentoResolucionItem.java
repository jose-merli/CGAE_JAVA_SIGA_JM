package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FundamentoResolucionItem {

	private String idFundamento;
	private String descripcionFundamento;
	private String recursoFundamento;
	private String descripcionResolucion;
	private String idTipoResolucion;
	private String idInstitucion;
	private Long usuModificacion;
	private Date fechaModificacion;
	private String textoPlantilla;
	private String textoPlantilla2;
	private String textoPlantilla3;
	private String textoPlantilla4;
	private boolean historico;
	private String fechaBaja;
	private String codigoExt;
	private String codigoEjis;

	
	@JsonProperty("idFundamento")
	public String getIdFundamento() {
		return idFundamento;
	}



	public void setIdFundamento(String idFundamento) {
		this.idFundamento = idFundamento;
	}


	@JsonProperty("descripcionFundamento")
	public String getDescripcionFundamento() {
		return descripcionFundamento;
	}


	public void setDescripcionFundamento(String descripcionFundamento) {
		this.descripcionFundamento = descripcionFundamento;
	}

	@JsonProperty("recursoFundamento")
	public String getRecursoFundamento() {
		return recursoFundamento;
	}

	public void setRecursoFundamento(String recursoFundamento) {
		this.recursoFundamento = recursoFundamento;
	}

	@JsonProperty("descripcionResolucion")
	public String getDescripcionResolucion() {
		return descripcionResolucion;
	}

	public void setDescripcionResolucion(String descripcionResolucion) {
		this.descripcionResolucion = descripcionResolucion;
	}

	@JsonProperty("idTipoResolucion")
	public String getIdTipoResolucion() {
		return idTipoResolucion;
	}

	public void setIdTipoResolucion(String idTipoResolucion) {
		this.idTipoResolucion = idTipoResolucion;
	}


	@JsonProperty("idInstitucion")
	public String getIdInstitucion() {
		return idInstitucion;
	}



	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}


	@JsonProperty("usuModificacion")
	public Long getUsuModificacion() {
		return usuModificacion;
	}



	public void setUsuModificacion(Long usuModificacion) {
		this.usuModificacion = usuModificacion;
	}


	@JsonProperty("fechaModificacion")
	public Date getFechaModificacion() {
		return fechaModificacion;
	}



	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}


	@JsonProperty("textoPlantilla")
	public String getTextoPlantilla() {
		return textoPlantilla;
	}



	public void setTextoPlantilla(String textoPlantilla) {
		this.textoPlantilla = textoPlantilla;
	}


	@JsonProperty("textoPlantilla2")
	public String getTextoPlantilla2() {
		return textoPlantilla2;
	}



	public void setTextoPlantilla2(String textoPlantilla2) {
		this.textoPlantilla2 = textoPlantilla2;
	}


	@JsonProperty("textoPlantilla3")
	public String getTextoPlantilla3() {
		return textoPlantilla3;
	}



	public void setTextoPlantilla3(String textoPlantilla3) {
		this.textoPlantilla3 = textoPlantilla3;
	}


	@JsonProperty("textoPlantilla4")
	public String getTextoPlantilla4() {
		return textoPlantilla4;
	}



	public void setTextoPlantilla4(String textoPlantilla4) {
		this.textoPlantilla4 = textoPlantilla4;
	}


	@JsonProperty("historico")
	public boolean isHistorico() {
		return historico;
	}



	public void setHistorico(boolean historico) {
		this.historico = historico;
	}


	@JsonProperty("fechaBaja")
	public String getFechaBaja() {
		return fechaBaja;
	}



	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}


	@JsonProperty("codigoExt")
	public String getCodigoExt() {
		return codigoExt;
	}



	public void setCodigoExt(String codigoExt) {
		this.codigoExt = codigoExt;
	}


	@JsonProperty("codigoEjis")
	public String getCodigoEjis() {
		return codigoEjis;
	}



	public void setCodigoEjis(String codigoEjis) {
		this.codigoEjis = codigoEjis;
	}



	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		FundamentoResolucionItem fundamentoResolucionItem = (FundamentoResolucionItem) o;
		return Objects.equals(this.idFundamento, fundamentoResolucionItem.idFundamento)
				&& Objects.equals(this.idInstitucion, fundamentoResolucionItem.idInstitucion)
				&& Objects.equals(this.codigoEjis, fundamentoResolucionItem.codigoEjis)
				&& Objects.equals(this.usuModificacion, fundamentoResolucionItem.usuModificacion)
				&& Objects.equals(this.fechaModificacion, fundamentoResolucionItem.fechaModificacion)
				&& Objects.equals(this.descripcionFundamento, fundamentoResolucionItem.descripcionFundamento)
				&& Objects.equals(this.idInstitucion, fundamentoResolucionItem.idInstitucion)
				&& Objects.equals(this.historico, fundamentoResolucionItem.historico)
				&& Objects.equals(this.codigoExt, fundamentoResolucionItem.codigoExt)
				&& Objects.equals(this.textoPlantilla, fundamentoResolucionItem.textoPlantilla)
				&& Objects.equals(this.textoPlantilla2, fundamentoResolucionItem.textoPlantilla2)
				&& Objects.equals(this.textoPlantilla3, fundamentoResolucionItem.textoPlantilla3)
				&& Objects.equals(this.textoPlantilla4, fundamentoResolucionItem.textoPlantilla4)
				&& Objects.equals(this.fechaBaja, fundamentoResolucionItem.fechaBaja)
				&& Objects.equals(this.recursoFundamento, fundamentoResolucionItem.recursoFundamento);

	}


	
	
	@Override
	public int hashCode() {
		return Objects.hash(idFundamento, descripcionFundamento, textoPlantilla, textoPlantilla2, usuModificacion, fechaModificacion, textoPlantilla3,
				textoPlantilla4, idInstitucion, historico, fechaBaja, codigoExt, recursoFundamento);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class FundamentoResolucionItem {\n");

		sb.append("    idFundamento: ").append(toIndentedString(idFundamento)).append("\n");
		sb.append("    textoenPlantilla: ").append(toIndentedString(textoPlantilla)).append("\n");
		sb.append("    textoenPlantilla2: ").append(toIndentedString(textoPlantilla2)).append("\n");
		sb.append("    textoenPlantilla3: ").append(toIndentedString(textoPlantilla3)).append("\n");
		sb.append("    textoenPlantilla4: ").append(toIndentedString(textoPlantilla4)).append("\n");
		sb.append("    usuModificacion: ").append(toIndentedString(usuModificacion)).append("\n");
		sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
		sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
		sb.append("    historico: ").append(toIndentedString(historico)).append("\n");
		sb.append("    fechabaja: ").append(toIndentedString(fechaBaja)).append("\n");
		sb.append("    codigoExt: ").append(toIndentedString(codigoExt)).append("\n");
		sb.append("    codigoEjis: ").append(toIndentedString(codigoEjis)).append("\n");
		sb.append("    descripcionFundamento: ").append(toIndentedString(descripcionFundamento)).append("\n");
		sb.append("    recursoFundamento: ").append(toIndentedString(recursoFundamento)).append("\n");


		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
