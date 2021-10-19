package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.itcgae.siga.DTOs.gen.Error;

public class QueryBuilderDTO {
	
	private String condition;
	private List <QueryBuilderItem> rules = new ArrayList <QueryBuilderItem>();
	private Error error = null;
	
	public List<QueryBuilderItem> getRules() {
		return rules;
	}
	public void setRules(List<QueryBuilderItem> rules) {
		this.rules = rules;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(condition, error, rules);
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
		return Objects.equals(condition, other.condition) && Objects.equals(error, other.error)
				&& Objects.equals(rules, other.rules);
	}
	
	@Override
	public String toString() {
		return "QueryBuilderDTO [condition=" + condition + ", rules=" + rules + ", error=" + error + "]";
	}

	
}
