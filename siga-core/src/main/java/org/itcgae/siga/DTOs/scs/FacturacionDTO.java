package org.itcgae.siga.DTOs.scs;

import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;

public class FacturacionDTO {

	private List<FacturacionItem>  facturacionItem = new ArrayList<FacturacionItem>();
	private Error error = null;
	
	public FacturacionDTO FacturacionItem(List<FacturacionItem> FacturacionItem){
		this.facturacionItem = FacturacionItem;
		return this;
	}
	
	public List<FacturacionItem> getFacturacionItem() {
		return facturacionItem;
	}
	
	public void setFacturacionItem(List<FacturacionItem> FacturacionItem) {
		this.facturacionItem = FacturacionItem;
	}
	
	public FacturacionDTO error(Error error){
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
		
		FacturacionDTO other = (FacturacionDTO) obj;
		
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