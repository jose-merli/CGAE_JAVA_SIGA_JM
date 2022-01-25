package org.itcgae.siga.DTOs.cen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DatosLiquidacionIntegrantesSearchDTO {
	
		
		private List<DatosLiquidacionIntegrantesSearchItem> datosLiquidacionItem = new ArrayList<DatosLiquidacionIntegrantesSearchItem>();
		private Error error = null;
		
		
		/**
		 */
		public DatosLiquidacionIntegrantesSearchDTO datosLiquidacionItem(List<DatosLiquidacionIntegrantesSearchItem> datosLiquidacionItem){
			this.datosLiquidacionItem = datosLiquidacionItem;
			return this;
		}
		
		
		public List<DatosLiquidacionIntegrantesSearchItem> getDatosLiquidacionItem() {
			return datosLiquidacionItem;
		}
		public void setDatosLiquidacionItem(List<DatosLiquidacionIntegrantesSearchItem> datosLiquidacionItem) {
			this.datosLiquidacionItem = datosLiquidacionItem;
		}
		
		
		/**
		 */
		public DatosLiquidacionIntegrantesSearchDTO error(Error error){
			this.error = error;
			return this;
		}
		
		public Error getError() {
			return error;
		}
		public void setError(Error error) {
			this.error = error;
		}


		@Override
		public int hashCode() {
			return Objects.hash(datosLiquidacionItem, error);
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			DatosLiquidacionIntegrantesSearchDTO other = (DatosLiquidacionIntegrantesSearchDTO) obj;
			return Objects.equals(datosLiquidacionItem, other.datosLiquidacionItem)
					&& Objects.equals(error, other.error);
		}


		
		
		
		
	}

