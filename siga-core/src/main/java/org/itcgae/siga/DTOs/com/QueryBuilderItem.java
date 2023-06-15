package org.itcgae.siga.DTOs.com;

import java.util.List;
import java.util.Objects;

public class QueryBuilderItem {
	
	private String condition = "";
	
	private String field;
	private String label;
	private String operator;
	private String type;
	private String value;

	private List<QueryBuilderItem> rules;

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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

	public List<QueryBuilderItem> getRules() {
		return rules;
	}

	public void setRules(List<QueryBuilderItem> rules) {
		this.rules = rules;
	}

	@Override
	public int hashCode() {
		return Objects.hash(condition, field, label, operator, rules, type, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryBuilderItem other = (QueryBuilderItem) obj;
		return Objects.equals(condition, other.condition) && Objects.equals(field, other.field)
				&& Objects.equals(label, other.label) && Objects.equals(operator, other.operator)
				&& Objects.equals(rules, other.rules) && Objects.equals(type, other.type)
				&& Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return "QueryBuilderItem [condition=" + condition + ", field=" + field + ", label=" + label + ", operator="
				+ operator + ", type=" + type + ", value=" + value + ", rules=" + rules + "]";
	}
	
}
