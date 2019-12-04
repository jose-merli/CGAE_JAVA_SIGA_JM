package org.itcgae.siga.DTOs.scs;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RetencionIRPFItem {

		private String idRetencion;
		private String descripcion;
		private String idDescripcion;
		private String claveModelo;
		private String tipoSociedad;
		private String descripcionSociedad;


		private String retencion;
		private String idInstitucion;
		private Long usuModificacion;
		private Date fechaModificacion;
		private boolean historico;
		private Date fechabaja;
		
		
		@JsonProperty("descripcionSociedad")
		public String getDescripcionSociedad() {
			return descripcionSociedad;
		}

		public void setDescripcionSociedad(String descripcionSociedad) {
			this.descripcionSociedad = descripcionSociedad;
		}
		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion= descripcion;
		}

		public String getIdDescripcion() {
			return idDescripcion;
		}

		public void setIdDescripcion(String idDescripcion) {
			this.idDescripcion= idDescripcion;
		}

		@JsonProperty("idRetencion")
		public String getIdRetencion() {
			return idRetencion;
		}

		public void setIdRetencion(String idRetencion) {
			this.idRetencion= idRetencion;
		}

		@JsonProperty("claveModelo")
		public String getClaveModelo() {
			return claveModelo;
		}

		public void setClaveModelo(String claveModelo) {
			this.claveModelo = claveModelo;
		}
		
		@JsonProperty("tipoSociedad")
		public String getTipoSociedad() {
			return tipoSociedad;
		}

		public void setTipoSociedad(String tipoSociedad) {
			this.tipoSociedad = tipoSociedad;
		}

		@JsonProperty("retencion")
		public String getRetencion() {
			return retencion;
		}

		public void setRetencion(String retencion) {
			this.retencion = retencion;
		}
		
		@JsonProperty("fechabaja")
		public Date getFechaBaja() {
			return fechabaja;
		}

		public void setFechaBaja(Date fechaBaja) {
			this.fechabaja = fechaBaja;
		}

		public RetencionIRPFItem idInstitucion(String idInstitucion) {
			this.idInstitucion = idInstitucion;
			return this;
		}

		@JsonProperty("idInstitucion")
		public String getIdInstitucion() {
			return idInstitucion;
		}

		public void setIdInstitucion(String idInstitucion) {
			this.idInstitucion = idInstitucion;
		}
		
		public RetencionIRPFItem usuModificacion(Long usuModificacion) {
			this.usuModificacion = usuModificacion;
			return this;
		}

		@JsonProperty("usuModificacion")
		public Long getUsuModificacion() {
			return usuModificacion;
		}

		public void setUsuModificacion(Long usuModificacion) {
			this.usuModificacion = usuModificacion;
		}

		/**
		 * 
		 **/
		public RetencionIRPFItem fechaModificacion(Date fechaModificacion) {
			this.fechaModificacion = fechaModificacion;
			return this;
		}

		@JsonProperty("fechaModificacion")
		public Date getFechaModificacion() {
			return fechaModificacion;
		}

		public void setFechaModificacion(Date fechaModificacion) {
			this.fechaModificacion = fechaModificacion;
		}

		public RetencionIRPFItem historico(boolean historico) {
			this.historico = historico;
			return this;
		}

		@JsonProperty("historico")
		public boolean getHistorico() {
			return historico;
		}

		public void setHistorico(boolean historico) {
			this.historico = historico;
		}
		

		@Override
		public boolean equals(java.lang.Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			RetencionIRPFItem retencionItem = (RetencionIRPFItem) o;
			return Objects.equals(this.idRetencion, retencionItem.idRetencion)
					&& Objects.equals(this.usuModificacion, retencionItem.usuModificacion)
					&& Objects.equals(this.fechaModificacion, retencionItem.fechaModificacion)
					&& Objects.equals(this.idInstitucion, retencionItem.idInstitucion)
					&& Objects.equals(this.historico, retencionItem.historico)
					&& Objects.equals(this.fechabaja, retencionItem.fechabaja)
					&& Objects.equals(this.descripcion, retencionItem.descripcion)
					&& Objects.equals(this.tipoSociedad, retencionItem.tipoSociedad)
					&& Objects.equals(this.retencion, retencionItem.retencion)
					&& Objects.equals(this.claveModelo, retencionItem.claveModelo)
					&& Objects.equals(this.idDescripcion, retencionItem.idDescripcion);

		}

		
		
		@Override
		public int hashCode() {
			return Objects.hash(idRetencion, descripcion, idDescripcion, usuModificacion, fechaModificacion, tipoSociedad ,
					retencion, claveModelo, idInstitucion, historico, fechabaja);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("class PrisionItem {\n");

			sb.append("    idRetencion: ").append(toIndentedString(idRetencion)).append("\n");
			sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
			sb.append("    idDescripcion: ").append(toIndentedString(idDescripcion)).append("\n");
			sb.append("    usuModificacion: ").append(toIndentedString(usuModificacion)).append("\n");
			sb.append("    fechaModificacion: ").append(toIndentedString(fechaModificacion)).append("\n");
			sb.append("    claveModelo: ").append(toIndentedString(claveModelo)).append("\n");
			sb.append("    tipoSociedad: ").append(toIndentedString(tipoSociedad)).append("\n");
			sb.append("    retencion: ").append(toIndentedString(retencion)).append("\n");
			sb.append("    idInstitucion: ").append(toIndentedString(idInstitucion)).append("\n");
			sb.append("    historico: ").append(toIndentedString(historico)).append("\n");
			sb.append("    fechabaja: ").append(toIndentedString(fechabaja)).append("\n");
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
