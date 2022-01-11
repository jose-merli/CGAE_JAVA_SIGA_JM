package org.itcgae.siga.DTO.fac;

import org.itcgae.siga.DTOs.gen.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InformeFacturacionDTO {
	private List<InformeFacturacionItem> informeFacturacionItems = new ArrayList<>();
	private Error error = null;
	
	public List<InformeFacturacionItem> getInformeFacturacion() {
		return informeFacturacionItems;
	}
	
	public void setInformeFacturacion(List<InformeFacturacionItem> estadosPagosItems) {
		this.informeFacturacionItems = estadosPagosItems;
	}
	
	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(error, informeFacturacionItems);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InformeFacturacionDTO other = (InformeFacturacionDTO) obj;
		return Objects.equals(error, other.error) && Objects.equals(informeFacturacionItems, other.informeFacturacionItems);
	}
	
	@Override
	public String toString() {
		return "InformeFacturacionDTO [InformeFacturacionItem=" + informeFacturacionItems + ", error=" + error + "]";
	}
	
}
