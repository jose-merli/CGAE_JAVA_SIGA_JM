package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConstructorConsultasRuleDTO {
	String condition;
	List <ConstructorConsultasRuleItem> rules = new ArrayList<ConstructorConsultasRuleItem>();
	List<ConstructorConsultasRuleDTO> rules2 = new ArrayList<ConstructorConsultasRuleDTO>();
	
	
	
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
	public List<ConstructorConsultasRuleDTO> getRules2() {
		return rules2;
	}
	public void setRules2(List<ConstructorConsultasRuleDTO> rules2) {
		this.rules2 = rules2;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(condition, rules, rules2);
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
		return Objects.equals(condition, other.condition) && Objects.equals(rules, other.rules)
				&& Objects.equals(rules2, other.rules2);
	}
	
	@Override
	public String toString() {
		return "ConstructorConsultasRuleDTO [condition=" + condition + ", rules=" + rules + ", rules2=" + rules2 + "]";
	}
	
}