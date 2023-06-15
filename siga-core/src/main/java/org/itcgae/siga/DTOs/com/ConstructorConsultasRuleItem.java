package org.itcgae.siga.DTOs.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConstructorConsultasRuleItem {
	List<ConstructorConsultasRuleDTO> rules = new ArrayList<ConstructorConsultasRuleDTO>();
	
	public List<ConstructorConsultasRuleDTO> getRules() {
		return rules;
	}

	public void setRules(List<ConstructorConsultasRuleDTO> rules) {
		this.rules = rules;
	}

	String label;
	String field;
	String operator;
	String type;
	String value;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(field, label, operator, rules, type, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConstructorConsultasRuleItem other = (ConstructorConsultasRuleItem) obj;
		return Objects.equals(field, other.field) && Objects.equals(label, other.label)
				&& Objects.equals(operator, other.operator) && Objects.equals(rules, other.rules)
				&& Objects.equals(type, other.type) && Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return "ConstructorConsultasRuleItem [rules=" + rules + ", label=" + label + ", field=" + field + ", operator="
				+ operator + ", type=" + type + ", value=" + value + "]";
	}

}
