package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

public class ConstructorConsultasDTO {
	
	private List <ConstructorConsultasItem> constructorConsultasItem = new ArrayList <ConstructorConsultasItem>();
	private String consulta;
	
	private Error error = null;
	
	public String getConsulta() {
		return consulta;
	}
	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}

	
	public List<ConstructorConsultasItem> getConstructorConsultasItem() {
		return constructorConsultasItem;
	}
	public void setConstructorConsultasItem(List<ConstructorConsultasItem> constructorConsultasItem) {
		this.constructorConsultasItem = constructorConsultasItem;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(constructorConsultasItem, consulta, error);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConstructorConsultasDTO other = (ConstructorConsultasDTO) obj;
		return Objects.equals(constructorConsultasItem, other.constructorConsultasItem)
				&& Objects.equals(consulta, other.consulta) && Objects.equals(error, other.error);
	}
	
	@Override
	public String toString() {
		return "ConstructorConsultasDTO [constructorConsultasItem=" + constructorConsultasItem + ", consulta="
				+ consulta + ", error=" + error + "]";
	}	

}
