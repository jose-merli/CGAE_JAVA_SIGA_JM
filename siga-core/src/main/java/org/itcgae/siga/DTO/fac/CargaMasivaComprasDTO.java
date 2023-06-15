package org.itcgae.siga.DTO.fac;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CargaMasivaComprasDTO {
		private List<CargaMasivaComprasItem> cargaMasivaComprasItem = new ArrayList<CargaMasivaComprasItem>();
		private Error error = null;
		
		@JsonProperty("cargaMasivaComprasItem")
		public List<CargaMasivaComprasItem> getCargaMasivaComprasItem() {
			return cargaMasivaComprasItem;
		}
		
		public void setCargaMasivaComprasItem(List<CargaMasivaComprasItem> cargaMasivaComprasItem) {
			this.cargaMasivaComprasItem = cargaMasivaComprasItem;
		}
		
		@JsonProperty("error")
		public Error getError() {
			return error;
		}
		
		public void setError(Error error) {
			this.error = error;
		}

}
