package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

public class QueryBuilderDTO {
	
	private String consulta;
	private String idconsulta;
	private List <QueryBuilderItem> rules = new ArrayList <QueryBuilderItem>();
	private Error error = null;
	
	public String getConsulta() {
		return consulta;
	}
	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}
	public String getIdconsulta() {
		return idconsulta;
	}
	public void setIdconsulta(String idconsulta) {
		this.idconsulta = idconsulta;
	}
	public List<QueryBuilderItem> getRules() {
		return rules;
	}
	public void setRules(List<QueryBuilderItem> rules) {
		this.rules = rules;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	@Override
	public int hashCode() {
		return Objects.hash(consulta, error, idconsulta, rules);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryBuilderDTO other = (QueryBuilderDTO) obj;
		return Objects.equals(consulta, other.consulta) && Objects.equals(error, other.error)
				&& Objects.equals(idconsulta, other.idconsulta) && Objects.equals(rules, other.rules);
	}
	@Override
	public String toString() {
		return "QueryBuilderDTO [consulta=" + consulta + ", idconsulta=" + idconsulta + ", rules=" + rules + ", error="
				+ error + "]";
	}
	
	
	
	
	
	
}
