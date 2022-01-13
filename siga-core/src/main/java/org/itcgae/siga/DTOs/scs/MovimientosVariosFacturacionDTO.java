package org.itcgae.siga.DTOs.scs;

import java.util.ArrayList;
import java.util.List;

import org.itcgae.siga.DTOs.gen.Error;

public class MovimientosVariosFacturacionDTO {
	
	private List<MovimientosVariosFacturacionItem>  facturacionItem = new ArrayList<MovimientosVariosFacturacionItem>();
	private Error error = null;
	
	public MovimientosVariosFacturacionDTO MovimientosVariosFacturacionItem(List<MovimientosVariosFacturacionItem> movimientosVariosFacturacionItem){
		this.facturacionItem = movimientosVariosFacturacionItem;
		return this;
	}
	
	
	public List<MovimientosVariosFacturacionItem> getFacturacionItem() {
		return facturacionItem;
	}

	public void setFacturacionItem(List<MovimientosVariosFacturacionItem> facturacionItem) {
		this.facturacionItem = facturacionItem;
	}


	public MovimientosVariosFacturacionDTO error(Error error){
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + ((facturacionItem == null) ? 0 : facturacionItem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		MovimientosVariosFacturacionDTO other = (MovimientosVariosFacturacionDTO) obj;
		
		if (error == null) {
			if (other.error != null) {
				return false;
			}
		} else if (!error.equals(other.error)) {
			return false;
		}
		
		if (facturacionItem == null) {
			if (other.facturacionItem != null) {
				return false;
			}
		} else if (!facturacionItem.equals(other.facturacionItem)) {
			return false;
		}
		
		return true;
	}	

}
