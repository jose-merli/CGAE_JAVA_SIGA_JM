package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

public class QueryBuilderDTO {
	
	private String consulta;
	private String idconsulta;
	private String sentencia;
	private String idinstitucion;
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
	public String getSentencia() {
		return sentencia;
	}
	public void setSentencia(String sentencia) {
		this.sentencia = sentencia;
	}
	public List<QueryBuilderItem> getRules() {
		return rules;
	}
	public void setRules(List<QueryBuilderItem> rules) {
		this.rules = rules;
	}
	public String getIdinstitucion() {
		return idinstitucion;
	}
	public void setIdinstitucion(String idinstitucion) {
		this.idinstitucion = idinstitucion;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	@Override
	public int hashCode() {
		return Objects.hash(consulta, error, idconsulta, idinstitucion, rules, sentencia);
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
				&& Objects.equals(idconsulta, other.idconsulta) && Objects.equals(idinstitucion, other.idinstitucion)
				&& Objects.equals(rules, other.rules) && Objects.equals(sentencia, other.sentencia);
	}
	@Override
	public String toString() {
		return "QueryBuilderDTO [consulta=" + consulta + ", idconsulta=" + idconsulta + ", sentencia=" + sentencia
				+ ", idinstitucion=" + idinstitucion + ", rules=" + rules + ", error=" + error + "]";
	}
	
}
