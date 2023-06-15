package org.itcgae.siga.DTOs.com;

import java.util.Objects;

public class ConfigColumnasQueryBuilderItem {
	private String tipocampo;
	private String idcampo;
	private String nombreenconsulta;
	private String selectayuda;
	
	public String getTipocampo() {
		return tipocampo;
	}
	public void setTipocampo(String tipocampo) {
		this.tipocampo = tipocampo;
	}
	public String getIdcampo() {
		return idcampo;
	}
	public void setIdcampo(String idcampo) {
		this.idcampo = idcampo;
	}
	public String getNombreenconsulta() {
		return nombreenconsulta;
	}
	public void setNombreenconsulta(String nombreenconsulta) {
		this.nombreenconsulta = nombreenconsulta;
	}
	public String getSelectayuda() {
		return selectayuda;
	}
	public void setSelectayuda(String selectayuda) {
		this.selectayuda = selectayuda;
	}
	
	@Override
	public String toString() {
		return "ConfigColumnasQueryBuilderItem [tipocampo=" + tipocampo + ", idcampo=" + idcampo + ", nombreenconsulta="
				+ nombreenconsulta + ", selectayuda=" + selectayuda + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(idcampo, nombreenconsulta, selectayuda, tipocampo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfigColumnasQueryBuilderItem other = (ConfigColumnasQueryBuilderItem) obj;
		return Objects.equals(idcampo, other.idcampo) && Objects.equals(nombreenconsulta, other.nombreenconsulta)
				&& Objects.equals(selectayuda, other.selectayuda) && Objects.equals(tipocampo, other.tipocampo);
	}
	
	
	
	
}
