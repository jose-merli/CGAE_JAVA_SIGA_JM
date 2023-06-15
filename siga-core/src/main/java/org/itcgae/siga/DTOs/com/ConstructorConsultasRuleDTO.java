package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConstructorConsultasRuleDTO {
	String condition;
	List <ConstructorConsultasRuleItem> rules = new ArrayList<ConstructorConsultasRuleItem>();
	//List<ConstructorConsultasRuleDTO> rules2 = new ArrayList<ConstructorConsultasRuleDTO>();
	
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public List<ConstructorConsultasRuleItem> getRules() {
		return rules;
	}
	public void setRules(List<ConstructorConsultasRuleItem> rules) {
		this.rules = rules;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(condition, rules);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConstructorConsultasRuleDTO other = (ConstructorConsultasRuleDTO) obj;
		return Objects.equals(condition, other.condition) && Objects.equals(rules, other.rules);
	}
	
	@Override
	public String toString() {
		return "ConstructorConsultasRuleDTO [condition=" + condition + ", rules=" + rules + "]";
	}
	
	

}
